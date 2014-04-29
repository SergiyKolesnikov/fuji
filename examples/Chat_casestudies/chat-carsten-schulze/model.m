EPMD_chat : UserInterface [Authentication] [Encryption] [History] [Spamfilter] [Usernames] :: _EPMD_chat ;

UserInterface : GUI
	| CLI ;

GUI : [FormatText] :: _GUI ;

Encryption : ROT
	| XOR ;

Usernames : [PrivatMessages] :: _Usernames ;

