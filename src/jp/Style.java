import java.awt.*;

/** Style == Indent, Color, Font and Leading.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class Style {
	int indent;

	Color color;

	Font font;

	int fontSize;

	int leading;

	public Style(int in, Color c, int pts, int lead) {
		indent  = in;
		color   = c;
		font    = new Font("Helvetica", Font.BOLD, fontSize=pts);
		leading = lead;
	}
	public String toString() {
		return "["+indent+","+color+"; "+fontSize+" on "+leading+"]";
	}
}
