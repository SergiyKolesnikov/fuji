Chat : Client Common Server :: _Chat ;

Client : UI+ SendMessage [ReceiveMessage] [UserName] ClientLauncher :: _Client ;

UI : Console
	| Gui ;

SendMessage : Encryption* [Color] :: _SendMessage ;

Encryption : ROT13
	| Swapped ;

Server : [MessageHistory] [Authentification] [Spamfilter] ServerLauncher :: _Server ;

