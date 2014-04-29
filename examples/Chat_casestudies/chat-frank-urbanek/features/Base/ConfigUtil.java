
public class ConfigUtil {
	public static void setConfig(String[] args) {
		try {
			Config.HOST = args[0];
			Config.PORT = Integer.parseInt(args[1]);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
