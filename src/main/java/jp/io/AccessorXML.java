package jp.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jp.Slide;
import jp.model.M;
import jp.model.MText;
import jp.model.Model;

/**
 * A Model subclass for XML-based text.
 *
 * $Id$
 */
public abstract class AccessorXML extends AbstractAccessor {

	/** Construct */
	protected AccessorXML(String fileName) {
		super(fileName);
	}

	public AccessorXML() {
	}

	/**
	 * Load a file and scan.
	 */
	public abstract void loadFile(Model mod, String fn) throws IOException;

	/**
	 * Save a file in XML.
	 */
	public void saveFile(Model model, String fn) throws IOException {
		System.out.println("saveFile(" + fn + ")");
		PrintWriter out = new PrintWriter(new FileWriter(fn));

		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE slideshow SYSTEM \"jabberpoint.dtd\">");
		out.println("<slideshow>");

		for (int i=0; i<model.getSize(); i++) {
			Slide s = model.getSlide(i);
			out.println("<slide>");
			out.print("<head><title>");
			out.print(s.getTitle());
			out.println("</title></head>");

			List v = s.getMs();
			for (int n = 0; n<v.size(); n++) {
				M m = (M)v.get(n);
				if (m instanceof MText && m.getLevel() > 0) {
					out.print("<h" + m.getLevel() + ">");
					putText(out, ((MText)m).getText());
					out.println("</h" + m.getLevel() + ">");
				} else {
					System.out.println("Ignoring " + m); }
			}
			out.println("</slide>");
		}
		out.println("</slideshow>");
		out.close();
	}

	private void putText(PrintWriter out, String text) {
		if (text.indexOf('<') >= 0 ||
			text.indexOf('>') >= 0 ||
			text.indexOf('&') >= 0)
		 {
			out.println("<![CDATA[");
			out.println(text);
			out.print("]]>");
		} else {
			out.print(text);
		}
	}
}
