package lunjure.nlp;

import java.util.Arrays;
import java.util.List;

import opennlp.tools.parser.Parse;

public class ProcessedText {

	private String[] tokens;
	private String[] names;
	private Parse parseTree;

	public String[] getTokens() {
		return tokens;
	}

	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}
	
	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public boolean isInc() {
		return isIncDec("+");
	}

	public boolean isQuestion() {
		final boolean result;
		if (this.tokens.length > 0) {
			result = (this.tokens[this.tokens.length -1].equals("?"));
		} else {
			result = false;
		}
		return result;
	}

	public boolean isDec() {
		return isIncDec("-");
	}

	private boolean isIncDec(final String sign) {
		final boolean result;
		final List<String> tokenList = Arrays.asList(this.tokens);
		if (tokenList.contains(sign + "1")) {
			result = true;
		} else if (tokenList.contains(sign)) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public Parse getParseTree() {
		return this.parseTree;
	}

	public void setParseTree(Parse parseTree) {
		this.parseTree = parseTree;
	}

	public String getParseTreeAsString() {
		final StringBuffer result = new StringBuffer();
		if (parseTree != null) {
			parseTree.show(result);
		}
		return result.toString();
	}

}
