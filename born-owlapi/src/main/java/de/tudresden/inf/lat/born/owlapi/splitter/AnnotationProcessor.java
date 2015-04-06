package de.tudresden.inf.lat.born.owlapi.splitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * An object of this does the splitting of an ontology, axiom by axiom.
 * 
 * @author Julian Mendez
 *
 */
public class AnnotationProcessor implements OWLAxiomVisitorEx<Boolean> {

	public static final String VARIABLE_PREFIX = "x";
	public static final String QUOTES = "\"";

	private final OWLDataFactory df;
	private final OWLOntology owlOntology;
	private final List<String> variableOrder = new ArrayList<String>();
	private final Map<String, String> network = new TreeMap<String, String>();

	public AnnotationProcessor(OWLOntologyManager manager)
			throws OWLOntologyCreationException {
		this.owlOntology = manager.createOntology();
		this.df = manager.getOWLDataFactory();
	}

	public List<String> getVariables() {
		return Collections.unmodifiableList(this.variableOrder);
	}

	public String getValue(String variable) {
		return this.network.get(variable);
	}

	public OWLOntology getOWLOntology() {
		return this.owlOntology;
	}

	public Map<String, String> getNetwork() {
		return this.network;
	}

	String asString(OWLAnnotationValue value) {
		if (value == null) {
			return "";
		} else {
			String str = value.toString().trim();
			if (str.startsWith(QUOTES)) {
				int pos = str.indexOf(QUOTES, QUOTES.length());
				if (pos == -1) {
					return str;
				} else {
					return str.substring(QUOTES.length(), pos);
				}
			} else {
				return str;
			}
		}
	}

	Set<OWLAnnotation> reg(Set<OWLAnnotation> annotations) {
		if (annotations.isEmpty()) {
			return Collections.emptySet();

		} else if (annotations.size() == 1) {
			OWLAnnotation annotation = annotations.iterator().next();
			String varName = VARIABLE_PREFIX + this.network.keySet().size();
			this.variableOrder.add(varName);
			String annotStr = asString(annotation.getValue());
			this.network.put(varName, annotStr);
			OWLAnnotationValue value = this.df.getOWLLiteral(varName);
			OWLAnnotation newAnnotation = this.df.getOWLAnnotation(
					annotation.getProperty(), value);
			return Collections.singleton(newAnnotation);

		} else {
			throw new IllegalArgumentException(
					"Unexpected number of annotations. The OWL axiom can have at most 1 annotation. Annotations: '"
							+ annotations.toString() + "'.");

		}
	}

	boolean add(OWLAxiom axiom) {
		this.owlOntology.getOWLOntologyManager().addAxiom(this.owlOntology,
				axiom);
		return true;
	}

	@Override
	public Boolean visit(OWLSubAnnotationPropertyOfAxiom a) {
		return add(this.df.getOWLSubAnnotationPropertyOfAxiom(
				a.getSubProperty(), a.getSuperProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyDomainAxiom a) {
		return add(this.df.getOWLAnnotationPropertyDomainAxiom(a.getProperty(),
				a.getDomain(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyRangeAxiom a) {
		return add(this.df.getOWLAnnotationPropertyRangeAxiom(a.getProperty(),
				a.getRange(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLSubClassOfAxiom a) {
		return add(this.df.getOWLSubClassOfAxiom(a.getSubClass(),
				a.getSuperClass(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLNegativeObjectPropertyAssertionAxiom a) {
		return add(this.df.getOWLNegativeObjectPropertyAssertionAxiom(
				a.getProperty(), a.getSubject(), a.getObject(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLAsymmetricObjectPropertyAxiom a) {
		return add(this.df.getOWLAsymmetricObjectPropertyAxiom(a.getProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLReflexiveObjectPropertyAxiom a) {
		return add(this.df.getOWLReflexiveObjectPropertyAxiom(a.getProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDisjointClassesAxiom a) {
		return add(this.df.getOWLDisjointClassesAxiom(a.getClassExpressions(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDataPropertyDomainAxiom a) {
		return add(this.df.getOWLDataPropertyDomainAxiom(a.getProperty(),
				a.getDomain(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLObjectPropertyDomainAxiom a) {
		return add(this.df.getOWLObjectPropertyDomainAxiom(a.getProperty(),
				a.getDomain(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLEquivalentObjectPropertiesAxiom a) {
		return add(this.df.getOWLEquivalentObjectPropertiesAxiom(
				a.getProperties(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLNegativeDataPropertyAssertionAxiom a) {
		return add(this.df.getOWLNegativeDataPropertyAssertionAxiom(
				a.getProperty(), a.getSubject(), a.getObject(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDifferentIndividualsAxiom a) {
		return add(this.df.getOWLDifferentIndividualsAxiom(a.getIndividuals(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDisjointDataPropertiesAxiom a) {
		return add(this.df.getOWLDisjointDataPropertiesAxiom(a.getProperties(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDisjointObjectPropertiesAxiom a) {
		return add(this.df.getOWLDisjointObjectPropertiesAxiom(
				a.getProperties(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLObjectPropertyRangeAxiom a) {
		return add(this.df.getOWLObjectPropertyRangeAxiom(a.getProperty(),
				a.getRange(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLObjectPropertyAssertionAxiom a) {
		return add(this.df.getOWLObjectPropertyAssertionAxiom(a.getProperty(),
				a.getSubject(), a.getObject(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLFunctionalObjectPropertyAxiom a) {
		return add(this.df.getOWLFunctionalObjectPropertyAxiom(a.getProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLSubObjectPropertyOfAxiom a) {
		return add(this.df.getOWLSubObjectPropertyOfAxiom(a.getSubProperty(),
				a.getSuperProperty(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDisjointUnionAxiom a) {
		return add(this.df.getOWLDisjointUnionAxiom(a.getOWLClass(),
				a.getClassExpressions(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDeclarationAxiom a) {
		return add(this.df.getOWLDeclarationAxiom(a.getEntity(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLAnnotationAssertionAxiom a) {
		return add(this.df.getOWLAnnotationAssertionAxiom(a.getProperty(),
				a.getSubject(), a.getValue(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLSymmetricObjectPropertyAxiom a) {
		return add(this.df.getOWLSymmetricObjectPropertyAxiom(a.getProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDataPropertyRangeAxiom a) {
		return add(this.df.getOWLDataPropertyRangeAxiom(a.getProperty(),
				a.getRange(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLFunctionalDataPropertyAxiom a) {
		return add(this.df.getOWLFunctionalDataPropertyAxiom(a.getProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLEquivalentDataPropertiesAxiom a) {
		return add(this.df.getOWLEquivalentDataPropertiesAxiom(
				a.getProperties(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLClassAssertionAxiom a) {
		return add(this.df.getOWLClassAssertionAxiom(a.getClassExpression(),
				a.getIndividual(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLEquivalentClassesAxiom a) {
		return add(this.df.getOWLEquivalentClassesAxiom(
				a.getClassExpressions(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDataPropertyAssertionAxiom a) {
		return add(this.df.getOWLDataPropertyAssertionAxiom(a.getProperty(),
				a.getSubject(), a.getObject(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLTransitiveObjectPropertyAxiom a) {
		return add(this.df.getOWLTransitiveObjectPropertyAxiom(a.getProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLIrreflexiveObjectPropertyAxiom a) {
		return add(this.df.getOWLIrreflexiveObjectPropertyAxiom(
				a.getProperty(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLSubDataPropertyOfAxiom a) {
		return add(this.df.getOWLSubDataPropertyOfAxiom(a.getSubProperty(),
				a.getSuperProperty(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom a) {
		return add(this.df.getOWLInverseFunctionalObjectPropertyAxiom(
				a.getProperty(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLSameIndividualAxiom a) {
		return add(this.df.getOWLSameIndividualAxiom(a.getIndividuals(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLSubPropertyChainOfAxiom a) {
		return add(this.df.getOWLSubPropertyChainOfAxiom(a.getPropertyChain(),
				a.getSuperProperty(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLInverseObjectPropertiesAxiom a) {
		return add(this.df.getOWLInverseObjectPropertiesAxiom(
				a.getFirstProperty(), a.getSecondProperty(),
				reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLHasKeyAxiom a) {
		return add(this.df.getOWLHasKeyAxiom(a.getClassExpression(),
				a.getObjectPropertyExpressions(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(OWLDatatypeDefinitionAxiom a) {
		return add(this.df.getOWLDatatypeDefinitionAxiom(a.getDatatype(),
				a.getDataRange(), reg(a.getAnnotations())));
	}

	@Override
	public Boolean visit(SWRLRule r) {
		return add(this.df.getSWRLRule(r.getBody(), r.getHead(),
				reg(r.getAnnotations())));
	}

}
