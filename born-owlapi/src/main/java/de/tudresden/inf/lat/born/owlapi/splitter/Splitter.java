package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLRenderer;

import de.tudresden.inf.lat.born.core.term.SubApp;

/**
 * An object of this class splits a probabilistic OWL ontology in two parts: an
 * OWL ontology with variables, and a Bayesian network.
 * 
 * @author Julian Mendez
 *
 */
public class Splitter implements SubApp {

	public static final String COLON_COLON = "::";
	public static final String POINT = ".";
	public static final String HELP = "Parameters: <input ontology> <output ontology> <Bayesian network>";

	/**
	 * Constructs a new splitter.
	 */
	public Splitter() {
	}

	/**
	 * Loads an ontology from an input stream and returns the ontology.
	 * 
	 * @param input
	 *            input stream
	 * @return the ontology provided by the given input stream
	 * @throws OWLOntologyCreationException
	 */
	OWLOntology loadOWLOntology(InputStream input)
			throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(input);
	}

	/**
	 * Stores an ontology
	 * 
	 * @param ontology
	 *            ontology
	 * @param ontologyOutputStream
	 *            output stream to store the ontology
	 * @throws IOException
	 *             if something went wrong with the I/O
	 * @throws OWLRendererException
	 *             if the ontology could not be rendered
	 */
	void storeOWLOntology(OWLOntology ontology,
			OutputStream ontologyOutputStream) throws IOException,
			OWLRendererException {
		AbstractOWLRenderer renderer = new OWLXMLRenderer();
		Writer writer = new OutputStreamWriter(ontologyOutputStream);
		renderer.render(ontology, writer);
		writer.flush();
	}

	/**
	 * Stores a given Bayesian network.
	 * 
	 * @param keyOrder
	 *            order of keys
	 * @param map
	 *            map
	 * @param outputNetwork
	 *            output network
	 * @throws IOException
	 *             if something went wrong with the I/P
	 */
	void storeBayesianNetwork(List<String> keyOrder, Map<String, String> map,
			OutputStream outputNetwork) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				outputNetwork));
		for (String key : keyOrder) {
			String value = map.get(key);
			writer.append(value);
			writer.append(COLON_COLON);
			writer.append(key);
			writer.append(POINT);
			writer.newLine();
		}
		writer.flush();
	}

	/**
	 * Splits a probabilistic OWL ontology in two parts: an OWL ontology with
	 * variables, and a Bayesian network.
	 * 
	 * @param ontologyInputStream
	 *            input stream of ontology
	 * @param newOntologyOutputStream
	 *            output stream of new ontology
	 * @param networkOutputStream
	 *            output stream of Bayesian network
	 * @throws IOException
	 *             if something went wrong with the I/O
	 * @throws OWLOntologyCreationException
	 *             if the OWL ontology could not be created
	 * @throws OWLRendererException
	 *             if the OWL ontology could not be rendered
	 */
	public void split(InputStream ontologyInputStream,
			OutputStream newOntologyOutputStream,
			OutputStream networkOutputStream) throws IOException,
			OWLOntologyCreationException, OWLRendererException {
		OWLOntology ont = loadOWLOntology(ontologyInputStream);
		AnnotationProcessor processor = new AnnotationProcessor(
				ont.getOWLOntologyManager());
		Set<OWLAxiom> axioms = ont.getAxioms();
		for (OWLAxiom axiom : axioms) {
			axiom.accept(processor);
		}
		storeOWLOntology(processor.getOWLOntology(), newOntologyOutputStream);
		storeBayesianNetwork(processor.getVariables(), processor.getNetwork(),
				networkOutputStream);

	}

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public boolean isValid(String args[]) {
		return (args.length == 3);
	}

	@Override
	public String run(String args[]) {
		if (isValid(args)) {
			Splitter instance = new Splitter();
			try {
				InputStream in = new FileInputStream(args[0]);
				OutputStream outOnt = new FileOutputStream(args[1]);
				OutputStream outNet = new FileOutputStream(args[2]);
				instance.split(in, outOnt, outNet);
				in.close();
				outNet.close();
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
