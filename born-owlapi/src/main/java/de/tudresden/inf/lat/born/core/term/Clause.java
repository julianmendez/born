package de.tudresden.inf.lat.born.core.term;

import java.util.List;

/**
 * This interface models a clause.
 * 
 * @author Julian Mendez
 *
 */
public interface Clause extends Formula {

	/**
	 * Returns the head.
	 * 
	 * @return the head
	 */
	Term getHead();

	/**
	 * Returns the body.
	 * 
	 * @return the body
	 */
	List<Term> getBody();

}
