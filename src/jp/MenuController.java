import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** This is the Menu Controller for the View */
public class MenuController extends MenuBar {
	/** The Model which we are controlling */
	Model model;

	public MenuController(Model m) {
		model = m;

		MenuItem mi;
		// model.nextPage();

		ResourceBundle b = ResourceBundle.getBundle("JabberPointMenus");
		String menuLabel;
		try { menuLabel = b.getString("file"+".label"); }
		catch (MissingResourceException e) { menuLabel="file"; }

		Menu fm = new Menu(menuLabel);
		fm.add(mi = mkMenuItem(b, "file", "open"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.loadFile(null);
			}
		});
		fm.add(mi = mkMenuItem(b, "file", "new"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.newFile();
			}
		});
		fm.add(mi = mkMenuItem(b, "file", "save"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.saveFile(null);
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
		vm.add(mkMenuItem(b, "view", "next"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.nextPage();
			}
		});
		vm.add(mkMenuItem(b, "view", "previous"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.prevPage();
			}
		});
		vm.addSeparator();
		vm.add(mkMenuItem(b, "view", "slideshow"));
		vm.add(mkMenuItem(b, "view", "outline"));
		vm.add(mkMenuItem(b, "view", "slidesorter"));
		add(vm);

		Menu hm = mkMenu(b,  "help");
		hm.add(mi = mkMenuItem(b, "help", "about"));
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
