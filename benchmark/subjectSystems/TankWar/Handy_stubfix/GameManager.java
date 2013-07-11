import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface GameManager {
    @Stub
	public static int MAIN_MENU = 0;
    @Stub
    void keyReleaseBehandeln(int key);
    @Stub
    void malenkontrolle();
    @Stub
    public static int LEVEL = 12;
    @Stub
    public static int SPIELEN = 2;
    @Stub
    public static int HELP = 4;
    @Stub
    int getTankMenge();
    @Stub
    void exit();
    @Stub
    void keyPressBehandeln(int key);
    @Stub
    public static int NOTE = 3;
    @Stub
    void setStatus(int status);
    @Stub
    public static int TANK_WAEHLEN = 1;
    @Stub
    int getBlood();
    
}
