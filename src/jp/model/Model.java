package jp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

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

// XXX Need both ListChangedEvents and ListSelectionEvents
// XXX for different methods here!

public class Model extends Observable implements ListModel {
	/** The slideshow title */
	protected String showTitle;

	/** the ArrayList of Slides */
	protected ArrayList<Slide> showList = null;

	/** The currently-displayed page */
	protected int pageNumber = 0;

	/** Constructor */
	public Model() {
		clear();
	}

	java.awt.Component view;

	// Methods used in the Controller(s) to control
	// what part of the data the view displays:

	/** Return the number of slides */
	public int getSize() {
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
	public void setSlideNumber(int slNumber) {

		pageNumber = slNumber;

		// tell the observers the current page
		setChanged();				// for the Observers (required!)
		notifyObservers(slNumber==-1?null:getCurrentSlide());

		// ListSelectionEvent evt = new ListDataEvent(view,
		// 	slNumber, slNumber, false);
		// for (int i=0; i<listenersList.size(); i++) {
		// 	((ListDataListener)listenersList.get(i)).valueChanged(evt);
		// }
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
		showList = new ArrayList<Slide>();
		setTitle("New presentation");
		setSlideNumber(-1);
	}

	/** Append a slide to the presentation */
	public void append(Slide s) {
		showList.add(s);
	}

	/** Retrieve a given slide, as seen by rest of program */
	public Slide getSlide(int n) {
		return (Slide)showList.get(n);
	}

	/** Retrieve a given slide, as used by JList and ListModel */
	public Object getElementAt(int n) {
		return showList.get(n);
	}

	/** Retrieve the current slide */
	public Slide getCurrentSlide() {
		return getSlide(getSlideNumber());
	}

	public void exit(int n) {
		System.exit(n);
	}

	private List<ListDataListener> listenersList =
		new ArrayList<ListDataListener>();

	public void addListDataListener(ListDataListener l) {
		listenersList.add(l);
	}
	public void removeListDataListener(ListDataListener l) {
		listenersList.remove(l);
	}
}
