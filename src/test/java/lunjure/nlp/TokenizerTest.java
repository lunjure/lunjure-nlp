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
	public void test() {
		final String[] tokens = tokenizer.tokenize("rewe 1200 +1");
		assertArrayEquals(new String[]{"rewe", "1200", "+1"}, tokens);
	}

}
