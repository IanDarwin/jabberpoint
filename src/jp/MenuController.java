package jp;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** This is the Menu Controller for the View.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class MenuController extends MenuBar {
	/** The Frame, used only for parenting Dialogs */
	JFrame parent;
	/** The Model which we are controlling */
	Model model;

	public MenuController(JFrame f, Model m) {
		parent = f;
		model = m;

		MenuItem mi;

		ResourceBundle b = ResourceBundle.getBundle("JabberPointMenus");

		Menu fm = mkMenu(b, "file");
		fm.add(mi = mkMenuItem(b, "file", "open"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(new File("."));
				int ret = chooser.showOpenDialog(parent);
				if (ret == -1) {
					return;
				}
				File selectedFile = chooser.getSelectedFile();
				if (selectedFile == null) {
					return;
				}
				String fileName = selectedFile.getAbsolutePath();
				Accessor xacc = Accessor.getInstance(fileName);
				try {
					xacc.loadFile(model, fileName);
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(parent,
						"IOException: " + ex, "Load Error",
						JOptionPane.ERROR_MESSAGE);
				}
				parent.repaint();
			}
		});
		fm.add(mi = mkMenuItem(b, "file", "new"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clear();
				parent.repaint();
			}
		});
		fm.add(mi = mkMenuItem(b, "file", "save"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accessor xacc = Accessor.getInstance("dump.xml");
				try {
					xacc.saveFile(model, "dump.xml");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(parent,
						"IOException: " + ex, "Save Error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fm.addSeparator();
		fm.add(mi = mkMenuItem(b, "file", "exit"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.exit(0);
			}
		});
		add(fm);

		Menu vm = mkMenu(b,  "view");
		vm.add(mi = mkMenuItem(b, "view", "next"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.nextPage();
			}
		});
		vm.add(mi = mkMenuItem(b, "view", "prev"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.prevPage();
			}
		});
		vm.add(mi = mkMenuItem(b, "view", "goto"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pnStr = JOptionPane.showInputDialog(
					(Object)"Page number?");
				int pn = Integer.parseInt(pnStr);
				model.setSlideNumber(pn);
			}
		});
		vm.addSeparator();
		vm.add(mi = mkMenuItem(b, "view", "slideshow"));
		vm.add(mi = mkMenuItem(b, "view", "outline"));
		vm.add(mi = mkMenuItem(b, "view", "sorter"));
		add(vm);

		Menu hm = mkMenu(b,  "help");
		hm.add(mi = mkMenuItem(b, "help", "about"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutBox.show(parent);
			}
		});
		setHelpMenu(hm);		// needed for portability (Motif, etc.).

	}

	/** Convenience routine to make a Menu */
	public Menu mkMenu(ResourceBundle b, String name) {
		String menuLabel;
		try { menuLabel = b.getString(name+".label"); }
		catch (MissingResourceException e) { menuLabel=name; }
		return new Menu(menuLabel);
	}

	/** Convenience routine to make a MenuItem */
	public MenuItem mkMenuItem(ResourceBundle b, String menu, String name) {
		String miLabel;
		try { miLabel = b.getString(menu + "." + name + ".label"); }
		catch (MissingResourceException e) { miLabel=name; }
		String key = null;
		try { key = b.getString(menu + "." + name + ".key"); }
		catch (MissingResourceException e) { key=null; }

		if (key == null)
			return new MenuItem(miLabel);
		else
			return new MenuItem(miLabel, new MenuShortcut(key.charAt(0)));
	}

}