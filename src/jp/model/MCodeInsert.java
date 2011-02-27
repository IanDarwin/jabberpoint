package jp.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.JOptionPane;

import jp.Style;

/** The data model to display an external file in a code editor
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class MCodeInsert extends M {

	/** The filename */
	protected String fileName;

	/** A helper object, to display a canned message */
	M mesg = new MText(1, "(see code window)");

	/** Construct an M given type, level and String */
	public MCodeInsert(String s) {
		fileName = s;
	}

	public String getText() {
		return "Code include of " + fileName;
	}

	public int getLevel() {
		return level;
	}

	public Dimension getBBox(ImageObserver o) {
		// FontMetrics fm = JabberPoint.view.getFontMetrics(JabberPoint.styles[level].font);
		// return new Dimension(fm.stringWidth(label), fm.getAscent());
		return new Dimension(600, 800);
	}

	boolean shown;

	public void draw(int x, int y, Graphics g, Style s, ImageObserver o) {
		g.setFont(s.getFont());
		g.setColor(s.getColor());
		mesg.draw(x, y, g, s, o);
		if (!shown) {
			try {
				Runtime.getRuntime().exec(
					"xterm -fn lucidasanstypewriter-18 -T " + fileName +
					" -e vi " + fileName);
				shown = true;
			} catch (java.io.IOException ex) {
				JOptionPane.showMessageDialog(null,
					ex.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public String toString() {
		return "MCode[" + level+","+fileName+"]";
	}

	public String getFileName() {
		return fileName;
	}
}
