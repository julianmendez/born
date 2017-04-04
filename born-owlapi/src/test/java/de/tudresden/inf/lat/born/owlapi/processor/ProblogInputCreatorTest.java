package de.tudresden.inf.lat.born.owlapi.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProblogInputCreatorTest {

	@Test
	public void testOrderByLongestFirst() {
		ProblogInputCreator instance = new ProblogInputCreator();
		List<String> list = new ArrayList<>();
		list.add("this");
		list.add("is");
		list.add("an");
		list.add("interesting");
		list.add("function");

		List<String> expected = new ArrayList<>();
		expected.add("interesting");
		expected.add("function");
		expected.add("this");
		expected.add("an");
		expected.add("is");
		List<String> actual = instance.orderByLongestFirst(list);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReplaceAll() {
		ProblogInputCreator instance = (new ProblogInputCreator());

		Map<String, String> map = new HashMap<>();
		map.put("born:", "https://lat.inf.tu-dresden.de/systems/born#");
		map.put("ont:", "https://lat.inf.tu-dresden.de/systems/born/born-example#");

		map.put("owl:", "https://www.w3.org/2002/07/owl#");
		map.put("rdf:", "https://www.w3.org/1999/02/22-rdf-syntax-ns#");
		map.put("xml:", "https://www.w3.org/XML/1998/namespace");
		map.put("xsd:", "https://www.w3.org/2001/XMLSchema#");
		map.put("rdfs:", "https://www.w3.org/2000/01/rdf-schema#");

		Map<String, String> revMap = new HashMap<>();
		map.keySet().forEach(key -> revMap.put(map.get(key), key));

		{
			String textWithPrefixes = "query(sub('ont:a', 'ont:e')).";
			String textWithoutPrefixes = "query(sub('https://lat.inf.tu-dresden.de/systems/born/born-example#a', 'https://lat.inf.tu-dresden.de/systems/born/born-example#e')).";

			Assert.assertEquals(textWithoutPrefixes, instance.replaceAll(map, textWithPrefixes));
			Assert.assertEquals(textWithPrefixes, instance.replaceAll(revMap, textWithoutPrefixes));
		}

		{
			String textWithPrefixes = "sub('ont:a','ont:e'):	0.09882";
			String textWithoutPrefixes = "sub('https://lat.inf.tu-dresden.de/systems/born/born-example#a','https://lat.inf.tu-dresden.de/systems/born/born-example#e'):	0.09882";
			Assert.assertEquals(textWithoutPrefixes, instance.replaceAll(map, textWithPrefixes));
			Assert.assertEquals(textWithPrefixes, instance.replaceAll(revMap, textWithoutPrefixes));
		}
	}

}
