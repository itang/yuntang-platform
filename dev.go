package main

import (
	"bufio"
	"fmt"
	"os"
	"os/exec"
	"path"
	"runtime"
	"time"
)

var lein_cmd = get_lein_cmd()
var ring_server_cmd = exec.Command(lein_cmd, "ring", "server")
var wd, _ = os.Getwd()

func main() {
	ring_server_cmd.Dir = path.Join(wd, "example")
	go ring_server_cmd.Run()

	fmt.Println("run ring server, exit?")
	scanner := bufio.NewScanner(os.Stdin)
	for {
		scanner.Scan()
		if scanner.Text() == "exit" {
			err := ring_server_cmd.Process.Kill()
			if err != nil {
				fmt.Errorf("error stop ring server:%v", err)
			}
			time.Sleep(time.Second * 2)
			break
		}
	}
}

func get_lein_cmd() string {
	if runtime.GOOS == "windows" {
		return "lein.bat"
	}
	return "lein"
}
