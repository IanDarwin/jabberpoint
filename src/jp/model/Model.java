import java.util.*;
public class Model extends Observable {
	/** the Vector of Slides */
	Vector show;
	/** The currently-displayed page */
	int pageNumber = 0;

	public Model() {
		show = new Vector();
	}

	public void loadFile(String fn) {
		System.out.println("In loadFile");
	}

	public void newFile() {
		System.out.println("In newdFile");
	}

	public void saveFile(String fn) {
		System.out.println("In saveFile");
	}

	public void append(Slide s) {
		show.addElement(s);
	}

	public Slide getPage() {
		return (Slide)show.elementAt(pageNumber);
	}
	public void setPage(int i) {
		if (i>=show.size())
			throw new IllegalArgumentException();
		pageNumber = i;
		setChanged();
		notifyObservers(getPage());
	}
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
