package de.tudresden.inf.lat.born.problog.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * An object this class computes successive permutations in a boolean vector.
 * 
 * @author Julian Mendez
 *
 */
class Permutation {

	private List<Boolean> currentPermutation = new ArrayList<>();

	/**
	 * Constructs a permutation with a given size, set to the initial
	 * configuration.
	 * 
	 * @param size
	 *            size
	 */
	public Permutation(int size) {
		IntStream.range(0, size).parallel().forEach(x -> this.currentPermutation.add(false));
	}

	boolean nextValue(boolean value) {
		return !value;
	}

	boolean hasCarry(boolean value) {
		return value;
	}

	/**
	 * Compute the next permutation, and returns <code>false</code> if starts
	 * from the initial configuration, and <code>true</code> otherwise.
	 * 
	 * @return <code>false</code> if starts from the initial configuration, and
	 *         <code>true</code> otherwise
	 */
	public boolean computeNextPermutation() {
		boolean carry = true;
		List<Boolean> ret = new ArrayList<>();
		for (int index = 0; (index < this.currentPermutation.size()); index++) {
			boolean current = this.currentPermutation.get(index);
			if (carry) {
				carry = hasCarry(current);
				boolean next = nextValue(current);
				ret.add(next);
			} else {
				ret.add(current);
			}
		}
		this.currentPermutation = ret;
		return !carry;
	}

	/**
	 * Returns the current permutation.
	 * 
	 * @return the current permutation
	 */
	public List<Boolean> getPermutation() {
		return this.currentPermutation;
	}

}
