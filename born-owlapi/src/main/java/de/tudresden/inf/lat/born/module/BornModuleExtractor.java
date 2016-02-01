package de.tudresden.inf.lat.born.module;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import de.tudresden.inf.lat.born.owlapi.processor.ProblogInputCreator;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.ontology.axiom.complex.ComplexIntegerAxiom;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactory;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactoryImpl;
import de.tudresden.inf.lat.jcel.ontology.normalization.OntologyNormalizer;
import de.tudresden.inf.lat.jcel.owlapi.translator.Translator;

/**
 * This is an executable class to extract a module.
 * 
 * @author Julian Mendez
 *
 */
public class BornModuleExtractor {

	public BornModuleExtractor() {
	}

	Set<String> readClasses(String fileName) throws IOException {
		Objects.requireNonNull(fileName);
		Set<String> ret = new TreeSet<>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		for (String line = reader.readLine(); Objects.nonNull(line); reader.readLine()) {
			ret.add(line.trim());
			line = reader.readLine();
		}
		reader.close();
		return ret;
	}

	public void run(String ontologyFileName, String signatureFileName, String moduleFileName)
			throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);
		Objects.requireNonNull(signatureFileName);
		Objects.requireNonNull(moduleFileName);

		Set<String> relevantSymbols = readClasses(signatureFileName);

		OWLOntology owlOntology = ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));

		IntegerOntologyObjectFactory factory = new IntegerOntologyObjectFactoryImpl();
		Translator translator = new Translator(owlOntology.getOWLOntologyManager().getOWLDataFactory(), factory);
		Set<ComplexIntegerAxiom> axioms = translator.translateSA(owlOntology.getAxioms());

		OntologyNormalizer normalizer = new OntologyNormalizer();
		Set<NormalizedIntegerAxiom> normalizedAxioms = normalizer.normalize(axioms, factory);

		DefaultModuleExtractor moduleExtractor = new DefaultModuleExtractor();

		ProblogInputCreator creator = new ProblogInputCreator();
		Set<Integer> setOfClasses = creator.getSetOfClasses(factory, relevantSymbols);
		Set<NormalizedIntegerAxiom> module = moduleExtractor.extractModule(normalizedAxioms, setOfClasses);

		Set<OWLAxiom> newAxioms = new HashSet<>();
		ReverseAxiomTranslator translatorToOwl = new ReverseAxiomTranslator(translator, owlOntology);
		module.forEach(axiom -> newAxioms.add(axiom.accept(translatorToOwl)));

		OWLOntology newOwlOntology = owlOntology.getOWLOntologyManager().createOntology(newAxioms);

		OWLRenderer renderer = new OWLFunctionalSyntaxRenderer();
		FileOutputStream output = new FileOutputStream(moduleFileName);
		renderer.render(newOwlOntology, output);
		output.flush();
		output.close();
	}

	public void run(String[] args) throws IOException, OWLException {
		Objects.requireNonNull(args);
		if (args.length == 3) {
			String ontologyFileName = args[0];
			String signatureFileName = args[1];
			String moduleFileName = args[2];
			run(ontologyFileName, signatureFileName, moduleFileName);
		} else {
			System.out.println("Parameters: <ontology input file> <signature input file> <module output file>");
		}
	}

	public static void main(String[] args) throws IOException, OWLException {
		(new BornModuleExtractor()).run(args);
	}

}
