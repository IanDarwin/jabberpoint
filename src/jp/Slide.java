import java.util.Vector;

public class Slide {
	/** each page contains a Vector of M's */
	Vector ms;

	public Slide() {
		ms = new Vector();
	}
	public void append(int lev, String msg) {
		ms.addElement(new M(M.TEXT, lev, msg));
	}
	public Vector getMs() {
		return ms;
	}
}
