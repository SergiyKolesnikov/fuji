import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface GameManager {
    @Stub
    public static int MAIN_MENU;
    @Stub
    void keyReleaseBehandeln(int key);
    @Stub
    public static int PAUSE;
    @Stub
    void malenkontrolle();
    @Stub
    public static int SPIELEN;
    @Stub
    public static int LEVEL;
    @Stub
    public static int LIAN_WANG;
    @Stub
    public static int HELP;
    @Stub
    int getTankMenge();
    @Stub
    public static int NAME_VERGEBEN;
    @Stub
    public static int LOSE;
    @Stub
    public static int EXIT;
    @Stub
    public static int KAI_FA;
    @Stub
    void keyPressBehandeln(int key);
    @Stub
    public static int NOTE;
    @Stub
    void setStatus(int status);
    @Stub
    public static int BANG_ZHU;
    @Stub
    void aktualisieren();
    @Stub
    public static int WIN;
    @Stub
    int getBlood();
    @Stub
    public static int TANK_WAEHLEN;
}
