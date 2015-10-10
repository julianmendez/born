package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import org.semanticweb.owlapi.model.OWLClass;

/**
 * An object of this class represent a subsumption query.
 * 
 * @author Julian Mendez
 *
 */
public class SubsumptionQuery {

	public static final String QUERY_PART0 = "query(sub('";
	public static final String QUERY_PART1 = "', '";
	public static final String QUERY_PART2 = "')).";

	private final OWLClass subClass;
	private final OWLClass superClass;

	/**
	 * Constructs a new subsumption query.
	 * 
	 * @param subClass
	 *            sub class
	 * @param superClass
	 *            super class
	 */
	public SubsumptionQuery(OWLClass subClass, OWLClass superClass) {
		if (subClass == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (superClass == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.subClass = subClass;
		this.superClass = superClass;
	}

	/**
	 * Returns the sub class.
	 * 
	 * @return the sub class
	 */
	public OWLClass getSubClass() {
		return this.subClass;
	}

	/**
	 * Returns the super class.
	 * 
	 * @return the super class
	 */
	public OWLClass getSuperClass() {
		return this.superClass;
	}

	/**
	 * Returns a ProbLog string representation of this query.
	 * 
	 * @return a ProbLog string representation of this query
	 */
	public String asProblogString() {
		return QUERY_PART0 + this.subClass.getIRI() + QUERY_PART1 + this.superClass.getIRI() + QUERY_PART2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubsumptionQuery)) {
			return false;
		}
		SubsumptionQuery other = (SubsumptionQuery) obj;
		return getSubClass().equals(other.getSubClass()) && getSuperClass().equals(other.getSuperClass());
	}

	@Override
	public int hashCode() {
		return this.subClass.hashCode() + 0x1F * this.superClass.hashCode();
	}

	@Override
	public String toString() {
		return asProblogString();
	}

}
