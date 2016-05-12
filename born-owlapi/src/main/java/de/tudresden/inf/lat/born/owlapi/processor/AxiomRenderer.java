package de.tudresden.inf.lat.born.owlapi.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import de.tudresden.inf.lat.born.core.rule.FormulaConstructor;
import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.core.term.Term;
import de.tudresden.inf.lat.jcel.coreontology.axiom.FunctObjectPropAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI0Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.IntegerAnnotation;
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
public class AxiomRenderer implements NormalizedIntegerAxiomVisitor<Set<Clause>> {

	public static final char APOSTROPHE = '\'';
	public static final char QUOTES = '"';
	public static final char BACKSLASH = '\\';

	private final IntegerOntologyObjectFactory factory;

	public AxiomRenderer(IntegerOntologyObjectFactory factory) {
		Objects.requireNonNull(factory);
		this.factory = factory;
	}

	String removeDoubleBackslash(String str) {
		Objects.requireNonNull(str);
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

	String removeApostrophes(String symbolStr0) {
		String symbolStr = symbolStr0;
		if (symbolStr.startsWith("" + Symbol.APOSTROPHE_CHAR) && symbolStr.endsWith("" + Symbol.APOSTROPHE_CHAR)) {
			symbolStr = symbolStr.substring(1);
			symbolStr = symbolStr.substring(0, symbolStr.length() - 1);
			return symbolStr;
		} else {
			return symbolStr0;
		}
	}

	String getValue(String value) {
		if (Objects.isNull(value)) {
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

	Clause ax(Term str, Set<IntegerAnnotation> annotations) {
		FormulaConstructor c = new FormulaConstructor();
		if (annotations.isEmpty()) {
			List<Term> emptyList = Collections.emptyList();
			return c.rule(str, emptyList);

		} else if (annotations.size() == 1) {
			IntegerAnnotation annotation = annotations.iterator().next();
			String value = removeApostrophes(getValue(get(annotation.getAnnotationValue()).asString()));
			Term newCons = c.newCons(value);
			List<Term> preconditions = new ArrayList<Term>();
			preconditions.add(newCons);
			return c.rule(str, preconditions);

		} else {
			throw new RuntimeException("Too many annotations in axiom: " + annotations.toString());

		}
	}

	public Clause renderDeclarationOfClass(Integer entity) {
		Objects.requireNonNull(entity);
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(entity);
		Set<IntegerAnnotation> emptySet = Collections.emptySet();
		Clause clause = ax(c.con(a), emptySet);
		return clause;
	}

	public Clause renderDeclarationOfObjectProperty(Integer entity) {
		Objects.requireNonNull(entity);
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(entity);
		Set<IntegerAnnotation> emptySet = Collections.emptySet();
		Clause clause = ax(c.role(a), emptySet);
		return clause;
	}

	public Clause renderDeclarationOfIndividual(Integer entity) {
		Objects.requireNonNull(entity);
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(entity);
		Set<IntegerAnnotation> emptySet = Collections.emptySet();
		Clause clause = ax(c.indiv(a), emptySet);
		return clause;
	}

	@Override
	public Set<Clause> visit(FunctObjectPropAxiom axiom) {
		Objects.requireNonNull(axiom);
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Clause> visit(GCI0Axiom axiom) {
		Objects.requireNonNull(axiom);
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(axiom.getSubClass());
		Term b = get(axiom.getSuperClass());

		return Collections.singleton(ax(c.gci(a, b), axiom.getAnnotations()));
	}

	@Override
	public Set<Clause> visit(GCI1Axiom axiom) {
		Objects.requireNonNull(axiom);
		FormulaConstructor c = new FormulaConstructor();
		Term a1 = get(axiom.getLeftSubClass());
		Term a2 = get(axiom.getRightSubClass());
		Term b = get(axiom.getSuperClass());

		return Collections.singleton(ax(c.gci(c.and(a1, a2), b), axiom.getAnnotations()));
	}

	@Override
	public Set<Clause> visit(GCI2Axiom axiom) {
		Objects.requireNonNull(axiom);
		FormulaConstructor c = new FormulaConstructor();
		Term a = get(axiom.getSubClass());
		Term r = get(axiom.getPropertyInSuperClass());
		Term b = get(axiom.getClassInSuperClass());

		return Collections.singleton(ax(c.gci(a, c.exists(r, b)), axiom.getAnnotations()));
	}

	@Override
	public Set<Clause> visit(GCI3Axiom axiom) {
		Objects.requireNonNull(axiom);
		FormulaConstructor c = new FormulaConstructor();
		Term r = get(axiom.getPropertyInSubClass());
		Term a = get(axiom.getClassInSubClass());
		Term b = get(axiom.getSuperClass());

		return Collections.singleton(ax(c.gci(c.exists(r, a), b), axiom.getAnnotations()));
	}

	@Override
	public Set<Clause> visit(NominalAxiom axiom) {
		Objects.requireNonNull(axiom);
		Set<Clause> ret = new HashSet<Clause>();
		FormulaConstructor c = new FormulaConstructor();
		Term i = get(axiom.getIndividual());
		Term a = get(axiom.getClassExpression());

		ret.add(ax(c.gci(i, a), new HashSet<IntegerAnnotation>()));
		ret.add(ax(c.gci(a, i), new HashSet<IntegerAnnotation>()));
		return ret;
	}

	@Override
	public Set<Clause> visit(RangeAxiom axiom) {
		Objects.requireNonNull(axiom);
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Clause> visit(RI1Axiom axiom) {
		Objects.requireNonNull(axiom);
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Clause> visit(RI2Axiom axiom) {
		Objects.requireNonNull(axiom);
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Clause> visit(RI3Axiom axiom) {
		Objects.requireNonNull(axiom);
		throw new UnsupportedOperationException();
	}

}
