import java.io.*;
import java.util.*;
/**
 * Model is the data model for JabberPoint.
 * It keeps track of all the slides in the presentation.
 */
public class Model extends Observable {
	/** the Vector of Slides */
	Vector show = null;
	/** The currently-displayed page */
	private int pageNumber = 0;

	/** Return the number of slides */
	public int getSize() {
		if (show == null)
			return 0;
		return show.size();
	}

	/** Return the current page */
	public int getCurrentNumber() {
		return pageNumber;
	}

	void resetShow() {
		show = new Vector();
		pageNumber = 0;
	}

	/**
	 * Load a file and scan, using BufferedReader 
	 */
	public void loadFile(String fn) {
		Slide sl = null;
		try {
			BufferedReader is = new BufferedReader(new FileReader(fn));
            String s;
			resetShow();
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

	/** Append a slide to the presentation */

	public void append(Slide s) {
		show.addElement(s);
	}

	public Slide getPage() {
		return (Slide)show.elementAt(pageNumber);
	}

	/* Here are some methods used in the Controller(s) to control
	 * what part of the data the view displays:
	 */

	/** setPage(int pnum) sets the current page(slide) and 
	 * notifies all the view(s) of the current page
	 */
	public void setPage(int i) {
		if (i<0 || i>=show.size())
			throw new IllegalArgumentException();
		pageNumber = i;
		setChanged();				// for the Observers (required!)
		notifyObservers(getPage());	// tell the observers the current page
	}
	/** Move to the previous page, unless at beginning */
	public void prevPage() {
		if (pageNumber > 0)
			setPage(pageNumber-1);
	}
	public void nextPage() {
		if (pageNumber < (show.size()-1))
			setPage(pageNumber+1);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
