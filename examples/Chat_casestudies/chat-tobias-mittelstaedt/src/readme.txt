How to use:

The chat program consists of a client and a server.

First start the server with any port number:

java server.Server 8080

Then start two or more clients with the address of the server (localhost if run on the same machine) and with the same port number.

java client.Client localhost 8080



=============================
ADDITIONAL README INFORMATION
=============================

------
Task 1
------

the password is 'epmd', case insensitive. it has to be provided as third commandline argument of the client

java client.Client localhost 8080 epmd

text color and encryption can be changed via gui.

------
Task 2
------

---Server---
start server with
-java server.Server 8080
for no logging. starting it like
-java server.Server 8080 "C:\log.txt"
will log all messages. it is necessary that the file already exist. using the same file twice will result in overriding.

---Client---

new syntax or client:

java client.Client localhost 8080 encryption:color:authentification epmd

replace encryption/color/authentification with 'true' for enabled and 'false' for disabled. password is only needed if authentification is enabled.


------
Task 4
------

---Server---
start server with
-java server.Server 8080
for no logging. starting it like
-java server.Server 8080 "C:\log.txt"
will log all messages. it is necessary that the file already exist. using the same file twice will result in overriding.


new syntax for client:

java client.Client localhost 8080 epmd

replace encryption/color/authentification with 'true' for enabled and 'false' for disabled. password is only needed if authentification is enabled.


---Plugin information---

The textcolor, encryption and authentification plugins are only applicable in combination with the gui plugin, since they rely on gui functionality. Gui is a plugin, default is console output.


------
Task 5 - FOP
------

---Server---
start server with
-java server.Server 8080
for no logging. starting it like
-java server.Server 8080 "C:\log.txt"
will log all messages. it is necessary that the file already exist. using the same file twice will result in overriding.

---Client---
java client.Client localhost 8080 epmd

password optional, only used if authentification feature is enabled
