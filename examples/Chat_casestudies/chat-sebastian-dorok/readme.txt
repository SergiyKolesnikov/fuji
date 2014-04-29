How to use:

The chat program consists of a client and a server.

version 1
#########

To start the server with any port number:
java server.Server 8080

To start a client and connect it to the server provide the host's name or 
IP address and add the port number the server is listening:
java client.Client localhost 8080

version 2
#########

Since version 2 either server and client provide several start options. 
By default all options are disabled.

The server can be started in auth-mode, that means every client must 
authenticate against the server.

To start the server in auth-mode simply add the start option -auth after port:
java server.Server 8080 -auth

The client has the following options:

1) java client.Client localhost 8080 -colors enables color selection for 
the user.

2) java client.Client localhost 8080 -crypt enables encryption of message 
content exchanged with the server.

3) java client.Client localhost 8080 -log enables logging of all mesages 
displayed to the user.

All options can be combined freely, but make sure to always append options 
after host and port.

version 3
#########

Since version 3 a new client option has been implemented: output to console. 
This feature is activated by starting the client with -console option.

Activating features via parameters is only possible if the feature ide config 
allows this otherwise you can simply enable and disable the features by 
selecting them from feature ide's configuration. Furthermore 3 example 
configurations are available.

version 4
#########

Since version 4 a plugin concept is implemented to control the message flow. The
start up mechanism hasn't changed. You should still set commandline arguments as 
in version 1. Following message plugins are available to change the server or 
clients behavior:

Client:
	CryptoPlugin - if registered encryption will be negotiated with the server 
	HistoryPlugin - if registered a history log is written to the "logs" folder

Server:
	SpamMessagePlugin - if registered spam detection in incoming messages is 
						activated
	AuthPlugin - if registered authentication is required by clients to connect 
				 successfully to the server
				 
The clients options to enable colors can be enabled or disabled by registering 
the normal "ChatFrame" or "NoColorChatFrame" as chat window after login screen.
The two implementations exclude each other.

The clients option to output messages on console can be enabled by registering 
the "ConsoleOutput" as output listener additionally. You can choose to stop 
message output in the chat frames by not adding the chat frame as output 
listener.

version 5
#########

Version 5 of the chat application was developed using FOP technologies. Using 
this approach it is no longer necessary to configure anything in code (e.g. 
plugins) or via parameters. Instead you select your desired features in a config 
file in configs folder or select an existing config. The FOP tool chain creates 
the source code with your selected features. 

To run the created software products you still have to provide host and port 
information for server and clients (see version 1).