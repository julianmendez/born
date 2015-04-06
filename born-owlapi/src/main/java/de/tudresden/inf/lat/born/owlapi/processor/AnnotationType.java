package de.tudresden.inf.lat.born.owlapi.processor;

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

}
