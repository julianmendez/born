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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import de.tudresden.inf.lat.born.owlapi.processor.ProblogInputCreator;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;
import de.tudresden.inf.lat.jcel.coreontology.axiom.Annotation;
import de.tudresden.inf.lat.jcel.coreontology.axiom.FunctObjectPropAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI0Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.GCI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NominalAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiomVisitor;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI1Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI2Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RI3Axiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.RangeAxiom;
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

	class AxiomTranslatorToOwl implements NormalizedIntegerAxiomVisitor<OWLAxiom> {

		private Translator translator;
		private OWLOntology ontology;

		public AxiomTranslatorToOwl(@Nonnull Translator translator, @Nonnull OWLOntology ontology) {
			Objects.requireNonNull(translator);
			Objects.requireNonNull(ontology);
			this.translator = translator;
			this.ontology = ontology;
		}

		@Override
		public OWLAxiom visit(FunctObjectPropAxiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(GCI0Axiom axiom) {
			OWLClass owlSubClass = translator.getTranslationRepository().getOWLClass(axiom.getSubClass());
			OWLClass owlSuperClass = translator.getTranslationRepository().getOWLClass(axiom.getSuperClass());
			Set<OWLAnnotation> owlAnnotations = translateAnnotations(axiom.getAnnotations());
			OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
			return dataFactory.getOWLSubClassOfAxiom(owlSubClass, owlSuperClass, owlAnnotations);
		}

		@Override
		public OWLAxiom visit(GCI1Axiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(GCI2Axiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(GCI3Axiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(NominalAxiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(RangeAxiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(RI1Axiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(RI2Axiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OWLAxiom visit(RI3Axiom axiom) {
			// TODO Auto-generated method stub
			return null;
		}

		OWLAnnotation translateAnnotation(Annotation annotation) {
			OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
			OWLAnnotationProperty owlAnnotationProperty = factory
					.getOWLAnnotationProperty(IRI.create(annotation.getAnnotationProperty()));
			OWLAnnotationValue owlAnnotationValue = factory
					.getOWLLiteral(annotation.getAnnotationProperty().toString());
			OWLAnnotation owlAnnotation = factory.getOWLAnnotation(owlAnnotationProperty, owlAnnotationValue);
			return owlAnnotation;
		}

		Set<OWLAnnotation> translateAnnotations(Set<Annotation> annotations) {
			Set<OWLAnnotation> owlAnnotations = new HashSet<>();
			annotations.forEach(annotation -> owlAnnotations.add(translateAnnotation(annotation)));
			return owlAnnotations;
		}

	}

	public BornModuleExtractor() {
	}

	Set<String> readClasses(String fileName) throws IOException {
		Set<String> ret = new TreeSet<String>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		for (String line = reader.readLine(); Objects.nonNull(line); reader.readLine()) {
			ret.add(reader.readLine().trim());
		}
		reader.close();
		return ret;
	}

	public void run(String ontologyFileName, String signatureFileName, String moduleFileName)
			throws IOException, OWLException {
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

		Set<OWLAxiom> newAxioms = new HashSet<OWLAxiom>();
		AxiomTranslatorToOwl translatorToOwl = new AxiomTranslatorToOwl(translator, owlOntology);
		module.forEach(axiom -> newAxioms.add(axiom.accept(translatorToOwl)));

		OWLOntology newOwlOntology = owlOntology.getOWLOntologyManager().createOntology(newAxioms);

		OWLRenderer renderer = new OWLFunctionalSyntaxRenderer();
		FileOutputStream output = new FileOutputStream(moduleFileName);
		renderer.render(newOwlOntology, output);
		output.flush();
		output.close();

	}

	public void run(String[] args) throws IOException, OWLException {
		if (args.length == 3) {
			String ontologyFileName = args[0];
			String signatureFileName = args[1];
			String moduleFileName = args[2];
			run(ontologyFileName, signatureFileName, moduleFileName);
		} else {
			System.out.println("Parameters: <ontology input file> <signature input file> <module output file>");
		}
	}

	public static void args(String[] args) throws IOException, OWLException {
		(new BornModuleExtractor()).run(args);
	}

}
