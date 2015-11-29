package de.tudresden.inf.lat.born.problog.parser;

public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 1852013028445275832L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable e) {
		super(e);
	}

	public ParseException(String message, Throwable e) {
		super(message, e);
	}

	public ParseException(String message, Token token) {
		super(message + "Line " + token.getLineNumber() + ": '" + token.getValue() + "'.");
	}

}
