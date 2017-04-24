package de.tudresden.inf.lat.born.problog.parser;

/**
 * An object of this class is a parse exception.
 * 
 * @author Julian Mendez
 *
 */
public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 1852013028445275832L;

	/**
	 * Constructs a parse exception.
	 * 
	 * @param message
	 *            message
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * Constructs a parse exception.
	 * 
	 * @param e
	 *            exception
	 */
	public ParseException(Throwable e) {
		super(e);
	}

	/**
	 * Constructs a parse exception.
	 * 
	 * @param message
	 *            message
	 * @param e
	 *            exception
	 */
	public ParseException(String message, Throwable e) {
		super(message, e);
	}

	/**
	 * Constructs a parse exception.
	 * 
	 * @param message
	 *            message
	 * @param token
	 *            token
	 */
	public ParseException(String message, Token token) {
		super(message + "Line " + token.getLineNumber() + ": '" + token.getValue() + "'.");
	}

}
