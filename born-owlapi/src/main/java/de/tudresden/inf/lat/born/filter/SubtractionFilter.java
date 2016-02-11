package de.tudresden.inf.lat.born.filter;

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

	public SubtractionFilter(Set<OWLAxiom> setOfAxioms) {
		Objects.requireNonNull(setOfAxioms);
		this.setOfAxioms = setOfAxioms;
	}

	@Override
	public boolean accept(OWLAxiom axiom) {
		Objects.requireNonNull(axiom);
		return !this.setOfAxioms.contains(axiom);
	}

}
