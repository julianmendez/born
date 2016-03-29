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
public class RR4Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new RR-4 rule.
	 */
	public RR4Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term r = c.newVar("R");
		Term x = c.newVar("X");
		Term b = c.newVar("B");

		Term head = c.subx(x, c.exists(r, b));
		List<Term> body = new ArrayList<>();
		body.add(c.role(r));
		body.add(c.indiv(x));
		body.add(c.indiv(b));
		body.add(c.assertion(r, x, b));

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
		} else if (!(obj instanceof RR4Rule)) {
			return false;
		} else {
			RR4Rule other = (RR4Rule) obj;
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
