Root : Base [Encryption] [Authentification] [Logging] UI :: _Root ;

Encryption : Reverse_Order
	| Pseudo_Caesar ;

UI : GUI
	| Console ;

GUI : [GUI_Color] :: _GUI ;

Console : [Console_Color] :: _Console ;

