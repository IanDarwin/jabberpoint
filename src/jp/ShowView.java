import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** JabberPoint Show View */
public class ShowView extends Component implements Observer {
	/** The collection of M's on the current page */
	Slide slide;
	/** Our preferred Width */
	int prefWidth;
	/** Our preferred Height */
	int prefHeight;
	/** The label font */
	Font labelFont = null;
	/** The Model */
	Model model = null;

	/** Construct a ShowView */
	ShowView() {
		setBackground(Color.white);
		Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
		prefWidth = them.width;
		prefHeight = them.height;
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
			throw new IllegalArgumentException("Wimpout 1");
		if (!(data instanceof Slide))
			throw new IllegalArgumentException("Observable gave bad Slide");
		model = (Model)model;
		slide = (Slide)data;
		repaint();
	}

	/** The Paint routine, draw the text */
	public void paint(Graphics g) {
		int y = 75;
		int indent;
		if (slide == null) {
			// System.err.println("ShowView.paint called while slide is null");
			return;
		}
		Vector v = slide.getMs();
		if (v == null) {
			System.err.println("ShowView.paint: getMs() yields null");
			return;
		}
		if (model != null)
			g.drawString("Slide " + model.getCurrentNumber() + " of " + model.getSize(),
				getSize().width-150, getSize().height-30);
		for (int i=0; i<v.size(); i++) {
			g.setFont(labelFont);
			M m = (M)v.elementAt(i);
			// System.out.println(m);
			Style s = JabberPoint.styles[m.level];
			if (m.level == 0)
				indent = (getSize().width-m.getBBox().width)/2;
			else
				indent = s.indent;
			// System.out.println(s);
			switch(m.type) {
				case M.TEXT:
					g.setFont(s.font);
					g.setColor(s.color);
					g.drawString(m.label, indent, y);
					y += s.leading;
					break;
				default:
					throw new IllegalArgumentException(
					"Unknown type (" + m.type + ") found in ShowView.paint()");
			}
		}
	}

}
