package de.tudresden.inf.lat.born.core.rule;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Term;

/**
 * This is a completion rule for ProbLog.
 * <ul>
 * <li>CR-2 : <b>if</b> X \u2291 A<sub>1</sub> &isin; S, X \u2291 A<sub>2</sub>
 * &isin; S, A<sub>1</sub> \u2293 A<sub>2</sub> \u2291 B &isin; <i>T</i><br>
 * <b>then</b> S := S &cup; {X \u2291 B}</li>
 * </ul>
 * <br>
 * 
 * @author Julian Mendez
 *
 */
public class CR2Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new CR-2 rule.
	 */
	public CR2Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term x = c.newVar("X");
		Term a1 = c.newVar("A1");
		Term a2 = c.newVar("A2");
		Term b = c.newVar("B");

		Term head = c.sub(x, b);
		List<Term> body = new ArrayList<>();
		body.add(c.subs(c.and(a1, a2), b));
		body.add(c.sub(x, a1));
		body.add(c.sub(x, a2));
		body.add(c.con(x));
		body.add(c.con(a1));
		body.add(c.con(a2));
		body.add(c.con(b));

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
		} else if (!(obj instanceof CR2Rule)) {
			return false;
		} else {
			CR2Rule other = (CR2Rule) obj;
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
