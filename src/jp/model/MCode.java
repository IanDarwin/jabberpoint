package jp.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.JOptionPane;

import jp.JabberPoint;
import jp.Style;


/** The data model, for a CODE item.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class MCode extends MText {

	/** Construct an MCode given code String */
	public MCode(String s) {
		super(0, s);
	}
	
	MCode() {
		this("NO TEXT GIVEN");
	}

	@Override
	public Style getStyle() {
		return JabberPoint.getCodeStyle();
	}
	
	@Override
	public Dimension getBBox(ImageObserver o) {
		return new Dimension(900, 36);
	}
	
	@Override
	public String toString() {
		return "MCode[" + level+","+getText()+"]";
	}
}
