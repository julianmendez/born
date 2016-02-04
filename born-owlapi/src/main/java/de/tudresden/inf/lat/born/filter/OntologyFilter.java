package de.tudresden.inf.lat.born.filter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;

/**
 * 
 * @author Julian Mendez
 *
 */
public class OntologyFilter {

	public static final String HELP = "Parameters: <input ontology> <output ontology>";

	public OWLOntology readOntology(String ontologyFileName) throws OWLOntologyCreationException, IOException {
		Objects.requireNonNull(ontologyFileName);
		return ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));
	}

	public OWLOntology filterOntology(OWLOntology owlOntology, OwlAxiomFilter filter)
			throws OWLOntologyCreationException {
		Objects.requireNonNull(owlOntology);
		Set<OWLAxiom> newAxioms = owlOntology.getAxioms().stream() //
				.filter(axiom -> filter.accept(axiom)) //
				.collect(Collectors.toSet());
		return owlOntology.getOWLOntologyManager().createOntology(newAxioms);
	}

	public void storeOntology(OWLOntology owlOntology, String fileName) throws IOException, OWLException {
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(fileName);
		OWLRenderer renderer = new OWLFunctionalSyntaxRenderer();
		FileOutputStream output = new FileOutputStream(fileName);
		renderer.render(owlOntology, output);
		output.flush();
		output.close();
	}

	public void filter(String ontologyFileName, String filteredOntologyFileName, OwlAxiomFilter filter)
			throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);
		Objects.requireNonNull(filteredOntologyFileName);
		storeOntology(filterOntology(readOntology(ontologyFileName), filter), filteredOntologyFileName);
	}

	public static void main(String[] args) throws IOException, OWLException {
		Objects.requireNonNull(args);
		if (args.length == 2) {
			String ontologyFileName = args[0];
			String outputFileName = args[1];
			OntologyFilter instance = new OntologyFilter();
			instance.filter(ontologyFileName, outputFileName, new ElAxiomFilter());

		} else {
			System.out.println(HELP);

		}
	}

}
