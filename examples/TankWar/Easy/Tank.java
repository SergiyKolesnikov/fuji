
public class Tank {

	protected void tankFeindKI() {
		long timeJ = System.currentTimeMillis();
		long timeR = timeJ;
		if (timeJ - time > 700) {
			feuern();
			time = timeJ;
		}

		if (timeR - time1 > 400) {
			int[] richtung = { 0, 1, 3, 5, 7 };

			int index = Math.abs(random.nextInt() % 5);
			tankRichtung = richtung[index];
			time1 = timeJ;
		}
	}
}