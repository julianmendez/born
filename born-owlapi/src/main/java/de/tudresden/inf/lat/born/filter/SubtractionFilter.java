package de.tudresden.inf.lat.born.filter;

import java.util.Objects;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * 
 * @author Julian Mendez
 *
 */
public class SubtractionFilter implements OwlAxiomFilter {

	private Set<OWLAxiom> setOfAxioms;

	public SubtractionFilter(Set<OWLAxiom> setOfAxioms) {
		this.setOfAxioms = setOfAxioms;
	}

	@Override
	public boolean accept(OWLAxiom axiom) {
		Objects.requireNonNull(axiom);
		return !this.setOfAxioms.contains(axiom);
	}

}
