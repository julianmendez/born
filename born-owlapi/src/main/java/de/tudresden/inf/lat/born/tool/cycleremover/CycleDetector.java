package de.tudresden.inf.lat.born.tool.cycleremover;

import java.util.Objects;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
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

import de.tudresden.inf.lat.born.tool.filter.OwlAxiomFilter;
import de.tudresden.inf.lat.jcel.core.graph.IntegerSubsumerGraphImpl;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerEntityManager;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactoryImpl;
import de.tudresden.inf.lat.jcel.owlapi.translator.Translator;

/**
 * An object of this class determines whether the OWL classes in an axiom
 * produce a cyclic dependency. If the axiom does not produce a cycle, its
 * classes are added.
 * 
 * @author Julian Mendez
 *
 */
public class CycleDetector implements OWLAxiomVisitorEx<Boolean>, OwlAxiomFilter {

	private IntegerSubsumerGraphImpl dependencyGraph = new IntegerSubsumerGraphImpl(IntegerEntityManager.bottomClassId,
			IntegerEntityManager.topClassId);
	private final Translator translator;

	public CycleDetector(OWLDataFactory dataFactory) {
		this.translator = new Translator(dataFactory, new IntegerOntologyObjectFactoryImpl());
	}

	@Override
	public Boolean visit(OWLAnnotationAssertionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyDomainAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyRangeAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSubClassOfAxiom axiom) {
		Set<OWLClass> classesInSub = axiom.getSubClass().getClassesInSignature();
		Set<OWLClass> classesInSuper = axiom.getSuperClass().getClassesInSignature();
		boolean acceptable = classesInSub.stream()
				.allMatch(subClass -> classesInSuper.stream().allMatch(superClass -> check(subClass, superClass)));
		if (acceptable) {
			classesInSub.stream()
					.forEach(subClass -> classesInSuper.stream().forEach(superClass -> add(subClass, superClass)));
		}
		return acceptable;
	}

	@Override
	public Boolean visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLReflexiveObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDisjointClassesAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDataPropertyDomainAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDifferentIndividualsAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDisjointDataPropertiesAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDisjointObjectPropertiesAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLObjectPropertyAssertionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSubObjectPropertyOfAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDisjointUnionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDataPropertyRangeAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLFunctionalDataPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLEquivalentDataPropertiesAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLClassAssertionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLEquivalentClassesAxiom axiom) {
		return (axiom.getClassExpressions().size() <= 1);
	}

	@Override
	public Boolean visit(OWLDataPropertyAssertionAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSubDataPropertyOfAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSameIndividualAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLSubPropertyChainOfAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLHasKeyAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(SWRLRule axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDeclarationAxiom axiom) {
		return true;
	}

	@Override
	public Boolean visit(OWLDatatypeDefinitionAxiom axiom) {
		return true;
	}

	@Override
	public boolean accept(OWLAxiom axiom) {
		Objects.requireNonNull(axiom);
		return axiom.accept(this);
	}

	boolean add(OWLClass owlSubClass, OWLClass owlSuperClass) {
		this.translator.getTranslationRepository().addClass(owlSubClass);
		int subClass = translator.getTranslationRepository().getId(owlSubClass);
		this.translator.getTranslationRepository().addClass(owlSuperClass);
		int superClass = translator.getTranslationRepository().getId(owlSuperClass);
		return this.dependencyGraph.addAncestor(subClass, superClass);
	}

	boolean check(OWLClass owlSubClass, OWLClass owlSuperClass) {
		this.translator.getTranslationRepository().addClass(owlSubClass);
		int subClass = translator.getTranslationRepository().getId(owlSubClass);
		this.translator.getTranslationRepository().addClass(owlSuperClass);
		int superClass = translator.getTranslationRepository().getId(owlSuperClass);
		return !this.dependencyGraph.getSubsumers(superClass).contains(subClass);
	}

}
