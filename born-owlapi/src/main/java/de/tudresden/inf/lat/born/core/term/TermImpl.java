package de.tudresden.inf.lat.born.core.term;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of a term.
 * 
 * @author Julian Mendez
 *
 */
public class TermImpl implements Term {

	private final String name;
	private final List<Term> arguments = new ArrayList<>();
	private final Term.Type termType;

	/**
	 * Constructs an empty term.
	 */
	public TermImpl() {
		this.name = "";
		this.termType = Term.Type.ATOM;
	}

	/**
	 * Constructs a new variable.
	 * 
	 * @param name
	 *            variable name
	 */
	public TermImpl(String name) {
		Objects.requireNonNull(name);
		if (isLong(name)) {
			this.termType = Term.Type.LONG;
		} else if (isDouble(name)) {
			this.termType = Term.Type.DOUBLE;
		} else if (isVariable(name)) {
			this.termType = Term.Type.VARIABLE;
		} else {
			this.termType = Term.Type.ATOM;
		}
		this.name = name;
	}

	/**
	 * Constructs a new formula. If the arguments is an empty list, this is
	 * considered a constant.
	 * 
	 * @param name
	 *            formula name
	 * @param arguments
	 *            arguments
	 */
	public TermImpl(String name, List<Term> arguments) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(arguments);
		if (isLong(name) || isDouble(name) || isVariable(name)) {
			throw new IllegalArgumentException("Invalid functor: '" + name + "'. ");
		}
		if (arguments.isEmpty()) {
			this.termType = Term.Type.ATOM;
		} else {
			this.termType = Term.Type.COMPOUND_TERM;
		}
		this.name = name;
		this.arguments.addAll(arguments);
	}

	/**
	 * Constructs a new term. If the arguments is an empty list, this is
	 * considered a constant.
	 * 
	 * @param leftTerm
	 *            left term
	 * @param infixOperator
	 *            infix operator
	 * @param rightTerm
	 *            right term
	 */
	public TermImpl(Term leftTerm, String infixOperator, Term rightTerm) {
		Objects.requireNonNull(leftTerm);
		Objects.requireNonNull(rightTerm);
		this.name = Objects.requireNonNull(infixOperator);
		this.arguments.add(leftTerm);
		this.arguments.add(rightTerm);
		this.termType = Term.Type.INFIX_OPERATOR;
	}

	/**
	 * Tells whether the given string is a variable.
	 * 
	 * @param str
	 *            string
	 * @return <code>true</code> if and only if the given string is a variable
	 */
	public boolean isVariable(String str) {
		Objects.requireNonNull(str);
		boolean result = false;
		if (Objects.isNull(str) || str.trim().isEmpty()) {
			result = false;
		} else {
			char firstChar = str.trim().charAt(0);
			result = ('A' <= firstChar && firstChar <= 'Z') || (firstChar == '_');
		}

		return result;
	}

	/**
	 * Tells whether the given string is a long (number).
	 * 
	 * @param str
	 *            string
	 * @return <code>true</code> if and only if the string is a long (number)
	 */
	public boolean isLong(String str) {
		Objects.requireNonNull(str);
		boolean result = false;
		if (Objects.isNull(str) || str.trim().isEmpty()) {
			result = false;
		} else {
			try {
				Long.parseLong(str.trim());
				result = true;
			} catch (NumberFormatException e) {
				result = false;
			}
		}

		return result;
	}

	/**
	 * Tells whether the given string is a double (number).
	 * 
	 * @param str
	 *            string
	 * @return <code>true</code> if and only if the string is a double (number)
	 */
	public boolean isDouble(String str) {
		Objects.requireNonNull(str);
		boolean result = false;
		if (Objects.isNull(str) || str.trim().isEmpty()) {
			result = false;
		} else {
			try {
				Double.parseDouble(str.trim());
				result = true;
			} catch (NumberFormatException e) {
				result = false;
			}
		}

		return result;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<Term> getArguments() {
		return this.arguments;
	}

	@Override
	public Term.Type getType() {
		return this.termType;
	}

	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		if (this.termType.equals(Term.Type.COMPOUND_TERM)) {
			sb.append(Symbol.PAR_A_CHAR);
			Iterator<Term> it = this.arguments.iterator();
			while (it.hasNext()) {
				Term current = it.next();
				sb.append(current);
				if (it.hasNext()) {
					sb.append(Symbol.COMMA_CHAR);
					sb.append(Symbol.SPACE_CHAR);
				}
			}
			sb.append(Symbol.PAR_B_CHAR);
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Term)) {
			return false;
		} else {
			Term other = (Term) obj;
			return getType().equals(other.getType()) && getName().equals(other.getName())
					&& getArguments().equals(other.getArguments());
		}
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + 0x1F * (this.arguments.hashCode() + 0x1F * Term.Type.values().hashCode());
	}

	@Override
	public String toString() {
		return asString();
	}

}
