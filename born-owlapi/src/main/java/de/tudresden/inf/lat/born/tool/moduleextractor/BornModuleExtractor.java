package de.tudresden.inf.lat.born.tool.moduleextractor;

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
import java.util.stream.IntStream;

import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLRenderer;

import de.tudresden.inf.lat.born.core.common.ResourceUtil;
import de.tudresden.inf.lat.born.module.DefaultModuleExtractor;
import de.tudresden.inf.lat.born.module.Module;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.ontology.axiom.complex.ComplexIntegerAxiom;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactory;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactoryImpl;
import de.tudresden.inf.lat.jcel.ontology.normalization.OntologyNormalizer;
import de.tudresden.inf.lat.jcel.owlapi.translator.Translator;

/**
 * An object of this class models a module extractor, i.e. an executable class
 * to extract a module.
 * 
 * @author Julian Mendez
 *
 */
public class BornModuleExtractor {

	public static final String HELP = "Parameters: \n" + //
			"  <ontology file> <signature file> <module file> : extract a module from <ontology file> using signature in <signature file> and store it in <module file> (<signature file> cannot be a valid number)\n"
			+ //
			"  <ontology file> <repetitions> <count file> : choose <repetitions> random classes from <ontology file> and append to <count file> a line for each class that contains the class and its module size\n"
			+ //
			"";

	public static final String SEPARATOR = "\t";

	/**
	 * Constructs a new module extractor.
	 */
	public BornModuleExtractor() {
	}

	/**
	 * Keeps a count of the size of the extracted module from a randomly chosen
	 * OWL class. This process can be repeated several times, but the OWL
	 * classes are not necessarily distinct.
	 * 
	 * @param ontologyFileName
	 *            file name of ontology
	 * @param repetitions
	 *            number of times to run the module extraction
	 * @param countFileName
	 *            file name of the results
	 * @throws IOException
	 *             if something went wrong with I/O
	 * @throws OWLException
	 *             if something went wrong when using the OWL API
	 */
	public void countRandom(String ontologyFileName, int repetitions, String countFileName)
			throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);

		OWLOntology owlOntology = ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));

		List<OWLClass> list = new ArrayList<>();
		list.addAll(owlOntology.getClassesInSignature());

		IntStream.range(0, repetitions).forEach(index -> {
			OWLClass chosenOwlClass = list.get((new Random()).nextInt(list.size()));
			Set<OWLClass> signature = Collections.singleton(chosenOwlClass);
			try {
				appendPair(countFileName, chosenOwlClass, extractModule(owlOntology, signature).getAxiomCount());
			} catch (IOException | OWLOntologyCreationException e) {
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * Extracts a module.
	 * 
	 * @param ontologyFileName
	 *            file name of ontology
	 * @param signatureFileName
	 *            file name of signature
	 * @param moduleFileName
	 *            file name of module
	 * @param renderer
	 *            renderer
	 * @throws IOException
	 *             if something went wrong with I/O
	 * @throws OWLException
	 *             if something went wrong using the OWL API
	 */
	public void extractModule(String ontologyFileName, String signatureFileName, String moduleFileName,
			OWLRenderer renderer) throws IOException, OWLException {
		Objects.requireNonNull(ontologyFileName);
		Objects.requireNonNull(signatureFileName);
		Objects.requireNonNull(moduleFileName);

		OWLOntology owlOntology = ProcessorConfigurationImpl.readOntology(new FileInputStream(ontologyFileName));

		Set<OWLClass> signature = readClasses(signatureFileName).stream() //
				.map(classStr -> owlOntology.getOWLOntologyManager().getOWLDataFactory()
						.getOWLClass(IRI.create(classStr))) //
				.collect(Collectors.toSet());

		storeOntology(extractModule(owlOntology, signature), moduleFileName, renderer);
	}

	/**
	 * Returns the extracted module for the given ontology and the given
	 * signature.
	 * 
	 * @param owlOntology
	 *            OWL ontology
	 * @param signature
	 *            signature
	 * @return the extracted module for the given ontology and the given
	 *         signature
	 * @throws OWLOntologyCreationException
	 *             if something went wrong while creating the ontology
	 */
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
		Module module = (new DefaultModuleExtractor()).extractModule(normalizedAxioms, setOfClasses);
		module.getAxioms().forEach(axiom -> classesInSignature.addAll(axiom.getClassesInSignature()));

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

	Set<String> readClasses(String fileName) throws IOException {
		Objects.requireNonNull(fileName);
		Set<String> ret = new TreeSet<>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		reader.lines().forEach(line -> ret.add(line.trim()));
		reader.close();
		return ret;
	}

	void storeOntology(OWLOntology owlOntology, String fileName, OWLRenderer renderer)
			throws OWLException, IOException {
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(fileName);
		Objects.requireNonNull(renderer);
		FileOutputStream output = new FileOutputStream(ResourceUtil.ensurePath(fileName));
		renderer.render(owlOntology, output);
		output.flush();
		output.close();
	}

	void appendPair(String fileName, OWLClass owlClass, int size) throws IOException {
		Objects.requireNonNull(fileName);
		Objects.requireNonNull(owlClass);
		BufferedWriter output = new BufferedWriter(new FileWriter(ResourceUtil.ensurePath(fileName), true));
		output.write(owlClass.getIRI().toURI().toString());
		output.write(SEPARATOR);
		output.write("" + size);
		output.newLine();
		output.flush();
		output.close();
	}

	/**
	 * This is the entry point to execute the module extractor.
	 * 
	 * @param args
	 *            arguments
	 * @throws IOException
	 *             if something went wrong with I/O
	 * @throws OWLException
	 *             if something went wrong when using the OWL API
	 */
	public static void main(String[] args) throws IOException, OWLException {
		Objects.requireNonNull(args);
		if (args.length == 3) {
			boolean storingModuleMode = false;
			int repetitions = 1;
			try {
				repetitions = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				storingModuleMode = true;
			}
			String ontologyFileName = args[0];
			String outputFileName = args[2];
			BornModuleExtractor instance = new BornModuleExtractor();

			if (storingModuleMode) {
				String signatureFileName = args[1];
				instance.extractModule(ontologyFileName, signatureFileName, outputFileName, new OWLXMLRenderer());
			} else {
				instance.countRandom(ontologyFileName, repetitions, outputFileName);
			}

		} else {
			System.out.println(HELP);

		}
	}

}
