package de.tudresden.inf.lat.born.owlapi.multiprocessor;

/**
 * Pseudorandom number generator. This is based on a Lehmer random number
 * generator.
 * 
 * @author Julian Mendez
 * 
 */
public class PseudorandomNumberGenerator {

	public int MULTIPLIER = 0xBC8F;
	public int MASK = 0xFFFF;
	public int BITS_TO_SHIFT = 0x10;

	private int seed;

	/**
	 * Constructs a pseudorandom number generator using a given seed.
	 * 
	 * @param seed
	 *            seed
	 */
	public PseudorandomNumberGenerator(int seed) {
		this.seed = seed;
	}

	/**
	 * Returns a number obtained using a pseudorandom number generator.
	 * 
	 * @return a number obtained using a pseudorandom number generator
	 * 
	 */
	public int nextInt() {
		this.seed = (MULTIPLIER * (this.seed & MASK)) + (this.seed >> BITS_TO_SHIFT);
		return this.seed;
	}

	/**
	 * Returns the seed.
	 * 
	 * @return the seed
	 */
	public int getSeed() {
		return this.seed;
	}

}
