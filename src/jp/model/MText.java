import java.awt.*;
import java.awt.image.ImageObserver;

/** The data model, for one TEXT item.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class MText extends M {

	/** The label or text of this thing */
	protected String label;

	/** Construct an M given type, level and String */
	public MText(int lev, String s) {
		level = lev;
		label = s;
	}

	/** Construct an M with no data */
	public MText() {
		this(0, "NO TEXT GIVEN");
	}

	public String getText() {
		return label;
	}

	public int getLevel() {
		return level;
	}

	public Dimension getBBox(ImageObserver o) {
		FontMetrics fm = JabberPoint.view.getFontMetrics(JabberPoint.styles[level].font);
		return new Dimension(fm.stringWidth(label), fm.getAscent());
	}

	public void draw(int x, int y, Graphics g, Style s, ImageObserver o) {
		g.setFont(s.font);
		g.setColor(s.color);
		g.drawString(label, x, y);
	}

	public String toString() {
		return "MText[" + level+","+label+"]";
	}
}
