import java.util.Vector;

/** Represents one slide in a slideshow.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class Slide {
	/** each page contains a Vector of M's */
	Vector ms;

	public Slide() {
		ms = new Vector();
	}
	public void append(int lev, String msg) {
		ms.addElement(new M(M.TEXT, lev, msg));
	}
	public Vector getMs() {
		return ms;
	}
}
