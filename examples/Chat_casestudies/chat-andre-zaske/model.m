EPMDF : Network Main Chat [ServerPacketInHandler] Ciphering :: _EPMDF ;

Network : [Unicast] :: _Network ;

Chat : [Color] [Smilies] [PrivateMsg] :: GUI
	| Console ;

Console : CClient
	| CServer ;

ServerPacketInHandler : [History] [Authentication] [Spamfilter] :: _ServerPacketInHandler ;

Ciphering : [Rot13] [AlphaBeta] :: _Ciphering ;

%%

Rot13 implies GUI ;
AlphaBeta implies GUI ;

