package lunjure.nlp;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class NLPProcessor {

	private TokenizerModel tokenizerModel;
	private ParserModel parserModel;

	private Dictionary dict;
	private String tokenizerModelPath;
	private String dictionaryPath;
	private String parserModelPath;

	public NLPProcessor() {
		this.tokenizerModelPath = "models/en-token.bin";
		this.dictionaryPath = "models/en-ner-dict.txt";
		this.parserModelPath = "models/en-parser-chunking.bin";
	}

	public void init() {
		initTokenizer();
		initNameFinder();
		initParser();
	}

	protected void initTokenizer() {
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

	protected void initNameFinder() {
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

	protected void initParser() {
		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream(this.parserModelPath);
			parserModel = new ParserModel(modelIn);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
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

	protected String createLineFormTokens(final String[] tokens) {
		final StringBuilder lineBuffer = new StringBuilder();
		for(final String token: tokens) {
			lineBuffer.append(token).append(" ");
		}
		if (lineBuffer.length() > 0) {
			lineBuffer.deleteCharAt(lineBuffer.length() -1);
		}
		return lineBuffer.toString();
	}
	
	protected Parse parse(final String[] tokens) {
		final Parser parser = ParserFactory.create(parserModel);
		final String line = createLineFormTokens(tokens);
		final Parse[] topParses = ParserTool.parseLine(line, parser, 1);
		return topParses[0];
	}
	
	public ProcessedText process(final String sentence) {
		final ProcessedText result = new ProcessedText();

		result.setTokens(this.tokenize(sentence));
		result.setNames(this.findNames(result.getTokens()));
		result.setParseTree(this.parse(result.getTokens()));
		return result;
	}

}
