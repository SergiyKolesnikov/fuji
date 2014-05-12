
public  class  Color {
	
	
	public static Color instance = new Color();

	
	
	public static final String COLOR_BLACK = "black";

	
	public static final String COLOR_RED = "red";

	
	public static final String COLOR_BLUE = "blue";

	
	public static final String COLOR_DEFAULT = COLOR_BLACK;

	
	public static final String[] COLORS = new String[] { COLOR_BLACK, COLOR_RED, COLOR_BLUE };

	
	
	protected String color = COLOR_DEFAULT;

	
	
	private Color() {
	}

	

	public String getColor() {
		return color;
	}

	

	public void setColor(String colorConstant) {
		this.color = colorConstant;
	}


}
