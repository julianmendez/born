package de.tudresden.inf.lat.born.tool.filter;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This models an OWL axiom filter. Every class implementing this interface has
 * a criterion to determine whether a given OWL axiom is accepted.
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
