# dntp

Downright Neighborly Transfer Protocol

Course project, to develop a protocol over UDP to provide reliability.

# Project I

## Important Details to Organize
- Sender cli input is in control of proxy error settings
- Run help from command line to see running arguments
- Default ports will be used for updating port numbers
- getArgsFromSender order of arguments must be precisely the same as the sender method





## Important Defaults
- Reference the `default.java` class 



# Project II

# Running notes
created using java sdk 1.8.0_152
 
## From Command Line

### To run File Receiver Program from Project1
1. go to /dntp in terminal
1. `javac -d bin -cp bin -sourcepath src src\project1\FileReceiver.java` to compile
1. `java -cp bin project1.FileReceiver [filename] [ip] [port]` to run where `[filename]`, `[ip]`, and `[port]` are optional parameters. 
- note that parameters must be in the order listed and all previous parameters must be given to pass a later one. 

### To run File Sender Program from Project1
1. go to /dntp in terminal
1. `javac -d bin -cp bin -sourcepath src src\project1\FileSender.java` to compile
1. `java -cp bin project1.FileSender [filename] [bytes per chunk] [ip] [port]` to run where `[filename]`, `[bytes per chunk]`, `[ip]`, and `[port]` are optional parameters. 
- note that parameters must be in the order listed and all previous parameters must be given to pass a later one. 

### To test running as a package use the helloworld
1. go to /dntp/src in terminal
1. `javac hello/HelloWorld.java`    to compile the .java file
1. `java -cp . hello/HelloWorld`    to run the file. This should output `Hello World!` in the terminal.

### To test the socketpractice application
1. open 2 terminal windows, you'll start the server, then the client
1. go to /dntp/src in terminal
1. `javac socketpractice/ServerSideSocket.java` to compile the server class
1.  `java -cp . socketpractice/ServerSideSocket.java` to run the server. You should see `The server's main is running` in the terminal window.
1. Go to /dntp/src in the other terminal window.
1. `javac socketpractice/ClientSideSocket.java` to compile the .java file
1. `java -cp . socketpractice/ClientSideSocket` to run the client. You should see `here is the string written out/r/n` in the terminal if it connected to the server correctly. Note that string does not exist on the client, so it must get it from the server.
    1. Also the server terminal should show `server try loop after accept running` after.
1. You will possibly need to stop the server correctly to ensure it does not keep running. (ctrl + c on mac)

relevant stackoverflow:   https://stackoverflow.com/questions/16137713/how-do-i-run-a-java-program-from-the-command-line-on-windows

# Project Structure
There are two packages for team testing, hello & socketpractice.

There are two packages for the actual project:
- /src/project1
- /src/project2

# Mac/Unix Commands
`javac -d bin -cp bin -sourcepath src src/project1/FileReceiver.java`
`javac -d bin -cp bin -sourcepath src src/project1/FileSender.java`
`java -cp bin project1.FileReceiver "testOut.jpg"`
`java -cp bin project1.FileSender "test.jpg"`
