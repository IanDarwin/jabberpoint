import java.awt.*;

/** The data model, for one item */
public class M {
	/** A Type for Text */
	public static final int TEXT=1;
	/** A Type for Bitmaps: PNG, GIF, JPEG, etc. */
	public static final int IMAGE=2;
	/** A Type for Drawings: WMF, AI, non-bitmap EPS, etc. */
	public static final int DRAWING=3;

	/** The type of thing this is */
	protected int type = 0;

	/** The level this thing is at (indent) */
	protected int level = 0;

	/** The label or text of this thing */
	protected String label;

	/** The Image, if it's a bitmap */
	protected Image im;

	/** Construct an M given type, level and String */
	public M(int t, int lev, String s) {
		type = t;
		level = lev;
		label = s;
	}
	/** Construct an M with no data */
	public M() {
		type = 0;
		level = 0;
		label = null;
	}
	public Dimension getBBox() {
		switch(type) {
		case TEXT:
			FontMetrics fm = JabberPoint.view.getFontMetrics(JabberPoint.styles[level].font);
			return new Dimension(fm.stringWidth(label), fm.getAscent());
		// case IMAGE:
			// return new Dimension(im.width, im.height);
		default:
			return new Dimension(0,0);
		}
	}
	public String toString() {
		return "["+type+","+level+","+label+"]";
	}
}
