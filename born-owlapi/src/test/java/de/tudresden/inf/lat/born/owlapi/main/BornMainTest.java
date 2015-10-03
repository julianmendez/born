package de.tudresden.inf.lat.born.owlapi.main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.owlapi.processor.ProblogInputCreator;

/**
 * 
 * @author Julian Mendez
 *
 */
public class BornMainTest {

	String readFile(String fileName) throws IOException {
		StringBuffer ret = new StringBuffer();
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		for (String line = input.readLine(); line != null; line = input.readLine()) {
			ret.append(line);
			ret.append("\n");
		}
		input.close();
		return ret.toString();
	}

	@Test
	public void testRun() throws OWLRendererException, OWLOntologyCreationException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ProblogInputCreator instance = new ProblogInputCreator();

		// String network = readFile("src/test/resources/network.pl");
		String query = readFile("src/test/resources/born-example.pl");

		instance.createProblogFile(new FileInputStream("src/test/resources/born-example.owl"),
				new FileInputStream("src/test/resources/network.pl"), query, output);
		// TODO finish the unit test
	}

}
