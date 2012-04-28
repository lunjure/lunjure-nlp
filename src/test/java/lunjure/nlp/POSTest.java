package lunjure.nlp;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class POSTest {

	static POSModel model;
	static POSTagger tagger;
	
	@BeforeClass
	public static void setUp() throws Exception {
		final InputStream modelIn = new FileInputStream("models/en-pos-maxent.bin");

		try {
		  model = new POSModel(modelIn);
		  tagger = new POSTaggerME(model);
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
	public void testPOS() {
		final String[] tokens = new String[]{"rewe", "1200", "+1"};
		String[] tags = tagger.tag(tokens);
		assertArrayEquals(new String[]{"JJ", "CD", "CD"}, tags);
	}

}
