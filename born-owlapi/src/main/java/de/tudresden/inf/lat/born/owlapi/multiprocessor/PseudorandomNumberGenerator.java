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
	 * Returns a number obtained using a pseudorandom number generator that is
	 * greater than or equal to 0, and less than the given number.
	 * 
	 * @param bound
	 *            bound
	 * @return a number obtained using a pseudorandom number generator that is
	 *         greater than or equal to 0, and less than the given number
	 */
	public int nextInt(int bound) {
		int result = 0;
		if (bound < 1) {
			throw new IllegalArgumentException("Bound must be a positive number.");
		} else if (bound == 1) {
			result = 0;
		} else {
			int ret = nextInt();
			if (ret < 0) {
				ret = (-1) * ret;
			}
			result = ret % bound;
		}

		return result;
	}

	/**
	 * Returns the seed.
	 * 
	 * @return the seed
	 */
	public int getSeed() {
		return this.seed;
	}

	/**
	 * Sets the seed.
	 * 
	 * @param seed
	 *            seed
	 */
	public void setSeed(int seed) {
		this.seed = seed;
	}

}
