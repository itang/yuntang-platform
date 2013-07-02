package main

import (
	"encoding/json"
	"flag"
	"fmt"
	gotang_io "github.com/itang/gotang/io"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
	"strings"
	"sync"
	"time"
)

type Task struct {
	Dir  string
	Sub  bool
	Sync bool
}

type InstallInfo struct {
	StoreFile string
	Data      map[string]int64
}

var (
	wd, _    = os.Getwd()
	lein_cmd = get_lein_cmd()
	wg       sync.WaitGroup
	tasks    = [...]Task{
		{Dir: filepath.Join(wd, "modules/common/core"), Sub: false, Sync: true},
		{filepath.Join(wd, "admin/core"), false, true},
		{filepath.Join(wd, "user/core"), false, true},
		{filepath.Join(wd, "modules/captcha/ui"), true, false},
		{filepath.Join(wd, "modules/common/ui"), true, false},
		{filepath.Join(wd, "admin/ui"), true, false},
		{filepath.Join(wd, "user/ui"), true, false},
		{filepath.Join(wd, "layout/ui"), true, false},
	}
	install_info    = &InstallInfo{StoreFile: ".install-info"}
	installed       = false
	force_reinstall bool
)

func init() {
	runtime.GOMAXPROCS(runtime.NumCPU()*2 - 1)
	flag.BoolVar(&force_reinstall, "f", false, "force reinstall all")
}

func main() {
	flag.Parse()

	println_info()

	run_installs()

	try_reload_app()
}

func run_installs() {
	log.Println("Start lein install ...")
	if force_reinstall {
		install_info.Clear()
	}
	install_info.Load()

	wg.Add(len(tasks))
	for _, task := range tasks {
		task.Run()
	}
	wg.Wait()

	install_info.Record()
	log.Printf("Finish lein install")
}

func try_reload_app() {
	if installed {
		//fmt.Println("\n去手动重启应用吧!@@...")
		log.Println("Try get http://localhost:3000/_dev/reload")
		resp, err := http.Get("http://localhost:3000/_dev/reload")
		if err != nil {
			fmt.Printf("%v\n", err)
		} else {
			fmt.Printf("%v\n", resp)
		}
	}
}

func (task Task) Run() {
	var cmd_args = []string{"install"}
	if task.Sub {
		cmd_args = []string{"sub", "install"}
	}
	if task.Sync {
		run_cmd(task.Dir, lein_cmd, cmd_args...)
	} else {
		go run_cmd(task.Dir, lein_cmd, cmd_args...)
	}
}

type ArrString struct {
	target []string
}

func (self ArrString) Some(predict func(int, string) bool) bool {
	for i, v := range self.target {
		if predict(i, v) {
			return true
		}
	}
	return false
}

func is_ignore_dirs(path string, ignore_dirs []string) bool {
	return ArrString{ignore_dirs}.Some(func(i int, v string) bool {
		return strings.HasSuffix(path, v)
	})
}

func is_monitor_file(path string, exts []string) bool {
	return ArrString{exts}.Some(func(i int, v string) bool {
		return strings.HasSuffix(path, v)
	})
}

func ensure_run(dir string) (ensure bool) {
	last_install := install_info.GetLastInstallTime(dir)
	if last_install == 0 {
		return true
	}
	ensure = false
	filepath.Walk(dir, func(path string, info os.FileInfo, err error) error {
		ignore_dirs := []string{"/target", "/bin", "/.settings", "/classes"}
		if is_ignore_dirs(path, ignore_dirs) {
			return filepath.SkipDir
		}

		monitor_exts := []string{".clj", ".html", ".mustache", ".js", ".cljs", ".css"}
		if is_monitor_file(path, monitor_exts) && info.ModTime().UnixNano() > last_install {
			ensure = true
			return filepath.SkipDir
		}
		return nil
	})
	return
}

func run_cmd(dir string, cmd string, args ...string) {
	if ensure_run(dir) {
		fmt.Printf("cd %s $ lein %s\n", dir, strings.Join(args, " "))
		command := exec.Command(cmd, args...)
		command.Dir = dir
		out, err := command.CombinedOutput()
		if err != nil {
			fmt.Println(err)
		} else {
			fmt.Printf("%s\n", out)
			installed = true
			install_info.UpdateLastInstallTime(dir, time.Now().UnixNano()) //last install time
		}
	} else {
		//fmt.Println(dir, "no need install")
	}
	wg.Done()
}

func get_lein_cmd() string {
	if runtime.GOOS == "windows" {
		return "lein.bat"
	}
	return "lein"
}

func (self *InstallInfo) Load() {
	if !gotang_io.Exists(self.StoreFile) {
		self.create()
	} else {
		self.retrieve()
	}
}

func (self *InstallInfo) Clear() {
	self.Data = nil
	if gotang_io.Exists(self.StoreFile) {
		err := os.Remove(self.StoreFile)
		if err != nil {
			panic(err)
		}
	}
}

func (self *InstallInfo) GetLastInstallTime(dir string) int64 {
	t, ok := self.Data[dir]
	if ok {
		return t
	}
	return 0
}

func (self *InstallInfo) UpdateLastInstallTime(dir string, t int64) {
	self.Data[dir] = t
}

func (self *InstallInfo) Record() {
	self.save()
}

func (self *InstallInfo) retrieve() {
	file, _ := os.Open(self.StoreFile)
	defer file.Close()
	decoder := json.NewDecoder(file)
	decoder.Decode(&self.Data)
}

func (self *InstallInfo) create() {
	self.Data = map[string]int64{}
	for _, task := range tasks {
		self.Data[task.Dir] = 0
	}

	self.save()
}

func (self *InstallInfo) save() {
	r, _ := json.MarshalIndent(self.Data, "", "  ")
	ioutil.WriteFile(self.StoreFile, r, 0666)
}

func println_info() {
	log.Printf("work dir: %s\n", wd)
	log.Printf("os  name: %s\n", runtime.GOOS)
	log.Printf("lein cmd: %s\n", lein_cmd)
	log.Printf("force reinstall: %v\n", force_reinstall)
	fmt.Println()
}
