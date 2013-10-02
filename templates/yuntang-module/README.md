# yuntang-module

A Leiningen template for yuntang-platform.


## Install

    
local install

    to .lein/profiles.clj
    
    {:user {:plugins [
                      [yuntang-module/lein-template "0.1.0-SNAPSHOT"]]

## Usage

    $ lein new yuntang-module my-module
        Generating fresh 'lein new' yuntang-module project.
    $ cd my-module/
    $ tree
        .
        ├── project.clj
        ├── README.md
        ├── resources
        │   ├── public
        │   │   ├── css
        │   │   │   └── my-module
        │   │   ├── img
        │   │   │   └── my-module
        │   │   └── js
        │   │       └── my-module
        │   └── templates
        │       └── my-module
        │           └── index.html
        ├── src
        │   └── my_module
        │       ├── bootstrap.clj
        │       ├── config.clj
        │       ├── handlers.clj
        │       ├── migrations.clj
        │       ├── models.clj
        │       ├── module.clj
        │       └── snippets.clj
        ├── src-cljs

    $ lein test
    $ lein install

## License

Copyright © 2013 itang

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
