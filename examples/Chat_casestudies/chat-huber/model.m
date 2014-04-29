Root : Base :: _Root ;

Base : [server] [client] [authentication] [history] [messages] :: _Base ;

client : clientgui ;

messages : [colored] [private] :: _messages ;

%%

server and not  client or not  server and client ;

