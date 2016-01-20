
package de.tudresden.inf.lat.born.module;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import de.tudresden.inf.lat.jcel.coreontology.axiom.FunctObjectPropAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI0Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NominalAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiomVisitor;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RangeAxiom;

/**
 * An object of this class collects symbols in an normalized axiom.
 * 
 * @author Julian Mendez
 *
 */
public class DefaultIdentifierCollector {

	private final Set<Integer> classesOnTheLeft = new TreeSet<>();
	private final Set<Integer> classesOnTheRight = new TreeSet<>();
	private final Set<Integer> objectPropertiesOnTheLeft = new TreeSet<>();
	private final Set<Integer> objectPropertiesOnTheRight = new TreeSet<>();
	private final NormalizedIntegerAxiom axiom;

	/**
	 * Constructs a new BornIdentifier collector.
	 * 
	 * @param axiom
	 *            normalized axiom
	 */
	public DefaultIdentifierCollector(NormalizedIntegerAxiom axiom) {
		Objects.requireNonNull(axiom);
		this.axiom = axiom;
		axiom.accept(new AuxBornIdentifierCollector());
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
		return Collections.unmodifiableSet(this.classesOnTheLeft);
	}

	/**
	 * Returns the class BornIdentifiers found on the right-hand side of the
	 * given axiom.
	 * 
	 * @return the class BornIdentifiers found on the right-hand side of the
	 *         given axiom
	 */
	public Set<Integer> getClassesOnTheRight() {
		return Collections.unmodifiableSet(this.classesOnTheRight);
	}

	/**
	 * Returns the object property BornIdentifiers found on the left-hand side
	 * of the given axiom.
	 * 
	 * @return the object property BornIdentifiers found on the left-hand side
	 *         of the given axiom
	 */
	public Set<Integer> getObjectPropertiesOnTheLeft() {
		return Collections.unmodifiableSet(this.objectPropertiesOnTheLeft);
	}

	/**
	 * Returns the object property found on the right-hand side of the given
	 * axiom.
	 * 
	 * @return the object property BornIdentifiers found on the right-hand side
	 *         of the given axiom
	 */
	public Set<Integer> getObjectPropertiesOnTheRight() {
		return Collections.unmodifiableSet(this.objectPropertiesOnTheRight);
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

	/**
	 * This is an auxiliary class used to collect the BornIdentifiers.
	 * 
	 * @author Julian Mendez
	 *
	 */
	private class AuxBornIdentifierCollector implements NormalizedIntegerAxiomVisitor<Boolean> {

		AuxBornIdentifierCollector() {
		}

		@Override
		public Boolean visit(FunctObjectPropAxiom axiom) {
			objectPropertiesOnTheLeft.add(axiom.getProperty());
			return true;
		}

		@Override
		public Boolean visit(GCI0Axiom axiom) {
			classesOnTheLeft.add(axiom.getSubClass());
			classesOnTheRight.add(axiom.getSuperClass());
			return true;
		}

		@Override
		public Boolean visit(GCI1Axiom axiom) {
			classesOnTheLeft.add(axiom.getLeftSubClass());
			classesOnTheLeft.add(axiom.getRightSubClass());
			classesOnTheRight.add(axiom.getSuperClass());
			return true;
		}

		@Override
		public Boolean visit(GCI2Axiom axiom) {
			classesOnTheLeft.add(axiom.getSubClass());
			objectPropertiesOnTheRight.add(axiom.getPropertyInSuperClass());
			classesOnTheRight.add(axiom.getClassInSuperClass());
			return true;
		}

		@Override
		public Boolean visit(GCI3Axiom axiom) {
			objectPropertiesOnTheLeft.add(axiom.getPropertyInSubClass());
			classesOnTheLeft.add(axiom.getClassInSubClass());
			classesOnTheRight.add(axiom.getSuperClass());
			return true;
		}

		@Override
		public Boolean visit(NominalAxiom axiom) {
			classesOnTheLeft.add(axiom.getClassExpression());
			classesOnTheRight.add(axiom.getClassExpression());
			return true;
		}

		@Override
		public Boolean visit(RangeAxiom axiom) {
			objectPropertiesOnTheLeft.add(axiom.getProperty());
			classesOnTheRight.add(axiom.getRange());
			return true;
		}

		@Override
		public Boolean visit(RI1Axiom axiom) {
			objectPropertiesOnTheRight.add(axiom.getSuperProperty());
			return true;
		}

		@Override
		public Boolean visit(RI2Axiom axiom) {
			objectPropertiesOnTheLeft.add(axiom.getSubProperty());
			objectPropertiesOnTheRight.add(axiom.getSuperProperty());
			return true;
		}

		@Override
		public Boolean visit(RI3Axiom axiom) {
			objectPropertiesOnTheLeft.add(axiom.getLeftSubProperty());
			objectPropertiesOnTheLeft.add(axiom.getRightSubProperty());
			objectPropertiesOnTheRight.add(axiom.getSuperProperty());
			return true;
		}
	}

}
