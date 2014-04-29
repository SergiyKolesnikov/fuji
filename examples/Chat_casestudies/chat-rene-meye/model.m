sple_chat_featureHouse : _Base :: _sple_chat_featureHouse ;

_Base : [SpamFilter] [Logging] [Authentication] [Coloring] :: __Base ;

Authentication : [Encryption] ServerCommands* :: _Authentication ;

Encryption : Rot13
	| MD5
	| SwitchingChars ;

ServerCommands : SendUserSpecificMessage
	| UsernameSupport ;

