EPMDChat : Basis Oberflaeche [Verschluesselung] [Server] [Authentifizierung] [Farben] [Historie] :: _EPMDChat ;

Oberflaeche : Konsole
	| GUI ;

Verschluesselung : ROT_13
	| CFC ;

Server : [Spamfilter] [Nickname_wechseln] [Private_Nachrichten] :: _Server ;

%%

Konsole implies not  (Farben or Historie or Nickname_wechseln or Private_Nachrichten or Authentifizierung) ;
Server implies GUI ;

