package jp;

import java.io.File;
import java.io.FileInputStream;
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
public abstract class Accessor {

	/** The current file name */
	protected String fileName;

	/** The filename for the demo show */
	public static final String DEMO_NAME = "Demonstration Slide Show";

	protected static Properties p;

	protected static String jabberpointHome;

	static {
		jabberpointHome = System.getProperty("JABBERPOINTHOME");
		p = new Properties();
		String PROPS = jabberpointHome +
			File.separator + "jabberpoint.properties";
		try {
			InputStream is = new FileInputStream(PROPS);
			p.load(is);
			is.close();
		} catch (IOException ex) {
			System.err.println("Can't load properties file " + PROPS);
			System.err.println(ex.toString());
		}
	}

	public static final String DEFAULT_EXTEN = ".jpt";

	/** getInstance returns the correct subclass for reading/writing. */
	public static Accessor getInstance(String fileName) {
		int lastDot = fileName.lastIndexOf(".");
		String ext;
		if (lastDot == -1) {
			fileName += DEFAULT_EXTEN;
			ext = DEFAULT_EXTEN;
		} else {
			ext = fileName.substring(lastDot);
		}
		String handlerClassName;
		if ((handlerClassName = p.getProperty("class"+ext)) != null) {
			System.out.println("Loading class " + handlerClassName);
			try {
				return (Accessor)
					Class.forName(handlerClassName).newInstance();
			} catch (Exception ex) {
				System.err.println("Error in dynamic class, using fallbacks");
				System.err.println(ex);
			}
		}
		if (fileName.endsWith(".xml"))
			return (Accessor)new AccessorJDOM(fileName);
		if (fileName.equals(DEMO_NAME))
			return new Demo(DEMO_NAME);
		return (Accessor)new AccessorText(fileName);
	}

	protected Accessor(String fn) {
		fileName = fn;
	}

	public Accessor() {
	}

	/**
	 * Load a file.
	 */
	public abstract void loadFile(Model m, String fn) throws java.io.IOException ;

	/**
	 * Save a file.
	 */
	public abstract void saveFile(Model m, String fn) throws java.io.IOException ;
}
