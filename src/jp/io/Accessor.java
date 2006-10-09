package jp.io;

import java.io.IOException;

import jp.model.Model;


/**
 * An Accessor lets you read/write Model data from/to a storage medium.
 * <p>
 * Non-abstract subclasses implement the methods.
 * Subclasses must be public for use in dynamic class loading.
 * @author Ian Darwin, ian@darwinsys.co
 * @version $Id$
 */
public interface Accessor {

	/**
	 * Load a file.
	 */
	public abstract void loadFile(Model m, String fn) throws IOException;

	/**
	 * Save a file.
	 */
	public abstract void saveFile(Model m, String fn) throws IOException;
}