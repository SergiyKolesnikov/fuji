LittleChat : Base Features :: _LittleChat ;

Features : System [Cryptography] :: _Features ;

System : View [History] [ChangeName] :: Client
	| SpamFilter [PrivateMessage] :: Server ;

View : Authentication [Color] :: GUI
	| Console ;

Cryptography : ROT13
	| SwapChars ;

%%

SpamFilter implies not  Cryptography ;

