import java.awt.Dimension;
import java.awt.Graphics;

/** The data model, for one item.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public abstract class M {

	/** The level this thing is at (indent) */
	protected int level = 0;

	/** Construct an M given type, level and String */
	public M(int lev) {
		level = lev;
	}
	/** Construct an M with no data */
	public M() {
		level = 0;
	}

	public abstract Dimension getBBox();

	public abstract void draw(int x, int y, Graphics g, Style s);
}
