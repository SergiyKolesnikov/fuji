Chat : [privateMessage] [Encryption] [TagRemovement] [Farbe] [Spam] Gui [Auth] [History] :: _Chat ;

Gui : GUI
	| Console ;

%%

Farbe implies GUI ;
privateMessage and not  Encryption or not  privateMessage and Encryption or not  Encryption and not  privateMessage ;

