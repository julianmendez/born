package de.tudresden.inf.lat.born.tool.filter;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

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
