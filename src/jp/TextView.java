package jp;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import jp.model.Model;

/** JabberPoint Text View.
 * <p>
 * Status - NOT WORKING YET
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class TextView extends JComponent implements Observer {

	/** The View */
	protected MutableList listView;
	/** The collection of M's on the current page */
	protected Slide slide;
	/** Our preferred Width */
	protected int prefWidth;
	/** Our preferred Height */
	protected int prefHeight;
	/** The label font */
	protected Font labelFont = null;
	/** The Model */
	protected Model model = null;

	class MutableList extends JList {
		MutableList() {
			super(new DefaultListModel());
		}
		DefaultListModel getListModel() {
			return (DefaultListModel)getModel();
		}
	}

	/** Construct a TextView */
	TextView(Model m) {

		// Create the ListView.
		listView = new MutableList();

		DefaultListModel lm = listView.getListModel();

		lm.addElement("Slide One - Introduction");
		lm.addElement("Slide Two - Introduction");
		lm.addElement("Slide Three - Introduction");
		lm.addElement("Slide Four - Introduction");
		lm.addElement("Slide Five - Introduction");
		lm.addElement("Slide Six - Introduction");

		// listView.setCellRenderer(new TextFieldRenderer());

		// Make the JList be our (only) JComponent child.
		add(new JScrollPane(listView));

		// Save the Model reference.
		model = m;
	}

	public void update(Observable model, Object data) {
		if (!(model instanceof Model))
			throw new IllegalArgumentException("Internal error: model not Model");
		if (data == null) {
			repaint();
			return;
		}
		if (!(data instanceof Slide))
			throw new IllegalArgumentException("Observable gave bad Slide");
		model = (Model)model;
		slide = (Slide)data;
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("hello");
		jf.getContentPane().add(new TextView(new Model()));
		jf.pack();
		jf.setVisible(true);
	}
}
