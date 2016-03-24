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
public class FR3Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new FR 3 rule.
	 */
	public FR3Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term r = c.newVar("R");
		Term x = c.newVar("X");
		Term b = c.newVar("B");

		Term head = c.inst(r, x, b);
		List<Term> body = new ArrayList<>();
		body.add(c.subx(x, c.exists(r, b)));
		body.add(c.role(r));
		body.add(c.indiv(x));
		body.add(c.indiv(b));

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
		} else if (!(obj instanceof FR3Rule)) {
			return false;
		} else {
			FR3Rule other = (FR3Rule) obj;
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
