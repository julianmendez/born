package de.tudresden.inf.lat.born.gui.common;

/**
 * This interface provides text constants for the graphical user interface.
 * 
 * @author Julian Mendez
 */
public interface Message {

	// common

	/** Tooltip for text field containing the input ontology file name. */
	String TOOLTIP_TEXT_FIELD_INPUT_ONTOLOGY_FILE = "select the input ontology file";

	/** Tooltip for button to open the input ontology file. */
	String TOOLTIP_BUTTON_OPEN_INPUT_ONTOLOGY_FILE = "open an input ontology file";

	// Main Panel

	/** Title of processor tab. */
	String TAB_PROCESSOR = "Reasoner";

	/** Tooltip for processor. */
	String TOOLTIP_PROCESSOR = "process an ontology to get the subsumption value";

	/** Title of test maker tab. */
	String TAB_TEST_MAKER = "Tests";

	/** Tooltip for test maker. */
	String TOOLTIP_TEST_MAKER = "help in the creation of test data";

	/** Title of experiment maker tab. */
	String TAB_EXPERIMENT_MAKER = "Experiments";

	/** Tooltip for experiment maker. */
	String TOOLTIP_EXPERIMENT_MAKER = "create and run experiments";

	// Processor Panel

	/** Tooltip for text field containing list of parents. */
	String TOOLTIP_TEXT_FIELD_LIST_OF_PARENTS = "select query file";

	/** Tooltip for text field containing Bayesian network file name. */
	String TOOLTIP_TEXT_FIELD_OPEN_BAYESIAN_NETWORK_FILE = "select the Bayesian network file";

	/** Tooltip for button to open the Bayesian network file. */
	String TOOLTIP_BUTTON_OPEN_BAYESIAN_NETWORK_FILE = "open the Bayesian network file";

	/** Tooltip for button to reset completion rules. */
	String TOOLTIP_BUTTON_RESET_COMPLETION_RULES = "reset the completion rules";

	/** Tooltip for button to go to the previous completion rules. */
	String TOOLTIP_BUTTON_GO_TO_PREVIOUS_COMPLETION_RULES = "go to the previous to the last used completion rules";

	/** Tooltip for button to compute inference. */
	String TOOLTIP_BUTTON_COMPUTE_INFERENCE = "compute inference";

	/** Tooltip for text field containing the maximum number of variables. */
	String TOOLTIP_TEXT_FIELD_MAX_NUMBER_OF_VAR = "maximum number of variables";

	/** Tooltip for text field containing the output file name. */
	String TOOLTIP_TEXT_FIELD_OUTPUT_FILE = "select the output file";

	/** Tooltip for button to load the selected example. */
	String TOOLTIP_BUTTON_UPDATE_EXAMPLE = "load the selected example";

	/** Tooltip for combo box to select an example. */
	String TOOLTIP_COMBO_BOX_EXAMPLE = "select example";

	/** Tooltip for button to view the ontology. */
	String TOOLTIP_BUTTON_VIEW_ONTOLOGY_FILE = "view ontology file";

	/** Tooltip for button to view the Bayesian network. */
	String TOOLTIP_BUTTON_VIEW_BAYESIAN_NETWORK_FILE = "view Bayesian network file";

	/** Label to indicate the ontology. */
	String LBL_ONTOLOGY = "ONTOLOGY";

	/** Label to indicate the Bayesian network. */
	String LBL_BAYESIAN_NETWORK = "BAYESIAN NETWORK";

	/** Label to indicate the rules. */
	String LBL_RULES = "RULES";

	/** Label to indicate the query. */
	String LBL_QUERY = "QUERY";

	/** Label to indicate the result. */
	String LBL_RESULT = "RESULT";

	/** Label to indicate the examples. */
	String LBL_EXAMPLES = "EXAMPLES";

	/** Label to indicate that the system is running. */
	String LBL_COMPUTING = "computing ...";

	// Test Maker Panel

	/** Label to indicate the input ontology. */
	String LBL_INPUT_ONTOLOGY = "INPUT ONTOLOGY";

	/** Label to indicate the threshold. */
	String LBL_THRESHOLD = "THRESHOLD [0,1]";

	/** Label to indicate the Bayesian network. */
	String LBL_INPUT_BAYESIAN_NETWORK = "INPUT BAYESIAN NETWORK";

	/** Label to indicate the list of parents. */
	String LBL_LIST_OF_PARENTS = "LIST OF PARENTS (e.g. \"1,1,2,3,5,8\")";

	/** Tooltip for text field containing threshold. */
	String TOOLTIP_TEXT_FIELD_THRESHOLD = "the threshold is a number between 0 and 1 to determine the probability to annotate an axiom";

	// Experiment Runner

	/** Tooltip for text field containing the seed. */
	String TOOLTIP_TEXT_FIELD_SEED = "define the seed to generate the pseudorandom numbers";

	/** Tooltip for text field containing the number of queries. */
	String TOOLTIP_TEXT_FIELD_NUMBER_OF_QUERIES = "define the number of queries";

	/** Tooltip for text field containing the output directory. */
	String TOOLTIP_TEXT_FIELD_OUTPUT_DIRECTORY = "select the output directory";

	/** Tooltip for button to select the output directory. */
	String TOOLTIP_BUTTON_OUTPUT_DIRECTORY = "select the output directory";

	/** Tooltip for button to update the seed. */
	String TOOLTIP_BUTTON_UPDATE_SEED = "update the seed with a new random number";

	/** Label to indicate the seed. */
	String LBL_SEED = "SEED";

	/** Label to indicate the number of queries. */
	String LBL_NUMBER_OF_QUERIES = "NUMBER OF QUERIES";

	/** Label to indicate the ontology directory. */
	String LBL_ONTOLOGY_DIRECTORY = "ONTOLOGY DIRECTORY";

	/** Label to indicate the Bayesian network directory. */
	String LBL_BAYESIAN_NETWORK_DIRECTORY = "BAYESIAN NETWORK DIRECTORY";

	/** Label to indicate the output directory. */
	String LBL_OUTPUT_DIRECTORY = "OUTPUT DIRECTORY";

	// Errors

	/** Text indicating that the file name is wrong. */
	String WRONG_FILE_NAME_ERROR = "WRONG FILE NAME! ";

}
