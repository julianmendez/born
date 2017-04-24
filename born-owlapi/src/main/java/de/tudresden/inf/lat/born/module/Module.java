package de.tudresden.inf.lat.born.module;

import java.util.Objects;
import java.util.Set;

import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;

/**
 * An objects of this class models a module of normalized axioms.
 * 
 * @author Julian Mendez
 *
 */
public class Module {

	private Set<Integer> entities;
	private Set<NormalizedIntegerAxiom> axioms;

	/**
	 * Constructs a module.
	 * 
	 * @param entities
	 *            entities
	 * @param axioms
	 *            axioms
	 */
	public Module(Set<Integer> entities, Set<NormalizedIntegerAxiom> axioms) {
		this.entities = Objects.requireNonNull(entities);
		this.axioms = Objects.requireNonNull(axioms);
	}

	/**
	 * Returns the entities.
	 * 
	 * @return the entities
	 */
	public Set<Integer> getEntities() {
		return this.entities;
	}

	/**
	 * Returns the normalized axioms
	 * 
	 * @return the normalized axioms
	 */
	public Set<NormalizedIntegerAxiom> getAxioms() {
		return this.axioms;
	}

}
