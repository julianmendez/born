package de.tudresden.inf.lat.born.module;

import java.util.Objects;
import java.util.Set;

import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;

public class Module {

	private Set<Integer> entities;
	private Set<NormalizedIntegerAxiom> axioms;

	public Module(Set<Integer> entities, Set<NormalizedIntegerAxiom> axioms) {
		this.entities = Objects.requireNonNull(entities);
		this.axioms = Objects.requireNonNull(axioms);
	}

	public Set<Integer> getEntities() {
		return this.entities;
	}

	public Set<NormalizedIntegerAxiom> getAxioms() {
		return this.axioms;
	}

}
