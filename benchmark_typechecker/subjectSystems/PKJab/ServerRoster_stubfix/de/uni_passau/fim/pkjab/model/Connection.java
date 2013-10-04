package de.uni_passau.fim.pkjab.model;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class Connection extends de.uni_passau.fim.pkjab.util.Observable {
    @Stub
    public de.uni_passau.fim.pkjab.model.Roster getRoster() {
        return null;
    }
    @Stub
    public de.uni_passau.fim.pkjab.model.xmpp.XMPPWriter getOutput() {
        return null;
    }
    @Stub
    public de.uni_passau.fim.pkjab.util.ConnectionState state;
}
