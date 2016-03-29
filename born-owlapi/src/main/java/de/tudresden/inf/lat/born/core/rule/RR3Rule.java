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
public class RR3Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new RR-3 rule.
	 */
	public RR3Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term x = c.newVar("X");
		Term b = c.newVar("B");

		Term head = c.subx(x, b);
		List<Term> body = new ArrayList<>();
		body.add(c.indiv(x));
		body.add(c.con(b));
		body.add(c.assertion(x, b));

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
		} else if (!(obj instanceof RR3Rule)) {
			return false;
		} else {
			RR3Rule other = (RR3Rule) obj;
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
