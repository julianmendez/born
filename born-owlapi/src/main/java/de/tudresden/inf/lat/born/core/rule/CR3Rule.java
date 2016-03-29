package de.tudresden.inf.lat.born.core.rule;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Term;

/**
 * This is a completion rule for ProbLog.
 * <ul>
 * <li>CR-3 : <b>if</b> X \u2291 A &isin; S, A \u2291 &exist; r <i>.</i> B
 * &isin; <i>T</i><br>
 * <b>then</b> R := R &cup;{(X \u2291 &exist; r <i>.</i> B)}</li>
 * </ul>
 * <br>
 * 
 * @author Julian Mendez
 *
 */
public class CR3Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new CR-3 rule.
	 */
	public CR3Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term x = c.newVar("X");
		Term a = c.newVar("A");
		Term b = c.newVar("B");
		Term r = c.newVar("R");

		Term head = c.sub(x, c.exists(r, b));
		List<Term> body = new ArrayList<>();
		body.add(c.gci(a, c.exists(r, b)));
		body.add(c.sub(x, a));
		body.add(c.con(x));
		body.add(c.con(a));
		body.add(c.con(b));
		body.add(c.role(r));

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
		} else if (!(obj instanceof CR3Rule)) {
			return false;
		} else {
			CR3Rule other = (CR3Rule) obj;
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
