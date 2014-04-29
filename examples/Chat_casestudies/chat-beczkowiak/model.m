Root : [Stuff] [Blocklist] [Audio] [History] [Colors] Encryption* [Login] UserInterface Base :: _Root ;

Stuff : [EncryptionOn] [LoginEncryption] [ConsoleLogin] [GUILogin] [DAES] :: _Stuff ;

Encryption : AES
	| DES ;

UserInterface : Console
	| GUI ;

%%

Colors implies not  Console ;
AES or DES iff EncryptionOn ;
AES and DES iff DAES ;
GUI and Login iff GUILogin ;
EncryptionOn and Login iff LoginEncryption ;
Console and Login iff ConsoleLogin ;

