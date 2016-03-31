package de.tudresden.inf.lat.born.core.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.ClauseImpl;
import de.tudresden.inf.lat.born.core.term.Term;
import de.tudresden.inf.lat.born.core.term.TermImpl;

/**
 * An object that implements this class is a formula constructor.
 * 
 * @author Julian Mendez
 *
 */
public class FormulaConstructor {

	public static final String TOP = "top";
	public static final String CON = "con";
	public static final String CONI = "coni";
	public static final String ROLE = "role";
	public static final String INDIV = "indiv";
	public static final String SUB = "sub";
	public static final String SUBX = "subx";
	public static final String GCI = "gci";
	public static final String INST = "inst";
	public static final String ASSERTION = "assertion";
	public static final String EXISTS = "exists";
	public static final String AND = "and";
	public static final String QUERY = "query";

	/**
	 * Constructs a new formula constructor.
	 */
	public FormulaConstructor() {
	}

	/**
	 * Returns a new constant.
	 * 
	 * @param name
	 *            name
	 * @return new constant
	 */
	public Term newCons(String name) {
		Objects.requireNonNull(name);
		List<Term> emptyList = Collections.emptyList();
		return new TermImpl(name, emptyList);
	}

	/**
	 * Returns a new variable.
	 * 
	 * @param name
	 *            name
	 * @return a new variable
	 */

	public Term newVar(String name) {
		Objects.requireNonNull(name);
		Term ret = new TermImpl(name);
		if (!(ret.getType().equals(Term.Type.VARIABLE))) {
			throw new IllegalArgumentException("Invalid variable name: '" + name
					+ "'. A variable name must start with a capital letter or with an underscore ('_').");
		}
		return ret;
	}

	/**
	 * Returns the result of applying the functor to one parameter.
	 * 
	 * @param name
	 *            functor name
	 * @param term
	 *            parameter
	 * @return the result of applying the functor to one parameter
	 */
	public Term fun(String name, Term term) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(term);
		List<Term> arguments = new ArrayList<>();
		arguments.add(term);
		return new TermImpl(name, arguments);
	}

	/**
	 * Returns the result of applying the functor to two parameters.
	 * 
	 * @param name
	 *            functor name
	 * @param left
	 *            first parameter
	 * @param right
	 *            second parameter
	 * @return the result of applying the functor to two parameters
	 */
	public Term fun(String name, Term left, Term right) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
		List<Term> arguments = new ArrayList<>();
		arguments.add(left);
		arguments.add(right);
		return new TermImpl(name, arguments);
	}

	/**
	 * Returns the result of applying the functor to three parameters.
	 * 
	 * @param name
	 *            functor name
	 * @param first
	 *            first parameter
	 * @param second
	 *            second parameter
	 * @param third
	 *            third parameter
	 * @return the result of applying the functor to three parameters
	 */
	public Term fun(String name, Term first, Term second, Term third) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(first);
		Objects.requireNonNull(second);
		Objects.requireNonNull(third);
		List<Term> arguments = new ArrayList<>();
		arguments.add(first);
		arguments.add(second);
		arguments.add(third);
		return new TermImpl(name, arguments);
	}

	/**
	 * Returns the 'top' constant.
	 * 
	 * @return the 'top' constant
	 */
	public Term top() {
		return newCons(TOP);
	}

	/**
	 * Returns the result of declaring a term as a concept (compare to a class).
	 * 
	 * @param clss
	 *            the term
	 * @return the result of declaring a term as a concept
	 */
	public Term con(Term clss) {
		Objects.requireNonNull(clss);
		return fun(CON, clss);
	}

	/**
	 * Returns the result of declaring a term as a concept or individual.
	 * 
	 * @param clss
	 *            the term
	 * @return the result of declaring a term as a concept or individual
	 */
	public Term coni(Term clss) {
		Objects.requireNonNull(clss);
		return fun(CONI, clss);
	}

	/**
	 * Returns the result of declaring a term as a role (compare to an object
	 * property).
	 * 
	 * @param clss
	 *            the term
	 * @return the result of declaring a term as a role (compare to an object
	 *         property)
	 */
	public Term role(Term clss) {
		Objects.requireNonNull(clss);
		return fun(ROLE, clss);
	}

	/**
	 * Returns the result of declaring a term as an individual.
	 * 
	 * @param ind
	 *            the term
	 * @return the result of declaring a term as an individual
	 */
	public Term indiv(Term ind) {
		Objects.requireNonNull(ind);
		return fun(INDIV, ind);
	}

	/**
	 * Returns the axiom denoting subsumption.
	 * 
	 * @param subClass
	 *            sub class
	 * @param superClass
	 *            super class
	 * @return the axiom denoting subsumption
	 */
	public Term sub(Term subClass, Term superClass) {
		Objects.requireNonNull(subClass);
		Objects.requireNonNull(superClass);
		return fun(SUB, subClass, superClass);
	}

	/**
	 * Returns the axiom denoting subsumption.
	 * 
	 * @param subClass
	 *            sub class
	 * @param superClass
	 *            super class
	 * @return the axiom denoting subsumption
	 */
	public Term subx(Term subClass, Term superClass) {
		Objects.requireNonNull(subClass);
		Objects.requireNonNull(superClass);
		return fun(SUBX, subClass, superClass);
	}

	/**
	 * Returns the axiom denoting a subsumption declared in the ontology.
	 * 
	 * @param subClass
	 *            sub class
	 * @param superClass
	 *            super class
	 * @return the axiom denoting a subsumption declared in the ontology
	 */
	public Term gci(Term subClass, Term superClass) {
		Objects.requireNonNull(subClass);
		Objects.requireNonNull(superClass);
		return fun(GCI, subClass, superClass);
	}

	/**
	 * Returns the axiom denoting an concept assertion.
	 * 
	 * @param indiv
	 *            individual
	 * @param clss
	 *            class
	 * @return the axiom denoting an concept assertion
	 */
	public Term inst(Term indiv, Term clss) {
		Objects.requireNonNull(indiv);
		Objects.requireNonNull(clss);
		return fun(INST, indiv, clss);
	}

	/**
	 * Returns the axiom denoting a role assertion.
	 * 
	 * @param role
	 *            role
	 * @param indiv0
	 *            left individual
	 * @param indiv1
	 *            right individual
	 * @return the axiom denoting a role assertion
	 */
	public Term inst(Term role, Term indiv0, Term indiv1) {
		Objects.requireNonNull(indiv0);
		Objects.requireNonNull(indiv1);
		Objects.requireNonNull(role);
		return fun(INST, role, indiv0, indiv1);
	}

	/**
	 * Returns the construction of the intersection of two concepts (or class
	 * expressions).
	 * 
	 * @param left
	 *            first term
	 * @param right
	 *            second term
	 * @return the construction of the intersection of two concepts (or class
	 *         expressions)
	 * 
	 */
	public Term and(Term left, Term right) {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
		return fun(AND, left, right);
	}

	/**
	 * Returns the construction of an existential restriction.
	 * 
	 * @param property
	 *            object property
	 * @param clss
	 *            class expression
	 * @return the construction of an existential restriction
	 * 
	 */
	public Term exists(Term property, Term clss) {
		Objects.requireNonNull(property);
		Objects.requireNonNull(clss);
		return fun(EXISTS, property, clss);
	}

	/**
	 * Returns the construction of a rule.
	 * 
	 * @param head
	 *            head of the rule
	 * @param body
	 *            body of the rule
	 * @return the construction of a rule
	 * 
	 */
	public Clause rule(Term head, List<Term> body) {
		Objects.requireNonNull(head);
		Objects.requireNonNull(body);
		return new ClauseImpl(head, body);
	}

	/**
	 * Returns the construction of a query.
	 * 
	 * @param query
	 *            query
	 * @return the construction of a query
	 * 
	 */
	public Clause query(Term query) {
		Objects.requireNonNull(query);
		List<Term> list = new ArrayList<>();
		list.add(query);
		TermImpl term = new TermImpl(QUERY, list);
		List<Term> emptyList = Collections.emptyList();
		return new ClauseImpl(term, emptyList);
	}

}
