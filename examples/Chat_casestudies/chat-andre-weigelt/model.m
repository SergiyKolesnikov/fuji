sple_chat_fop_ : sple_chat_fop+ :: _sple_chat_fop ;

sple_chat_fop : UserInterface [Logging] :: Base ;

UserInterface : [Authentication] [Spamfilter] [Color] [Name] [Encryption] :: GUI
	| Console ;

Name : [PrivateMSG] :: _Name ;

