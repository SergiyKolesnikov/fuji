package de.uni_passau.fim.pkjab.model;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.String;
import java.lang.IllegalArgumentException;
import de.uni_passau.fim.pkjab.model.messages.TextMessage;
import de.uni_passau.fim.pkjab.model.Connection;
import de.uni_passau.fim.pkjab.util.ChatState;
import de.uni_passau.fim.pkjab.model.messages.ChatStateMessage;
import de.uni_passau.fim.pkjab.model.messages.Message;
public class Contact implements java.lang.Comparable {
    @Stub
    public de.uni_passau.fim.pkjab.util.Jid getBareJid() {
        return null;
    }
    @Stub
    public de.uni_passau.fim.pkjab.model.messages.TextMessage original(java.lang.String text) {
        return null;
    }
    @Stub
    public void original(de.uni_passau.fim.pkjab.util.ChatState chatState) {
        return ;
    }
    @Stub
    public de.uni_passau.fim.pkjab.model.Connection connection;
    @Stub
	public int compareTo(Object o) {
    	return 0;
    }
}
