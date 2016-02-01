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
import java.util.stream.Collectors;

import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

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

		Set<NormalizedIntegerAxiom> normalizedAxioms = (new OntologyNormalizer()).normalize(axioms, factory);

		ProblogInputCreator creator = new ProblogInputCreator();
		Set<Integer> setOfClasses = creator.getSetOfClasses(factory, relevantSymbols);

		Set<Integer> classesInSignature = new HashSet<>();
		(new DefaultModuleExtractor()).extractModule(normalizedAxioms, setOfClasses)
				.forEach(axiom -> classesInSignature.addAll(axiom.getClassesInSignature()));

		Set<OWLClass> moduleOwlClasses = classesInSignature.stream()
				.filter(cls -> !translator.getOntologyObjectFactory().getEntityManager().isAuxiliary(cls))
				.map(intClass -> translator.getTranslationRepository().getOWLClass(intClass))
				.collect(Collectors.toSet());

		Set<OWLAxiom> newAxioms = owlOntology.getAxioms().stream().filter(axiom -> //

		((axiom instanceof OWLSubClassOfAxiom) && ((OWLSubClassOfAxiom) axiom).getSubClass().getClassesInSignature()
				.stream().anyMatch(owlClass -> moduleOwlClasses.contains(owlClass))) //

				|| ((axiom instanceof OWLEquivalentClassesAxiom) && ((OWLEquivalentClassesAxiom) axiom)
						.getClassesInSignature().stream().anyMatch(owlClass -> moduleOwlClasses.contains(owlClass))) //

		).collect(Collectors.toSet());

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
