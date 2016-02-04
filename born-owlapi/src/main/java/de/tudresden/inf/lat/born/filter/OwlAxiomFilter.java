package de.tudresden.inf.lat.born.filter;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * 
 * @author Julian Mendez
 *
 */
@FunctionalInterface
public interface OwlAxiomFilter {

	/**
	 * Tells whether the given axiom should be accepted.
	 * 
	 * @param axiom
	 *            axiom
	 * @return <code>true</code> if and only if the given axiom should be
	 *         accepted
	 */
	boolean accept(OWLAxiom axiom);

}
