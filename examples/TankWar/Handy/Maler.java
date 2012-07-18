

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Image;
import java.util.Hashtable;
import java.io.IOException;

public class Maler extends Canvas {

    protected GameManager gameManager;
    protected int GAME_WIDTH, GAME_HEIGHT;
    Graphics gTemp = null;
    protected Display dis;
    private KeyMonitor KeyMonitor;
    public int tankType = 1;
    public int status;
    protected Menu menu;
    protected long time;
    protected int tankAllNum;
    

    public Maler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void init() {
    	gamesize();
        dis = Display.getDisplay((MIDlet) gameManager);
        dis.setCurrent(this);
        setFullScreenMode(true);
        KeyMonitor = new KeyMonitor(gameManager);
        this.setCommandListener(KeyMonitor);
    }
    protected void gamesize(){
    	GAME_WIDTH=240;
    	GAME_HEIGHT=240;
    }

    public void setColor(int R, int G, int B) {

        gTemp.setColor(R, G, B);

    }

    public void fillOvall(int x, int y, int width, int height) {

        gTemp.fillArc(x, y, width, height, 0, 360);

    }

    public void fillRect(int x, int y, int width, int height) {
        gTemp.fillRect(x, y, width, height);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        gTemp.drawLine(x1, y1, x2, y2);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcw,
            int arch) {
        gTemp.drawRoundRect(x, y, width, height, arcw, arch);
    }

    protected void paint(Graphics g) {
        gTemp = g;
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, getWidth(), getHeight());
        int c = g.getColor();
        gameManager.malenkontrolle();
        info(g);
        g.setColor(c);

    }
    
     public void info(Graphics g) {
      	if (status == GameManager.SPIELEN) {
            if (gameManager.getBlood() != 0) {
                g.setColor(0, 255, 0);
                g.drawString("MyTank Life: " + gameManager.getBlood(), 5,
                        GAME_HEIGHT, Graphics.TOP | Graphics.LEFT);
                g.setColor(255, 0, 0);
                g.fillRect(5, GAME_HEIGHT + 15, 5 * 2 * gameManager.getBlood(), 5);
                g.setColor(0, 255, 0);
                g.drawString("Tanks:" + gameManager.getTankMenge(), 5,
                        GAME_HEIGHT + 20, Graphics.TOP | Graphics.LEFT);
            }
        }
    }

    public int getScreenWidth() {

        return this.getWidth();
    }

    public int getScreenHeight() {

        return this.getWidth();
    }

    public void setStatus(int status) {
        this.status = status;
    }
////----------------------------------------------------------------

    public void menuBehandeln(String option) {
        System.out.println(option);
        if (option.equals(Sprach.START)) {
			this.setStatus(GameManager.TANK_WAEHLEN);
			this.gameManager.setStatus(GameManager.TANK_WAEHLEN);
			menu = null;
		}
        if (option.equals(Sprach.MAIN_MENU)) {
            this.setStatus(GameManager.MAIN_MENU);
            this.gameManager.setStatus(GameManager.MAIN_MENU);
            menu = null;
        }
        
        if (option.equals(Sprach.NOTE)) {
            this.setStatus(GameManager.NOTE);
            this.gameManager.setStatus(GameManager.NOTE);
            menu = null;
        }
        if (option.equals(Sprach.HNOTE)) {
            this.setStatus(GameManager.MAIN_MENU);
            this.gameManager.setStatus(GameManager.MAIN_MENU);
            menu = null;
        }
        if (option.equals(Sprach.EXIT)) {
            gameManager.exit();
        }
        if (option.equals(Sprach.PAUSE)) {
            this.setStatus(GameManager.SPIELEN);
            this.gameManager.setStatus(GameManager.SPIELEN);
            menu = null;
        }
        if (option.equals(Sprach.RESUME)) {
            gamescreenstart();
        }
        if (option.equals(Sprach.TANKA)) {
            menu = null;
            tankType = 01;
            this.setStatus(GameManager.LEVEL);
            gameManager.setStatus(GameManager.LEVEL);
        }
        if (option.equals(Sprach.TANKB)) {
            menu = null;
            tankType = 02;
            this.setStatus(GameManager.LEVEL);
            gameManager.setStatus(GameManager.LEVEL);
        }
        if (option.equals(Sprach.TANKC)) {
            menu = null;
            tankType = 03;
            this.setStatus(GameManager.LEVEL);
            gameManager.setStatus(GameManager.LEVEL);
        }
        if (option.equals(Sprach.HELP)) {
            this.setStatus(GameManager.HELP);
            gameManager.setStatus(GameManager.HELP);
            menu = null;
        }
        if (option.equals(Sprach.HelpItem)) {
            this.setStatus(GameManager.MAIN_MENU);
            this.gameManager.setStatus(GameManager.MAIN_MENU);
            menu = null;
        }
    }

    protected void gameScreenStart() {
        this.setStatus(GameManager.SPIELEN);
        this.gameManager.setStatus(GameManager.SPIELEN);
    }

    public void mainMenu() {
        if (menu == null) {
            mainMenuerstellen();
        }
        menu.erscheinen(gTemp);
        //System.out.println(menu.logoimage == null);
    }

    public void mainMenuerstellen() {

        menu = new Menu(this);
        menu.add(Sprach.START, 0);
        menu.add(Sprach.HELP, 10);
        menu.add(Sprach.EXIT, 11);
        menu.setStyle(0);
        menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT * 2 / 3);
        menu.setZeileAbstand(20);
        menu.addLogo(loadImage("15.png",0,0));
        menu.setLogoKoordinate(0, 0);
    }

    public void tankWaehlen() {
        if (menu == null) {
            tankErstellen();
        }
        menu.erscheinen(gTemp);

    }

    protected void tankErstellen() {
        menu = new Menu(this);
        menu.addLogo(loadImage("15.png",0,0));
        menu.setStyle(1);
        menu.setZeileAbstand(10);
        menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT*2/3);
    }
    
    public void help() {
        if (menu == null) {
            helpItemErstellen();
        }
        menu.erscheinen(gTemp);
    }

    protected void helpItemErstellen() {
        menu = new Menu(this);
        menu.add(Sprach.HelpItem, loadImage("transparent.png", 0, 0), loadImage("help.png", 0, 0), 0);
        menu.setStyle(3);
        menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT / 2);
    }

    public void pause() {
        if (menu == null) {
            menu = new Menu(this);
            menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT / 2);
            menu.setZeileAbstand(0);
            menu.add(Sprach.PAUSE, 0);
            menu.setStyle(0);
        }
        menu.erscheinen(gTemp);
    }

    protected void gameLevel(int lvl) {
        if (menu == null) {
            time = System.currentTimeMillis();

            menu = new Menu(this);
            menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT / 2);
            menu.setZeileAbstand(0);
            menu.add(Sprach.LEVEL, 0);
            menu.add(lvl + "", 1);
            menu.setStyle(0);
            menu.setZeileAbstand(55);
            menu.setWaehlbar(false);
        }
        menu.erscheinen(gTemp);
        if (System.currentTimeMillis() - time > 2000) {
            gamescreenstart();
            menu = null;
        }

    }

    protected void gamescreenstart() {
        this.setStatus(GameManager.SPIELEN);
        this.gameManager.setStatus(GameManager.SPIELEN);
        menu = null;
    }

    public void gameLose() {
        if (menu == null) {
            time = System.currentTimeMillis();
            menu = new Menu(this);
            menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT / 2);
            menu.setZeileAbstand(0);
            menu.add(Sprach.LOSE, 0);
            menu.setStyle(0);
            menu.setZeileAbstand(55);
        }
        menu.erscheinen(gTemp);
        if (System.currentTimeMillis() - time > 2000) {
            this.setStatus(GameManager.MAIN_MENU);
            gameManager.setStatus(gameManager.MAIN_MENU);
           // gameManager.writeScore();
            menu = null;
        }

    }

    protected void gameWin() {
        if (menu == null) {
            time = System.currentTimeMillis();
            menu = new Menu(this);
            menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT / 2);
            menu.setZeileAbstand(0);
            menu.add(Sprach.WIN, 0);
            menu.setStyle(0);

        }
        menu.erscheinen(gTemp);
        if (System.currentTimeMillis() - time > 2000) {
            this.setStatus(GameManager.MAIN_MENU);
            this.gameManager.setStatus(GameManager.MAIN_MENU);
            menu = null;
            //gameManager.writeScore();
        }

    }

    public void gameExit() {
        if (menu == null) {
            menu = new Menu(this);
            menu.setKoordinateImage(GAME_WIDTH / 2, GAME_HEIGHT / 2);
            menu.setZeileAbstand(0);
            menu.add(Sprach.MAIN_MENU, 1);
            menu.add(Sprach.RESUME, 0);
            menu.setStyle(0);
            menu.setZeileAbstand(55);
        }
        menu.erscheinen(gTemp);
    }
////--------------------------------------------------------------------------------------

    protected void keyPressed(int keyCode) {
    
        KeyMonitor.KeyPressed(keyCode);
    }

    protected void keyReleased(int keyCode) {

        KeyMonitor.keyReleased(keyCode);
    }
    
    protected void keyPressedBehandeln(int keyCode) {
        if (menu != null) {
            menu.KeyBehandeln(keyCode);
        }
       
    }
    
    public Image loadImage(String str, int a, int b) {
        Image tempimage = null;
        try {
            tempimage = Image.createImage("/" + str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (a == 0 && b == 0) {
            return tempimage;
        }
        return scale(tempimage, a, b);
    }

    public static Image scale(Image src, int width, int height) {
        
        int scanline = src.getWidth();
        int srcw = src.getWidth();
        int srch = src.getHeight();
        int buf[] = new int[srcw * srch];
        src.getRGB(buf, 0, scanline, 0, 0, srcw, srch);
        int buf2[] = new int[width * height];
        for (int y = 0; y < height; y++) {
            int c1 = y * width;
            int c2 = (y * srch / height) * scanline;
            for (int x = 0; x < width; x++) {
                buf2[c1 + x] = buf[c2 + x * srcw / width];
            }
        }
        Image img = Image.createRGBImage(buf2, width, height, true);
        return img;
    }    
}