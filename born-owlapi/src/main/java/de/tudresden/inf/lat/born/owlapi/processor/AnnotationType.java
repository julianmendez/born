package de.tudresden.inf.lat.born.owlapi.processor;

import java.util.Arrays;
import java.util.List;

public enum AnnotationType {

	Byte, //
	Decimal, //
	Int, //
	Integer, //
	Long, //
	NegativeInteger, //
	NonNegativeInteger, //
	NonPositiveInteger, //
	PositiveInteger, //
	Short, //
	UnsignedLong, //
	UnsignedInt, //
	UnsignedShort, //
	UnsignedByte; //

	public String getName() {
		return name().substring(0, 1).toLowerCase() + name().substring(1);
	}

	public static List<AnnotationType> getValues() {
		return Arrays.asList(values());
	}

}
