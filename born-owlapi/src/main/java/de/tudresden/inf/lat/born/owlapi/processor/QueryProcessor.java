package de.tudresden.inf.lat.born.owlapi.processor;

/**
 * Executes a query.
 * 
 * @author Julian Mendez
 *
 */
public interface QueryProcessor {

	/**
	 * Executes a query and returns a value given by the operating system.
	 * 
	 * @param start
	 *            starting point
	 * @param outputFileName
	 *            output file name
	 * @return a value given by the operating system
	 */
	int execute(long start, String outputFileName);

}
