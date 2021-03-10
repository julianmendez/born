package de.tudresden.inf.lat.born.owlapi.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is a unit test for {@link ExampleLoader}.
 * 
 * @author Julian Mendez
 *
 */
public class ExampleLoaderTest {

	private final List<ExampleConfiguration> list = new ArrayList<>();

	/**
	 * Tests the example loader and loads all the example configurations.
	 */
	@BeforeEach
	public void testGetExampleConfigurations() {
		ExampleLoader loader = new ExampleLoader();
		this.list.clear();
		this.list.addAll(loader.getExampleConfigurations());
		Assertions.assertEquals(6, this.list.size());
	}

	/**
	 * Tests example 0.
	 */
	@Test
	public void testExample0() {
		ExampleConfiguration conf = this.list.get(0);
		Assertions.assertEquals("example-0", conf.getOntologyName());
		Assertions.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-0.owl"));
		Assertions.assertEquals("query(sub('ont:a', 'ont:e')).\n\n", conf.getQuery());
	}

	/**
	 * Tests example 1.
	 */
	@Test
	public void testExample1() {
		ExampleConfiguration conf = this.list.get(1);
		Assertions.assertEquals("example-1", conf.getOntologyName());
		Assertions.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-1.owl"));
		Assertions.assertEquals("query(sub('ont:a', 'ont:e')).\n\n", conf.getQuery());
	}

	/**
	 * Tests example 2.
	 */
	@Test
	public void testExample2() {
		ExampleConfiguration conf = this.list.get(2);
		Assertions.assertEquals("example-2", conf.getOntologyName());
		Assertions.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-2.owl"));
		Assertions.assertEquals("query(inst('ont:i0', 'ont:e')).\n\n", conf.getQuery());
	}

	/**
	 * Tests example 3.
	 */
	@Test
	public void testExample3() {
		ExampleConfiguration conf = this.list.get(3);
		Assertions.assertEquals("example-3", conf.getOntologyName());
		Assertions.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-3.owl"));
		Assertions.assertEquals("query(inst('ont:r', 'ont:i0', 'ont:i1')).\n\n", conf.getQuery());
	}

	/**
	 * Tests example 4.
	 */
	@Test
	public void testExample4() {
		ExampleConfiguration conf = this.list.get(4);
		Assertions.assertEquals("example-4", conf.getOntologyName());
		Assertions.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-4.owl"));
		Assertions.assertEquals("query(inst('ont:r', 'ont:i0', 'ont:i1')).\n\n", conf.getQuery());
	}

	/**
	 * Tests example 5.
	 */
	@Test
	public void testExample5() {
		ExampleConfiguration conf = this.list.get(5);
		Assertions.assertEquals("example-5", conf.getOntologyName());
		Assertions.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-5.owl"));
		Assertions.assertEquals("query(sub('ont:a', 'ont:d')).\n\n", conf.getQuery());
	}

}
