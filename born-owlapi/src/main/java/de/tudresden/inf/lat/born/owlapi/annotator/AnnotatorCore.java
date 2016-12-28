package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLRenderer;

/**
 * An object of this class add annotations with variables to an OWL ontology.
 * 
 * @author Julian Mendez
 *
 */
public class AnnotatorCore {

	/**
	 * Constructs a new annotator core.
	 */
	public AnnotatorCore() {
	}

	OWLOntology loadOWLOntology(InputStream input) throws OWLOntologyCreationException {
		Objects.requireNonNull(input);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(input);
	}

	void storeOWLOntology(OWLOntology ontology, OutputStream ontologyOutputStream)
			throws IOException, OWLRendererException {
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(ontologyOutputStream);
		AbstractOWLRenderer renderer = new OWLXMLRenderer();
		renderer.render(ontology, ontologyOutputStream);
		ontologyOutputStream.flush();
	}

	/**
	 * Annotates an ontology with the given parameters.
	 * 
	 * @param ontologyInputStream
	 *            input stream of input ontology
	 * @param newOntologyOutputStream
	 *            output stream of output ontology
	 * @param threshold
	 *            threshold for the annotations, which is between 0 and 1
	 * @param bayesianNetworkVariables
	 *            variables of the Bayesian network
	 * @throws IOException
	 *             if something went wrong with I/O
	 * @throws OWLOntologyCreationException
	 *             if something went wrong with the ontology creations
	 * @throws OWLRendererException
	 *             if something went wrong while rendering the output ontology
	 */
	public void annotate(InputStream ontologyInputStream, OutputStream newOntologyOutputStream, double threshold,
			Set<String> bayesianNetworkVariables)
			throws IOException, OWLOntologyCreationException, OWLRendererException {
		Objects.requireNonNull(ontologyInputStream);
		Objects.requireNonNull(newOntologyOutputStream);
		Objects.requireNonNull(bayesianNetworkVariables);
		OWLOntology ont = loadOWLOntology(ontologyInputStream);
		AnnotationCreator processor = new AnnotationCreator(ont.getOWLOntologyManager(), threshold,
				bayesianNetworkVariables);
		ont.getAxioms().forEach(axiom -> axiom.accept(processor));
		storeOWLOntology(processor.getOWLOntology(), newOntologyOutputStream);
	}

	/**
	 * Runs the annotator with the given configuration.
	 * 
	 * @param conf
	 *            configuration
	 */
	public void run(AnnotatorConfiguration conf) {
		Objects.requireNonNull(conf);
		try {
			annotate(conf.getInputOntology(), conf.getOutputOntology(), conf.getThreshold(),
					conf.getInputBayesianNetworkVariables());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OWLRendererException e) {
			throw new RuntimeException(e);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}

	}

}
