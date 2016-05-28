package de.tudresden.inf.lat.born.gui.common;

/**
 * This interface provides text constants for the graphical user interface.
 * 
 * @author Julian Mendez
 */
public interface Message {

	// common
	String tooltipTextFieldInputOntologyFile = "select the input ontology file";
	String tooltipOpenInputOntologyFile = "opens an input ontology file";

	// Main Panel
	String tabProcessor = "Reasoner";
	String tooltipProcessor = "processes an ontology to get the subsumption value";
	String tabTestMaker = "Tests";
	String tooltipTestMaker = "helps in the creation of test data";
	String tabExperminentMaker = "Experiments";
	String tooltipExperimentMaker = "creates and runs experiments";

	// Processor Panel
	String tooltipTextFieldListOfParents = "select query file";
	String tooltipTextFieldBayesianNetworkFile = "select the Bayesian network file";
	String tooltipButtonResetCompletionRules = "reset the completion rules";
	String tooltipButtonGoToPreviousCompletionRules = "go to the previous to the last used completion rules";
	String tooltipComputeInference = "compute inference";
	String tooltipTextFieldMaxNumberOfVar = "maximum number of variables";
	String tooltipTextFieldOutputFile = "select the output file";
	String tooltipButtonUpdateExample = "load the selected example";
	String tooltipComboBoxExample = "select example";
	String tooltipViewOntologyFile = "view ontology file";
	String tooltipViewBayesianNetworkFile = "view Bayesian network file";
	String ONTOLOGY = "ONTOLOGY";
	String BAYESIAN_NETWORK = "BAYESIAN NETWORK";
	String RULES = "RULES";
	String QUERY = "QUERY";
	String RESULT = "RESULT";
	String EXAMPLES = "EXAMPLES";
	String COMPUTING = "computing ...";

	// Test Maker Panel
	String INPUT_ONTOLOGY = "INPUT ONTOLOGY";
	String THRESHOLD = "THRESHOLD [0,1]";
	String INPUT_BAYESIAN_NETWORK = "INPUT BAYESIAN NETWORK";
	String LIST_OF_PARENTS = "LIST OF PARENTS (e.g. \"1,1,2,3,5,8\")";
	String tooltipTextFieldThreshold = "the threshold is a number between 0 and 1 to determine the probability to annotate an axiom";

	// Experiment Runner
	String tooltipTextFieldSeed = "define the seed to generate the pseudorandom numbers";
	String tooltipTextFieldNumberOfQueries = "define the number of queries";
	String tooltipTextFieldOutputDirectory = "select the output directory";
	String tooltipButtonOutputDirectory = "select the output directory";
	String tooltipButtonUpdateSeed = "update the seed with a new random number";
	String textSeed = "SEED";
	String textNumberOfQueries = "NUMBER OF QUERIES";
	String ONTOLOGY_DIRECTORY = "ONTOLOGY DIRECTORY";
	String BAYESIAN_NETWORK_DIRECTORY = "BAYESIAN NETWORK DIRECTORY";
	String OUTPUT_DIRECTORY = "OUTPUT DIRECTORY";

	// Errors
	String WRONG_FILE_NAME_ERROR = "WRONG FILE NAME! ";

}
