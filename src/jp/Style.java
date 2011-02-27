package jp;

import java.awt.Color;
import java.awt.Font;

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

	public Style(int in, Color c, int size, int lead) {
		indent  = in;
		color   = c;
		font    = new Font("Helvetica", Font.BOLD, fontSize=size);
		leading = lead;
	}
	public String toString() {
		return "["+indent+","+color+"; "+fontSize+" on "+leading+"]";
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	public int getLeading() {
		return leading;
	}
	public void setLeading(int leading) {
		this.leading = leading;
	}
}
