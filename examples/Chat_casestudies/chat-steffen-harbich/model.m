Root : Base :: _Root ;

Base : Common Client Server UI [Encryption] [Authentication] [History] [Color] :: _Base ;

UI : Console
	| GUI ;

Console : [ColoredConsole] :: _Console ;

GUI : [ColoredGUI] :: _GUI ;

Encryption : Encryption_Rot13
	| Encryption_Reverse ;

%%

Console and Color implies ColoredConsole ;
Console and ColoredConsole implies Color ;
GUI and Color implies ColoredGUI ;
GUI and ColoredGUI implies Color ;

