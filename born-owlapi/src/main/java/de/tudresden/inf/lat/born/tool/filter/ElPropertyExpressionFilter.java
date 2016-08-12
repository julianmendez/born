package de.tudresden.inf.lat.born.tool.filter;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;

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
