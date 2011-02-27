package jp;

import java.awt.Frame;

import javax.swing.JOptionPane;

/** This is the About dialog for JabberPoint.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class AboutBox {
	public static void show(Frame parent) {
		JOptionPane.showMessageDialog(parent,
		"JabberPoint(tm) -- the free, cross-platform slideshow\n " +
		"JabberPoint is a primitive slide-show program in Java(tm). It\n" +
		"is freely copyable as long as you keep this notice and\n" +
		"the splash screen intact.\n" +
		"Copyright (c) 1995-2011 by Ian F. Darwin, ian@darwinsys.com.\n" +
		"Author's version available from http://www.darwinsys.com/",
		"About JabberPoint",
		JOptionPane.INFORMATION_MESSAGE
		/* new ImageIcon(myImage) */
		);
	}
}
