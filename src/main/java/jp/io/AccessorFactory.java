package jp.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * An Accessor lets you read/write Model data from/to a storage medium.
 * <p>
 * Non-abstract subclasses implement the methods.
 * Subclasses must be public for use in dynamic class loading.
 * @author Ian Darwin, ian@darwinsys.co
 * @version $Id$
 */
public class AccessorFactory {

	/** The current file name */
	protected String fileName;
	/** default filname */
	final static String PROPS = "/jabberpoint.properties";

	private static boolean loaded;

	/** The filename for the demo show */
	public static final String DEMO_NAME = "Demonstration Slide Show";

	protected static Properties programProperties = new Properties();

	static void loadProperties() {
		try {
			InputStream is = AccessorFactory.class.getResourceAsStream(PROPS);
			programProperties.load(is);
			is.close();
			loaded = true;
		} catch (IOException ex) {
			System.err.println("Can't load properties file " + PROPS);
			System.err.println(ex.toString());
		}
	}

	public static final String DEFAULT_EXTEN = ".jpt";

	/** getInstance returns the correct subclass for reading/writing. */
	public static Accessor getInstance(String fileName) {
		if (fileName.equals(DEMO_NAME)) {
			return new Demo(DEMO_NAME);
		}
		if (!loaded) {
			loadProperties();
		}
		int lastDot = fileName.lastIndexOf(".");
		String ext;
		if (lastDot == -1) {
			fileName += DEFAULT_EXTEN;
			ext = DEFAULT_EXTEN;
		} else {
			ext = fileName.substring(lastDot);
		}
		String handlerClassName;
		if ((handlerClassName = programProperties.getProperty("class"+ext)) != null) {
			System.out.println("Loading class " + handlerClassName);
			try {
				return (Accessor)
					Class.forName(handlerClassName).newInstance();
			} catch (Exception ex) {
				System.err.println(ex);
				System.err.println("Error in dynamic class, using fallbacks");
			}
		}

		return (Accessor)new AccessorText(fileName);
	}

	protected AccessorFactory(String fn) {
		fileName = fn;
	}

	public AccessorFactory() {
	}
}
