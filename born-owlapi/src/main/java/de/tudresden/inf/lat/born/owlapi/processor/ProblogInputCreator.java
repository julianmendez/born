package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
import de.tudresden.inf.lat.born.core.rule.FormulaConstructor;
import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.problog.parser.Token;
import de.tudresden.inf.lat.born.problog.parser.TokenCreator;
import de.tudresden.inf.lat.born.problog.parser.TokenType;
import de.tudresden.inf.lat.born.problog.type.ProblogProgram;
import de.tudresden.inf.lat.jcel.core.algorithm.module.ModuleExtractor;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerEntityManager;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerEntityType;
import de.tudresden.inf.lat.jcel.ontology.axiom.complex.ComplexIntegerAxiom;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactory;
import de.tudresden.inf.lat.jcel.ontology.axiom.extension.IntegerOntologyObjectFactoryImpl;
import de.tudresden.inf.lat.jcel.ontology.normalization.OntologyNormalizer;
import de.tudresden.inf.lat.jcel.owlapi.translator.Translator;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProblogInputCreator {

	private static final String NUMBER_OF_OWL_AXIOMS_MSG = "  Number of OWL axioms: ";
	private static final String NUMBER_OF_AXIOMS_MSG = "  Number of axioms: ";
	private static final String NUMBER_OF_NORM_AXIOMS_MSG = "  Number of normalized axioms: ";
	private static final String NUMBER_OF_AXIOMS_IN_MODULE = "  Number of axioms in module: ";

	Set<String> parseRelevantSymbols(Reader reader) throws IOException {
		TokenCreator c = new TokenCreator();
		List<Token> tokens = c.createTokens(reader);
		List<String> list = tokens.stream()
				.filter(t -> (t.getType().equals(TokenType.IDENTIFIER) || t.getType().equals(TokenType.CONSTANT)))
				.map(t -> t.getValue()).collect(Collectors.toList());
		list.remove(FormulaConstructor.QUERY);
		list.remove(FormulaConstructor.SUB);
		Set<String> set = new TreeSet<>();
		if (!list.isEmpty()) {
			set.add(list.iterator().next());
		}
		return set;
	}

	List<CompletionRule> getCompletionRules() {
		List<CompletionRule> completionRules = new ArrayList<>();
		completionRules.add(new BR1Rule());
		completionRules.add(new BR2Rule());
		completionRules.add(new BR3Rule());
		completionRules.add(new CR1Rule());
		completionRules.add(new CR2Rule());
		completionRules.add(new CR3Rule());
		completionRules.add(new CR4Rule());
		return completionRules;
	}

	void write(Writer output, ProblogProgram program) throws IOException {
		BufferedWriter writer = new BufferedWriter(output);
		writer.write(program.asString());
		writer.flush();
		writer.close();
	}

	List<Clause> getDeclarations(IntegerOntologyObjectFactory factory, Set<NormalizedIntegerAxiom> axioms) {
		List<Clause> ret = new ArrayList<>();
		AxiomRenderer renderer = new AxiomRenderer(factory);

		Set<Integer> classes = new TreeSet<>();
		Set<Integer> objectProperties = new TreeSet<>();
		axioms.parallelStream().forEach(axiom -> {
			classes.addAll(axiom.getClassesInSignature());
			objectProperties.addAll(axiom.getObjectPropertiesInSignature());
		});

		classes.forEach(cls -> ret.add(renderer.renderDeclarationOfClass(cls)));
		objectProperties.forEach(objectProperty -> ret.add(renderer.renderDeclarationOfObjectProperty(objectProperty)));

		return ret;
	}

	List<Clause> getClauses(IntegerOntologyObjectFactory factory, Set<NormalizedIntegerAxiom> axioms)
			throws IOException {

		List<Clause> ontology = new ArrayList<>();
		AxiomRenderer renderer = new AxiomRenderer(factory);
		ontology.addAll(getDeclarations(factory, axioms));

		axioms.forEach(axiom -> {
			Clause clause = axiom.accept(renderer);
			ontology.add(clause);
		});
		return ontology;
	}

	String removeApostrophes(String symbolStr0) {
		String symbolStr = symbolStr0;
		if (symbolStr.startsWith("" + Symbol.APOSTROPHE_CHAR) && symbolStr.endsWith("" + Symbol.APOSTROPHE_CHAR)) {
			symbolStr = symbolStr.substring(1);
			symbolStr = symbolStr.substring(0, symbolStr.length() - 1);
			return symbolStr;
		} else {
			return symbolStr0;
		}
	}

	Integer getId(Map<String, Integer> map, String symbolStr0) {
		String symbolStr = removeApostrophes(symbolStr0);
		if (symbolStr.equals(FormulaConstructor.TOP)) {
			return IntegerEntityManager.topClassId;
		} else {
			return map.get(symbolStr);
		}
	}

	Set<Integer> getSetOfClasses(IntegerOntologyObjectFactory factory, Set<String> symbolStrSet) {
		Map<String, Integer> map = new TreeMap<>();
		factory.getEntityManager().getEntities(IntegerEntityType.CLASS, false)
				.forEach(id -> map.put(factory.getEntityManager().getName(id), id));

		Set<Integer> ret = new TreeSet<>();
		symbolStrSet.forEach(symbolStr -> {
			Integer id = getId(map, symbolStr);
			if (id != null) {
				ret.add(id);
			}

		});
		return ret;
	}

	public String createProblogFile(OWLOntology owlOntology, String bayesianNetwork, String query,
			OutputStream resultOutputStream) throws IOException, OWLOntologyCreationException {
		if (owlOntology == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		if (bayesianNetwork == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		if (query == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		if (resultOutputStream == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		StringBuffer sbuf = new StringBuffer();
		sbuf.append(Symbol.NEW_LINE_CHAR);

		sbuf.append(NUMBER_OF_OWL_AXIOMS_MSG + owlOntology.getAxiomCount());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		ProblogProgram program = new ProblogProgram();
		program.setQueryListAddendum(query);

		Set<String> relevantSymbols = parseRelevantSymbols(new StringReader(query));

		IntegerOntologyObjectFactory factory = new IntegerOntologyObjectFactoryImpl();
		Translator translator = new Translator(owlOntology.getOWLOntologyManager().getOWLDataFactory(), factory);
		Set<ComplexIntegerAxiom> axioms = translator.translateSA(owlOntology.getAxioms());
		sbuf.append(NUMBER_OF_AXIOMS_MSG + axioms.size());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		OntologyNormalizer normalizer = new OntologyNormalizer();
		Set<NormalizedIntegerAxiom> normalizedAxioms = normalizer.normalize(axioms, factory);
		sbuf.append(NUMBER_OF_NORM_AXIOMS_MSG + normalizedAxioms.size());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		ModuleExtractor moduleExtractor = new ModuleExtractor();
		Set<Integer> setOfClasses = getSetOfClasses(factory, relevantSymbols);

		Set<Integer> emptySet = Collections.emptySet();
		Set<NormalizedIntegerAxiom> module = moduleExtractor.extractModule(normalizedAxioms, setOfClasses, emptySet);
		sbuf.append(NUMBER_OF_AXIOMS_IN_MODULE + module.size());
		sbuf.append(Symbol.NEW_LINE_CHAR);

		program.setOntology(getClauses(factory, module));

		program.setCompletionRules(getCompletionRules());

		program.setBayesianNetworkAddendum(bayesianNetwork);

		write(new OutputStreamWriter(resultOutputStream), program);

		return sbuf.toString();
	}

}
