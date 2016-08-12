package de.tudresden.inf.lat.born.module;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import de.tudresden.inf.lat.jcel.coreontology.axiom.FunctObjectPropAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI0Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NominalAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiomVisitor;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RangeAxiom;

/**
 * This is an auxiliary class used to collect the BornIdentifiers.
 * 
 * @author Julian Mendez
 *
 */
class AuxiliaryIdentifierCollector implements NormalizedIntegerAxiomVisitor<Boolean> {

	private final Set<Integer> classesOnTheLeft = new TreeSet<>();
	private final Set<Integer> classesOnTheRight = new TreeSet<>();
	private final Set<Integer> objectPropertiesOnTheLeft = new TreeSet<>();
	private final Set<Integer> objectPropertiesOnTheRight = new TreeSet<>();

	AuxiliaryIdentifierCollector() {
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
	public Boolean visit(FunctObjectPropAxiom axiom) {
		this.objectPropertiesOnTheLeft.add(axiom.getProperty());
		return true;
	}

	@Override
	public Boolean visit(GCI0Axiom axiom) {
		this.classesOnTheLeft.add(axiom.getSubClass());
		this.classesOnTheRight.add(axiom.getSuperClass());
		return true;
	}

	@Override
	public Boolean visit(GCI1Axiom axiom) {
		this.classesOnTheLeft.add(axiom.getLeftSubClass());
		this.classesOnTheLeft.add(axiom.getRightSubClass());
		this.classesOnTheRight.add(axiom.getSuperClass());
		return true;
	}

	@Override
	public Boolean visit(GCI2Axiom axiom) {
		this.classesOnTheLeft.add(axiom.getSubClass());
		this.objectPropertiesOnTheRight.add(axiom.getPropertyInSuperClass());
		this.classesOnTheRight.add(axiom.getClassInSuperClass());
		return true;
	}

	@Override
	public Boolean visit(GCI3Axiom axiom) {
		this.objectPropertiesOnTheLeft.add(axiom.getPropertyInSubClass());
		this.classesOnTheLeft.add(axiom.getClassInSubClass());
		this.classesOnTheRight.add(axiom.getSuperClass());
		return true;
	}

	@Override
	public Boolean visit(NominalAxiom axiom) {
		this.classesOnTheLeft.add(axiom.getClassExpression());
		this.classesOnTheRight.add(axiom.getClassExpression());
		return true;
	}

	@Override
	public Boolean visit(RangeAxiom axiom) {
		this.objectPropertiesOnTheLeft.add(axiom.getProperty());
		this.classesOnTheRight.add(axiom.getRange());
		return true;
	}

	@Override
	public Boolean visit(RI1Axiom axiom) {
		this.objectPropertiesOnTheRight.add(axiom.getSuperProperty());
		return true;
	}

	@Override
	public Boolean visit(RI2Axiom axiom) {
		this.objectPropertiesOnTheLeft.add(axiom.getSubProperty());
		this.objectPropertiesOnTheRight.add(axiom.getSuperProperty());
		return true;
	}

	@Override
	public Boolean visit(RI3Axiom axiom) {
		this.objectPropertiesOnTheLeft.add(axiom.getLeftSubProperty());
		this.objectPropertiesOnTheLeft.add(axiom.getRightSubProperty());
		this.objectPropertiesOnTheRight.add(axiom.getSuperProperty());
		return true;
	}

	@Override
	public int hashCode() {
		return this.classesOnTheLeft.hashCode() + 0x1F * (this.classesOnTheRight.hashCode() + 0x1F
				* (this.objectPropertiesOnTheLeft.hashCode() + 0x1F * this.objectPropertiesOnTheRight.hashCode()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof DefaultIdentifierCollector)) {
			return false;
		} else {
			AuxiliaryIdentifierCollector other = (AuxiliaryIdentifierCollector) obj;
			return getClassesOnTheLeft().equals(other.getClassesOnTheLeft())
					&& getClassesOnTheRight().equals(other.getClassesOnTheRight())
					&& getObjectPropertiesOnTheLeft().equals(other.getObjectPropertiesOnTheLeft())
					&& getObjectPropertiesOnTheRight().equals(other.getObjectPropertiesOnTheRight());
		}
	}

	@Override
	public String toString() {
		return this.classesOnTheLeft.toString() + "\n" + this.classesOnTheRight + "\n" + this.objectPropertiesOnTheLeft
				+ "\n" + this.objectPropertiesOnTheRight + "\n";
	}

}
