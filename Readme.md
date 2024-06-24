Get started

1. clone the project
2. run "mvn clean install"
3. run from console "java -jar tictactoe-0.0.1-SNAPSHOT.jar"

NOTE: to get the best user experience DO NOT start from IDE console. ClearConsole does not work from IDE terminal.
NOTE: This typically occurs because Docker containers do not have a standard input (stdin) in the same way a normal terminal does, especially when running in detached mode (-d).
        If you want to run in a Docker Contaiter, try to do it using -it instead of -d.
        Example: docker run -it -p 9000:9000 --name tictactoe-container tictactoe-app

Have a nice play.