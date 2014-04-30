Root : Base GUI [Krypto] [Color] [Chathistory] [Userauth] :: _Root ;

GUI : Console
	| Java_GUI ;

%%

Color implies Java_GUI ;
Userauth implies Krypto ;

