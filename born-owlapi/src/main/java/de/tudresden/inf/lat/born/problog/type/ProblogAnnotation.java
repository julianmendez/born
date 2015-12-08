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

	AnnotationType getContainedType(String value) {
		return AnnotationType.getValues().stream().filter(x -> value.endsWith(x.getName())).findFirst().get();
	}

	String getContainedValue(String value) {
		String str = value.trim();
		if (str.startsWith("" + Symbol.QUOTES_CHAR)) {
			int pos = value.indexOf(Symbol.QUOTES_CHAR, 1);
			if (pos != -1) {
				return value.substring(1, pos);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
