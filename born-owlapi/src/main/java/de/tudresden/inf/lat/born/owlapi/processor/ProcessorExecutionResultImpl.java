package de.tudresden.inf.lat.born.owlapi.processor;

/**
 * This is the default implementation of a processor execution result.
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorExecutionResultImpl implements ProcessorExecutionResult {

	private long translationTime;
	private long normalizationTime;
	private long moduleExtractionTime;
	private long problogReasoningTime;
	private long totalTime;
	private int normalizedOntologySize;
	private int ontologySize;
	private int moduleSize;
	private String result;

	/**
	 * Constructs a new processor execution result.
	 */
	public ProcessorExecutionResultImpl() {
	}

	@Override
	public long getTranslationTime() {
		return this.translationTime;
	}

	@Override
	public void setTranslationTime(long translationTime) {
		this.translationTime = translationTime;
	}

	@Override
	public long getNormalizationTime() {
		return this.normalizationTime;
	}

	@Override
	public void setNormalizationTime(long normalizationTime) {
		this.normalizationTime = normalizationTime;
	}

	@Override
	public long getModuleExtractionTime() {
		return this.moduleExtractionTime;
	}

	@Override
	public void setModuleExtractionTime(long moduleExtractionTime) {
		this.moduleExtractionTime = moduleExtractionTime;
	}

	@Override
	public long getProblogReasoningTime() {
		return this.problogReasoningTime;
	}

	@Override
	public void setProblogReasoningTime(long problogReasoningTime) {
		this.problogReasoningTime = problogReasoningTime;
	}

	@Override
	public long getTotalTime() {
		return this.totalTime;
	}

	@Override
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public int getOntologySize() {
		return this.ontologySize;
	}

	@Override
	public void setOntologySize(int ontologySize) {
		this.ontologySize = ontologySize;
	}

	@Override
	public int getNormalizedOntologySize() {
		return this.normalizedOntologySize;
	}

	@Override
	public void setNormalizedOntologySize(int normalizedOntologySize) {
		this.normalizedOntologySize = normalizedOntologySize;
	}

	@Override
	public int getModuleSize() {
		return this.moduleSize;
	}

	@Override
	public void setModuleSize(int moduleSize) {
		this.moduleSize = moduleSize;
	}

	@Override
	public String getResult() {
		return this.result;
	}

	@Override
	public void setResult(String result) {
		this.result = result;
	}

}
