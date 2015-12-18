package de.tudresden.inf.lat.born.core.term;

import java.util.List;
import java.util.Objects;

/**
 * Default implementation of a clause.
 * 
 * @author Julian Mendez
 *
 */
public class ProbClauseImpl implements ProbClause {

	public static final String CERTAIN = "1";

	private final String probability;
	private final Clause clause;

	/**
	 * Constructs an empty clause.
	 */
	public ProbClauseImpl() {
		this.clause = new ClauseImpl();
		this.probability = CERTAIN;
	}

	/**
	 * Constructs a new clause.
	 * 
	 * @param head
	 *            head
	 * @param body
	 *            body
	 * @param probability
	 *            probability
	 */
	public ProbClauseImpl(Term head, List<Term> body, String probability) {
		Objects.requireNonNull(head);
		Objects.requireNonNull(body);
		this.clause = new ClauseImpl(head, body);
		this.probability = probability;
	}

	@Override
	public Term getHead() {
		return this.clause.getHead();
	}

	@Override
	public List<Term> getBody() {
		return this.clause.getBody();
	}

	@Override
	public String getProbability() {
		return this.probability;
	}

	@Override
	public String asString() {
		if (this.probability.equals(CERTAIN)) {
			return this.clause.asString();
		} else {
			return this.probability + Symbol.COLON_COLON + this.clause.asString();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Clause)) {
			return false;
		} else {
			Clause other = (Clause) obj;
			if (getHead().equals(other.getHead()) && getBody().equals(other.getBody())) {
				if (obj instanceof ProbClause) {
					ProbClause otherProb = (ProbClause) other;
					return getProbability().equals(otherProb.getProbability());
				} else {
					return getProbability().equals(CERTAIN);
				}
			} else {
				return false;
			}
		}
	}

	@Override
	public int hashCode() {
		return this.clause.hashCode() + 0x1F * this.probability.hashCode();
	}

	@Override
	public String toString() {
		return asString();
	}

}
