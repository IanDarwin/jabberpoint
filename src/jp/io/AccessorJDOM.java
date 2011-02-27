package jp.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jp.Slide;
import jp.model.MText;
import jp.model.Model;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * JDOM-based Model subclass for XML-based text.
 *
 * $Id$
 */
public class AccessorJDOM extends AccessorXML {

	/** Construct */
	protected AccessorJDOM(String fileName) {
		super(fileName);
	}

	public AccessorJDOM() {
	}

	/**
	 * Load a file and scan.
	 */
	public void loadFile(Model model, String fn) throws IOException {
		try {
			SAXBuilder b = new SAXBuilder(true);    // true -> validate

			// Create a JDOM document.
			Document doc = b.build(new File(fn));

			Element r = doc.getRootElement();

			String title = r.getChild("head").getChild("title").getText();
			model.setShowTitle(title);

			List slides = r.getChildren("slide");
			System.out.println("There are " + slides.size() + " slides.");

			for (int i = 0; i < slides.size(); i++) {
				Element xmlSlide = (Element)slides.get(i);

				Slide s = new Slide();
				s.setTitle(xmlSlide.getChild("title").getText());
				model.append(s);

				List ms = xmlSlide.getChildren();
				for (int nm = 0; nm < ms.size(); nm++) {
					Element m = (Element)ms.get(nm);
					String type = m.getName();
					if (type.equals("h1")) {
						s.append(new MText(1, m.getText()));
					} else if (type.equals("h2")) {
						s.append(new MText(2, m.getText()));
					} // AND SO ON...
				}
			}
		} catch (JDOMException jdx) {
			System.err.println(jdx.toString());
			throw new IOException("Parse Exception");
		}
	}

	// saveFile is inherited from AccessorXML.
}
