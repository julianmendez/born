package de.tudresden.inf.lat.problogapi;

import java.io.Reader;

/**
 * This interface models the query processor.
 * 
 * @author Julian Mendez
 *
 */
@FunctionalInterface
public interface QueryProcessor {

	/**
	 * Executes a ProbLog program and returns the result of executing the
	 * processor.
	 * 
	 * @param input
	 *            ProbLog program given as input
	 * @return the result of executing the processor
	 */
	String execute(Reader input);

}
