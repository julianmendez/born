package de.tudresden.inf.lat.born.owlapi.example;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is a unit test for {@link ExampleLoader}.
 * 
 * @author Julian Mendez
 *
 */
public class ExampleLoaderTest {

	@Test
	public void testGetExampleConfigurations() {
		ExampleLoader loader = new ExampleLoader();
		List<ExampleConfiguration> list = loader.getExampleConfigurations();
		Assert.assertEquals(6, list.size());

		{
			ExampleConfiguration conf = list.get(0);
			Assert.assertEquals("example-0", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-0.owl"));
			Assert.assertEquals("query(sub('ont:a', 'ont:e')).\n\n", conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(1);
			Assert.assertEquals("example-1", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-1.owl"));
			Assert.assertEquals("query(sub('ont:a', 'ont:e')).\n\n", conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(2);
			Assert.assertEquals("example-2", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-2.owl"));
			Assert.assertEquals("query(inst('ont:i0', 'ont:e')).\n\n", conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(3);
			Assert.assertEquals("example-3", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-3.owl"));
			Assert.assertEquals("query(inst('ont:r', 'ont:i0', 'ont:i1')).\n\n", conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(4);
			Assert.assertEquals("example-4", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-4.owl"));
			Assert.assertEquals("query(inst('ont:r', 'ont:i0', 'ont:i1')).\n\n", conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(5);
			Assert.assertEquals("example-5", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-5.owl"));
			Assert.assertEquals("query(sub('ont:a', 'ont:d')).\n\n", conf.getQuery());
		}

	}

}
