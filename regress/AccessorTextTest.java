
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import jp.Slide;
import jp.io.AccessorText;
import jp.model.MCode;
import jp.model.MCodeInsert;
import jp.model.MText;
import jp.model.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AccessorTextTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSimple()  throws Throwable  {
		String text = "Slideshow Title\n" +
			"Slide Title" + "\n" +
			"\t" + "Heading" + "\n" +
			"\t" + "Text" + "\n" +
			"C " + "int i = 0" + "\n" +
			"I " + "/tmp/id" + "\n";
		Model m = read(text);
		assertEquals("Slideshow Title", m.getShowTitle());
		Slide sl = m.getSlide(0);
		assertEquals("Heading", ((MText)sl.getM(0)).getText());
		assertEquals("Text", ((MText)sl.getM(1)).getText());
		assertEquals("int i = 0", ((MCode)sl.getM(2)).getText());
		assertEquals("/tmp/id", ((MCodeInsert)sl.getM(3)).getFileName());
	}

	private Model read(String text) throws IOException{
		BufferedReader is = new BufferedReader(new StringReader(text));
		Model m = new Model();;
		new AccessorText().loadFile(m, is);
		return m;
	}
}
