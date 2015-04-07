package de.tudresden.inf.lat.born.problog.type;

import java.net.URI;

import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.processor.AnnotationType;
import de.tudresden.inf.lat.jcel.coreontology.axiom.Annotation;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProblogAnnotation implements Annotation {

	private final URI annotationProperty;
	private final String annotationValue;
	private final AnnotationType containedType;
	private final String containedValue;

	public ProblogAnnotation(Annotation annotation) {
		if (annotation == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.annotationProperty = annotation.getAnnotationProperty();
		this.annotationValue = annotation.getAnnotationValue();
		this.containedType = getContainedType(this.annotationValue);
		this.containedValue = getContainedValue(this.annotationValue);

	}

	@Override
	public URI getAnnotationProperty() {
		return this.annotationProperty;
	}

	@Override
	public String getAnnotationValue() {
		return this.annotationValue;
	}

	public AnnotationType getContainedType() {
		return this.containedType;
	}

	public String getContainedValue() {
		return this.containedValue;
	}

	AnnotationType getContainedType(String value0) {
		AnnotationType ret = null;
		for (AnnotationType current : AnnotationType.values()) {
			if (value0.endsWith(current.getName())) {
				ret = current;
			}
		}
		return ret;
	}

	String getContainedValue(String value0) {
		String str = value0.trim();
		if (str.startsWith("" + Symbol.QUOTES_CHAR)) {
			int pos = value0.indexOf(Symbol.QUOTES_CHAR, 1);
			if (pos != -1) {
				return value0.substring(1, pos);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
