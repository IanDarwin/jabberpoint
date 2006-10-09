package jp;

/**
 * This example demonstrates how to create a JList whose contents
 * are dynamic.  The list has a KeyListener that adds one character
 * list elements when any key is typed, the last element is removed
 * when backspace is typed.
 *
 * Tested against swing-1.1, JDK1.1.7.
 */


import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;


/**
 * Create a JList whose KeyListener adds/removes elements from
 * the list's DefaultListModel.
 */

public class DynamicList
{
    public static void main(String[] args) {
	final DefaultListModel model = new DefaultListModel();

	KeyListener keyTypedListener = new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		if ((e.getKeyChar() == '\b') && (model.getSize() > 0)) {
		    model.removeElementAt(model.getSize() - 1);
		}
		else if (e.getKeyChar() != '\b') {
		    model.addElement("Added " + e.getKeyChar());
		}
	    }
	};

	JList list = new JList(model);
	list.addKeyListener(keyTypedListener);

	JFrame frame = new JFrame("DefaultListModel JList Demo");

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) { System.exit(0); }
	};
	frame.addWindowListener(l);

	JScrollPane scrollPane = new JScrollPane(list);
	scrollPane.setBorder(new TitledBorder("Type to add list elements, backspace to remove"));

	frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
    }

}

