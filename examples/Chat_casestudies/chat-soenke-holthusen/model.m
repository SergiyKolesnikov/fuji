Root : Base [Authentication] UI [History] [Encryption] :: _Root ;

UI : Console
	| [Color] [GUIAuthentication] :: GUI ;

Console : [ConsoleAuthentication] :: _Console ;

%%

Console and Authentication implies ConsoleAuthentication ;
GUI and Authentication implies GUIAuthentication ;
ConsoleAuthentication implies Authentication ;
GUIAuthentication implies Authentication ;

