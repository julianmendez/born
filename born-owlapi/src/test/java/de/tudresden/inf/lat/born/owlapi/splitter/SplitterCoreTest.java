package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * 
 * @author Julian Mendez
 *
 */
public class SplitterCoreTest {

	@Test
	public void testRun() throws OWLRendererException, OWLOntologyCreationException, IOException {
		ByteArrayOutputStream output0 = new ByteArrayOutputStream();
		ByteArrayOutputStream output1 = new ByteArrayOutputStream();
		SplitterCore instance = new SplitterCore();
		instance.split(new FileInputStream("src/test/resources/born-example.owl"), output0, output1);
		// TODO finish the unit test
	}

}
