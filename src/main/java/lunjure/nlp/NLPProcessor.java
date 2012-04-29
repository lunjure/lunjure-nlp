package lunjure.nlp;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class NLPProcessor {

	private TokenizerModel tokenizerModel;
	private Dictionary dict;
	private String tokenizerModelPath;
	private String dictionaryPath;
	
	public NLPProcessor() {
		this.tokenizerModelPath = "models/en-token.bin";
		this.dictionaryPath = "models/en-ner-dict.txt";
	}
	
	public void init() {
		initTokenizer();
		initNameFinder();
	}
	
	protected void initTokenizer()  throws RuntimeException {
		InputStream in = null;
		try {
			in = new FileInputStream(this.tokenizerModelPath);
			tokenizerModel = new TokenizerModel(in);
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

	protected void initNameFinder()  throws RuntimeException {
		Reader r = null;
		try {
			r = new FileReader(this.dictionaryPath);
			dict = Dictionary.parseOneEntryPerLine(r);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException ex) {
					
				}
			}
		}
	}
	
	protected String[] tokenize(final String sentence) {
		final Tokenizer tokenizer = new TokenizerME(tokenizerModel);
		return tokenizer.tokenize(sentence);
	}
	
	protected String[] findNames(final String[] tokens) {
		final DictionaryNameFinder finder = new DictionaryNameFinder(dict);
		final Span[] spans = finder.find(tokens);
		return Span.spansToStrings(spans, tokens);
	}
	
	public ProcessedText process(final String sentence) {
		final ProcessedText result = new ProcessedText();
		
		result.setTokens(this.tokenize(sentence));
		result.setNames(findNames(result.getTokens()));
		
		return result;
	}
	
}
