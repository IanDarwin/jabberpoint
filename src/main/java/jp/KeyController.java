package jp;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import jp.model.Model;

/** This is the KeyController (KeyListener) for the View
 * <P>
 * This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.
 * @author Ian F. Darwin, ian@darwinsys.com
 * @version $Id$
 */
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
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
		case '+':
			model.nextPage();
			break;
		case KeyEvent.VK_PAGE_UP:
		case KeyEvent.VK_UP:
		case '-':
			model.prevPage();
			break;
		case 'q':
		case 'Q':
			System.exit(0);
			/*NOTREACHED*/
			break;
		default:
			break;
		}
	}
}
