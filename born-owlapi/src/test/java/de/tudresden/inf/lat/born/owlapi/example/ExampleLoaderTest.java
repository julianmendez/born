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
		Assert.assertEquals(4, list.size());

		{
			ExampleConfiguration conf0 = list.get(0);
			Assert.assertEquals("example-0", conf0.getOntologyName());
			Assert.assertTrue(conf0.getOntologyFileName().endsWith("/examples/example-0.owl"));
			Assert.assertEquals(
					"query(sub('http://lat.inf.tu-dresden.de/systems/born/born-example#a', 'http://lat.inf.tu-dresden.de/systems/born/born-example#e')).\n\n",
					conf0.getQuery());
		}

		{
			ExampleConfiguration conf1 = list.get(1);
			Assert.assertEquals("example-1", conf1.getOntologyName());
			Assert.assertTrue(conf1.getOntologyFileName().endsWith("/examples/example-1.owl"));
			Assert.assertEquals(
					"query(sub('http://lat.inf.tu-dresden.de/systems/born/born-example#a', 'http://lat.inf.tu-dresden.de/systems/born/born-example#e')).\n\n",
					conf1.getQuery());
		}

		{
			ExampleConfiguration conf2 = list.get(2);
			Assert.assertEquals("example-2", conf2.getOntologyName());
			Assert.assertTrue(conf2.getOntologyFileName().endsWith("/examples/example-2.owl"));
			Assert.assertEquals(
					"query(inst('http://lat.inf.tu-dresden.de/systems/born/born-example#i0', 'http://lat.inf.tu-dresden.de/systems/born/born-example#e')).\n\n",
					conf2.getQuery());
		}

		{
			ExampleConfiguration conf3 = list.get(3);
			Assert.assertEquals("example-3", conf3.getOntologyName());
			Assert.assertTrue(conf3.getOntologyFileName().endsWith("/examples/example-3.owl"));
			Assert.assertEquals(
					"query(inst('http://lat.inf.tu-dresden.de/systems/born/born-example#r', 'http://lat.inf.tu-dresden.de/systems/born/born-example#i0', 'http://lat.inf.tu-dresden.de/systems/born/born-example#i1')).\n\n",
					conf3.getQuery());
		}

	}

}
