import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.ImageObserver;
import java.text.*;
import java.util.*;

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

	/** The text of this line */
	protected String text;
	/** The TextLayouts corresponding to "text" */
	java.util.List layouts;

	/** Construct an M given type, level and String */
	public MText(int lev, String s) {
		level = lev;
		text = s;
	}

	/** Construct an M with no data */
	public MText() {
		this(0, "NO TEXT GIVEN");
	}

	public String getText() {
		return text;
	}

	public int getLevel() {
		return level;
	}

	/** Get the BBOX for this text item, by maxing the widths
	 * and summing the heights of each TextLayout (~= "line").
	 */
	public Dimension getBBox(ImageObserver o) {
		if (layouts == null)
			getLayouts();
		int xsize = 0, ysize = 0;
		Iterator it = layouts.iterator();
		Style s = JabberPoint.getStyle(level);
		while (it.hasNext()) {
			TextLayout layout = (TextLayout) it.next();
			Rectangle2D bounds = layout.getBounds();
			if (bounds.getWidth() > xsize)
				xsize = (int)bounds.getWidth();
			//ysize += bounds.getHeight() + s.leading;
			ysize += s.leading;
		}
		return new Dimension(xsize, ysize);
	}

	public void draw(int x, int y, Graphics g, Style s, ImageObserver o) {
		if (text == null || text.length() == 0)
			return;
		if (layouts == null)
			getLayouts();

		Point pen = new Point(0, 0);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(s.color);
		g2d.setFont(s.font);

		Iterator it = layouts.iterator();
		while (it.hasNext()) {
			TextLayout layout = (TextLayout) it.next();
			pen.y += (layout.getAscent());
			g2d.setFont(s.font);
			layout.draw(g2d, x+pen.x, y+pen.y);
			pen.y += layout.getDescent();
			//pen.y += s.leading;
		}
	}

	/** Lazy evaluation of the List of TextLayout objects corresponding
	 * to this MText. Some things are approximations!
	 */
	private void getLayouts() {
		layouts = new ArrayList();

		Point pen = new Point(10, 20);
		Graphics2D g2d = (Graphics2D) JabberPoint.getGraphics();
		FontRenderContext frc = g2d.getFontRenderContext();

		Style s = JabberPoint.getStyle(level);

		AttributedString attrStr = new AttributedString(text);
		attrStr.addAttribute(TextAttribute.FONT, s.font, 0, text.length());   
		LineBreakMeasurer measurer = new LineBreakMeasurer(
			attrStr.getIterator(), frc);
		float wrappingWidth;

		// wrappingWidth = getSize().width - 15;
		wrappingWidth = 800 - 15;

		while (measurer.getPosition() < text.length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}
	}

	public String toString() {
		return "MText[" + level+","+text+"]";
	}
}
