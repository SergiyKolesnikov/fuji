
import java.util.Arrays;
import java.util.List;

public class SpamFilter {

	String[] filter = { "viagra", "spam", "morespam", "epmd", "fight club" };

	List<String> filterList = Arrays.asList(filter);

	public SpamFilter() {

	}

	public boolean isSpam(String msg) {
		for (String word : msg.split(" ")) {
			if (filterList.contains(word)) {
				return true;
			}
		}
		return false;
	}

}
