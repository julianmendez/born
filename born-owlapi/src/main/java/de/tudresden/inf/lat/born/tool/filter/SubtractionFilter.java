package de.tudresden.inf.lat.born.tool.filter;

import java.util.Objects;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * An object of this class is an axiom filter that determines whether a given
 * axiom belongs to predefined set.
 * 
 * @author Julian Mendez
 *
 */
public class SubtractionFilter implements OwlAxiomFilter {

	private Set<OWLAxiom> setOfAxioms;

	/**
	 * Constructs a new subtraction filter.
	 * 
	 * @param setOfAxioms
	 *            set of axioms
	 */
	public SubtractionFilter(Set<OWLAxiom> setOfAxioms) {
		this.setOfAxioms = Objects.requireNonNull(setOfAxioms);
	}

	@Override
	public boolean accept(OWLAxiom axiom) {
		Objects.requireNonNull(axiom);
		return !this.setOfAxioms.contains(axiom);
	}

}
