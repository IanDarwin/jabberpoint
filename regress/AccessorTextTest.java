
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

	private static final AccessorText accessor = new AccessorText();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSimple()  throws Throwable  {
		String text = "Slideshow Title\n" +
			"Slide Title" + "\n" +
			"\t" + "Heading" + "\n" +	// 0
			"\t" + "Text" + "\n" +		// 1
			"\t\t" + "Text" + "\n" +	// 2
			"C " + "int i = 0" + "\n" +	// 3
			"I " + "/tmp/id" + "\n";	// 4
		Model m = read(text);
		assertEquals("Slideshow Title", m.getShowTitle());
		Slide sl = m.getSlide(0);
		assertEquals("Heading", ((MText)sl.getM(0)).getText());
		assertEquals("Text", ((MText)sl.getM(1)).getText());
		assertEquals(1, ((MText)sl.getM(1)).getLevel());
		assertEquals("int i = 0", ((MCode)sl.getM(3)).getText());
		assertEquals("/tmp/id", ((MCodeInsert)sl.getM(4)).getFileName());
	}

	private Model read(String text) throws IOException{
		BufferedReader is = new BufferedReader(new StringReader(text));
		Model m = new Model();;
		accessor.loadFile(m, is);
		return m;
	}
}
