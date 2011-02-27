package jp;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import jp.io.Accessor;
import jp.io.AccessorFactory;
import jp.model.M;
import jp.model.MText;
import jp.model.Model;

/** This is the Menu Controller for the View.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class MenuController extends JMenuBar {

	private static final long serialVersionUID = -1624393224606663016L;
	/** The Frame, used only for parenting Dialogs */
	private JFrame parent;
	/** The Model which we are controlling */
	private Model model;
	private final ClipboardOwner object = new ClipboardOwner() {
		public void lostOwnership(Clipboard clipboard, Transferable contents) {
			// don't care
		}               	
	};

	public MenuController(JFrame f, Model m) {
		parent = f;
		model = m;

		JMenuItem mi;

		ResourceBundle b = ResourceBundle.getBundle("JabberPointMenus");

		JMenu fm = mkMenu(b, "file");
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
				Accessor xacc = AccessorFactory.getInstance(fileName);
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
				Accessor xacc = AccessorFactory.getInstance("dump.xml");
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
		fm.add(mi = mkMenuItem(b, "file", "demo"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accessor xacc = AccessorFactory.getInstance(AccessorFactory.DEMO_NAME);
				try {
					xacc.loadFile(model, AccessorFactory.DEMO_NAME);
				} catch (IOException ex) {
					System.err.println(ex);
					return;
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
		
		JMenu em = mkMenu(b, "edit");
		em.add(mi = mkMenuItem(b, "edit", "copy"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuffer sb = new StringBuffer();
				final Slide slide = model.getCurrentSlide();
				sb.append(slide.getTitle()).append("\n");
				for (M m : slide.getMs()) {
					if (m instanceof MText) {
						sb.append(((MText)m).getText()).append("\n");
					}
				}
			    Clipboard clipboard = getToolkit().getSystemClipboard();
			    StringSelection contents = new StringSelection(sb.toString());
				clipboard.setContents(contents, object);
			}
		});
		add(em);

		JMenu vm = mkMenu(b,  "view");
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

		JMenu hm = mkMenu(b,  "help");
		hm.add(mi = mkMenuItem(b, "help", "about"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutBox.show(parent);
			}
		});
		//setHelpMenu(hm);		// needed for portability (Motif, etc.).

	}

	/** Convenience routine to make a Menu */
	public JMenu mkMenu(ResourceBundle b, String name) {
		String menuLabel;
		try { menuLabel = b.getString(name+".label"); }
		catch (MissingResourceException e) { menuLabel=name; }
		return new JMenu(menuLabel);
	}

	/** Convenience routine to make a MenuItem */
	public JMenuItem mkMenuItem(ResourceBundle b, String menu, String name) {
		String miLabel;
		try { miLabel = b.getString(menu + "." + name + ".label"); }
		catch (MissingResourceException e) { miLabel=name; }
		String key = null;
		try { key = b.getString(menu + "." + name + ".key"); }
		catch (MissingResourceException e) { key=null; }

		if (key == null)
			return new JMenuItem(miLabel);
		else
			return new JMenuItem(miLabel, key.charAt(0));
	}

}