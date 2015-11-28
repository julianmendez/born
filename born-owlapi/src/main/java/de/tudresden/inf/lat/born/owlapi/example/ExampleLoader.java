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
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * An object of this class loads all the examples provided in the distribution.
 * 
 * @author Julian Mendez
 *
 */
public class ExampleLoader {

	public static final String EXAMPLES_DIRECTORY = "examples/";

	public static final char NEW_LINE_CHAR = '\n';
	public static final char SLASH_CHAR = '/';

	public static final String OWL_EXTENSION = ".owl";
	public static final String NETWORK_EXTENSION = ".pl";
	public static final String QUERY_EXTENSION = ".query";

	private List<ExampleConfiguration> exampleConfigurations = new ArrayList<ExampleConfiguration>();

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
			examples = new ArrayList<ExampleConfiguration>();
			e.printStackTrace();

		} catch (IOException e) {
			examples = new ArrayList<ExampleConfiguration>();
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
		List<String> listOfExamples = getExampleFiles(EXAMPLES_DIRECTORY);
		List<ExampleConfiguration> examples = getOntologyAndNetworkFiles(listOfExamples);
		return examples;
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
		List<String> ret = new ArrayList<String>();
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) {
			String fileName = jarEntries.nextElement().getName();
			if (fileName.startsWith(path)) {
				ret.add(fileName);
			}
		}
		jarFile.close();
		return ret;
	}

	List<String> getExampleFilesFromDirectory(String path) {
		List<String> ret = new ArrayList<String>();
		URL url = getClass().getClassLoader().getResource(path);
		if (url != null) {
			File f = new File(url.getPath());
			File[] list = f.listFiles();
			for (int index = 0; index < list.length; index++) {
				ret.add(list[index].getAbsolutePath());
			}
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
		List<String> ret = new ArrayList<String>();
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
		List<String> ret = new ArrayList<String>();
		for (String fileName : list) {
			if (fileName.endsWith(extension)) {
				ret.add(fileName);
			}
		}
		return ret;
	}

	OWLOntology readOntology(InputStream input) throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ret = manager.loadOntologyFromOntologyDocument(input);
		return ret;
	}

	String getFile(InputStream input) throws IOException {
		StringBuffer sbuf = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			sbuf.append(line);
			sbuf.append(NEW_LINE_CHAR);
		}
		reader.close();
		return sbuf.toString();
	}

	String getFileName(String fileNameWithPath) {
		String ret = fileNameWithPath;
		int lastIndex = fileNameWithPath.lastIndexOf(SLASH_CHAR);
		if (lastIndex != -1) {
			ret = fileNameWithPath.substring(lastIndex + 1);
		}
		return ret;
	}

	public InputStream getInputStreamForFile(String fileName) throws FileNotFoundException {
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
		return (jarFile != null) && (jarFile.isFile());
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
		List<ExampleConfiguration> ret = new ArrayList<ExampleConfiguration>();
		List<String> owlFiles = getFilesWithExtension(list, OWL_EXTENSION);

		for (String fileName : owlFiles) {
			String fileNamePrefix = fileName.substring(0, fileName.length() - OWL_EXTENSION.length());
			String bayesianNetworkFileName = fileNamePrefix + NETWORK_EXTENSION;
			String queryFileName = fileNamePrefix + QUERY_EXTENSION;

			String owlOntologyName = fileName;
			OWLOntology owlOntology = readOntology(getInputStreamForFile(owlOntologyName));
			String bayesianNetwork = getFile(getInputStreamForFile(bayesianNetworkFileName));
			String query = getFile(getInputStreamForFile(queryFileName));

			ExampleConfiguration exampleConf = new ExampleConfiguration(getFileName(fileNamePrefix), owlOntologyName,
					owlOntology, bayesianNetworkFileName, bayesianNetwork, query);

			ret.add(exampleConf);
		}
		return ret;
	}

}
