package de.tudresden.inf.lat.born.tool.filter;

import java.util.Objects;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
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
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
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
 * An object of this class is an axiom filter that determines whether an axiom
 * uses only EL constructs.
 * 
 * @author Julian Mendez
 *
 */
public class ElAxiomFilter implements OWLAxiomVisitorEx<Boolean>, OwlAxiomFilter {

	public final boolean withAnnotations;

	class ElPropertyExpressionFilter implements OWLPropertyExpressionVisitorEx<Boolean> {

		public ElPropertyExpressionFilter() {
		}

		@Override
		public Boolean visit(OWLObjectProperty propertyExpression) {
			return true;
		}

		@Override
		public Boolean visit(OWLObjectInverseOf propertyExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataProperty propertyExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLAnnotationProperty propertyExpression) {
			return false;
		}

	}

	class ElClassExpressionFilter implements OWLClassExpressionVisitorEx<Boolean> {

		public ElClassExpressionFilter() {
		}

		@Override
		public Boolean visit(OWLClass classExpression) {
			return !classExpression.isOWLNothing();
		}

		@Override
		public Boolean visit(OWLObjectIntersectionOf classExpression) {
			return classExpression.getOperands().stream().allMatch(operand -> operand.accept(this));
		}

		@Override
		public Boolean visit(OWLObjectUnionOf classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectComplementOf classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectSomeValuesFrom classExpression) {
			return classExpression.getProperty().accept(new ElPropertyExpressionFilter())
					&& classExpression.getFiller().accept(this);
		}

		@Override
		public Boolean visit(OWLObjectAllValuesFrom classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectHasValue classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectMinCardinality classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectExactCardinality classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectMaxCardinality classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectHasSelf classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLObjectOneOf classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataSomeValuesFrom classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataAllValuesFrom classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataHasValue classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataMinCardinality classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataExactCardinality classExpression) {
			return false;
		}

		@Override
		public Boolean visit(OWLDataMaxCardinality classExpression) {
			return false;
		}

	}

	public ElAxiomFilter() {
		this.withAnnotations = true;
	}

	public ElAxiomFilter(boolean withAnnotations) {
		this.withAnnotations = withAnnotations;
	}

	@Override
	public Boolean visit(OWLAnnotationAssertionAxiom axiom) {
		return this.withAnnotations;
	}

	@Override
	public Boolean visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		return this.withAnnotations;
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyDomainAxiom axiom) {
		return this.withAnnotations;
	}

	@Override
	public Boolean visit(OWLAnnotationPropertyRangeAxiom axiom) {
		return this.withAnnotations;
	}

	@Override
	public Boolean visit(OWLSubClassOfAxiom axiom) {
		ElClassExpressionFilter filter = new ElClassExpressionFilter();
		return axiom.getSubClass().accept(filter) && axiom.getSuperClass().accept(filter);
	}

	@Override
	public Boolean visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLReflexiveObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDisjointClassesAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDataPropertyDomainAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDifferentIndividualsAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDisjointDataPropertiesAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDisjointObjectPropertiesAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLObjectPropertyAssertionAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLSubObjectPropertyOfAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDisjointUnionAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDataPropertyRangeAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLFunctionalDataPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLEquivalentDataPropertiesAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLClassAssertionAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLEquivalentClassesAxiom axiom) {
		ElClassExpressionFilter filter = new ElClassExpressionFilter();
		return axiom.getClassExpressions().stream().allMatch(classExpression -> classExpression.accept(filter));
	}

	@Override
	public Boolean visit(OWLDataPropertyAssertionAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLSubDataPropertyOfAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLSameIndividualAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLSubPropertyChainOfAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLHasKeyAxiom axiom) {
		return false;
	}

	@Override
	public Boolean visit(SWRLRule axiom) {
		return false;
	}

	@Override
	public Boolean visit(OWLDeclarationAxiom axiom) {
		return axiom.getEntity().isOWLClass() || axiom.getEntity().isOWLObjectProperty();
	}

	@Override
	public Boolean visit(OWLDatatypeDefinitionAxiom axiom) {
		return false;
	}

	@Override
	public boolean accept(OWLAxiom axiom) {
		Objects.requireNonNull(axiom);
		return axiom.accept(this);
	}

}
