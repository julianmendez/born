package de.tudresden.inf.lat.born.owlapi.main;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.owlapi.processor.ProblogInputCreator;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class BornMainTest {

	@Test
	public void testRun() throws OWLRendererException, OWLOntologyCreationException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ProblogInputCreator instance = new ProblogInputCreator();

		OWLOntology ontology = ProcessorConfiguration
				.readOntology(new FileInputStream("src/test/resources/born-example.owl"));
		String bayesianNetwork = ProcessorConfiguration.read(new FileReader("src/test/resources/network.pl"));
		String query = ProcessorConfiguration.read(new FileReader("src/test/resources/born-example.pl"));

		instance.createProblogFile(ontology, bayesianNetwork, query, output);
		// TODO finish the unit test
	}

}
