package lunjure.nlp;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {

	static Parser parser;
	
	@BeforeClass
	public static void setUp() throws Exception {
		InputStream modelIn = new FileInputStream("models/en-parser-chunking.bin");
		try {
		  final ParserModel model = new ParserModel(modelIn);
		  parser = ParserFactory.create(model);
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
	public void testRewe() {
		final String sentence = "rewe 1200 +1";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		topParses[0].show();
	}

	@Test
	public void testHavanna() {
		// Parser expects all tokens in a space delimited string
		final String sentence = "Havanna 12:30 , anybody ?";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		topParses[0].show();
	}
	
	@Test
	public void testPizza() {
		final String sentence = "almost forgot : there some pizza left in the kitchen";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		topParses[0].show();
	}
	
	@Test
	public void testParser() throws Exception {
		final String sentence = "REWE 1200 +1";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		topParses[0].show();
	}
	
}
