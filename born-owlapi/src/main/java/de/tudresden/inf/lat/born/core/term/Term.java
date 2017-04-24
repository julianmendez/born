package de.tudresden.inf.lat.born.core.term;

import java.util.List;

/**
 * This interface models a term.
 * 
 * @author Julian Mendez
 *
 */
public interface Term extends Formula {

	/**
	 * This models the types of terms.
	 * 
	 * @author Julian Mendez
	 *
	 */
	enum Type {

		UNDEFINED, ATOM, LONG, DOUBLE, VARIABLE, COMPOUND_TERM, INFIX_OPERATOR

	}

	/**
	 * Returns the type of this term.
	 * 
	 * @return the type of this term
	 */
	Type getType();

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Returns the arguments.
	 * 
	 * @return the arguments
	 */
	List<Term> getArguments();

}
