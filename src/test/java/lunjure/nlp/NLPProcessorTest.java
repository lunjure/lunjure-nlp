package lunjure.nlp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NLPProcessorTest {

	private NLPProcessor processor;
	
	@Before
	public void setUp() throws Exception {
		processor = new NLPProcessor();
		processor.init();
	}

	@Test
	public void testTokenize() {
		final String[] tokens = processor.tokenize("Lamme somebody?");
		assertArrayEquals(new String[]{"Lamme", "somebody", "?"}, tokens);
	}

	@Test
	public void testIsQuestion() throws Exception {
		final boolean result = processor.isQuestion("Lamme somebody?");
		assertTrue(result);
	}

	@Test
	public void testIsQuestionFalse() throws Exception {
		final boolean result = processor.isQuestion("or maybe ...");
		assertFalse(result);
	}

	@Test
	public void testIsInc() throws Exception {
		final boolean result = processor.isInc("REWE 1200 +1");
		assertTrue(result);
	}

	@Test
	public void testIsIncPlusPlus() throws Exception {
		final boolean result = processor.isInc("REWE 1200 ++");
		assertTrue(result);
	}

}
