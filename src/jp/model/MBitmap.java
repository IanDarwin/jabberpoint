import java.awt.*;

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
	protected Image im;
	/** The image name */
	protected String imName;

	/** Construct an M given type, level and String */
	public MBitmap(int lev, String name) {
		level = lev;
		imName = name;
		if (imName == null) {
			// warn("Null bitmap filename");
			return;
		}
		im = Toolkit.getDefaultToolkit().getImage(imName);
	}

	/** Construct an M with no data */
	public MBitmap() {
		level = 0;
		label = null;
	}

	public Dimension getBBox() {
		return new Dimension(im.width, im.height);
	}

	public void draw(int x, int y, Graphics g, Style s) {
		g.drawImage(im, x, y);
	}

	public String toString() {
		return "MBitmap[" + level + "," + name + "]";
	}
}
