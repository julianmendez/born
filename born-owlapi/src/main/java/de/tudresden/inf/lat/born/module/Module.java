package de.tudresden.inf.lat.born.module;

import java.util.Objects;
import java.util.Set;

import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;

public class Module {

	private Set<Integer> entities;
	private Set<NormalizedIntegerAxiom> axioms;

	public Module(Set<Integer> entities, Set<NormalizedIntegerAxiom> axioms) {
		Objects.requireNonNull(entities);
		Objects.requireNonNull(axioms);
		this.entities = entities;
		this.axioms = axioms;
	}

	public Set<Integer> getEntities() {
		return this.entities;
	}

	public Set<NormalizedIntegerAxiom> getAxioms() {
		return this.axioms;
	}
	
}
