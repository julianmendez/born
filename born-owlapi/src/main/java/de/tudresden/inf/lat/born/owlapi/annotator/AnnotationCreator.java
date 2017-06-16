package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
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

import de.tudresden.inf.lat.born.core.common.OptMap;
import de.tudresden.inf.lat.born.core.common.OptMapImpl;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.problog.parser.Token;
import de.tudresden.inf.lat.born.problog.parser.TokenCreator;

/**
 * An object of this annotates an ontology, axiom by axiom.
 * 
 * @author Julian Mendez
 *
 */
public class AnnotationCreator implements OWLAxiomVisitorEx<Boolean> {

	public static final URI PROBABILITY_URI = URI.create("https://lat.inf.tu-dresden.de/systems/born#probability");

	public static final String VARIABLE_PREFIX = "x";
	public static final String QUOTES = "\"";
	public static final String PROBABILITY_OPERATOR = "::";

	private final OWLAnnotationProperty annotationProperty;
	private final OWLDataFactory df;
	private final OWLOntology owlOntology;
	private final List<String> variableOrder = new ArrayList<>();
	private final Set<String> bayesianNetworkVariableSet = new TreeSet<>();
	private final List<String> bayesianNetworkVariableList = new ArrayList<>();
	private final OptMap<String, String> network = new OptMapImpl<>(new TreeMap<>());
	private int counter = 0;
	private final double threshold;

	/**
	 * Constructs a new annotation creator.
	 * 
	 * @param manager
	 *            OWL ontology manager
	 * @param threshold
	 *            threshold
	 * @param bayesianNetworkVariables
	 *            variables of the input Bayesian network
	 * @throws OWLOntologyCreationException
	 *             if something went wrong while creating the OWL ontology
	 */
	public AnnotationCreator(OWLOntologyManager manager, double threshold, Set<String> bayesianNetworkVariables)
			throws OWLOntologyCreationException {
		Objects.requireNonNull(manager);
		Objects.requireNonNull(bayesianNetworkVariables);
		this.owlOntology = manager.createOntology();
		this.df = manager.getOWLDataFactory();
		IRI probabilityIri = IRI.create(PROBABILITY_URI);
		this.annotationProperty = manager.getOWLDataFactory().getOWLAnnotationProperty(probabilityIri);
		this.threshold = threshold;
		this.bayesianNetworkVariableSet.addAll(bayesianNetworkVariables);
		this.bayesianNetworkVariableList.addAll(bayesianNetworkVariables);
	}

	public static Set<String> extractVariables(String bayesianNetwork) {
		Set<String> variables = new TreeSet<>();
		StringReader reader = new StringReader(bayesianNetwork);
		TokenCreator instance = new TokenCreator();
		List<Token> tokens = instance.createTokens(reader);
		int[] colons = new int[1];
		colons[0] = 0;
		tokens.forEach(token -> {
			if (colons[0] == 2) {
				variables.add(token.getValue());
				colons[0] = 0;
			} else if (token.getValue().equals("" + Symbol.COLON_CHAR)) {
				colons[0] = colons[0] + 1;
			} else {
				colons[0] = 0;
			}
		});
		return variables;
	}

	public List<String> getVariables() {
		return Collections.unmodifiableList(this.variableOrder);
	}

	public Set<String> getBayesianNetworkVariables() {
		return Collections.unmodifiableSet(this.bayesianNetworkVariableSet);
	}

	public Optional<String> getValue(String variable) {
		Objects.requireNonNull(variable);
		return this.network.get(variable);
	}

	public OWLOntology getOWLOntology() {
		return this.owlOntology;
	}

	public Map<String, String> getNetwork() {
		return this.network.asMap();
	}

	String asString(OWLAnnotationValue value) {
		Objects.requireNonNull(value);
		if (Objects.isNull(value)) {
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

	String getNextVariable() {
		this.counter++;
		if (this.counter >= this.bayesianNetworkVariableList.size()) {
			this.counter = 0;
		}
		return this.bayesianNetworkVariableList.get(counter);
	}

	OWLAnnotation createNewAnnotation() {
		String varName = getNextVariable();
		OWLAnnotationValue value = this.df.getOWLLiteral(varName);
		OWLAnnotation newAnnotation = this.df.getOWLAnnotation(annotationProperty, value);
		return newAnnotation;
	}

	Set<OWLAnnotation> annot() {
		Set<OWLAnnotation> ret = new TreeSet<>();
		if (Math.random() < this.threshold) {
			ret.add(createNewAnnotation());
		}
		return ret;
	}

	Set<OWLAnnotation> empty() {
		return Collections.emptySet();
	}

	boolean add(OWLAxiom axiom) {
		Objects.requireNonNull(axiom);
		this.owlOntology.getOWLOntologyManager().addAxiom(this.owlOntology, axiom);
		return true;
	}

	@Override
	public Boolean visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(
				this.df.getOWLSubAnnotationPropertyOfAxiom(axiom.getSubProperty(), axiom.getSuperProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyDomainAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLAnnotationPropertyDomainAxiom(axiom.getProperty(), axiom.getDomain(), empty()));
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyRangeAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLAnnotationPropertyRangeAxiom(axiom.getProperty(), axiom.getRange(), empty()));
	}

	@Override
	public Boolean visit(OWLSubClassOfAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLSubClassOfAxiom(axiom.getSubClass(), axiom.getSuperClass(), annot()));
	}

	@Override
	public Boolean visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLNegativeObjectPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(),
				axiom.getObject(), empty()));
	}

	@Override
	public Boolean visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLAsymmetricObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLReflexiveObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLReflexiveObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLDisjointClassesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDisjointClassesAxiom(axiom.getClassExpressions(), annot()));
	}

	@Override
	public Boolean visit(OWLDataPropertyDomainAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDataPropertyDomainAxiom(axiom.getProperty(), axiom.getDomain(), empty()));
	}

	@Override
	public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLObjectPropertyDomainAxiom(axiom.getProperty(), axiom.getDomain(), empty()));
	}

	@Override
	public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLEquivalentObjectPropertiesAxiom(axiom.getProperties(), empty()));
	}

	@Override
	public Boolean visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLNegativeDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(),
				axiom.getObject(), empty()));
	}

	@Override
	public Boolean visit(OWLDifferentIndividualsAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDifferentIndividualsAxiom(axiom.getIndividuals(), empty()));
	}

	@Override
	public Boolean visit(OWLDisjointDataPropertiesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDisjointDataPropertiesAxiom(axiom.getProperties(), empty()));
	}

	@Override
	public Boolean visit(OWLDisjointObjectPropertiesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDisjointObjectPropertiesAxiom(axiom.getProperties(), empty()));
	}

	@Override
	public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLObjectPropertyRangeAxiom(axiom.getProperty(), axiom.getRange(), empty()));
	}

	@Override
	public Boolean visit(OWLObjectPropertyAssertionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLObjectPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(),
				axiom.getObject(), empty()));
	}

	@Override
	public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLFunctionalObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLSubObjectPropertyOfAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLSubObjectPropertyOfAxiom(axiom.getSubProperty(), axiom.getSuperProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLDisjointUnionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDisjointUnionAxiom(axiom.getOWLClass(), axiom.getClassExpressions(), annot()));
	}

	@Override
	public Boolean visit(OWLDeclarationAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDeclarationAxiom(axiom.getEntity(), empty()));
	}

	@Override
	public Boolean visit(OWLAnnotationAssertionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLAnnotationAssertionAxiom(axiom.getProperty(), axiom.getSubject(), axiom.getValue(),
				empty()));
	}

	@Override
	public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLSymmetricObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLDataPropertyRangeAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDataPropertyRangeAxiom(axiom.getProperty(), axiom.getRange(), empty()));
	}

	@Override
	public Boolean visit(OWLFunctionalDataPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLFunctionalDataPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLEquivalentDataPropertiesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLEquivalentDataPropertiesAxiom(axiom.getProperties(), empty()));
	}

	@Override
	public Boolean visit(OWLClassAssertionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLClassAssertionAxiom(axiom.getClassExpression(), axiom.getIndividual(), annot()));
	}

	@Override
	public Boolean visit(OWLEquivalentClassesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLEquivalentClassesAxiom(axiom.getClassExpressions(), annot()));
	}

	@Override
	public Boolean visit(OWLDataPropertyAssertionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(), axiom.getObject(),
				empty()));
	}

	@Override
	public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLTransitiveObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLIrreflexiveObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLSubDataPropertyOfAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLSubDataPropertyOfAxiom(axiom.getSubProperty(), axiom.getSuperProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLInverseFunctionalObjectPropertyAxiom(axiom.getProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLSameIndividualAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLSameIndividualAxiom(axiom.getIndividuals(), empty()));
	}

	@Override
	public Boolean visit(OWLSubPropertyChainOfAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLSubPropertyChainOfAxiom(axiom.getPropertyChain(), axiom.getSuperProperty(), empty()));
	}

	@Override
	public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLInverseObjectPropertiesAxiom(axiom.getFirstProperty(), axiom.getSecondProperty(),
				empty()));
	}

	@Override
	public Boolean visit(OWLHasKeyAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(
				this.df.getOWLHasKeyAxiom(axiom.getClassExpression(), axiom.getObjectPropertyExpressions(), empty()));
	}

	@Override
	public Boolean visit(OWLDatatypeDefinitionAxiom axiom) {
		Objects.requireNonNull(axiom);
		return add(this.df.getOWLDatatypeDefinitionAxiom(axiom.getDatatype(), axiom.getDataRange(), empty()));
	}

	@Override
	public Boolean visit(SWRLRule rule) {
		Objects.requireNonNull(rule);
		return add(this.df.getSWRLRule(rule.getBody(), rule.getHead(), empty()));
	}

}
