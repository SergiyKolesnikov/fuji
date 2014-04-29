joChatBase : [authentication] messaging userIO [history] [spamfilter] :: _joChatBase ;

authentication : authenticationANDgui
	| authenticationANDconsole ;

messaging : [sound] [coloredtext] encryption :: _messaging ;

encryption : [rot13] [invert] :: _encryption ;

userIO : gui
	| console ;

spamfilter : spamfilterANDconsole
	| spamfilterANDgui ;

%%

spamfilter and gui implies spamfilterANDgui ;
console and spamfilter implies spamfilterANDconsole ;
console implies not  coloredtext ;
authentication and gui implies authenticationANDgui ;
authentication and console implies authenticationANDconsole ;

##

authenticationANDconsole { hidden } 
authenticationANDgui { hidden } 
spamfilterANDconsole { hidden } 
spamfilterANDgui { hidden } 
