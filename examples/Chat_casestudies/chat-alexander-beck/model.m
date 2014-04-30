Root : [Color] [Logging] [Entschluesselung] [Authentifizierung] [Verschluesselung] UI :: _Root ;

UI : GUI
	| Console ;

%%

Verschluesselung implies Entschluesselung ;
Entschluesselung implies Verschluesselung ;

