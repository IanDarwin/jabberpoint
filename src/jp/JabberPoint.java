import java.awt.*;
import java.awt.event.*;

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
	protected Frame frame;
	/** The model */
	protected static Model model;
	/** The view */
	protected static ShowView view;
	/** The styles */
	protected static Style[] styles;

	/** The Real Main Program */
	public static void main(String argv[]) {

		JabberPoint jp = new JabberPoint();

		if (argv.length == 0) // run a demo program
			new Demo(model).loadDemo();
		else
			model.loadFile(argv[0]); // read and parse a slideshow file(s)...

		// Start view at first page
		jp.model.setPage(0);
	}

	/** Construct a JabberPoint Program */
	JabberPoint() {

		model = new Model();			// model,
		view = new ShowView();
		model.addObserver(view);		// view,

		frame = new Frame("JabberPoint 0.0");	// GUI
        frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.add(view);
		frame.pack();
		frame.setVisible(true);

		frame.addKeyListener(new KeyController(model));	// and controller.
		frame.setMenuBar(new MenuController(frame, model));	// Another controller

		styles = new Style[5];
		// Presumably these will come from a file
		styles[0] = new Style(100, Color.red,   48, 60);	// title
		styles[1] = new Style(20, Color.blue,  48, 54);	// main or H1
		styles[2] = new Style(50, Color.black, 36, 44);	// sub or H2
		styles[3] = new Style(70, Color.black, 30, 36);	// sub or H3
		styles[4] = new Style(90, Color.black, 24, 30);	// sub or H4

	}
}
