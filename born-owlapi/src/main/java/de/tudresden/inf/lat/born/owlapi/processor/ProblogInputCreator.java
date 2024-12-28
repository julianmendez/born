package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.core.rule.BR1Rule;
import de.tudresden.inf.lat.born.core.rule.BR2Rule;
import de.tudresden.inf.lat.born.core.rule.BR3Rule;
import de.tudresden.inf.lat.born.core.rule.CR1Rule;
import de.tudresden.inf.lat.born.core.rule.CR2Rule;
import de.tudresden.inf.lat.born.core.rule.CR3Rule;
import de.tudresden.inf.lat.born.core.rule.CR4Rule;
import de.tudresden.inf.lat.born.core.rule.CompletionRule;
import de.tudresden.inf.lat.born.core.rule.EmptyRule;
import de.tudresden.inf.lat.born.core.rule.FR1Rule;
import de.tudresden.inf.lat.born.core.rule.FR2Rule;
import de.tudresden.inf.lat.born.core.rule.FR3Rule;
import de.tudresden.inf.lat.born.core.rule.FormulaConstructor;
import de.tudresden.inf.lat.born.core.rule.RR1Rule;
import de.tudresden.inf.lat.born.core.rule.RR2Rule;
import de.tudresden.inf.lat.born.core.rule.TR1Rule;
import de.tudresden.inf.lat.born.core.rule.TR2Rule;
import de.tudresden.inf.lat.born.core.rule.TR3Rule;
import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.module.DefaultModuleExtractor;
import de.tudresden.inf.lat.born.module.Module;
import de.tudresden.inf.lat.born.problog.parser.Token;
import de.tudresden.inf.lat.born.problog.parser.TokenCreator;
import de.tudresden.inf.lat.born.problog.parser.TokenType;
import de.tudresden.inf.lat.born.problog.type.ProblogProgram;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NominalAxiom;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerEntityManager;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerEntityType;
import de.tudresden.inf.lat.jcel.ontology.axiom.complex.ComplexIntegerAxiom;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactory;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactoryImpl;
import de.tudresden.inf.lat.jcel.ontology.normalization.OntologyNormalizer;
import de.tudresden.inf.lat.jcel.owlapi.translator.Translator;
import de.tudresden.inf.lat.util.map.OptMap;
import de.tudresden.inf.lat.util.map.OptMapImpl;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProblogInputCreator {

	private static final Logger logger = Logger.getLogger(ProblogInputCreator.class.getName());

	static final String NUMBER_OF_OWL_AXIOMS_MSG = "  Number of OWL axioms: ";
	static final String NUMBER_OF_AXIOMS_MSG = "  Number of axioms: ";
	static final String NUMBER_OF_NORM_AXIOMS_MSG = "  Number of normalized axioms: ";
	static final String NUMBER_OF_AXIOMS_IN_MODULE = "  Number of axioms in module: ";

	static final String RULES_TO_INTERPRET_QUERIES_MSG = " Rules to interpret the queries";
	static final String RULES_TO_PROCESS_INDIVIDUALS_MSG = " Rules to process individuals";
	static final String BASIC_RULES_FOR_COMPLETION_MSG = " Basic rules for the completion";
	static final String EL_COMPLETION_RULES_MSG = " EL complettion rules";
	static final String RULES_TO_AVOID_EMPTY_PREDICATES_OF_ENTITIES_MSG = " Rules to avoid empty predicates of entities";

	Set<String> parseRelevantSymbols(Reader reader) throws IOException {
		Set<String> result = new TreeSet<>();
		TokenCreator c = new TokenCreator();
		List<Token> tokens = c.createTokens(reader);
		List<String> list = tokens.stream().filter(
				token -> (token.getType().equals(TokenType.IDENTIFIER) || token.getType().equals(TokenType.CONSTANT)))
				.map(token -> token.getValue()).collect(Collectors.toList());

		if (list.get(0).equals(FormulaConstructor.QUERY)) {
			list.remove(FormulaConstructor.QUERY);
			if (list.get(0).equals(FormulaConstructor.SUB)) {
				list.remove(FormulaConstructor.SUB);
				if (!list.isEmpty()) {
					result.add(list.iterator().next());
				}
			} else if (list.get(0).equals(FormulaConstructor.INST)) {
				list.remove(FormulaConstructor.INST);
				if (list.size() > 2) {
					result.add(list.get(1));
				} else if (list.size() > 1) {
					result.add(list.get(0));
				}
			}
		}

		return result;
	}

	/**
	 * Returns the default list of completion rules.
	 * 
	 * @return the default list of completion rules
	 */
	public List<CompletionRule> getDefaultCompletionRules() {
		List<CompletionRule> result = new ArrayList<>();
		result.add(new EmptyRule());
		result.add(new EmptyRule(RULES_TO_INTERPRET_QUERIES_MSG));
		result.add(new FR1Rule());
		result.add(new FR2Rule());
		result.add(new FR3Rule());
		result.add(new EmptyRule());
		result.add(new EmptyRule(RULES_TO_PROCESS_INDIVIDUALS_MSG));
		result.add(new RR1Rule());
		result.add(new RR2Rule());
		result.add(new EmptyRule());
		result.add(new EmptyRule(BASIC_RULES_FOR_COMPLETION_MSG));
		result.add(new BR1Rule());
		result.add(new BR2Rule());
		result.add(new BR3Rule());
		result.add(new EmptyRule());
		result.add(new EmptyRule(EL_COMPLETION_RULES_MSG));
		result.add(new CR1Rule());
		result.add(new CR2Rule());
		result.add(new CR3Rule());
		result.add(new CR4Rule());
		result.add(new EmptyRule());
		result.add(new EmptyRule(RULES_TO_AVOID_EMPTY_PREDICATES_OF_ENTITIES_MSG));
		result.add(new TR1Rule());
		result.add(new TR2Rule());
		result.add(new TR3Rule());

		return result;
	}

	void write(Writer output, ProblogProgram program) throws IOException {
		Objects.requireNonNull(output);
		Objects.requireNonNull(program);
		BufferedWriter writer = new BufferedWriter(output);
		writer.write(program.asString());
		writer.flush();
		writer.close();
	}

	List<Clause> getDeclarations(IntegerOntologyObjectFactory factory, Module module) {
		List<Clause> result = new ArrayList<>();
		AxiomRenderer renderer = new AxiomRenderer(factory);

		Set<Integer> classes = new TreeSet<>();
		Set<Integer> objectProperties = new TreeSet<>();
		Set<Integer> individuals = new TreeSet<>();
		Set<Integer> entities = module.getEntities();
		entities.forEach(entity -> {
			if (factory.getEntityManager().getType(entity).equals(IntegerEntityType.INDIVIDUAL)) {
				individuals.add(entity);
			} else if (factory.getEntityManager().getType(entity).equals(IntegerEntityType.CLASS)) {
				classes.add(entity);
			} else if (factory.getEntityManager().getType(entity).equals(IntegerEntityType.OBJECT_PROPERTY)) {
				objectProperties.add(entity);
			} else {
				throw new IllegalStateException("Entity of unknown type: '" + entity + "'.");
			}
		});
		module.getAxioms().forEach(axiom -> {
			// classes.addAll(axiom.getClassesInSignature());
			objectProperties.addAll(axiom.getObjectPropertiesInSignature());
			// individuals.addAll(axiom.getIndividualsInSignature());
		});

		classes.forEach(cls -> result.add(renderer.renderDeclarationOfClass(cls)));
		objectProperties.forEach(objectProperty -> result.add(renderer.renderDeclarationOfObjectProperty(objectProperty)));
		individuals.forEach(individual -> result.add(renderer.renderDeclarationOfIndividual(individual)));

		return result;
	}

	List<Clause> getClauses(IntegerOntologyObjectFactory factory, Module module) throws IOException {
		List<Clause> result = new ArrayList<>();
		AxiomRenderer renderer = new AxiomRenderer(factory);
		result.addAll(getDeclarations(factory, module));

		module.getAxioms().forEach(axiom -> {
			Set<Clause> clauses = axiom.accept(renderer);
			result.addAll(clauses);
		});

		return result;
	}

	String removeApostrophes(String symbolStr0) {
		String result = "";
		String symbolStr = symbolStr0;
		if (symbolStr.startsWith("" + Symbol.APOSTROPHE_CHAR) && symbolStr.endsWith("" + Symbol.APOSTROPHE_CHAR)) {
			symbolStr = symbolStr.substring(1);
			symbolStr = symbolStr.substring(0, symbolStr.length() - 1);
			result = symbolStr;
		} else {
			result = symbolStr0;
		}

		return result;
	}

	Optional<Integer> getId(OptMap<String, Integer> map, String symbolStr0) {
		Optional<Integer> result = Optional.empty();
		String symbolStr = removeApostrophes(symbolStr0);
		if (symbolStr.equals(FormulaConstructor.TOP)) {
			result = Optional.of(IntegerEntityManager.topClassId);
		} else {
			result = map.get(symbolStr);
		}

		return result;
	}

	Set<Integer> getSetOfEntities(IntegerOntologyObjectFactory factory, Set<String> symbolStrSet) {
		Set<Integer> result = new TreeSet<>();
		OptMap<String, Integer> map = new OptMapImpl<>(new TreeMap<>());
		factory.getEntityManager().getEntities(IntegerEntityType.CLASS, false)
				.forEach(id -> map.put(factory.getEntityManager().getName(id), id));
		factory.getEntityManager().getEntities(IntegerEntityType.INDIVIDUAL, false)
				.forEach(id -> map.put(factory.getEntityManager().getName(id), id));

		symbolStrSet.forEach(symbolStr -> {
			Optional<Integer> optId = getId(map, symbolStr);
			if (optId.isPresent()) {
				result.add(optId.get());
			}

		});
		return result;
	}

	Set<Integer> getSetOfClasses(IntegerOntologyObjectFactory factory, Set<Integer> setOfEntities) {
		Set<Integer> result = new TreeSet<>();
		setOfEntities.forEach(entity -> {
			if (factory.getEntityManager().getType(entity).equals(IntegerEntityType.CLASS)) {
				result.add(entity);
			} else if (factory.getEntityManager().getType(entity).equals(IntegerEntityType.INDIVIDUAL)) {
				result.add(entity);
				Optional<Integer> classForIndivOpt = factory.getEntityManager().getAuxiliaryNominal(entity);
				if (classForIndivOpt.isPresent()) {
					result.add(classForIndivOpt.get());
				}
			}
		});

		return result;
	}

	Set<NormalizedIntegerAxiom> removeUnnecessaryAnnotations(Set<NormalizedIntegerAxiom> axioms,
			IntegerOntologyObjectFactory factory) {
		Set<NormalizedIntegerAxiom> result = new HashSet<>();
		axioms.forEach(axiom -> {
			if (axiom instanceof NominalAxiom) {
				NominalAxiom nominalAxiom = (NominalAxiom) axiom;
				result.add(factory.getNormalizedAxiomFactory().createNominalAxiom(nominalAxiom.getClassExpression(),
						nominalAxiom.getIndividual(), new HashSet<>()));
			} else {
				result.add(axiom);
			}
		});

		return result;
	}

	List<String> orderByLongestFirst(Collection<String> oldList) {
		List<String> result = new ArrayList<>();
		result.addAll(oldList);
		Collections.sort(result, new Comparator<String>() {
			@Override
			public int compare(String str0, String str1) {
				int str0len = str0 == null ? -1 : str0.length();
				int str1len = str1 == null ? -1 : str1.length();
				int ret = str1len - str0len;
				if (ret == 0 && str0 != null) {
					ret = str0.compareTo(str1);
				}
				return ret;
			}
		});

		return result;
	}

	String replaceAll(OptMap<String, String> map, String text) {
		String[] current = new String[1];
		current[0] = text;
		List<String> keys = orderByLongestFirst(map.keySet());
		keys.forEach(key -> {
			String value = map.get(key).get();
			current[0] = current[0].replaceAll(key, value);
		});
		return current[0];
	}

	/**
	 * Returns the given text with its prefixes expanded. The prefixes are
	 * defined in the given ontology.
	 * 
	 * @param ontology
	 *            ontology
	 * @param text
	 *            text
	 * @return the given text with its prefixes expanded
	 */
	public String expandPrefixes(OWLOntology ontology, String text) {
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(text);
		PrefixDocumentFormat prefixes = ProcessorConfigurationImpl.getPrefixes(ontology);
		OptMap<String, String> prefixNames = new OptMapImpl<>(new HashMap<>());
		prefixes.getPrefixNames().forEach(prefixName -> {
			if (prefixName.length() > 1) {
				prefixNames.put(prefixName, prefixes.getIRI(prefixName).toString());
			}
		});
		return replaceAll(prefixNames, text);
	}

	/**
	 * Returns the given text with its prefixes compressed. The prefixes are
	 * defined in the given ontology.
	 * 
	 * @param ontology
	 *            ontology
	 * @param text
	 *            text
	 * @return the given text with its prefixes compressed
	 */
	public String replaceByPrefixes(OWLOntology ontology, String text) {
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(text);
		PrefixDocumentFormat prefixes = ProcessorConfigurationImpl.getPrefixes(ontology);
		OptMap<String, String> revPrefixNames = new OptMapImpl<>(new HashMap<>());
		prefixes.getPrefixNames().forEach(prefixName -> {
			if (prefixName.length() > 1) {
				revPrefixNames.put(prefixes.getIRI(prefixName).toString(), prefixName);
			}
		});
		return replaceAll(revPrefixNames, text);
	}

	/**
	 * Returns a ProbLog program with the given parameters.
	 * 
	 * @param useOfDefaultCompletionRules
	 *            <code>true</code> if and only if the default completion rules
	 *            are used
	 * @param additionalCompletionRules
	 *            addition completion rules
	 * @param owlOntology
	 *            OWL ontology
	 * @param bayesianNetwork
	 *            Bayesian network
	 * @param query
	 *            query
	 * @param resultOutputStream
	 *            output stream for the result
	 * @param executionResult
	 *            result of the processor execution
	 * @return a ProbLog program with the given parameters
	 * @throws IOException
	 *             if something went wrong with I/O
     */
	public String createProblogFile(boolean useOfDefaultCompletionRules, String additionalCompletionRules,
			OWLOntology owlOntology, String bayesianNetwork, String query, OutputStream resultOutputStream,
			ProcessorExecutionResult executionResult) throws IOException {
		Objects.requireNonNull(additionalCompletionRules);
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(bayesianNetwork);
		Objects.requireNonNull(query);
		Objects.requireNonNull(resultOutputStream);
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(Symbol.NEW_LINE_CHAR);

		sbuf.append(NUMBER_OF_OWL_AXIOMS_MSG + owlOntology.getAxiomCount());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		ProblogProgram program = new ProblogProgram();
		String expandedQuery = expandPrefixes(owlOntology, query);
		program.setQueryListAddendum(expandedQuery);

		Set<String> relevantSymbols = parseRelevantSymbols(new StringReader(expandedQuery));

		IntegerOntologyObjectFactory factory = new IntegerOntologyObjectFactoryImpl();

		long translationStart = System.nanoTime();
		logger.fine("OWL Axioms: " + owlOntology.getAxioms());

		Translator translator = new Translator(owlOntology.getOWLOntologyManager().getOWLDataFactory(), factory);
		Set<ComplexIntegerAxiom> axioms = translator.translateSA(owlOntology.getAxioms());
		logger.fine("Integer Axioms: " + axioms);

		executionResult.setTranslationTime(System.nanoTime() - translationStart);
		executionResult.setOntologySize(axioms.size());
		sbuf.append(NUMBER_OF_AXIOMS_MSG + axioms.size());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		long normalizationStart = System.nanoTime();
		OntologyNormalizer normalizer = new OntologyNormalizer();
		Set<NormalizedIntegerAxiom> normalizedAxioms = removeUnnecessaryAnnotations(
				normalizer.normalize(axioms, factory), factory);
		logger.fine("Normalized Axioms: " + normalizedAxioms);

		executionResult.setNormalizationTime(System.nanoTime() - normalizationStart);
		executionResult.setNormalizedOntologySize(normalizedAxioms.size());
		sbuf.append(NUMBER_OF_NORM_AXIOMS_MSG + normalizedAxioms.size());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		long moduleExtractionStart = System.nanoTime();
		DefaultModuleExtractor moduleExtractor = new DefaultModuleExtractor();
		Set<Integer> setOfEntities = getSetOfEntities(factory, relevantSymbols);
		Set<Integer> setOfClasses = getSetOfClasses(factory, setOfEntities);

		Module module = moduleExtractor.extractModule(normalizedAxioms, setOfClasses);
		logger.fine("Module entities: " + module.getEntities());
		logger.fine("Module axioms: " + module.getAxioms());

		executionResult.setModuleExtractionTime(System.nanoTime() - moduleExtractionStart);
		executionResult.setModuleSize(module.getAxioms().size());
		sbuf.append(NUMBER_OF_AXIOMS_IN_MODULE + module.getAxioms().size());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		List<Clause> clauses = getClauses(factory, module);
		program.getData().setOntology(clauses);
		logger.fine("Ontology: " + program.getData().getOntology());

		if (useOfDefaultCompletionRules) {
			program.getData().setCompletionRules(getDefaultCompletionRules());
		} else {
			program.getData().setCompletionRules(Collections.emptyList());
		}
		logger.fine("Completion Rules: " + program.getData().getCompletionRules());

		program.setAdditionalCompletionRulesAsText(additionalCompletionRules);
		logger.fine("Additional Completion Rules: " + program.getAdditionalCompletionRulesAsText());

		program.setBayesianNetworkAddendum(bayesianNetwork);
		logger.fine("Bayesian Network: " + program.getBayesianNetworkAddendum());

		write(new OutputStreamWriter(resultOutputStream), program);

		return sbuf.toString();
	}

}
