package de.tudresden.inf.lat.born.owlapi.main;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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

	@Test
	public void testRun() throws OWLRendererException,
			OWLOntologyCreationException, IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ProblogInputCreator instance = new ProblogInputCreator();
		instance.createProblogFile(new FileInputStream(
				"src/test/resources/ontology.owl"), new FileInputStream(
				"src/test/resources/network.pl"), new FileInputStream(
				"src/test/resources/query.pl"), output);
		// TODO finish the unit test
	}

}
