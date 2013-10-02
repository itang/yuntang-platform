# yuntang-app

A Leiningen template for yuntang-platform.


## Install

    
local install

    to .lein/profiles.clj
    
    {:user {:plugins [
                      [yuntang-app/lein-template "0.1.0-SNAPSHOT"]]

## Usage

    $ lein new yuntang-app my-app
        Generating fresh 'lein new' yuntang-app project.
    $ cd my-app/
    $ ls
        prepare.sh  prod.sh  project.clj  README.md  resources  src  src-cljs
    $ chmod +x prepare.sh 
    $ ./prepare.sh 
        2013-10-02 11:38:19,149 INFO  cljwtang.tools.migrate: try migrate ...
        2013-10-02 11:38:25,947 INFO  com.mchange.v2.log.MLog: MLog clients using log4j logging.
        add-users-table
        add-permits-table
        add-permits_targets-table
        add-posts-table
        add-comments-table
        add-appconfig-table
        2013-10-02 11:38:31,376 INFO  cljwtang.tools.migrate: after migrate ...
        (:appconfigs :comments :lobos_migrations :permits :permits_targets :posts :users)
        Compiling ClojureScript.
        Compiling "resources/public/js/app.js" from ["src-cljs"]...
        Successfully compiled "resources/public/js/app.js" in 21.906883568 seconds.
        Compiling "resources/public/js/app-debug.js" from ["src-cljs"]...
        Successfully compiled "resources/public/js/app-debug.js" in 8.056098795 seconds.
    $ lein ring server

## License

Copyright © 2013 itang

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
