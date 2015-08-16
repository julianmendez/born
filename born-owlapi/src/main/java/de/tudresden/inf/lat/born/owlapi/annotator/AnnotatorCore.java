package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

//for OWL API 3.5.1
import org.coode.owlapi.owlxml.renderer.OWLXMLRenderer;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

//for OWL API 4.0.2
//import org.semanticweb.owlapi.owlxml.renderer.OWLXMLRenderer;

/**
 * An object of this class add annotations with variables to an OWL ontology.
 * These variables are of the form x0, x1, x2, ... .
 * 
 * @author Julian Mendez
 *
 */
public class AnnotatorCore {

	public AnnotatorCore() {
	}

	OWLOntology loadOWLOntology(InputStream input)
			throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(input);
	}

	void storeOWLOntology(OWLOntology ontology,
			OutputStream ontologyOutputStream) throws IOException,
			OWLRendererException {
		AbstractOWLRenderer renderer = new OWLXMLRenderer();
		Writer writer = new OutputStreamWriter(ontologyOutputStream);
		renderer.render(ontology, writer);
		writer.flush();
	}

	public void annotate(InputStream ontologyInputStream,
			OutputStream newOntologyOutputStream, double threshold,
			int maxNumberOfVars) throws IOException,
			OWLOntologyCreationException, OWLRendererException {
		OWLOntology ont = loadOWLOntology(ontologyInputStream);
		AnnotationCreator processor = new AnnotationCreator(
				ont.getOWLOntologyManager(), threshold, maxNumberOfVars);
		Set<OWLAxiom> axioms = ont.getAxioms();
		for (OWLAxiom axiom : axioms) {
			axiom.accept(processor);
		}
		storeOWLOntology(processor.getOWLOntology(), newOntologyOutputStream);
	}

	public void run(AnnotatorConfiguration conf) {
		try {
			annotate(conf.getInputOntology(), conf.getOutputOntology(),
					conf.getThreshold(), conf.getMaxNumberOfVars());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OWLRendererException e) {
			throw new RuntimeException(e);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}

	}

}
