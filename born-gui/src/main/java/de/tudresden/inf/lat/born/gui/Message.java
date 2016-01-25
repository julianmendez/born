package de.tudresden.inf.lat.born.gui;

/**
 * This interface provides text constants for the graphical user interface.
 * 
 * @author Julian Mendez
 */
public interface Message {

	String tabProcessor = "Reasoner";
	String tooltipProcessor = "processes an ontology to get the subsumption value";

	String tabTestMaker = "Tests";
	String tooltipTestMaker = "helps in the creation of test data";

	String tabExperminentMaker = "Experiments";
	String tooltipExperimentMaker = "creates and runs experiments";

	String textNumberOfQueries = "number of queries";
	String textSeed = "seed";

	String tooltipTextFieldInputOntologyFile = "select the input ontology file";
	String tooltipTextFieldBayesianNetworkFile = "select the Bayesian network file";
	String tooltipTextFieldListOfParents = "select query file";

	String tooltipTextFieldOutputDirectory = "select the output directory";
	String tooltipTextFieldNumberOfQueries = "define the number of queries";
	String tooltipTextFieldSeed = "define the seed to generate the pseudorandom numbers";

	String tooltipOpenInputOntologyFile = "opens an input ontology file";
	String tooltipButtonOutputDirectory = "select the output directory";
	String tooltipButtonUpdateSeed = "update the seed with a new random number";

	String tooltipButtonResetCompletionRules = "reset the completion rules";
	String tooltipButtonGoToPreviousCompletionRules = "go to the previous completion rules";

	// Processor
	String tooltipComputeInference = "compute inference";
	String tooltipTextFieldMaxNumberOfVar = "maximum number of variables";
	String tooltipTextFieldOutputFile = "select the output file";
	String tooltipButtonUpdateExample = "load the selected example";
	String tooltipComboBoxExample = "select example";

	// Annotator
	String tooltipTextFieldThreshold = "the threshold is a number between 0 and 1 to determine the probability to annotate an axiom";

}
