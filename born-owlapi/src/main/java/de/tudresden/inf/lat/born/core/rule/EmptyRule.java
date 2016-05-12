package de.tudresden.inf.lat.born.core.rule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.tudresden.inf.lat.born.core.term.Term;

/**
 * This is an empty completion rule for ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class EmptyRule implements CompletionRule {

	public static final String BEGIN_COMMENT = "%";
	public static final String END_COMMENT = "\n";

	private final String comment;

	/**
	 * Constructs a new empty rule.
	 */
	public EmptyRule() {
		this.comment = "";
	}

	public EmptyRule(String comment) {
		Objects.requireNonNull(comment);
		this.comment = comment;
	}

	@Override
	public Term getHead() {
		return null;
	}

	@Override
	public List<Term> getBody() {
		return Collections.emptyList();
	}

	@Override
	public String asString() {
		return BEGIN_COMMENT + comment + END_COMMENT;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof EmptyRule)) {
			return false;
		} else {
			EmptyRule other = (EmptyRule) obj;
			return this.comment.equals(other.comment);
		}
	}

	@Override
	public int hashCode() {
		return this.comment.hashCode();
	}

	@Override
	public String toString() {
		return asString();
	}

}
