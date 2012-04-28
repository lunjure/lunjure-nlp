package lunjure.nlp;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class NameFinderTest {
	NameFinderME timeNameFinder;
	NameFinderME personNameFinder;
	
	@Before
	public void setUp() throws Exception {
		timeNameFinder = getNameFinderFromModel("models/en-ner-time.bin");
		personNameFinder = getNameFinderFromModel("models/en-ner-person.bin");
	}
	
	private NameFinderME getNameFinderFromModel(final String path) throws Exception {
		final InputStream modelIn = new FileInputStream(path);

		try {
		  final TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
		  return new NameFinderME(model);
		}
		catch (IOException ex) {
		  throw ex;
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}				
	}
	
	@Test
	@Ignore("Times are not recognized...")
	public void testTimeSimple() {
		final String[] tokens = new String[]{"Havanna", "12:30h", ",", "anybody", "?"};
		final Span timeSpans[] = timeNameFinder.find(tokens);
		assertEquals(1, timeSpans.length);		
	}

	@Test
	@Ignore("Times are not recognized...")
	public void testTimeNoDelim() {
		final String[] tokens = new String[]{"rewe", "1200", "+1"};
		final Span timeSpans[] = timeNameFinder.find(tokens);
		assertEquals(1, timeSpans.length);		
	}	
	
	@Test
	@Ignore("Times are not recognized...")
	public void testTimeText() throws Exception {
		final String[] tokens = new String[]{"Havanna", "at", "twelve", "thirty", ",", "anybody", "?"};
		final Span timeSpans[] = timeNameFinder.find(tokens);
		assertEquals(1, timeSpans.length);		
	}
	
	@Test
	public void testPersonName() throws Exception {
		final String[] tokens = new String[]{
				"Pierre",
			    "Vinken",
			    "is",
			    "61",
			    "years",
			    "old",
			    "."};
		final Span[] personSpans = personNameFinder.find(tokens);
		assertEquals(1, personSpans.length);
		assertEquals("person", personSpans[0].getType());
		assertEquals("Pierre Vinken", Span.spansToStrings(personSpans, tokens)[0]);
	}
}
