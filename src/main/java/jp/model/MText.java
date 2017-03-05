package jp.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.JabberPoint;
import jp.Style;

/**
 * The data model, for one TEXT item.
 * <P>
 * This program is distributed under the terms of the accompanying COPYRIGHT.txt
 * file (which is NOT the GNU General Public License). Please read it. Your use
 * of the software constitutes acceptance of the terms in the COPYRIGHT.txt
 * file.
 *
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class MText extends M {

	/** The text of this line */
	protected String text;

	/** The TextLayouts corresponding to "text" */
	List<TextLayout> layouts;

	private int MAX_INDENT = 4;

	/** Construct an M given type, level and String */
	public MText(int lev, String s) {
		text = s;
		setLevel(lev);
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
	
	public void setLevel(int level) {
		if (level >= 0 && level <= MAX_INDENT) { 
			this.level = level;
		}
	}
	
	public void indent() {
		if (level > MAX_INDENT) {			
			level++;
		}
	}
	
	public void undent() {
		if (level > 0) {
			--level;
		}
	}

	/**
	 * Get the BBOX for this text item, by maxing the widths and summing the
	 * heights of each TextLayout (~= "line").
	 */
	@Override
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
				xsize = (int) bounds.getWidth();
			// ysize += bounds.getHeight() + s.leading;
			ysize += s.getLeading();
		}
		return new Dimension(xsize, ysize);
	}

	@Override
	public void draw(int x, int y, Graphics g, Style s, ImageObserver o) {
		if (text == null || text.length() == 0)
			return;
		if (layouts == null)
			getLayouts();

		Point pen = new Point(0, 0);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(s.getColor());
		g2d.setFont(s.getFont());

		Iterator it = layouts.iterator();
		while (it.hasNext()) {
			TextLayout layout = (TextLayout) it.next();
			pen.y += (layout.getAscent());
			g2d.setFont(s.getFont());
			layout.draw(g2d, x + pen.x, y + pen.y);
			pen.y += layout.getDescent();
			// pen.y += s.leading;
		}
	}

	/**
	 * Lazy evaluation of the List of TextLayout objects corresponding to this
	 * MText. Some things are approximations!
	 */
	private void getLayouts() {
		layouts = new ArrayList<TextLayout>();

		// Point pen = new Point(10, 20);
		Graphics2D g2d = (Graphics2D) JabberPoint.getGraphics();
		FontRenderContext frc = g2d.getFontRenderContext();

		Style s = getStyle();

		AttributedString attrStr = new AttributedString(text);
		attrStr.addAttribute(TextAttribute.FONT, s.getFont(), 0, text.length());
		LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr
				.getIterator(), frc);
		float wrappingWidth;

		// wrappingWidth = getSize().width - 15;
		wrappingWidth = 900 - 15;	// XXX

		while (measurer.getPosition() < text.length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}
	}

	@Override
	public String toString() {
		return "MText[" + level + "," + text + "]";
	}
}
