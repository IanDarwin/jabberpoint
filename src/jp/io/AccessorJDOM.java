import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;

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
			model.setTitle(title);

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
