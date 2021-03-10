package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * 
 * This is a unit test for {@link SplitterCore}.
 * 
 * @author Julian Mendez
 *
 */
public class SplitterCoreTest {

	/**
	 * Tests that reading some example does not throw any exception.
	 * 
	 * @throws OWLRendererException
	 *             if something went wrong when serializing the OWL ontology
	 * @throws OWLOntologyCreationException
	 *             if something went wrong when creating the ontology
	 * @throws IOException
	 *             if something went wrong with I/O
	 */
	@Test
	public void testRun() throws OWLRendererException, OWLOntologyCreationException, IOException {
		ByteArrayOutputStream output0 = new ByteArrayOutputStream();
		ByteArrayOutputStream output1 = new ByteArrayOutputStream();
		SplitterCore instance = new SplitterCore();
		instance.split(new FileInputStream("src/test/resources/born-example.owl"), output0, output1);
		// TODO finish the unit test
	}

}
