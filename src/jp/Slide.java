package jp;

import java.util.ArrayList;
import java.util.List;

import jp.model.M;
import jp.model.MText;

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

	/** Each slide has a title */
	protected String title;

	/** each page contains a List of M's */
	protected List<M> ms = new ArrayList<M>();

	/** Append an M. */
	public void append(M anItem) {
		ms.add(anItem);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String nt) {
		title = nt;
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
		return (M)ms.get(n);
	}

	/** Get the List of all Ms */
	public List<M> getMs() {
		return ms;
	}

	public int getSize() {
		return ms.size();
	}
	
	@Override
	public String toString() {
		if (title != null && title.length() != 0) {
			return title;
		}
		return super.toString();
	}
}
