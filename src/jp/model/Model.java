import java.io.*;
import java.util.*;

/**
 * Model is the data model for JabberPoint.
 * It keeps track of all the slides in the presentation.
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
public class Model extends Observable {
	/** The slideshow title */
	protected String showTitle;
	/** the Vector of Slides */
	protected Vector showList = null;
	/** The currently-displayed page */
	private int pageNumber = 0;

	/** Constructor */
	public Model() {
		resetShow();
	}

	/** Return the number of slides */
	public int getSize() {
		if (showList == null)
			return 0;
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	/** Return the current page */
	public int getCurrentNumber() {
		return pageNumber;
	}

	void resetShow() {
		showList = new Vector();
		pageNumber = 0;
	}

	/** Append a slide to the presentation */

	public void append(Slide s) {
		showList.addElement(s);
	}

	/** Retrieve a given slide */
	public Slide getSlide(int n) {
		return (Slide)showList.elementAt(n);
	}

	/** Retrieve the current slide */
	public Slide getCurrentSlide() {
		return getSlide(pageNumber);
	}

	/**
	 * Load a file and scan, using BufferedReader 
	 */
	public void loadFile(String fn) {
		Slide sl = null;
		if (fn == null) {
			System.err.println("Code for FileChooser not here yet");
			return;
		}
		try {
			BufferedReader is = new BufferedReader(new FileReader(fn));
            String s;
			resetShow();

			showTitle = is.readLine();

            while ((s = is.readLine()) != null) {
				if (s.startsWith("\t")) {
					int lev;
					for (lev=0; s.charAt(lev) == '\t'; lev++)
						/*nullbody*/;
					sl.append(lev, s.substring(lev));
				} else {
					sl = new Slide();
					sl.append(0, s);
					append(sl);
				}
            }
            is.close();
		} catch (FileNotFoundException e) {
			System.err.println("Can't open file: " + e);
        } catch (IOException e) {
            System.out.println("Read error: " + e);
		}
    }

	public void newFile() {
		System.out.println("newFile() not written yet");
	}

	public void saveFile(String fn) {
		System.out.println("saveFile() not written yet");
	}


	/* Here are some methods used in the Controller(s) to control
	 * what part of the data the view displays:
	 */

	/** setPage(int pnum) sets the current page(slide) and 
	 * notifies all the view(s) of the current page
	 */
	public void setPage(int i) {
		if (i<0 || i>=showList.size())
			throw new IllegalArgumentException();
		pageNumber = i;
		setChanged();				// for the Observers (required!)
		notifyObservers(getCurrentSlide());	// tell the observers the current page
	}

	/** Move to the previous page, unless at beginning */
	public void prevPage() {
		if (pageNumber > 0)
			setPage(pageNumber-1);
	}
	public void nextPage() {
		if (pageNumber < (showList.size()-1))
			setPage(pageNumber+1);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
