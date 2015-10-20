package de.tudresden.inf.lat.born.owlapi.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

	public void reset() {
		List<ExampleConfiguration> examples = null;
		try {
			List<File> listOfExamples = getExampleFiles(EXAMPLES_DIRECTORY);
			examples = getOntologyAndNetworkFiles(listOfExamples);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.exampleConfigurations.clear();
		this.exampleConfigurations.addAll(examples);
	}

	/**
	 * Returns the example configurations.
	 * 
	 * @return the example configurations
	 */
	public List<ExampleConfiguration> getExampleConfigurations() {
		return this.exampleConfigurations;
	}

	List<File> getExampleFilesFromJar(File file, String path) throws IOException {
		List<File> ret = new ArrayList<File>();
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) {
			String fileName = jarEntries.nextElement().getName();
			if (fileName.startsWith(path)) {
				ret.add(new File(fileName));
			}
		}
		jarFile.close();
		return ret;
	}

	List<File> getExampleFilesFromDirectory(String path) {
		List<File> ret = new ArrayList<File>();
		URL url = ExampleLoader.class.getClassLoader().getResource(path);
		if (url != null) {
			File f = new File(url.getPath());
			File[] list = f.listFiles();
			for (int index = 0; index < list.length; index++) {
				ret.add(list[index]);
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
	public List<File> getExampleFiles(String path) throws IOException {
		List<File> ret = new ArrayList<File>();
		File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		if (jarFile.isFile()) {
			ret.addAll(getExampleFilesFromJar(jarFile, path));
		} else {
			ret.addAll(getExampleFilesFromDirectory(path));
		}
		return ret;
	}

	List<File> getFilesWithExtension(List<File> list, String extension) {
		List<File> ret = new ArrayList<File>();
		for (File file : list) {
			String fileName = file.getAbsolutePath();
			if (fileName.endsWith(extension)) {
				ret.add(file);
			}
		}
		return ret;
	}

	OWLOntology readOntology(File file) throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ret = manager.loadOntologyFromOntologyDocument(file);
		return ret;
	}

	String getFile(String fileName) throws IOException {
		StringBuffer sbuf = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			sbuf.append(line);
			sbuf.append(NEW_LINE_CHAR);
		}
		reader.close();
		return sbuf.toString();
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
	public List<ExampleConfiguration> getOntologyAndNetworkFiles(List<File> list)
			throws IOException, OWLOntologyCreationException {
		List<ExampleConfiguration> ret = new ArrayList<ExampleConfiguration>();
		List<File> owlFiles = getFilesWithExtension(list, OWL_EXTENSION);

		for (File owlFile : owlFiles) {
			String fileName = owlFile.getAbsolutePath();
			String fileNamePrefix = fileName.substring(0, fileName.length() - OWL_EXTENSION.length());
			String bayesianNetworkFileName = fileNamePrefix + NETWORK_EXTENSION;
			String queryFileName = fileNamePrefix + QUERY_EXTENSION;

			String owlOntologyName = owlFile.getName();
			OWLOntology owlOntology = readOntology(owlFile);
			String bayesianNetwork = getFile(bayesianNetworkFileName);
			String query = getFile(queryFileName);

			ExampleConfiguration exampleConf = new ExampleConfiguration(owlOntologyName, owlOntology, bayesianNetwork,
					query);

			ret.add(exampleConf);
		}
		return ret;
	}

}
