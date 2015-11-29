package de.tudresden.inf.lat.born.owlapi.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import de.tudresden.inf.lat.born.core.rule.FormulaConstructor;
import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Term;
import de.tudresden.inf.lat.jcel.coreontology.axiom.Annotation;
import de.tudresden.inf.lat.jcel.coreontology.axiom.FunctObjectPropAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI0Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NominalAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiomVisitor;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RangeAxiom;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerEntityManager;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactory;

/**
 * An object of this class renders a normalized integer axiom.
 * 
 * @author Julian Mendez
 *
 */
public class AxiomRenderer implements NormalizedIntegerAxiomVisitor<Clause> {

	public static final char APOSTROPHE = '\'';
	public static final char QUOTES = '"';
	public static final char BACKSLASH = '\\';

	private final IntegerOntologyObjectFactory factory;

	public AxiomRenderer(IntegerOntologyObjectFactory factory) {
		this.factory = factory;
	}

	String removeDoubleBackslash(String str) {
		StringBuffer sbuf = new StringBuffer();
		boolean backslash = false;
		for (int index = 0; index < str.length(); index++) {
			char ch = str.charAt(index);
			if (ch == BACKSLASH) {
				if (backslash) {
					backslash = false;
				} else {
					sbuf.append(ch);
					backslash = true;
				}
			} else {
				sbuf.append(ch);
				backslash = false;
			}
		}
		return sbuf.toString();
	}

	String getValue(String value) {
		if (value == null) {
			return "";
		} else {
			String str = value.toString().trim();
			if (str.startsWith("" + QUOTES)) {
				int pos = str.indexOf(QUOTES, 1);
				if (pos == -1) {
					return removeDoubleBackslash(str);
				} else {
					return removeDoubleBackslash(str.substring(1, pos));
				}
			} else {
				return str;
			}
		}
	}

	Term get(int identifier) {
		FormulaConstructor c = new FormulaConstructor();
		if (identifier == IntegerEntityManager.topClassId) {
			return c.top();
		} else {
			String name = this.factory.getEntityManager().getName(identifier).trim();
			return c.newCons(APOSTROPHE + name + APOSTROPHE);
		}
	}

	Clause ax(Term str, Set<Annotation> annotations) {
		FormulaConstructor c = new FormulaConstructor();
		if (annotations.isEmpty()) {
			List<Term> emptyList = Collections.emptyList();
			return c.rule(str, emptyList);

		} else if (annotations.size() == 1) {
			Annotation annotation = annotations.iterator().next();
			String value = getValue(annotation.getAnnotationValue());
			Term newCons = c.newCons(value);
			List<Term> preconditions = new ArrayList<Term>();
			preconditions.add(newCons);
			return c.rule(str, preconditions);

		} else {
			throw new RuntimeException("Too many annotations in axiom: " + annotations.toString());

		}
	}

	public Clause renderDeclarationOfClass(Integer entity) {
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(entity);
		Set<Annotation> emptySet = Collections.emptySet();
		Clause clause = ax(c.con(a), emptySet);
		return clause;
	}

	public Clause renderDeclarationOfObjectProperty(Integer entity) {
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(entity);
		Set<Annotation> emptySet = Collections.emptySet();
		Clause clause = ax(c.role(a), emptySet);
		return clause;
	}

	@Override
	public Clause visit(FunctObjectPropAxiom axiom) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clause visit(GCI0Axiom axiom) {
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(axiom.getSubClass());
		Term b = get(axiom.getSuperClass());

		return ax(c.subs(a, b), axiom.getAnnotations());
	}

	@Override
	public Clause visit(GCI1Axiom axiom) {
		FormulaConstructor c = new FormulaConstructor();
		Term a1 = get(axiom.getLeftSubClass());
		Term a2 = get(axiom.getRightSubClass());
		Term b = get(axiom.getSuperClass());

		return ax(c.subs(c.and(a1, a2), b), axiom.getAnnotations());
	}

	@Override
	public Clause visit(GCI2Axiom axiom) {
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(axiom.getSubClass());
		Term r = get(axiom.getPropertyInSuperClass());
		Term b = get(axiom.getClassInSuperClass());

		return ax(c.subs(a, c.exists(r, b)), axiom.getAnnotations());
	}

	@Override
	public Clause visit(GCI3Axiom axiom) {
		FormulaConstructor c = new FormulaConstructor();
		Term r = get(axiom.getPropertyInSubClass());
		Term a = get(axiom.getClassInSubClass());
		Term b = get(axiom.getSuperClass());

		return ax(c.subs(c.exists(r, a), b), axiom.getAnnotations());
	}

	@Override
	public Clause visit(NominalAxiom axiom) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clause visit(RangeAxiom axiom) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clause visit(RI1Axiom axiom) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clause visit(RI2Axiom axiom) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clause visit(RI3Axiom axiom) {
		throw new UnsupportedOperationException();
	}

}
