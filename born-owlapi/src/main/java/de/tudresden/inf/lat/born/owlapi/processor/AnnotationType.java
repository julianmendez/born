package de.tudresden.inf.lat.born.owlapi.processor;

import java.util.Arrays;
import java.util.List;

/**
 * This enumeration models the type of an annotation.
 * 
 * @author Julian Mendez
 *
 */
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

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name().substring(0, 1).toLowerCase() + name().substring(1);
	}

	/**
	 * Returns the values.
	 * 
	 * @return the values
	 */
	public static List<AnnotationType> getValues() {
		return Arrays.asList(values());
	}

}
