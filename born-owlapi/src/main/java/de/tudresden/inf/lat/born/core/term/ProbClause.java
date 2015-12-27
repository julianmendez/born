package de.tudresden.inf.lat.born.core.term;

/**
 * This interface models a clause with probability.
 * 
 * @author Julian Mendez
 *
 */
public interface ProbClause extends Clause {

	/**
	 * Returns the probability.
	 * 
	 * @return the probability
	 */
	String getProbability();

}
