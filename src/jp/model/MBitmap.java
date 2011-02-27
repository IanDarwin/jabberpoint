package jp.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import jp.Style;

/** The data model, for one BITMAP item.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class MBitmap extends M {

	/** The level this thing is at (indent) */
	protected int level = 0;

	/** The Image, if it's a valid bitmap */
	protected Image image;
	/** The image name */
	protected String imageName;

	/** Construct an MBitmap given type, level and String */
	public MBitmap(int lev, String name) {
		level = lev;
		imageName = name;
		if (imageName == null) {
			// warn("Null bitmap filename");
			return;
		}
		image = Toolkit.getDefaultToolkit().getImage(imageName);
	}

	/** Construct an M with no data */
	public MBitmap() {
		level = 0;
		image = null;
	}

	/** Return the filename */
	public String getName() {
		return imageName;
	}

	/** Find the bounding box of the image */
	@Override
	public Dimension getBBox(ImageObserver o) {
		return new Dimension(image.getWidth(o), image.getHeight(o));
	}

	@Override
	public void draw(int x, int y, Graphics g, Style s, ImageObserver o) {
		// System.out.println("Drawing " + image + " at " + x + "," + y);
		g.drawImage(image, x, y, o);
	}

	@Override
	public String toString() {
		return "MBitmap[" + level + "," + imageName + "]";
	}
}
