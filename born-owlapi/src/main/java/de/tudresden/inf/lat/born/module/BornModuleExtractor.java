package de.tudresden.inf.lat.born.module;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

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

	public static final String HELP = "Parameters: \n" + //
			"  <ontology file> <signature file> <module file> : extract a module from <ontology file> using signature in <signature file> and store it in <module file>\n"
			+ //
			"  <ontology file> <count file> : choose a random class from <ontology file> and append a line that contains the class and its module size to <count file>\n"
			+ //
			"";

	public static final String SEPARATOR = "\t";

	public BornModuleExtractor() {
	}

	public void countRandom(String ontologyFileName, String countFile) throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);

		OWLOntology owlOntology = ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));

		List<OWLClass> list = new ArrayList<>();
		list.addAll(owlOntology.getClassesInSignature());

		OWLClass chosenOwlClass = list.get((new Random()).nextInt(list.size()));
		Set<OWLClass> signature = Collections.singleton(chosenOwlClass);

		appendPair(countFile, chosenOwlClass, extractModule(owlOntology, signature).getAxiomCount());
	}

	public void extractModule(String ontologyFileName, String signatureFileName, String moduleFileName)
			throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);
		Objects.requireNonNull(signatureFileName);
		Objects.requireNonNull(moduleFileName);

		OWLOntology owlOntology = ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));

		Set<OWLClass> signature = readClasses(signatureFileName).stream() //
				.map(classStr -> owlOntology.getOWLOntologyManager().getOWLDataFactory()
						.getOWLClass(IRI.create(classStr))) //
				.collect(Collectors.toSet());

		storeOntology(extractModule(owlOntology, signature), moduleFileName);
	}

	public OWLOntology extractModule(OWLOntology owlOntology, Set<OWLClass> signature)
			throws OWLOntologyCreationException {
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(signature);

		IntegerOntologyObjectFactory factory = new IntegerOntologyObjectFactoryImpl();
		Translator translator = new Translator(owlOntology.getOWLOntologyManager().getOWLDataFactory(), factory);
		Set<ComplexIntegerAxiom> axioms = translator.translateSA(owlOntology.getAxioms());

		Set<NormalizedIntegerAxiom> normalizedAxioms = (new OntologyNormalizer()).normalize(axioms, factory);

		Set<Integer> setOfClasses = signature.stream() //
				.map(owlClass -> translator.translateC(owlClass)) //
				.map(intClass -> intClass.getId()).collect(Collectors.toSet());

		Set<Integer> classesInSignature = new HashSet<>();
		(new DefaultModuleExtractor()).extractModule(normalizedAxioms, setOfClasses)
				.forEach(axiom -> classesInSignature.addAll(axiom.getClassesInSignature()));

		Set<OWLClass> moduleOwlClasses = classesInSignature.stream()
				.filter(cls -> !translator.getOntologyObjectFactory().getEntityManager().isAuxiliary(cls))
				.map(intClass -> translator.getTranslationRepository().getOWLClass(intClass))
				.collect(Collectors.toSet());

		Set<OWLAxiom> newAxioms = owlOntology.getAxioms().stream().filter(axiom -> //
		(((axiom instanceof OWLSubClassOfAxiom) && ((OWLSubClassOfAxiom) axiom).getSubClass().getClassesInSignature()
				.stream().anyMatch(owlClass -> moduleOwlClasses.contains(owlClass))) //
				|| ((axiom instanceof OWLEquivalentClassesAxiom) && ((OWLEquivalentClassesAxiom) axiom)
						.getClassesInSignature().stream().anyMatch(owlClass -> moduleOwlClasses.contains(owlClass)))) //
		).collect(Collectors.toSet());

		return owlOntology.getOWLOntologyManager().createOntology(newAxioms);
	}

	public void run(String[] args) throws IOException, OWLException {
	}

	public static void main(String[] args) throws IOException, OWLException {
		Objects.requireNonNull(args);
		if (args.length == 2) {
			String ontologyFileName = args[0];
			String countFileName = args[1];
			BornModuleExtractor instance = new BornModuleExtractor();
			instance.countRandom(ontologyFileName, countFileName);

		} else if (args.length == 3) {
			String ontologyFileName = args[0];
			String signatureFileName = args[1];
			String moduleFileName = args[2];
			BornModuleExtractor instance = new BornModuleExtractor();
			instance.extractModule(ontologyFileName, signatureFileName, moduleFileName);

		} else {
			System.out.println(HELP);

		}

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

	void storeOntology(OWLOntology owlOntology, String fileName) throws OWLException, IOException {
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(fileName);
		OWLRenderer renderer = new OWLFunctionalSyntaxRenderer();
		FileOutputStream output = new FileOutputStream(fileName);
		renderer.render(owlOntology, output);
		output.flush();
		output.close();
	}

	void appendPair(String fileName, OWLClass owlClass, int size) throws IOException {
		Objects.requireNonNull(fileName);
		Objects.requireNonNull(owlClass);
		BufferedWriter output = new BufferedWriter(new FileWriter(fileName, true));
		output.write(owlClass.getIRI().toURI().toString());
		output.write(SEPARATOR);
		output.write("" + size);
		output.newLine();
		output.flush();
		output.close();
	}

}
