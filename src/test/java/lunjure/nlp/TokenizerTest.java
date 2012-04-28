package lunjure.nlp;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TokenizerTest {

	TokenizerModel model;
	Tokenizer tokenizer;
	
	@Before
	public void setUp() throws Exception {
		final InputStream in = new FileInputStream("models/en-token.bin");
		try {
			model = new TokenizerModel(in);
			tokenizer = new TokenizerME(model);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					
				}
			}
		}
	}

	@Test
	public void testSimple() {
		final String[] tokens = tokenizer.tokenize("rewe 1200 +1");
		assertArrayEquals(new String[]{"rewe", "1200", "+1"}, tokens);
	}
	
	@Test
	public void testQuestion() throws Exception {
		final String[] tokens = tokenizer.tokenize("Havanna 12:30, anybody?");
		assertArrayEquals(new String[]{"Havanna", "12:30", ",", "anybody", "?"}, tokens);
	}
	
	@Test
	public void testTime() throws Exception {
		final String[] tokens = tokenizer.tokenize("Havanna at twelve thirty, anybody?");
		assertArrayEquals(new String[]{"Havanna", "at", "twelve", "thirty", ",", "anybody", "?"}, tokens);
	}
	
	@Test
	public void testPizza() throws Exception {
		final String[] tokens = tokenizer.tokenize("almost forgot: there some pizza left in the kitchen");
		assertArrayEquals(new String[]{"almost", "forgot", ":", "there", "some", "pizza", "left", "in", "the", "kitchen"}, tokens);
	}
}
