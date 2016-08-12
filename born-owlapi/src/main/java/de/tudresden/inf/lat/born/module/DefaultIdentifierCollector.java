
package de.tudresden.inf.lat.born.module;

import java.util.Objects;
import java.util.Set;

import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;

/**
 * An object of this class collects symbols in an normalized axiom.
 * 
 * @author Julian Mendez
 *
 */
public class DefaultIdentifierCollector {

	private final NormalizedIntegerAxiom axiom;
	private final AuxiliaryIdentifierCollector collector = new AuxiliaryIdentifierCollector();

	/**
	 * Constructs a new BornIdentifier collector.
	 * 
	 * @param axiom
	 *            normalized axiom
	 */
	public DefaultIdentifierCollector(NormalizedIntegerAxiom axiom) {
		Objects.requireNonNull(axiom);
		this.axiom = axiom;
		axiom.accept(new AuxiliaryIdentifierCollector());
	}

	/**
	 * Returns the normalized axiom.
	 * 
	 * @return the normalized axiom
	 */
	public NormalizedIntegerAxiom getAxiom() {
		return this.axiom;
	}

	/**
	 * Returns the class BornIdentifiers found on the left-hand side of the
	 * given axiom.
	 *
	 * @return the class BornIdentifiers found on the left-hand side of the
	 *         given axiom
	 */
	public Set<Integer> getClassesOnTheLeft() {
		return this.collector.getClassesOnTheLeft();
	}

	/**
	 * Returns the class BornIdentifiers found on the right-hand side of the
	 * given axiom.
	 * 
	 * @return the class BornIdentifiers found on the right-hand side of the
	 *         given axiom
	 */
	public Set<Integer> getClassesOnTheRight() {
		return this.collector.getClassesOnTheRight();
	}

	/**
	 * Returns the object property BornIdentifiers found on the left-hand side
	 * of the given axiom.
	 * 
	 * @return the object property BornIdentifiers found on the left-hand side
	 *         of the given axiom
	 */
	public Set<Integer> getObjectPropertiesOnTheLeft() {
		return this.collector.getObjectPropertiesOnTheLeft();
	}

	/**
	 * Returns the object property found on the right-hand side of the given
	 * axiom.
	 * 
	 * @return the object property BornIdentifiers found on the right-hand side
	 *         of the given axiom
	 */
	public Set<Integer> getObjectPropertiesOnTheRight() {
		return this.collector.getObjectPropertiesOnTheRight();
	}

	@Override
	public int hashCode() {
		return this.axiom.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof DefaultIdentifierCollector)) {
			return false;
		} else {
			DefaultIdentifierCollector other = (DefaultIdentifierCollector) obj;
			return getAxiom().equals(other.getAxiom());
		}
	}

	@Override
	public String toString() {
		return this.axiom.toString();
	}

}
