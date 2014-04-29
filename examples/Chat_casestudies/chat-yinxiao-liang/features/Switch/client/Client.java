package client;

/**
 * TODO description
 */
public class Client {
	public void send(String line) {
		String[] ss = line.split(" ");
		StringBuffer newString = new StringBuffer();
		for (int i = 0; i < ss.length; i++) {
			if (ss[i].length() >= 2) {

				StringBuffer Stmp = new StringBuffer(ss[i]);
				char ctmp = ss[i].charAt(0);
				if (ctmp != '<') {
					char ctmp2 = ss[i].charAt(1);
					Stmp.setCharAt(1, ctmp);
					Stmp.setCharAt(0, ctmp2);
					ss[i] = Stmp.toString();
				} 
			}
		}
		for (int i = 0; i < ss.length; i++) {
			newString.append(ss[i] + " ");

		}
		original(newString.toString());
	}
}
