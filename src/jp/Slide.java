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

	/** Append an M. */
	public void append(M anItem) {
		ms.addElement(anItem);
	}

	public String getTitle() {
		if (ms.size() == 0) {
			return "UNTITLED SLIDE";
		}
		M m = getM(0);
		if (!(m instanceof MText))
			return "SLIDE WITH INVALID TITLE";
		return ((MText)m).getText();
	}

	/** Append a String.
	 * <P>This is a convenience routine for 
	 * <BR>append(new MText(lev, String));
	 */
	public void append(int lev, String msg) {
		append(new MText(lev, msg));
	}

	/** Get the given M (line or image) */
	public M getM(int n) {
		return (M)ms.elementAt(n);
	}

	/** Get the Vector of all Ms */
	public Vector getMs() {
		return ms;
	}

	public int getSize() {
		return ms.size();
	}
}
