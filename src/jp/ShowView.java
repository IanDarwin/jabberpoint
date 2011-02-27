package jp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import jp.model.M;
import jp.model.MText;
import jp.model.Model;

/** JabberPoint Slide Show - Presentation View.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class ShowView extends JComponent implements Observer {

	/** The collection of M's on the current page */
	protected Slide slide;
	/** Our preferred Width */
	protected int prefWidth;
	/** Our preferred Height */
	protected int prefHeight;
	/** The label font */
	protected Font labelFont = null;
	/** The Model */
	protected Model model = null;

	/** Construct a ShowView */
	ShowView(Model m) {
		setBackground(Color.white);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		prefWidth = screenSize.width;
		prefHeight = screenSize.height;
		model = m;
		labelFont = new Font("Dialog", Font.BOLD, 10);
	}

	public Dimension getPreferredSize() {
		if (prefWidth>800)
			prefWidth = 800;
		if (prefHeight>600)
			prefHeight = 600;
		return new Dimension(prefWidth, prefHeight);
	}

	public void update(Observable model, Object data) {
		if (!(model instanceof Model))
			throw new IllegalArgumentException("Internal error: model not Model");
		if (data == null) {
			repaint();
			return;
		}
		if (!(data instanceof Slide))
			throw new IllegalArgumentException("Observable gave bad Slide");
		model = (Model)model;
		slide = (Slide)data;
		repaint();
	}

	/** The paint routine: draw the text and other goo of this slide. */
	public void paint(Graphics g) {

		int indent;
		int y = 20;

		// Clear the screen
		g.setColor(Color.white);
		g.fillRect(0, 0, getSize().width, getSize().height);

		if (model.getPageNumber() < 0)
			return;

		if (slide == null) {
			System.err.println("ShowView.paint called while slide is null");
			return;
		}

		java.util.List v = slide.getMs();
		if (v == null) {
			System.err.println("ShowView.paint: getMs() yields null");
			return;
		}

		g.setFont(labelFont);
		g.setColor(Color.black);
		g.drawString("Slide " + (1+model.getSlideNumber()) + " of " +
			model.getSize(),
			600, 30);

		// Handle title specially
		M m = new MText(0, slide.getTitle());
		Style s = JabberPoint.getStyle(0);
		g.setFont(s.font);
		m.draw(0, y, g, s, this);
		y += m.getBBox(this).height;

		for (int i=0; i<v.size(); i++) {
			m = (M)v.get(i);
			// System.out.println(m);
			s = JabberPoint.getStyle(m.getLevel());
			g.setFont(s.font);
			Dimension box = m.getBBox(this);
			// if (m.level == 0)
				// indent = (getSize().width-box.width)/2;
			// else
				indent = s.indent;

			// DRAW IT
			m.draw(indent, y, g, s, this);

			y += box.height;
		}
	}
}
