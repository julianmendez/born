package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.List;

import de.tudresden.inf.lat.born.owlapi.processor.QueryProcessor;

public interface MultiProcessorConfiguration {

	List<OntologyAndNetwork> getOntologyList();

	void setOntologyList(List<OntologyAndNetwork> ontologyList);

	String getOutputDirectory();

	void setOutputDirectory(String outputDirectory);

	int getNumberOfQueries();

	void setNumberOfQueries(int numberOfQueries);

	int getSeed();

	void setSeed(int seed);

	QueryProcessor getQueryProcessor();

	void setQueryProcessor(QueryProcessor queryProcessor);

	boolean isShowingLog();

	void setShowingLog(boolean showingLog);

}