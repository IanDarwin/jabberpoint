import java.util.*;

/**
 * Model is the data model for JabberPoint.
 * It keeps track of all the slides in the presentation.
 * <p>
 * In the Model's world, page numbers go from 0 to n-1.
 * <p>
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

	/** the ArrayList of Slides */
	protected ArrayList showList = null;

	/** The currently-displayed page */
	protected int pageNumber = 0;

	/** Constructor */
	public Model() {
		clear();
	}

	// Methods used in the Controller(s) to control
	// what part of the data the view displays:

	/** Return the number of slides */
	public int getSize() {
		if (showList == null)
			return 0;
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	/** Return the current page */
	public int getSlideNumber() {
		return pageNumber;
	}

	/** Set the current page(slide) number, and notify all the view(s).
	 */
	public void setSlideNumber(int i) {

		pageNumber = i;

		// tell the observers the current page
		setChanged();				// for the Observers (required!)
		notifyObservers(i==-1?null:getCurrentSlide());	
	}

	/** Move to the previous page, unless at beginning */
	public void prevPage() {
		if (pageNumber > 0)
			setSlideNumber(pageNumber-1);
	}

	/** Move to the next page, unless at the end. */
	public void nextPage() {
		if (pageNumber < (showList.size()-1))
			setSlideNumber(pageNumber+1);
	}

	/** Clear out the show, getting it ready for the next use */
	void clear() {
		showList = new ArrayList();
		setTitle("New presentation");
		setSlideNumber(-1);
	}

	/** Append a slide to the presentation */
	public void append(Slide s) {
		showList.add(s);
	}

	/** Retrieve a given slide */
	public Slide getSlide(int n) {
		return (Slide)showList.get(n);
	}

	/** Retrieve the current slide */
	public Slide getCurrentSlide() {
		return getSlide(pageNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
