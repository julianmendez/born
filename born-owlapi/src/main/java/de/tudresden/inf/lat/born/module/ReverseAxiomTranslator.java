
package de.tudresden.inf.lat.born.module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

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
import de.tudresden.inf.lat.jcel.owlapi.translator.Translator;

/**
 * An object of this class translates normalized integer axioms into OWL axioms.
 * 
 * @author Julian Mendez
 *
 */
public class ReverseAxiomTranslator implements NormalizedIntegerAxiomVisitor<OWLAxiom> {

	private final Translator translator;
	private final OWLOntology ontology;

	/**
	 * Creates a new reverse axiom translator
	 * 
	 * @param translator
	 *            translator
	 * @param ontology
	 *            ontology
	 */
	public ReverseAxiomTranslator(@Nonnull Translator translator, @Nonnull OWLOntology ontology) {
		Objects.requireNonNull(translator);
		Objects.requireNonNull(ontology);
		this.translator = translator;
		this.ontology = ontology;
	}

	@Override
	public OWLAxiom visit(@Nonnull FunctObjectPropAxiom axiom) {
		Objects.requireNonNull(axiom);
		OWLObjectProperty owlProperty = translator.getTranslationRepository().getOWLObjectProperty(axiom.getProperty());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		return dataFactory.getOWLFunctionalObjectPropertyAxiom(owlProperty, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull GCI0Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLClass owlSubClass = translator.getTranslationRepository().getOWLClass(axiom.getSubClass());
		OWLClass owlSuperClass = translator.getTranslationRepository().getOWLClass(axiom.getSuperClass());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		return dataFactory.getOWLSubClassOfAxiom(owlSubClass, owlSuperClass, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull GCI1Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLClass owlLeftSubClass = translator.getTranslationRepository().getOWLClass(axiom.getLeftSubClass());
		OWLClass owlRightSubClass = translator.getTranslationRepository().getOWLClass(axiom.getRightSubClass());
		OWLClass owlSuperClass = translator.getTranslationRepository().getOWLClass(axiom.getSuperClass());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		Set<OWLClass> set = new HashSet<>();
		set.add(owlLeftSubClass);
		set.add(owlRightSubClass);
		OWLClassExpression owlObjectIntersectionOf = dataFactory.getOWLObjectIntersectionOf(set);
		return dataFactory.getOWLSubClassOfAxiom(owlObjectIntersectionOf, owlSuperClass, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull GCI2Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLClass owlSubClass = translator.getTranslationRepository().getOWLClass(axiom.getSubClass());
		OWLClass owlClassInSuperClass = translator.getTranslationRepository().getOWLClass(axiom.getClassInSuperClass());
		OWLObjectProperty owlObjectProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getPropertyInSuperClass());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		OWLClassExpression owlObjectSomeValuesFrom = dataFactory.getOWLObjectSomeValuesFrom(owlObjectProperty,
				owlClassInSuperClass);
		return dataFactory.getOWLSubClassOfAxiom(owlSubClass, owlObjectSomeValuesFrom, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull GCI3Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLClass owlSuperClass = translator.getTranslationRepository().getOWLClass(axiom.getSuperClass());
		OWLClass owlClassInSubClass = translator.getTranslationRepository().getOWLClass(axiom.getClassInSubClass());
		OWLObjectProperty owlObjectProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getPropertyInSubClass());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		OWLClassExpression owlObjectSomeValuesFrom = dataFactory.getOWLObjectSomeValuesFrom(owlObjectProperty,
				owlClassInSubClass);
		return dataFactory.getOWLSubClassOfAxiom(owlObjectSomeValuesFrom, owlSuperClass, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull NominalAxiom axiom) {
		Objects.requireNonNull(axiom);
		OWLNamedIndividual owlIndividual = translator.getTranslationRepository()
				.getOWLNamedIndividual(axiom.getIndividual());
		OWLClass owlClass = translator.getTranslationRepository().getOWLClass(axiom.getClassExpression());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		OWLObjectOneOf owlObjectOneOf = dataFactory.getOWLObjectOneOf(owlIndividual);
		Set<OWLClassExpression> owlClassExpressions = new HashSet<>();
		owlClassExpressions.add(owlObjectOneOf);
		owlClassExpressions.add(owlClass);
		return dataFactory.getOWLEquivalentClassesAxiom(owlClassExpressions, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull RangeAxiom axiom) {
		Objects.requireNonNull(axiom);
		OWLObjectProperty owlObjectProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getProperty());
		OWLClass owlClass = translator.getTranslationRepository().getOWLClass(axiom.getRange());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		return dataFactory.getOWLObjectPropertyRangeAxiom(owlObjectProperty, owlClass, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull RI1Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLObjectProperty owlSuperProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getSuperProperty());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		List<OWLObjectProperty> owlPropertyList = new ArrayList<>();
		return dataFactory.getOWLSubPropertyChainOfAxiom(owlPropertyList, owlSuperProperty, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull RI2Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLObjectProperty owlSubProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getSubProperty());
		OWLObjectProperty owlSuperProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getSuperProperty());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		return dataFactory.getOWLSubObjectPropertyOfAxiom(owlSubProperty, owlSuperProperty, owlAnnotations);
	}

	@Override
	public OWLAxiom visit(@Nonnull RI3Axiom axiom) {
		Objects.requireNonNull(axiom);
		OWLObjectProperty owlLeftSubProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getLeftSubProperty());
		OWLObjectProperty owlRightSubProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getRightSubProperty());
		OWLObjectProperty owlSuperProperty = translator.getTranslationRepository()
				.getOWLObjectProperty(axiom.getSuperProperty());
		Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
		OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
		List<OWLObjectProperty> owlPropertyList = new ArrayList<>();
		owlPropertyList.add(owlLeftSubProperty);
		owlPropertyList.add(owlRightSubProperty);
		return dataFactory.getOWLSubPropertyChainOfAxiom(owlPropertyList, owlSuperProperty, owlAnnotations);
	}

	OWLAnnotation translateAnnotation(@Nonnull Annotation annotation) {
		Objects.requireNonNull(annotation);
		OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
		OWLAnnotationProperty owlAnnotationProperty = factory
				.getOWLAnnotationProperty(IRI.create(annotation.getAnnotationProperty()));
		OWLAnnotationValue owlAnnotationValue = factory.getOWLLiteral(annotation.getAnnotationProperty().toString());
		OWLAnnotation owlAnnotation = factory.getOWLAnnotation(owlAnnotationProperty, owlAnnotationValue);
		return owlAnnotation;
	}

	Set<OWLAnnotation> translateAnnotations(@Nonnull Set<Annotation> annotations) {
		Objects.requireNonNull(annotations);
		Set<OWLAnnotation> owlAnnotations = new HashSet<>();
		annotations.forEach(annotation -> owlAnnotations.add(translateAnnotation(annotation)));
		return owlAnnotations;
	}

}
