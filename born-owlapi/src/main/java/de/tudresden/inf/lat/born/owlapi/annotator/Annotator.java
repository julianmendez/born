package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.term.SubApp;

//for OWL API 3.5.1
import org.coode.owlapi.owlxml.renderer.OWLXMLRenderer;

//for OWL API 4.0.2
//import org.semanticweb.owlapi.owlxml.renderer.OWLXMLRenderer;


/**
 * An object of this class add annotations with variables to an OWL ontology.
 * These variables are of the form x0, x1, x2, ... .
 * 
 * @author Julian Mendez
 *
 */
public class Annotator implements SubApp {

	public static final String COLON_COLON = "::";
	public static final String POINT = ".";
	public static final String HELP = "Parameters: <input ontology> <output ontology> [<threshold> [<number of variables>] ]";

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

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public boolean isValid(String[] args) {
		return (2 <= args.length) && (args.length <= 4);
	}

	@Override
	public String run(String args[]) {
		if (isValid(args)) {
			Annotator instance = new Annotator();
			double threshold = 1;
			if (args.length >= 3) {
				threshold = Double.parseDouble(args[2]);
			}
			int maxNumberOfVars = Integer.MAX_VALUE;
			if (args.length >= 4) {
				maxNumberOfVars = Integer.parseInt(args[3]);
			}
			try {
				InputStream in = new FileInputStream(args[0]);
				OutputStream outOnt = new FileOutputStream(args[1]);
				instance.annotate(in, outOnt, threshold, maxNumberOfVars);
				in.close();
				outOnt.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (OWLRendererException e) {
				throw new RuntimeException(e);
			} catch (OWLOntologyCreationException e) {
				throw new RuntimeException(e);
			}
			return "Done.";
		} else {
			return getHelp();
		}
	}

}
