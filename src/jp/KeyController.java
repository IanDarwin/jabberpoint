import java.awt.event.*;

/** This is the KeyController (KeyListener) for the View */
public class KeyController extends KeyAdapter {
	/** The Model which we are controlling */
	Model model;

	public KeyController(Model m) {
		model = m;
	}

	public void keyPressed(KeyEvent k) {
		// System.out.println("keyPressed: " + k.getKeyCode());
		switch(k.getKeyCode()) {
		case KeyEvent.VK_PAGE_DOWN:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_ADD:
		case KeyEvent.VK_ENTER:
			model.nextPage();
			break;
		case KeyEvent.VK_PAGE_UP:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_SUBTRACT:
			model.prevPage();
			break;
		default:
			break;
		}
	}
}
