package test.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
@SuppressWarnings("serial")
public class TestProperties extends Properties {

	private final Collection<String> includeAdd = new LinkedList<String>();
	private final Collection<String> excludeAdd = new LinkedList<String>();

	@Override
	public String getProperty(String key, String defaultValue) {
		String value = System.getProperty(key, "");
		if (value.isEmpty()) {
			value = super.getProperty(key, defaultValue);
		}
		return value;
	}

	/**
	 * Add a test to the list of included tests.
	 * Can be overridden by the test property.
	 * @param path the path to the test directory
	 */
	public void include(String path) {
		includeAdd.add(path);
	}

	/**
	 * Add a test to the list of included tests.
	 * Can be overridden by the test property.
	 * @param path the path to the test directory
	 */
	public void exclude(String path) {
		excludeAdd.add(path);
	}

	/**
	 * @return collection of included test directories
	 */
	public Collection<String> includes() {
		Collection<String> includes = new LinkedList<String>();
		String testProperty = getProperty("test", "").trim();
		if (!testProperty.isEmpty()) {
			addPaths(includes, testProperty);
		} else {
			includes.addAll(includeAdd);
			addPaths(includes, getProperty("includes", ""));
		}
		return includes;
	}

	/**
	 * @return collection of the excluded test directories
	 */
	public Collection<String> excludes() {
		Collection<String> excludes = new LinkedList<String>();

		String testProperty = getProperty("test", "").trim();
		if (testProperty.isEmpty()) {
			excludes.addAll(excludeAdd);
			addPaths(excludes, getProperty("excludes", ""));
		}
		return excludes;
	}

	/**
 	 * Add comma-separated paths to list
 	 */
	private static void addPaths(Collection<String> list, String pathList) {
		String[] items = pathList.split(",");
		for (String item : items) {
			item = item.trim().replace('\\', '/');
			if (!item.isEmpty()) {
				list.add(item);
			}
		}
	}

}
