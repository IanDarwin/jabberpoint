import java.awt.*;
import java.awt.event.*;

/** JabberPoint Main Program */
public class JabberPoint {
	/** The Frame for the View */
	Frame frame;
	/** The model */
	static Model model;
	/** The view */
	static View view;
	/** The styles */
	static Style[] styles;

	/** The Real Main Program */
	public static void main(String av[]) {

		JabberPoint jp = new JabberPoint();

		// if (argv.length == 0) // run a demo program
		jp.doDemo();
		// else read and parse a slideshow file(s)...

		// Start view at first page
		jp.model.setPage(0);
	}

	/** Construct a JabberPoint Program */
	JabberPoint() {

		model = new Model();			// model,
		view = new View();
		model.addObserver(view);		// view,

		frame = new Frame("JabberPoint 0.0");	// GUI
        frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.add(view);
		frame.setVisible(true);

		frame.addKeyListener(new KeyController(model));	// and controller
		frame.setMenuBar(new MenuController(model));	// and controller

		styles = new Style[5];
		// Presumably these will come from a file
		styles[0] = new Style(100, Color.red,   48, 60);	// title
		styles[1] = new Style(20, Color.blue,  48, 52);	// main or H1
		styles[2] = new Style(50, Color.black, 36, 44);	// sub or H2
		styles[3] = new Style(70, Color.black, 30, 36);	// sub or H3
		styles[4] = new Style(90, Color.black, 24, 30);	// sub or H4

	}

	/** Run a demo for testing */
	public void doDemo() {
		Slide s = new Slide();
		model.append(s);
		s.append(0, "JabberPoint Slide Styles Demonstration");
		s.append(1, "Main Point");
		s.append(2, "Sub Point");
		s.append(1, "A Far Point");
		s.append(1, "A Powerful Point");
		s.append(1, "A Jabberful Point");
		s.append(2, "Sub Point");
		s.append(3, "SubSub Point");
		s.append(4, "SubSubSub Point");
		// Page 2
		s = new Slide();
		model.append(s);
		s.append(0, "Slide The Second");
		s.append(1, "Main Point of Slide 2");
	}
}
