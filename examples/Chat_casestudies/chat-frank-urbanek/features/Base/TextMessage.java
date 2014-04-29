import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


public class TextMessage implements Content {

	private static final long serialVersionUID = -9161595018411902079L;
	private String content;
	
	//#if Color
	private Color color;
	//#endif
	Map<String, Object> properties = new HashMap<String, Object>();
	
	public Object getProperty(String key){
		return properties.get(key);
	}
	
	public boolean addProperty(String key, Object value){
		if (properties.containsKey(key))
			return false;
		properties.put(key, value);
		return true;
	}
	
	public boolean setProperty(String key, Object value){
		Object ret = properties.put(key, value);
		return true;
	}
	
	//private static Color DEFAULT_COLOR = Color.BLACK;

	public TextMessage(String content) {
		//this(content, DEFAULT_COLOR);
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(Object content) {
		this.content = (String)content;
	}
	
	//#if Color
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	//#endif
	
}
