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
		Assert.assertEquals(5, list.size());

		{
			ExampleConfiguration conf = list.get(0);
			Assert.assertEquals("example-0", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-0.owl"));
			Assert.assertEquals(
					"query(sub('http://lat.inf.tu-dresden.de/systems/born/born-example#a', 'http://lat.inf.tu-dresden.de/systems/born/born-example#e')).\n\n",
					conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(1);
			Assert.assertEquals("example-1", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-1.owl"));
			Assert.assertEquals(
					"query(sub('http://lat.inf.tu-dresden.de/systems/born/born-example#a', 'http://lat.inf.tu-dresden.de/systems/born/born-example#e')).\n\n",
					conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(2);
			Assert.assertEquals("example-2", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-2.owl"));
			Assert.assertEquals(
					"query(inst('http://lat.inf.tu-dresden.de/systems/born/born-example#i0', 'http://lat.inf.tu-dresden.de/systems/born/born-example#e')).\n\n",
					conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(3);
			Assert.assertEquals("example-3", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-3.owl"));
			Assert.assertEquals(
					"query(inst('http://lat.inf.tu-dresden.de/systems/born/born-example#r', 'http://lat.inf.tu-dresden.de/systems/born/born-example#i0', 'http://lat.inf.tu-dresden.de/systems/born/born-example#i1')).\n\n",
					conf.getQuery());
		}

		{
			ExampleConfiguration conf = list.get(4);
			Assert.assertEquals("example-4", conf.getOntologyName());
			Assert.assertTrue(conf.getOntologyFileName().endsWith("/examples/example-4.owl"));
			Assert.assertEquals(
					"query(inst('http://lat.inf.tu-dresden.de/systems/born/born-example#r', 'http://lat.inf.tu-dresden.de/systems/born/born-example#i0', 'http://lat.inf.tu-dresden.de/systems/born/born-example#i1')).\n\n",
					conf.getQuery());
		}

	}

}
