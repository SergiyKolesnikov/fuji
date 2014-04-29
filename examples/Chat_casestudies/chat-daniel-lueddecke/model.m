Chat : [GlobalFeatures] [Products] :: _Chat ;

GlobalFeatures : [Encryption] :: _GlobalFeatures ;

Products : [Logging] UserInterface [Auth] [SPAMFilter] :: Client
	| Server ;

UserInterface : Console
	| Grahpical ;

Grahpical : [ColorSupport] :: _Grahpical ;

Server : [UserSupport] :: _Server ;

%%

Auth implies Grahpical ;

