import java.awt.*;
/** Style == Indent, Color, Font and Leading */
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
