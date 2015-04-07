package de.tudresden.inf.lat.born.problog.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.core.term.Symbol;

public class TokenCreator {

	enum Mode {
		UNDEFINED, CODE, CONSTANT, COMMENT
	}

	List<Token> createTokens(String str, int lineNumber) {
		List<Token> ret = new ArrayList<Token>();
		if (str != null && !str.isEmpty()) {
			Mode mode = Mode.CODE;
			StringBuffer token = new StringBuffer();
			int i = 0;
			while (i < str.length()) {
				char ch = str.charAt(i);
				if (Character.isLetterOrDigit(ch) && mode.equals(Mode.CODE)) {
					token.append(ch);
				} else if ((ch == Symbol.APOSTROPHE_CHAR)
						&& mode.equals(Mode.CODE)) {
					ret.add(new TokenImpl(token.toString(), lineNumber));
					token = new StringBuffer();
					token.append(ch);
					mode = Mode.CONSTANT;

				} else if ((ch == Symbol.APOSTROPHE_CHAR)
						&& mode.equals(Mode.CONSTANT)) {
					token.append(ch);
					ret.add(new TokenImpl(token.toString(), lineNumber));
					token = new StringBuffer();
					mode = Mode.CODE;

				} else if ((ch == Symbol.PERCENT_CHAR)
						&& mode.equals(Mode.CODE)) {
					ret.add(new TokenImpl(token.toString(), lineNumber));
					token = new StringBuffer();
					token.append(ch);
					mode = Mode.COMMENT;

				} else if (mode.equals(Mode.CODE)) {
					ret.add(new TokenImpl(token.toString(), lineNumber));
					token = new StringBuffer();
					if ((ch == Symbol.COLON_CHAR) && (i < str.length() - 1)
							&& (str.charAt(i + 1) == Symbol.HYPHEN_CHAR)) {
						ret.add(new TokenImpl(Symbol.IF_SYMBOL, lineNumber));
						i += 1;
					} else {
						ret.add(new TokenImpl("" + ch, lineNumber));
					}

				} else if (mode.equals(Mode.CONSTANT)
						|| mode.equals(Mode.COMMENT)) {
					token.append(ch);

				} else {
					throw new IllegalStateException(
							"Illegal state while parsing '" + str
									+ "' at position " + i + " of line "
									+ lineNumber + ".");
				}

				i += 1;
			}
			if (!token.toString().isEmpty()) {
				ret.add(new TokenImpl(token.toString(), lineNumber));
			}
		}
		return ret;
	}

	List<Token> removeBlanksAndComments(List<Token> tokens) {
		List<Token> ret = new ArrayList<Token>();
		if (tokens != null) {
			for (Token token : tokens) {
				if (!token.getType().equals(TokenType.BLANK)
						&& !token.getType().equals(TokenType.COMMENT)) {
					ret.add(token);
				}
			}
		}
		return ret;
	}

	public List<Token> createTokens(Reader reader) throws IOException {
		List<Token> ret = new ArrayList<Token>();
		BufferedReader in = new BufferedReader(reader);
		int lineNumber = 0;
		String line = "";
		while (line != null) {
			line = in.readLine();
			if (line != null) {
				lineNumber += 1;
				ret.addAll(removeBlanksAndComments(createTokens(line,
						lineNumber)));
			}
		}
		return ret;
	}

}
