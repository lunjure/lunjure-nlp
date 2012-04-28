package lunjure.nlp;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.util.Span;

import org.junit.BeforeClass;
import org.junit.Test;

public class DictionaryNameFinderTest {

	static DictionaryNameFinder finder;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		Reader r = null;
		try {
			r = new FileReader("models/en-ner-dict.txt");
			final Dictionary dict = Dictionary.parseOneEntryPerLine(r);
			finder = new DictionaryNameFinder(dict);	
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException ex) {
					
				}
			}
		}
	}
	
	@Test
	public void test() {
		final String[] tokens = new String[]{"Havanna", "12:30", ",", "anybody", "?"};
		final Span timeSpans[] = finder.find(tokens);
		assertEquals(1, timeSpans.length);		
	}

	@Test
	public void testDateSample() throws Exception {
		final String[] tokens = new String[] {"REWE", "1200", "+1"};
		final Span timeSpans[] = finder.find(tokens);
		assertEquals(1, timeSpans.length);		
	}

}
