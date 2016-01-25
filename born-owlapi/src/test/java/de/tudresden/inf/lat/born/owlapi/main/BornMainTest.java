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
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorExecutionResult;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorExecutionResultImpl;

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

		String completionRules = "";
		OWLOntology ontology = ProcessorConfigurationImpl
				.readOntology(new FileInputStream("src/test/resources/born-example.owl"));
		String bayesianNetwork = ProcessorConfigurationImpl.read(new FileReader("src/test/resources/network.pl"));
		String query = ProcessorConfigurationImpl.read(new FileReader("src/test/resources/born-example.pl"));

		ProcessorExecutionResult executionResult = new ProcessorExecutionResultImpl();
		instance.createProblogFile(true, completionRules, ontology, bayesianNetwork, query, output, executionResult);
		// TODO finish the unit test
	}

}
