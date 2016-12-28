package de.tudresden.inf.lat.born.gui.common;

/**
 * This interface provides text constants for the graphical user interface.
 * 
 * @author Julian Mendez
 */
public interface Message {

	// common
	String TOOLTIP_TEXT_FIELD_INPUT_ONTOLOGY_FILE = "select the input ontology file";
	String TOOLTIP_BUTTON_OPEN_INPUT_ONTOLOGY_FILE = "open an input ontology file";

	// Main Panel
	String TAB_PROCESSOR = "Reasoner";
	String TOOLTIP_PROCESSOR = "process an ontology to get the subsumption value";
	String TAB_TEST_MAKER = "Tests";
	String TOOLTIP_TEST_MAKER = "help in the creation of test data";
	String TAB_EXPERIMENT_MAKER = "Experiments";
	String TOOLTIP_EXPERIMENT_MAKER = "create and run experiments";

	// Processor Panel
	String TOOLTIP_TEXT_FIELD_LIST_OF_PARENTS = "select query file";
	String TOOLTIP_TEXT_FIELD_OPEN_BAYESIAN_NETWORK_FILE = "select the Bayesian network file";
	String TOOLTIP_BUTTON_OPEN_BAYESIAN_NETWORK_FILE = "open the Bayesian network file";
	String TOOLTIP_BUTTON_RESET_COMPLETION_RULES = "reset the completion rules";
	String TOOLTIP_BUTTON_GO_TO_PREVIOUS_COMPLETION_RULES = "go to the previous to the last used completion rules";
	String TOOLTIP_BUTTON_COMPUTE_INFERENCE = "compute inference";
	String TOOLTIP_TEXT_FIELD_MAX_NUMBER_OF_VAR = "maximum number of variables";
	String TOOLTIP_TEXT_FIELD_OUTPUT_FILE = "select the output file";
	String TOOLTIP_BUTTON_UPDATE_EXAMPLE = "load the selected example";
	String TOOLTIP_COMBO_BOX_EXAMPLE = "select example";
	String TOOLTIP_BUTTON_VIEW_ONTOLOGY_FILE = "view ontology file";
	String TOOLTIP_BUTTON_VIEW_BAYESIAN_NETWORK_FILE = "view Bayesian network file";
	String LBL_ONTOLOGY = "ONTOLOGY";
	String LBL_BAYESIAN_NETWORK = "BAYESIAN NETWORK";
	String LBL_RULES = "RULES";
	String LBL_QUERY = "QUERY";
	String LBL_RESULT = "RESULT";
	String LBL_EXAMPLES = "EXAMPLES";
	String LBL_COMPUTING = "computing ...";

	// Test Maker Panel
	String LBL_INPUT_ONTOLOGY = "INPUT ONTOLOGY";
	String LBL_THRESHOLD = "THRESHOLD [0,1]";
	String LBL_INPUT_BAYESIAN_NETWORK = "INPUT BAYESIAN NETWORK";
	String LBL_LIST_OF_PARENTS = "LIST OF PARENTS (e.g. \"1,1,2,3,5,8\")";
	String TOOLTIP_TEXT_FIELD_THRESHOLD = "the threshold is a number between 0 and 1 to determine the probability to annotate an axiom";

	// Experiment Runner
	String TOOLTIP_TEXT_FIELD_SEED = "define the seed to generate the pseudorandom numbers";
	String TOOLTIP_TEXT_FIELD_NUMBER_OF_QUERIES = "define the number of queries";
	String TOOLTIP_TEXT_FIELD_OUTPUT_DIRECTORY = "select the output directory";
	String TOOLTIP_BUTTON_OUTPUT_DIRECTORY = "select the output directory";
	String TOOLTIP_BUTTON_UPDATE_SEED = "update the seed with a new random number";
	String LBL_SEED = "SEED";
	String LBL_NUMBER_OF_QUERIES = "NUMBER OF QUERIES";
	String LBL_ONTOLOGY_DIRECTORY = "ONTOLOGY DIRECTORY";
	String LBL_BAYESIAN_NETWORK_DIRECTORY = "BAYESIAN NETWORK DIRECTORY";
	String LBL_OUTPUT_DIRECTORY = "OUTPUT DIRECTORY";

	// Errors
	String WRONG_FILE_NAME_ERROR = "WRONG FILE NAME! ";

}
