
import java.util.Random;
import java.util.Vector;

public class TankManager extends MIDlet implements GameManager {

	public Maler maler;
	public int status = GameManager.MAIN_MENU;
	protected int screenWidth, screenHeight;
	protected int koernigkeit, koernigkeit2;
	protected String name;

	public Tank tank1;
	public Tank tank2;
	public Tank base;
	protected int lvl = 1;
	protected Vector tankMenge = new Vector();
	protected Vector missilesMenge = new Vector();
	protected Vector brickwall = new Vector(); //
	protected Vector metalwall = new Vector(); //
	protected Vector grasswall = new Vector(); //
	protected Vector waterwall = new Vector(); //

	protected Vector enemyPosition;
	protected Vector enemyType;
	protected int playerPositionX;
	protected int playerPositionY;
	protected int bPositionX;
	protected int bPositionY;
	protected int tankAllNum;
	protected int tankMaxNum;
	protected int enemyTankNum;
	protected static Random random = new Random();
	protected boolean game = false;
	protected boolean mapReady = true;

	public static void main(String[] args) {
		new TankManager();

	}

	public TankManager() {
		init();

	}

	public void init() {
		maler = new Maler(this);
		maler.init();
		screenWidth = maler.getScreenWidth();
		screenHeight = maler.getScreenHeight();
		koernigkeit = screenWidth / 30;
		koernigkeit2 = koernigkeit / 2;
		new MalerZeit(this).start();

		
	}

	public void aktualisieren() {
		maler.repaint();
	}

	public void keyPressBehandeln(int key) {

		if (status != GameManager.SPIELEN) {
			maler.keyPressedBehandeln(key);
		} else {
			if (key == 10) {
				this.setStatus(GameManager.PAUSE);
				maler.setStatus(GameManager.PAUSE);
			}
			if (key == 27) {
				this.setStatus(GameManager.EXIT);
				maler.setStatus(GameManager.EXIT);
			}
			if (tank1 != null)
				tank1.keyPressBehandeln(key);
		}
	}

	public void keyReleaseBehandeln(int key) {
		if (status == GameManager.SPIELEN) {
			if (tank1 != null)
				tank1.keyReleaseBehandeln(key);
		}

	}

	public void malenkontrolle() {
		switch (status) {
		case GameManager.MAIN_MENU:
			game = false;
			lvl = 1;
			maler.mainMenu();
			break;
		case GameManager.TANK_WAEHLEN:
			maler.tankWaehlen();
			break;
		case GameManager.HELP:
			maler.help();
			break;
		case GameManager.SPIELEN:
			komponenteMalen();
			break;
		case GameManager.PAUSE:
			komponenteMalen();
			maler.pause();
			break;
		case GameManager.LEVEL:
			maler.gameLevel(lvl);
			break;
		case GameManager.LOSE:
			komponenteMalen();
			maler.gameLose();
			break;
		case GameManager.EXIT:
			komponenteMalen();
			maler.gameExit();
			break;
		}
	}

	public void komponenteMalen() {
		if (!game) {
			mapLaden();
			tanksLaden();
			game = true;
		}
		if (mapReady) {
			addEnemyTank();
			if (tank1 == null) {
				this.setStatus(GameManager.LOSE);
				maler.setStatus(GameManager.LOSE);
			}
			if (base != null && base.energie <= 0) {
				((TankManager) this).setStatus(GameManager.LOSE);
				maler.setStatus(GameManager.LOSE);
			}
			if (tankAllNum + enemyTankNum == 0) {
				game = false;
				tankMenge.removeAllElements();
				this.setStatus(GameManager.LEVEL);
				maler.setStatus(GameManager.LEVEL);
			}
			gruppeMalen(brickwall);
			gruppeMalen(tankMenge);
			gruppeMalen(waterwall);
			gruppeMalen(missilesMenge);
			gruppeMalen(metalwall);
			gruppeMalen(grasswall);
			} else {
			this.setStatus(GameManager.WIN);
			maler.setStatus(GameManager.WIN);
		}
	}

	protected void gruppeMalen(Vector gruppe) {
		for (int i = 0; i < gruppe.size(); i++) {
			((GameObject) gruppe.elementAt(i)).malen();
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void mapLaden() {
		tankMenge.removeAllElements();
		tank1 = null;
		tank2 = null;
		base = null;
		MapInfo map = new MapInfo(this, lvl++);
		mapReady = map.read;
		brickwall = map.brickWall;
		metalwall = map.metalWall;
		grasswall = map.grassWall;
		waterwall = map.waterWall;
		enemyPosition = map.enemyPosition;
		playerPositionX = map.playerLocationX;
		playerPositionY = map.playerLocationY;
		tankAllNum = map.enemyAllNr;
		tankMaxNum = map.enemyMaxNr;
		bPositionX = map.baseLocationX;
		bPositionY = map.baseLocationY;
		enemyType = map.enemyType;
	}

	public void tanksLaden() {
		if (mapReady) {
			int a = enemyPosition.size();
			int b = enemyType.size();
			// build tanks
			tank1 = new Tank(this, playerPositionX, playerPositionY, maler.tankType);
			tankMenge.addElement(tank1);
			if (bPositionX != 0) {
				base = new Tank(this, bPositionX, bPositionY, 00);
				tankMenge.addElement(base);
			}
			for (int i = 0; i < a; i++) {
				int z = Math.abs(random.nextInt() % b);// rand.nextInt(b);
				Integer tankTy = (Integer) enemyType.elementAt(z);
				int tankType = tankTy.intValue();
				Integer d = (Integer) enemyPosition.elementAt(i);
				int d2 = d.intValue();
				Tank enemyTank = new Tank(this, d2 / 100, d2 % 100, tankType);
				tankMenge.addElement(enemyTank);
				this.tankAllNum--;
			}
		}
	}

	public void addEnemyTank() {
		enemyTankNum=tankMenge.size()-(tank1==null?0:1)-(tank2==null?0:1)-(base==null?0:1);
		if (enemyTankNum < tankMaxNum && tankAllNum > 0) {
			int f = enemyPosition.size();
			int i = Math.abs(random.nextInt() % f);
			int g = enemyType.size();
			int h = Math.abs(random.nextInt() % g);
			Integer tankTy2 = (Integer) enemyType.elementAt(h);
			int tankType = tankTy2.intValue();
			Integer y = (Integer) enemyPosition.elementAt(i);
			int y2 = y.intValue();
			Tank enemyTank = new Tank(this, y2 / 100, y2 % 100, tankType);
			if (!enemyTank.stossenGegen(tankMenge)) {
				tankMenge.addElement(enemyTank);
				this.tankAllNum--;
			}
		}
	}
	
	public int getBlood(){
		if(tank1!=null){
			return tank1.energie/10;
		}
		return 0;
	}
	
	public int getTankMenge() {
		return (tankAllNum + enemyTankNum);
	}
}
