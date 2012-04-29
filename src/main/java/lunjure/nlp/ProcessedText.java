package lunjure.nlp;

import java.util.Arrays;
import java.util.List;

public class ProcessedText {

	private String[] tokens;
	private String[] names;

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
		final boolean result;
		final List<String> tokenList = Arrays.asList(this.tokens);
		if (tokenList.contains("+1")) {
			result = true;
		} else if (tokenList.contains("+")) {
			result = true;
		} else {
			result = false;
		}
		return result;
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

}
