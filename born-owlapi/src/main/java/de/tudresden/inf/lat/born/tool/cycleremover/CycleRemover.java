package de.tudresden.inf.lat.born.tool.cycleremover;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;

/**
 * This is an executable class to remove cycles from an ontology.
 * 
 * @author Julian Mendez
 *
 */
public class CycleRemover {

	public static final String HELP = "Parameters: \n" + //
			"  <ontology file> <output file>: removes the cycles in a given ontology \n";

	public CycleRemover() {
	}

	OWLOntology removeCycles(OWLOntology ontology) {
		Objects.requireNonNull(ontology);
		// TODO
		return ontology;
	}

	void removeCycle(String ontologyFileName, String outputFileName) throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);
		Objects.requireNonNull(outputFileName);
		OWLOntology owlOntology = ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));
		storeOntology(removeCycles(owlOntology), outputFileName);
	}

	void storeOntology(OWLOntology owlOntology, String fileName) throws OWLException, IOException {
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(fileName);
		OWLRenderer renderer = new OWLFunctionalSyntaxRenderer();
		FileOutputStream output = new FileOutputStream(fileName);
		renderer.render(owlOntology, output);
		output.flush();
		output.close();
	}

	public static void main(String[] args) throws IOException, OWLException {
		Objects.requireNonNull(args);
		if (args.length == 2) {
			String ontologyFileName = args[0];
			String outputFileName = args[1];
			CycleRemover instance = new CycleRemover();
			instance.removeCycle(ontologyFileName, outputFileName);

		} else {
			System.out.println(HELP);

		}

	}

}
