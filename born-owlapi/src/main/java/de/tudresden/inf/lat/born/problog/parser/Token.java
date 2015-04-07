package de.tudresden.inf.lat.born.problog.parser;

/**
 * 
 * This interface models a token.
 * 
 * @author Julian Mendez
 *
 */
public interface Token {

	/**
	 * Returns the value of this token.
	 * 
	 * @return the value of this token
	 */
	String getValue();

	/**
	 * Returns the type of this token.
	 * 
	 * @return the type of this token
	 */
	TokenType getType();

	/**
	 * Returns the line number.
	 * 
	 * @return the line number
	 */
	int getLineNumber();

}
