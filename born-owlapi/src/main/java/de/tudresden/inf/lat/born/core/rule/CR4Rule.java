package de.tudresden.inf.lat.born.core.rule;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Term;

/**
 * This is a completion rule for ProbLog.
 * <ul>
 * <li>CR-4 : <b>if</b> X \u2291 &exist; r <i>.</i> Y &isin; R, Y \u2291 A
 * &isin; S, &exist; r <i>.</i> A \u2291 B &isin; <i>T</i><br>
 * <b>then</b> S := S &cup; {X \u2291 B}</li>
 * </ul>
 * <br>
 * 
 * @author Julian Mendez
 *
 */
public class CR4Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new CR-4 rule.
	 */
	public CR4Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term x = c.newVar("X");
		Term y = c.newVar("Y");
		Term a = c.newVar("A");
		Term b = c.newVar("B");
		Term r = c.newVar("R");

		Term head = c.subx(x, b);
		List<Term> body = new ArrayList<>();
		body.add(c.subs(c.exists(r, a), b));
		body.add(c.subx(x, c.exists(r, y)));
		body.add(c.subx(y, a));
		body.add(c.coni(x));
		body.add(c.coni(y));
		body.add(c.coni(a));
		body.add(c.coni(b));
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
		} else if (!(obj instanceof CR4Rule)) {
			return false;
		} else {
			CR4Rule other = (CR4Rule) obj;
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
