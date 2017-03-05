package jp.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import jp.Slide;
import jp.model.M;
import jp.model.MBitmap;
import jp.model.MText;
import jp.model.Model;

/** Convert a JabberPoint file to HTML.
 * Filename is unused; writes to a series of HTML files.
 * @author Ian Darwin
 * @version $Id$
 */
public class AccessorHTML extends AbstractAccessor {

	protected static String SUBDIR = "htmlshow";
	protected static File subdirFile = new File(SUBDIR);
	protected static final String SLASH = File.separator;
	protected static String TOCFILENAME="index.html";
	protected final NumberFormat nf = new DecimalFormat("00");

	public AccessorHTML(String fn) {
		super(fn);
	}

	private String mkFile(String fn) throws IOException {
		if (!subdirFile.exists()) {
			subdirFile.mkdirs();
		}
		return SUBDIR + SLASH + fn;
	}

	/**
	 * Load a file.
	 */
	public void loadFile(Model m, String fn) throws IOException {
		throw new IllegalStateException("Cannot load HTML files!");
	}

	/**
	 * Save a file.
	 */
	public void saveFile(Model model, String unusedFileName)
	throws IOException {
		String outFileName = null, oldFileName = null;
		PrintWriter outFile = null;
		int pageNum = 0;

		System.out.println("Making HTML for show named " + model.getShowTitle());
		PrintWriter tocFile = new PrintWriter(new FileWriter(mkFile(TOCFILENAME)));

		tocFile.println("<HTML>");
		tocFile.println("<!-- made by $Id$");
		tocFile.println(" -->");
		tocFile.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"stylesheet.css\" TITLE=\"Style\">");
		tocFile.println("<BODY BGCOLOR=WHITE>");
		tocFile.println("<TITLE>"+model.getShowTitle()+": Table of Content</TITLE>");
		mkNavigator(tocFile, "Contents Page", TOCFILENAME, TOCFILENAME, mkFileName(1));
		tocFile.println("<H1 ALIGN=CENTER>"+model.getShowTitle()+": Table of Content</H1>");
		tocFile.println("<OL>");


		for (int i=0; i<model.getSize(); i++) {
			Slide s = model.getSlide(i);
			if (outFile != null)
				outFile.close();
			outFileName = mkFileName(++pageNum);
			tocFile.println("<LI><A HREF=\""+outFileName+"\">"+s.getTitle()+"</A></LI>");
			outFile = new PrintWriter(new FileWriter(mkFile(outFileName)));
			outFile.println("<HTML>");
			outFile.println("<HEAD>");
			outFile.println("<TITLE>"+s.getTitle()+"</TITLE>");
			outFile.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"stylesheet.css\" TITLE=\"Style\">");
			outFile.println("<BODY BGCOLOR=WHITE>");
			if (oldFileName == null)
				oldFileName = "index.html";
			mkNavigator(outFile, "Page "+pageNum + " of " + model.getSize(),
				oldFileName, TOCFILENAME, mkFileName(1+pageNum));
			outFile.println("<H1>"+s.getTitle()+"</H1>");

			oldFileName = outFileName;

			for (int l=0; l<s.getSize(); l++) {
				M m = s.getM(l);
				if (m instanceof MText) {
					switch(m.getLevel()) {
					case 4:
					case 3:
						para(outFile, "H5", ((MText)m).getText());
						break;
					case 2:
						para(outFile, "H4", ((MText)m).getText());
						break;
					case 1:
						para(outFile, "H3", ((MText)m).getText());
						break;
					case 0:
						para(outFile, "H2", ((MText)m).getText());
						break;
					default: System.err.println("Unknown level in " + m);
						break;
					}
				} else if (m instanceof MBitmap) {
					outFile.println("<IMG SRC=\"" + ((MBitmap)m).getName() + "\"></IMG>");
				} else
					System.err.println("Unknown type " + m);
			}
		}

		tocFile.close();

		oldFileName = outFileName;
		outFile.close();
		outFileName = mkFileName(++pageNum);
		outFile = new PrintWriter(new FileWriter(mkFile(outFileName)));
		outFile.println("<H1>/*EOF - "+model.getShowTitle()+"</H1>");
		outFile.println("That is the end of the slide show named ");
		outFile.println("<I>"+model.getShowTitle()+"</I>. Please go");
		outFile.println("<A HREF="+oldFileName+">b a c k</A>.");
		outFile.close();

	}


	/** Make up the fileName for a page */
	private String mkFileName(int pgNum) {
		return "page" + nf.format(pgNum) + ".html";
	}

	private void para(PrintWriter out, String tag, String line) {
		out.println("<"+tag+">"+line+"</"+tag+">");
	}

	/** Generate the navigator at the top of the page.
	 * This version uses a 3 column HTML table.
	 */
	private void mkNavigator(PrintWriter outFile, String pageInd,
			String prev, String ind, String nxt) {
			outFile.println("<TABLE BGCOLOR=#CC0000 WIDTH=100%>");
			outFile.println("<TR>");
			outFile.println("<TD ALIGN=CENTER><A ID=Nav HREF="+prev+">&lt;&lt; Back</A>");
			outFile.println("<TD ALIGN=CENTER><A ID=Nav HREF="+ind+">Contents</A>");
			outFile.println("<TD ALIGN=CENTER><A ID=Nav HREF="+nxt+">Next &gt;&gt;</A>");
			outFile.println("</TR>");
			outFile.println("<TR><TD><TD ALIGN=CENTER ID=Nav><FONT ID=Nav>"+pageInd+"</FONT><TD></TR>");
			outFile.println("</TABLE>");
	}
}
