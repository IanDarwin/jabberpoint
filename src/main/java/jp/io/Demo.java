package jp.io;

import jp.Slide;
import jp.model.MBitmap;
import jp.model.Model;

/*
 * Run a built-in demo.
 */
class Demo extends AbstractAccessor {

	Demo(String fn) {
		super(fn);
	}

	/** Run a demo for testing */
	public void loadFile(Model m, String unusedFilename) {
		Slide s;

		s = new Slide();
		s.setTitle("JabberPoint");
		s.append(1, "The Java Presentation Tool");
		s.append(2, "Copyright (c) 1996-2000 Ian Darwin");
		s.append(1, "Invoking JabberPoint with");
		s.append(1, "no file argument runs this show");
		s.append(1, "To move around:");
		s.append(2, "Next Page: PgDn or Enter");
		s.append(2, "Prev Page: PgUp or up-arrow");
		s.append(2, "To exit: q or Q");
		m.append(s);

		s = new Slide();
		s.setTitle("JabberPoint Styles Demonstration");
		s.append(1, "Main Point");
		s.append(2, "Sub Point");
		s.append(1, "A Far Point");
		s.append(1, "A Powerful Point");
		s.append(1, "A Jabberful Point");
		s.append(2, "Sub Point");
		s.append(3, "SubSub Point");
		s.append(4, "SubSubSub Point");
		m.append(s);

		// Page 2
		s = new Slide();
		s.setTitle("Slide The Third");
		s.append(1, "Main Point of Slide 2");
		s.append(2, "To load a file here, use File->Open");
		s.append(1, " ");
		s.append(1, "This is the end of the show.");
		s.append(new MBitmap(1, "JabberPoint.jpg"));
		m.append(s);
	}

	public void saveFile(Model m, String unusedFilename) {
		throw new IllegalStateException("Called Save As->Demo!");
	}
}
