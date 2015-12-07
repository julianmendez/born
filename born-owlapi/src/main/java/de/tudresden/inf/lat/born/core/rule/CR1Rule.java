package de.tudresden.inf.lat.born.core.rule;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Term;

/**
 * This is a completion rule for ProbLog.
 * <ul>
 * <li>CR-1 : <b>if</b> X \u2291 A &isin; S, A \u2291 B &isin; <i>T</i><br>
 * <b>then</b> S := S &cup; {X \u2291 B}</li>
 * </ul>
 * <br>
 * 
 * @author Julian Mendez
 *
 */
public class CR1Rule implements CompletionRule {

	private final Clause clause;

	/**
	 * Constructs a new CR-1 rule.
	 */
	public CR1Rule() {
		FormulaConstructor c = new FormulaConstructor();

		Term x = c.newVar("X");
		Term a = c.newVar("A");
		Term b = c.newVar("B");

		Term head = c.sub(x, b);
		List<Term> body = new ArrayList<>();
		body.add(c.subs(a, b));
		body.add(c.sub(x, a));
		body.add(c.con(x));
		body.add(c.con(a));
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
		return this.clause.equals(obj);
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
