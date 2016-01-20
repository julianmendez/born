package de.tudresden.inf.lat.born.owlapi.processor;

/**
 * This models a result produced by the processor after its execution.
 * 
 * @author Julian Mendez
 *
 */
public interface ProcessorExecutionResult {

	/**
	 * Returns the time in nanoseconds used for the translation of OWL axioms
	 * into integers.
	 * 
	 * @return the time in nanoseconds used for the translation of OWL axioms
	 *         into integers
	 */
	long getTranslationTime();

	/**
	 * Sets the translation time.
	 * 
	 * @param translationTime
	 *            translation time
	 */
	void setTranslationTime(long translationTime);

	/**
	 * Returns the time in nanoseconds used for the normalization of axioms.
	 * 
	 * @return the time in nanoseconds used for the normalization of axioms
	 */
	long getNormalizationTime();

	/**
	 * Sets the normalization time.
	 * 
	 * @param normalizationTime
	 *            normalization time
	 */
	void setNormalizationTime(long normalizationTime);

	/**
	 * Returns the time in nanoseconds used for the module extraction.
	 * 
	 * @return the time in nanoseconds used for the module extraction
	 */
	long getModuleExtractionTime();

	/**
	 * Sets the module extraction time
	 * 
	 * @param moduleExtractionTime
	 *            module extraction time
	 */
	void setModuleExtractionTime(long moduleExtractionTime);

	/**
	 * Returns the time in nanoseconds used by ProbLog for reasoning.
	 * 
	 * @return the time in nanoseconds used by ProbLog for reasoning
	 */
	long getProblogReasoningTime();

	/**
	 * Sets the ProbLog reasoning time
	 * 
	 * @param problogReasoningTime
	 *            ProbLog reasoning time
	 */
	void setProblogReasoningTime(long problogReasoningTime);

	/**
	 * Returns the total time in nanoseconds.
	 * 
	 * @return the total time in nanoseconds
	 */
	long getTotalTime();

	/**
	 * Sets the total time
	 * 
	 * @param totalTime
	 *            total time
	 */
	void setTotalTime(long totalTime);

	/**
	 * Returns the number of axioms in the ontology.
	 * 
	 * @return the number of axioms in the ontology
	 */
	int getOntologySize();

	/**
	 * Sets the ontology size.
	 * 
	 * @param ontologySize
	 *            ontology size
	 */
	void setOntologySize(int ontologySize);

	/**
	 * Returns the number of axioms in the normalized ontology.
	 * 
	 * @return the number of axioms in the normalized ontology
	 */
	int getNormalizedOntologySize();

	/**
	 * Sets the normalized ontology size.
	 * 
	 * @param normalizedOntologySize
	 *            normalized ontology size
	 */
	void setNormalizedOntologySize(int normalizedOntologySize);

	/**
	 * Returns the number of axioms in the extracted module.
	 * 
	 * @return the number of axioms in the extracted module
	 */
	int getModuleSize();

	/**
	 * Sets the module size.
	 * 
	 * @param moduleSize
	 *            module size
	 */
	void setModuleSize(int moduleSize);

	/**
	 * Returns the results of the processing.
	 * 
	 * @return the results of the processing
	 */
	String getResult();

	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            result
	 */
	void setResult(String result);

}
