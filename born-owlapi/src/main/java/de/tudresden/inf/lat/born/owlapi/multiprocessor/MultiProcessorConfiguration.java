package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.List;

import de.tudresden.inf.lat.born.owlapi.processor.QueryProcessor;

/**
 * This interface models the configuration of a multi-processor.
 * 
 * @author Julian Mendez
 *
 */
public interface MultiProcessorConfiguration {

	/**
	 * Returns the ontology list.
	 * 
	 * @return the ontology list
	 */
	List<OntologyAndNetwork> getOntologyList();

	/**
	 * Sets the ontology list.
	 * 
	 * @param ontologyList
	 *            ontology list
	 */
	void setOntologyList(List<OntologyAndNetwork> ontologyList);

	/**
	 * Returns the output directory.
	 * 
	 * @return the output directory
	 */
	String getOutputDirectory();

	/**
	 * Sets the output directory.
	 * 
	 * @param outputDirectory
	 *            output directory
	 */
	void setOutputDirectory(String outputDirectory);

	/**
	 * Returns the number of queries.
	 * 
	 * @return the number of queries
	 */
	int getNumberOfQueries();

	/**
	 * Sets the number of queries.
	 * 
	 * @param numberOfQueries
	 *            number of queries
	 */
	void setNumberOfQueries(int numberOfQueries);

	/**
	 * Returns the seed for the pseudorandom number generator.
	 * 
	 * @return the seed for the pseudorandom number generator
	 */
	int getSeed();

	/**
	 * Sets the seed for the pseudorandom number generator.
	 * 
	 * @param seed
	 *            seed
	 */
	void setSeed(int seed);

	/**
	 * Returns the query processor.
	 * 
	 * @return the query processor
	 */
	QueryProcessor getQueryProcessor();

	/**
	 * Sets the query processor.
	 * 
	 * @param queryProcessor
	 *            query processor
	 */
	void setQueryProcessor(QueryProcessor queryProcessor);

	/**
	 * Tells whether the log is showing.
	 * 
	 * @return <code>true</code> if and only if the log is showing
	 */
	boolean isShowingLog();

	/**
	 * Sets whether the log is showing.
	 * 
	 * @param showingLog
	 *            <code>true</code> if and only if the log is showing
	 */
	void setShowingLog(boolean showingLog);

}
