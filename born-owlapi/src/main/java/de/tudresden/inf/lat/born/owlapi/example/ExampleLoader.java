package de.tudresden.inf.lat.born.owlapi.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.term.Symbol;

/**
 * An object of this class loads all the examples provided in the distribution.
 * 
 * @author Julian Mendez
 *
 */
public class ExampleLoader {

	public static final String EXAMPLES_DIRECTORY = "examples/";

	public static final String OWL_EXTENSION = ".owl";
	public static final String NETWORK_EXTENSION = ".pl";
	public static final String QUERY_EXTENSION = ".query";

	private List<ExampleConfiguration> exampleConfigurations = new ArrayList<>();

	/**
	 * Constructs a new example loader.
	 */
	public ExampleLoader() {
		reset();
	}

	/**
	 * Re-reads all example configurations. If something goes wrong with
	 * input/output, this method assumes that the set is empty and prints the
	 * stack trace.
	 */
	public void reset() {
		List<ExampleConfiguration> examples = null;
		try {
			examples = readExampleConfigurations();

		} catch (OWLOntologyCreationException e) {
			examples = new ArrayList<>();
			e.printStackTrace();

		} catch (IOException e) {
			examples = new ArrayList<>();
			e.printStackTrace();

		}
		this.exampleConfigurations.clear();
		this.exampleConfigurations.addAll(examples);
	}

	/**
	 * Returns the example configurations forcing a re-read.
	 * 
	 * @return the example configurations forcing a re-read
	 * @throws OWLOntologyCreationException
	 *             if something goes wrong with the ontology creation
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * 
	 */
	public List<ExampleConfiguration> readExampleConfigurations() throws OWLOntologyCreationException, IOException {
		return getOntologyAndNetworkFiles(getExampleFiles(EXAMPLES_DIRECTORY));
	}

	/**
	 * Returns the example configurations.
	 * 
	 * @return the example configurations
	 */
	public List<ExampleConfiguration> getExampleConfigurations() {
		return this.exampleConfigurations;
	}

	List<String> getExampleFilesFromJar(File file, String path) throws IOException {
		Objects.requireNonNull(file);
		Objects.requireNonNull(path);
		JarFile jarFile = new JarFile(file);
		List<String> ret = jarFile.stream().map(jarEntry -> jarEntry.getName())
				.filter(fileName -> fileName.startsWith(path)).collect(Collectors.toList());
		jarFile.close();
		return ret;
	}

	List<String> getExampleFilesFromDirectory(String path) {
		Objects.requireNonNull(path);
		List<String> ret = new ArrayList<>();
		URL url = getClass().getClassLoader().getResource(path);
		if (!Objects.isNull(url)) {
			File f = new File(url.getPath());
			List<File> files = Arrays.asList(f.listFiles());
			files.forEach(elem -> ret.add(elem.getAbsolutePath()));
		}
		return ret;
	}

	/**
	 * Returns the files in the directory path.
	 * 
	 * @param path
	 *            path
	 * @return the files in the directory path
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public List<String> getExampleFiles(String path) throws IOException {
		Objects.requireNonNull(path);
		List<String> ret = new ArrayList<>();
		File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		if (jarFile.isFile()) {
			ret.addAll(getExampleFilesFromJar(jarFile, path));
		} else {
			ret.addAll(getExampleFilesFromDirectory(path));
		}
		Collections.sort(ret);
		return ret;
	}

	List<String> getFilesWithExtension(List<String> list, String extension) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(extension);
		return list.stream().filter(fileName -> fileName.endsWith(extension)).collect(Collectors.toList());
	}

	OWLOntology readOntology(InputStream input) throws OWLOntologyCreationException {
		Objects.requireNonNull(input);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ret = manager.loadOntologyFromOntologyDocument(input);
		return ret;
	}

	String getFile(InputStream input) throws IOException {
		Objects.requireNonNull(input);
		StringBuffer sbuf = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		for (String line = reader.readLine(); !Objects.isNull(line); line = reader.readLine()) {
			sbuf.append(line);
			sbuf.append(Symbol.LINE_SEPARATOR);
		}
		reader.close();
		return sbuf.toString();
	}

	String getFileName(String fileNameWithPath) {
		Objects.requireNonNull(fileNameWithPath);
		String ret = fileNameWithPath;
		int lastIndex = fileNameWithPath.lastIndexOf(Symbol.FILE_SEPARATOR);
		if (lastIndex != -1) {
			ret = fileNameWithPath.substring(lastIndex + Symbol.FILE_SEPARATOR.length());
		}
		return ret;
	}

	public InputStream getInputStreamForFile(String fileName) throws FileNotFoundException {
		Objects.requireNonNull(fileName);
		InputStream owlInputStream;
		if (isInJar()) {
			owlInputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		} else {
			owlInputStream = new FileInputStream(fileName);
		}
		return owlInputStream;

	}

	public boolean isInJar() {
		File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		return !Objects.isNull(jarFile) && (jarFile.isFile());
	}

	/**
	 * Returns a list of example configurations based on a list of files.
	 * 
	 * @param list
	 *            list of files
	 * @return a list of example configurations based on a list of files
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws OWLOntologyCreationException
	 *             if something goes wrong with the creation of the ontologies
	 */
	public List<ExampleConfiguration> getOntologyAndNetworkFiles(List<String> list)
			throws IOException, OWLOntologyCreationException {
		Objects.requireNonNull(list);
		List<ExampleConfiguration> ret = new ArrayList<>();
		List<String> owlFiles = getFilesWithExtension(list, OWL_EXTENSION);

		for (String fileName : owlFiles) {
			String fileNamePrefix = fileName.substring(0, fileName.length() - OWL_EXTENSION.length());
			String bayesianNetworkFileName = fileNamePrefix + NETWORK_EXTENSION;
			String queryFileName = fileNamePrefix + QUERY_EXTENSION;

			String owlOntologyName = fileName;
			OWLOntology owlOntology = readOntology(getInputStreamForFile(owlOntologyName));
			String bayesianNetwork = getFile(getInputStreamForFile(bayesianNetworkFileName));
			String query = getFile(getInputStreamForFile(queryFileName));

			ExampleConfiguration exampleConf = new ExampleConfigurationImpl(getFileName(fileNamePrefix),
					owlOntologyName, owlOntology, bayesianNetworkFileName, bayesianNetwork, query);

			ret.add(exampleConf);
		}
		return ret;
	}

}
