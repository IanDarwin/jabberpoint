import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
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
	/** The styles */
	protected static Style[] styles;

	/** The Real Main Program */
	public static void main(String argv[]) {

		JabberPoint jp = new JabberPoint();

		try {
			if (argv.length == 0) { // run a demo program
				Accessor.getInstance(Accessor.DEMO_NAME).loadFile(jp.model, "");
			} else {
				Accessor.getInstance(argv[0]).loadFile(jp.model, argv[0]);
			}
			jp.model.setSlideNumber(0);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
				"IO Error: " + ex, "JabberPoint Error",
				JOptionPane.ERROR_MESSAGE);
		}
	}

	/** Construct a JabberPoint Program */
	JabberPoint() {

		model = new Model();
		slideView = new ShowView(model);
		textView = new JList(model);
		model.addObserver(slideView);		// view,

		frame = new JFrame("JabberPoint 0.0");	// GUI
		JSplitPane pane = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT, slideView, textView);
		frame.setContentPane(pane);

		//frame.pack();
		UtilGUI.maximize(frame);
		pane.setResizeWeight(0.4);
		frame.setVisible(true);

		frame.addKeyListener(new KeyController(model));	// and a controller.
		frame.setMenuBar(new MenuController(frame, model));	// Another one.

        frame.addWindowListener(new WindowAdapter() {	// Last controller.
			public void windowClosing(WindowEvent e) {
				// XXX save changes here
				System.exit(0);
			}
		});

		styles = new Style[5];
		// Presumably these will come from a file
		styles[0] = new Style(50, Color.red,   48, 60);	// title
		styles[1] = new Style(20, Color.blue,  40, 46);	// main or H1
		styles[2] = new Style(50, Color.black, 36, 44);	// sub or H2
		styles[3] = new Style(70, Color.black, 30, 36);	// sub or H3
		styles[4] = new Style(90, Color.black, 24, 30);	// sub or H4

	}

	public static Style getStyle(int lev) {
		if (lev >= styles.length)
			lev = styles.length - 1;
		return styles[lev];
	}

	public static Graphics getGraphics() {
		return frame.getGraphics();
	}
}
