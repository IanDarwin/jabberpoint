import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** JabberPoint View */
public class View extends Component implements Observer {
	/** The collection of M's on the current page */
	Slide slide;

	/** Construct a View */
	View() {
		setBackground(Color.white);
		Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, them.width, them.height);
	}

	public void update(Observable model, Object data) {
		if (!(model instanceof Model))
			throw new IllegalArgumentException("Wimpout 1");
		if (!(data instanceof Slide))
			throw new IllegalArgumentException("Observable gave bad Slide");
		slide = (Slide)data;
		repaint();
	}

	/** The Paint routine, draw the text */
	public void paint(Graphics g) {
		int y = 75;
		int indent;
		if (slide == null) {
			// System.err.println("View.paint called while slide is null");
			return;
		}
		Vector v = slide.getMs();
		if (v == null) {
			System.err.println("View.paint: getMs() yields null");
			return;
		}
		for (int i=0; i<v.size(); i++) {
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
					"Unknown type (" + m.type + ") found in View.paint()");
			}
		}
	}

}
