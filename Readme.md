Get started
The Project is on Java, Spring Boot

1. clone the project
2. run "mvn clean install"
3. open your prompt terminal at tictactoe/target folder
4. run from the console/prompt "java -jar tictactoe-0.0.1-SNAPSHOT.jar". Make sure you have maven and java 17 or higher on your machine.

NOTE: to get the best user experience DO NOT start from IDE console. ClearConsole does not work from IDE terminal.

NOTE: by start inside a Docker Container there is the error "java.util.NoSuchElementException: No line found
2024-06-24 01:08:55     at java.base/java.util.Scanner.nextLine(Scanner.java:1651)"

        This typically occurs because Docker containers do not have a standard input (stdin) in the same way a normal terminal does, especially when running in detached mode (-d).
        If you want to run in a Docker Contaiter, try to do it using -it instead of -d.
        Example: docker run -it -p 9000:9000 --name tictactoe-container tictactoe-app

Have a nice play.