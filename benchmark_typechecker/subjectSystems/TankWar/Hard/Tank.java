
public class Tank {

	protected void tankFeindKI() {
		long timeJ = System.currentTimeMillis();
		long timeR = timeJ;
		if (timeJ - time > 400) {
			feuern();
			time = timeJ;
		}
		if (timeR - time1 > 300) {
			// U=1 UR=2 R=3 DR=4 D=5 LD=6 L=7 UL=8
			if (tankManager.tank1 != null) {
				int px = tankManager.tank1.x_Koordinate;
				int py = tankManager.tank1.y_Koordinate;
				int richtungAI = 0;
				if (((px - x_Koordinate) > 0) && ((py - y_Koordinate) > 0)) {
					if ((px - x_Koordinate) > (py - y_Koordinate))
						richtungAI = 5;
					else
						richtungAI = 3;
				}
				if (((px - x_Koordinate) < 0) && ((py - y_Koordinate) < 0)) {
					if ((px - x_Koordinate) > (py - y_Koordinate))
						richtungAI = 1;
					else
						richtungAI = 7;
				}
				if (((px - x_Koordinate) > 0) && ((py - y_Koordinate) < 0)) {
					if ((px - x_Koordinate) > (y_Koordinate - py))
						richtungAI = 1;
					else
						richtungAI = 3;
				}
				if (((px - x_Koordinate) < 0) && ((py - y_Koordinate) > 0)) {
					if ((x_Koordinate - px) > (y_Koordinate - py))
						richtungAI = 5;
					else
						richtungAI = 7;
				}
				int[] richtung = { 0, 1, 3, 5, 7, richtungAI, richtungAI,
						richtungAI, richtungAI };
				int index = Math.abs(random.nextInt() % 9);
				tankRichtung = richtung[index];
			} else {
				int[] richtung = { 0, 1, 3, 5, 7 };
				int index = Math.abs(random.nextInt() % 5);
				tankRichtung = richtung[index];
			}
			time1 = timeJ;
		}

	}

}