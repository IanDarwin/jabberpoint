package jp.io;

import jp.model.Model;

/**
 * An Accessor lets you read/write Model data from/to a storage medium.
 * <p>
 * Non-abstract subclasses implement the methods.
 * Subclasses must be public for use in dynamic class loading.
 * @author Ian Darwin, ian@darwinsys.co
 * @version $Id$
 */
public abstract class AbstractAccessor implements Accessor {

	/** The current file name */
	protected String fileName;

	protected Model model;

	protected AbstractAccessor(String fn) {
		fileName = fn;
	}

	public AbstractAccessor() {
	}
}
