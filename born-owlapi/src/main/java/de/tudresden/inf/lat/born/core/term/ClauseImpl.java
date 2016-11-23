package de.tudresden.inf.lat.born.core.term;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of a clause.
 * 
 * @author Julian Mendez
 *
 */
public class ClauseImpl implements Clause {

	public static final String TAB = "   ";

	private final Term head;
	private final List<Term> body = new ArrayList<>();

	/**
	 * Constructs an empty clause.
	 */
	public ClauseImpl() {
		this.head = new TermImpl();
	}

	/**
	 * Constructs a new clause.
	 * 
	 * @param head
	 *            head
	 * @param body
	 *            body
	 */
	public ClauseImpl(Term head, List<Term> body) {
		Objects.requireNonNull(body);
		this.head = Objects.requireNonNull(head);
		this.body.addAll(body);
	}

	@Override
	public Term getHead() {
		return this.head;
	}

	@Override
	public List<Term> getBody() {
		return this.body;
	}

	@Override
	public String asString() {
		if (this.body.isEmpty()) {
			return this.head.asString() + Symbol.POINT_CHAR + Symbol.NEW_LINE_CHAR;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(this.head.asString() + Symbol.SPACE_CHAR + Symbol.IF_SYMBOL + Symbol.SPACE_CHAR);
			if (this.body.size() == 1) {
				sb.append(this.body.iterator().next().asString() + Symbol.POINT_CHAR + Symbol.NEW_LINE_CHAR);
				return sb.toString();
			} else {
				boolean[] first = new boolean[1];
				first[0] = true;
				this.body.forEach(term -> {
					if (first[0]) {
						first[0] = false;
					} else {
						sb.append(Symbol.COMMA_CHAR);
						sb.append(Symbol.SPACE_CHAR);
					}
					sb.append(term.asString());
				});
				sb.append(Symbol.POINT_CHAR);
				sb.append(Symbol.NEW_LINE_CHAR);
				return sb.toString();
			}
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
			return getHead().equals(other.getHead()) && getBody().equals(other.getBody());
		}
	}

	@Override
	public int hashCode() {
		return this.head.hashCode() + 0x1F * this.body.hashCode();
	}

	@Override
	public String toString() {
		return asString();
	}

}
