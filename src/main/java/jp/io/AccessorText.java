package jp.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import jp.Slide;
import jp.model.MBitmap;
import jp.model.MCode;
import jp.model.MCodeInsert;
import jp.model.Model;

/**
 * A Model subclass for tab-indented text.
 * Input Format:
 * <pre>
 * Slide One
 * 	Point
 * 		Sub-point
 * Slide Two
 * B bitmap.jgp
 * Slide Three
 * C Demo.java
 * </pre>
 * $Id$
 */
public class AccessorText extends AbstractAccessor {

	/** Construct */
	protected AccessorText(String fileName) {
		super(fileName);
	}

	public AccessorText() {
	}

	/**
	 * Load a file and scan, using BufferedReader
	 */
	public void loadFile(Model model, String fn) throws IOException {
		if (fn == null) {
			System.err.println("Code for FileChooser not here yet");
			return;
		}
		BufferedReader is = new BufferedReader(new FileReader(fn));
		loadFile(model, is);
	}

	public void loadFile(Model model, BufferedReader is) throws IOException {
		Slide sl = null;
		String line;
		model.clear();

		model.setShowTitle(is.readLine());

		while ((line = is.readLine()) != null) {
			if (line.length() == 0)	// null lines make trouble
				continue;
			if (line.startsWith("\t")) {
				int lev;
				for (lev=0; line.charAt(lev) == '\t'; lev++)
					/* nullbody--count tabs */
					;
				if (sl == null) { sl = new Slide(); model.append(sl); }
				sl.append(lev, line.substring(lev));
			} else if (line.startsWith("B ")) {
				if (sl == null) { sl = new Slide(); model.append(sl); }
				sl.append(new MBitmap(1, line.substring(1).trim()));
			} else if (line.startsWith("C ")) {
				if (sl == null) { sl = new Slide(); model.append(sl); }
				sl.append(new MCode(line.substring(1).trim()));
			} else if (line.startsWith("I ")) {
				if (sl == null) { sl = new Slide(); model.append(sl); }
				sl.append(new MCodeInsert(line.substring(1).trim()));
			} else {
				// Line beginning at left, start a new slide
				sl = new Slide();
				sl.setTitle(line);
				model.append(sl);
			}
		}
		is.close();
	}

	public void saveFile(Model model, String fn) throws IOException {
		throw new IOException("saveFile() not written yet");
	}
}
