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
		final ProcessedText result = processor.process("Lamme somebody?");
		assertTrue(result.isQuestion());
	}

	@Test
	public void testIsQuestionFalse() throws Exception {
		final ProcessedText result = processor.process("or maybe ...");
		assertFalse(result.isQuestion());
	}

	@Test
	public void testIsInc() throws Exception {
		final ProcessedText result = processor.process("REWE 1200 +1");
		assertTrue(result.isInc());
	}

	@Test
	public void testIsIncPlusPlus() throws Exception {
		final ProcessedText result = processor.process("REWE 1200 ++");
		assertTrue(result.isInc());
	}
	
	@Test
	public void testProcess() throws Exception {
		final ProcessedText result = processor.process("Lamme somebody?");
		assertArrayEquals(new String[]{"Lamme"}, result.getNames());
		assertTrue(result.isQuestion());		
	}

	@Test
	public void testProcessREWE() throws Exception {
		final ProcessedText result = processor.process("REWE 1200 +1");
		assertArrayEquals(new String[]{"REWE"}, result.getNames());
		assertTrue(result.isInc());
	}
}
