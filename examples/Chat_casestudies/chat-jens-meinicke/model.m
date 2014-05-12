Base : UI [Auth] Crypter* [History] [Color] :: _Base ;

UI : GUI
	| Console ;

Crypter : Cryptreverse
	| Cryptswitch ;

%%

Color implies GUI ;

