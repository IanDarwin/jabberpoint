package jp.io;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jp.Slide;
import jp.model.MText;
import jp.model.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Apple Keynote(tm) slide show accessor; currently read-only.
 * $Id$
 */
public class AccessorKeynote extends AbstractAccessor {

	/** Construct */
	protected AccessorKeynote(String fileName) {
		super(fileName);
	}

	public AccessorKeynote() {
	}

	/**
	 * Load a file and scan.
	 */
	public void loadFile(Model model, String fn) throws IOException {
		this.model = model;
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			f.setValidating(true);
			DocumentBuilder p = f.newDocumentBuilder();
			Document doc = p.parse(fn);
			walk(doc);
        } catch (SAXException ex) {
            System.err.println("+================================+");
            System.err.println("|         *Parse Error*          |");
            System.err.println("+================================+");
            System.err.println(ex.getClass());
            System.err.println(ex.getMessage());
            System.err.println("+================================+");
			throw new IOException("SAX Parse Error");
        } catch (Exception ex) {
            System.err.println("+================================+");
            System.err.println("|           *XML Error*          |");
            System.err.println("+================================+");
            System.err.println(ex.toString());
		}
	}

	Slide slide;

	/** Walk a DOM tree in one pass, making slides as we go.
	 */
	void walk(Node n) {
		if (n.getNodeType() == Node.ELEMENT_NODE) {
			String type = n.getNodeName();
			System.out.println("ELEMENT " + type);
			if (type.equals("slide")) {
				slide = new Slide();
				model.append(slide);
			} else if (type.equals("title")) {
				if (slide == null)
					model.setShowTitle(getChildNodeText(n));
				else
					slide.setTitle(getChildNodeText(n));
			} else if (type.equals("h1")) {
				slide.append(new MText(1, getChildNodeText(n)));
			} else if (type.equals("h2")) {
				slide.append(new MText(2, getChildNodeText(n)));
			}
		}
		// Process child nodes recursively.
		if (n.hasChildNodes()) {
			NodeList nodes = n.getChildNodes();
			for (int i=0; i<nodes.getLength(); i++) {
				walk(nodes.item(i));
			}
		}
	}

	/** Get the first text node under a given node.
	 * This is NOT very general but it works for the slideshow.
	 */
	String getChildNodeText(Node n) {
		NodeList nodes = n.getChildNodes();
		String text = null;
		int i = 0;
		do {
			Node current = nodes.item(i);
			text = current.getNodeValue();
			System.out.println("Node " + i + " text " + text);
			++i;
		} while (text == null || text.length() == 0 || i < nodes.getLength());
		return text;
	}

	public void saveFile(Model model, String fn) throws IOException {
		throw new UnsupportedOperationException("Can't save Keynote format yet");
	}
}
