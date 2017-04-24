package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.core.common.ResourceUtil;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorCore;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorExecutionResult;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorExecutionResultImpl;

/**
 * An object of this class processed several ontologies with their respective
 * Bayesian networks, using pseudorandomly generated queries.
 * 
 * @author Julian Mendez
 *
 */
public class MultiProcessorCore {

	public static final char TAB_CHAR = '\t';
	public static final String LINE_SEPARATOR = Symbol.LINE_SEPARATOR;
	public static final String FILE_SEPARATOR = Symbol.FILE_SEPARATOR;
	public static final String TEMP_FILE_SUFFIX = ".tmp";
	public static final String CSV_EXTENSION = ".csv";
	public static final String LOG_EXTENSION = ".log";
	public static final String OWL_EXTENSION = ".owl";
	public static final String PL_EXTENSION = ".pl";

	public static final String[] FIRST_LINE = { "ontology file name", "Bayesian network file name", "sub class",
			"super class", "query", "result", "translation time", "normalization time", "module extraction time",
			"ProbLog reasoning time", "total time", "ontology size", "normalized ontology size", "module size" };
	public static final List<String> FIRST_LINE_LIST = Arrays.asList(FIRST_LINE);

	/**
	 * Constructs a new multi processor core.
	 */
	public MultiProcessorCore() {
	}

	List<OWLClass> getClasses(OWLOntology ontology) {
		Objects.requireNonNull(ontology);
		List<OWLClass> listOfClasses = new ArrayList<>();
		Set<OWLClass> treeSet = new TreeSet<>();
		treeSet.addAll(ontology.getClassesInSignature());
		listOfClasses.addAll(treeSet);
		return listOfClasses;
	}

	SubsumptionQuery getNextQuery(List<OWLClass> listOfClasses, PseudorandomNumberGenerator random) {
		Objects.requireNonNull(listOfClasses);
		Objects.requireNonNull(random);
		int n = listOfClasses.size();
		int a = random.nextInt(n);
		int b = random.nextInt(n);
		return new SubsumptionQuery(listOfClasses.get(a), listOfClasses.get(b));
	}

	List<SubsumptionQuery> getQueries(OWLOntology ontology, int numberOfQueries, PseudorandomNumberGenerator random) {
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(random);
		List<OWLClass> listOfClasses = getClasses(ontology);
		List<SubsumptionQuery> listOfQueries = new ArrayList<>();
		IntStream.range(0, numberOfQueries).forEach(x -> {
			listOfQueries.add(getNextQuery(listOfClasses, random));
		});
		return listOfQueries;
	}

	/**
	 * Returns a line formed by the concatenations of the given columns.
	 * 
	 * @param columns
	 *            columns
	 * @return a line formed by the concatenations of the given columns
	 */
	public String makeLine(List<String> columns) {
		StringBuilder sb = new StringBuilder();
		columns.forEach(column -> {
			sb.append(column);
			sb.append(TAB_CHAR);
		});
		return sb.toString();
	}

	/**
	 * Returns the conditions of one query as presented in the CSV file.
	 * 
	 * @param ontPair
	 *            ontology-network pair
	 * @param configuration
	 *            configuration
	 * @param query
	 *            query
	 * @return the conditions of one query as presented in the CSV file
	 */
	List<String> getConditions(OntologyAndNetwork ontPair, ProcessorConfiguration configuration,
			SubsumptionQuery query) {
		List<String> ret = new ArrayList<>();
		ret.add(ontPair.getOntologyName() + OWL_EXTENSION);
		ret.add(ontPair.getOntologyName() + PL_EXTENSION);
		ret.add(query.getSubClass().getIRI().toURI().toString());
		ret.add(query.getSuperClass().getIRI().toURI().toString());
		return ret;
	}

	/**
	 * Returns the result of one query as presented in the CSV file.
	 *
	 * @param executionResult
	 *            execution result
	 * @return the result of one query as presented in the CSV file
	 */
	List<String> getResult(ProcessorExecutionResult executionResult) {
		List<String> ret = new ArrayList<>();

		String result = executionResult.getResult().trim();
		if (result.indexOf(TAB_CHAR) == -1) {
			// the result must have two fields
			result += TAB_CHAR;
		}

		ret.add(result);
		ret.add("" + executionResult.getTranslationTime());
		ret.add("" + executionResult.getNormalizationTime());
		ret.add("" + executionResult.getModuleExtractionTime());
		ret.add("" + executionResult.getProblogReasoningTime());
		ret.add("" + executionResult.getTotalTime());
		ret.add("" + executionResult.getOntologySize());
		ret.add("" + executionResult.getNormalizedOntologySize());
		ret.add("" + executionResult.getModuleSize());
		return ret;
	}

	String write(Writer output, String str) {
		try {
			output.write(str);
			output.flush();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return str;
	}

	public List<String> run(MultiProcessorConfiguration conf, long start) {
		Objects.requireNonNull(conf);
		List<String> ret = new ArrayList<>();
		PseudorandomNumberGenerator random = new PseudorandomNumberGenerator(conf.getSeed());
		ProcessorCore core = new ProcessorCore();

		conf.getOntologyList().forEach(ontPair -> {
			try {

				String resultFileName = conf.getOutputDirectory() + FILE_SEPARATOR + ontPair.getOntologyName()
						+ LOG_EXTENSION;
				Writer output = new FileWriter(ResourceUtil.ensurePath(resultFileName), true);

				String temporaryFileName = conf.getOutputDirectory() + FILE_SEPARATOR + ontPair.getOntologyName()
						+ TEMP_FILE_SUFFIX;
				ProcessorConfiguration configuration = new ProcessorConfigurationImpl();
				configuration.setOntology(ontPair.getOntology());
				configuration.setBayesianNetwork(ontPair.getBayesianNetwork());
				configuration.setOutputFileName(temporaryFileName);
				configuration.setQueryProcessor(conf.getQueryProcessor());
				configuration.setShowingLog(conf.isShowingLog());
				List<SubsumptionQuery> queries = getQueries(ontPair.getOntology(), conf.getNumberOfQueries(), random);

				StringBuffer sbuf = new StringBuffer();
				sbuf.append(write(output, makeLine(FIRST_LINE_LIST) + LINE_SEPARATOR));

				queries.forEach(query -> {
					configuration.setQuery(query.asProblogString());
					sbuf.append(write(output, makeLine(getConditions(ontPair, configuration, query))));
					ProcessorExecutionResult executionResult = new ProcessorExecutionResultImpl();
					core.run(configuration, start, executionResult);
					sbuf.append(write(output, makeLine(getResult(executionResult)).trim() + LINE_SEPARATOR));
				});
				ret.add(sbuf.toString());
				output.flush();
				output.close();

			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		return ret;
	}

	/**
	 * Returns the list of ontology-network pairs.
	 * 
	 * @param ontologyDirectory
	 *            ontology directory
	 * @param bayesianNetworkDirectory
	 *            Bayesian network directory
	 * @return the list of ontology-network pairs
	 */
	public static List<OntologyAndNetwork> getOntologyAndNetworkList(String ontologyDirectory,
			String bayesianNetworkDirectory) {
		Objects.requireNonNull(ontologyDirectory);
		Objects.requireNonNull(bayesianNetworkDirectory);
		try {
			List<OntologyAndNetwork> ret = new ArrayList<>();
			if (!ontologyDirectory.isEmpty() && !bayesianNetworkDirectory.isEmpty()) {
				File file = new File(ontologyDirectory);
				File[] files = file.listFiles();
				Arrays.sort(files);

				for (int index = 0; index < files.length; index++) {

					String fileName = files[index].getName();
					if (fileName.endsWith(OWL_EXTENSION)) {
						String ontologyName = fileName.substring(0, fileName.length() - OWL_EXTENSION.length());

						File ontologyFile = new File(ontologyDirectory + FILE_SEPARATOR + ontologyName + OWL_EXTENSION);
						File bayesianNetworkFile = new File(
								bayesianNetworkDirectory + FILE_SEPARATOR + ontologyName + PL_EXTENSION);

						OWLOntology owlOntology = ProcessorConfigurationImpl
								.readOntology(new FileInputStream(ontologyFile));

						String bayesianNetwork = "";
						if (bayesianNetworkFile.exists()) {
							bayesianNetwork = ProcessorConfigurationImpl.read(new FileReader(bayesianNetworkFile));
						}

						ret.add(new OntologyAndNetwork(ontologyName, owlOntology, bayesianNetwork));
					}
				}
			}
			return ret;

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Stores the results.
	 * 
	 * @param conf
	 *            configuration
	 * @param results
	 *            results
	 * @throws IOException
	 *             if something went wrong with the I/O
	 */
	public void storeResults(MultiProcessorConfiguration conf, List<String> results) throws IOException {
		Objects.requireNonNull(conf);
		Objects.requireNonNull(results);
		Iterator<String> resultIt = results.iterator();
		List<OntologyAndNetwork> ontologyList = conf.getOntologyList();
		String outputDirectory = conf.getOutputDirectory();
		for (OntologyAndNetwork ontNet : ontologyList) {
			String result = resultIt.next();
			String fileName = outputDirectory + FILE_SEPARATOR + ontNet.getOntologyName() + CSV_EXTENSION;
			FileWriter fileWriter = new FileWriter(ResourceUtil.ensurePath(fileName), true);
			fileWriter.write(result);
			fileWriter.flush();
			fileWriter.close();
		}
	}

}
