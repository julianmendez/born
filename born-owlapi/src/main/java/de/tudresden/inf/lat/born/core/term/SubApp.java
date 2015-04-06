package de.tudresden.inf.lat.born.core.term;

/**
 * An object implementing this interface is a 'sub app', or in other words, an
 * piece of BORN that is executable from the command line.
 * 
 * @author Julian Mendez
 *
 */
public interface SubApp {

	/**
	 * Returns the help.
	 * 
	 * @return the help
	 */
	String getHelp();

	/**
	 * Returns <code>true</code> if and only if the input is valid according to
	 * the specification.
	 * 
	 * @param args
	 *            arguments
	 * @return <code>true</code> if and only if the input is valid according to
	 *         the specification
	 */
	boolean isValid(String[] args);

	/**
	 * Executes this and returns a report after the execution
	 * 
	 * @param args
	 *            arguments
	 * @return a report after the execution
	 */
	String run(String[] args);

}
