package lunjure.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class NLPProcessor {

	private Tokenizer tokenizer;
	private String tokenizerModelPath;
	
	public NLPProcessor() {
		this.tokenizerModelPath = "models/en-token.bin";
	}
	
	public void init()  throws RuntimeException {
		InputStream in = null;
		try {
			in = new FileInputStream(this.tokenizerModelPath);
			TokenizerModel model = new TokenizerModel(in);
			tokenizer = new TokenizerME(model);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					
				}
			}
		}
	}
	
	public String[] tokenize(final String sentence) {
		return this.tokenizer.tokenize(sentence);
	}

	public boolean isQuestion(String sentence) {
		final String[] tokens = this.tokenize(sentence);
		final boolean result;
		if (tokens.length > 0) {
			result = (tokens[tokens.length -1].equals("?"));
		} else {
			result = false;
		}
		return result;
	}

	public boolean isInc(String sentence) {
		final String[] tokens = this.tokenize(sentence);
		final boolean result;
		final List<String> tokenList = Arrays.asList(tokens);
		if (tokenList.contains("+1")) {
			result = true;
		} else if (tokenList.contains("+")) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	
}
