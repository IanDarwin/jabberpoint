package jp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import jp.io.AccessorFactory;
import jp.model.Model;

import com.darwinsys.swingui.UtilGUI;

/** JabberPoint Main Program
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class JabberPoint {
	/** The Frame for the ShowView */
	protected static JFrame frame;
	/** The model */
	protected Model model;
	/** The view */
	protected static ShowView slideView;
	/** Other View */
	protected static JList textView;
	/** The styles XXX should not be static */
	protected static Style[] styles;
	private static Style codeStyle;

	/** The Real Main Program */
	public static void main(String argv[]) {

		JabberPoint jp = null;

		try {
			jp = new JabberPoint();

			if (argv.length == 0) { // run a demo program
				AccessorFactory.getInstance(AccessorFactory.DEMO_NAME).loadFile(jp.model, "");
			} else {
				AccessorFactory.getInstance(argv[0]).loadFile(jp.model, argv[0]);
			}
			jp.model.setSlideNumber(0);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
				"IO Error: " + ex, "JabberPoint Error",
				JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	/** Construct a JabberPoint Program */
	JabberPoint() {

		model = new Model();
		slideView = new ShowView(model);
		textView = new JList<Slide>(model);
		model.addObserver(slideView);		// view

		frame = new JFrame("JabberPoint 0.1");	// GUI
		model.setParentView(frame);
		JSplitPane pane = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT, textView, slideView);
		frame.setContentPane(pane);

		//frame.pack();
		UtilGUI.maximize(frame);
		pane.setResizeWeight(0.4);
		frame.setVisible(true);

		final KeyController keyController = new KeyController(model);
		slideView.addKeyListener(keyController);	// and a controller.
		textView.addKeyListener(keyController);
		frame.setJMenuBar(new MenuController(frame, model));	// Another one.

		frame.addWindowListener(new WindowAdapter() {	// Last controller.
			public void windowClosing(WindowEvent e) {
				// XXX save changes here
				System.exit(0);
			}
		});

		styles = new Style[] {
			// Presumably these will come from a file
			new Style(50, Color.red,   40, 48),	// title
			new Style(20, Color.blue,  32, 36),	// main or H1
			new Style(50, Color.black, 24, 28),	// sub or H2
			new Style(70, Color.black, 20, 24),	// sub or H3
			new Style(90, Color.black, 16, 20),	// sub or H4
		};
		codeStyle = new Style(50, Color.black, 20, 4);
	}

	public static Style getStyle(int lev) {
		if (lev >= styles.length)
			lev = styles.length - 1;
		return styles[lev];
	}

	public static Graphics getGraphics() {
		return frame.getGraphics();
	}

	public static Style getCodeStyle() {
		return codeStyle;
	}

	public static JFrame getFrame() {
		return frame;
	}
}

