package de.tudresden.inf.lat.born.core.rule;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Term;

/**
 * This is a rule for ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class RR1Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new RR-1 rule.
	 */
	public RR1Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term x = c.newVar("X");

		Term head = c.coni(x);
		List<Term> body = new ArrayList<>();
		body.add(c.con(x));

		this.clause = c.rule(head, body);
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
	public String asString() {
		return this.clause.asString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof RR1Rule)) {
			return false;
		} else {
			RR1Rule other = (RR1Rule) obj;
			return this.clause.equals(other.clause);
		}
	}

	@Override
	public int hashCode() {
		return this.clause.hashCode();
	}

	@Override
	public String toString() {
		return this.clause.toString();
	}

}
