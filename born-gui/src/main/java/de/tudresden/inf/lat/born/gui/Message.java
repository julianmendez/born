package de.tudresden.inf.lat.born.gui;

/**
 * This interface provides messages for the user interface.
 * 
 * @author Julian Mendez
 */
public interface Message {

	String iconOpenFile = "icons/openfolder-16x16.png";
	String iconSaveFile = "icons/floppydisk-16x16.png";
	String iconComputeInference = "icons/play-16x16.png";

	String textInputOntologyFile = "input ontology file (e.g. \"ontology.owl\")";
	String textBayesianNetworkFile = "Bayesian network file (e.g. \"network.pl\")";
	String textQuery = "query (e.g. \"query(sub('A', 'B')).\" )";
	String textOutput = "console output";

	String tooltipTextFieldInputOntologyFile = "select the input ontology file";
	String tooltipTextFieldBayesianNetworkFile = "select the Bayesian network file";
	String tooltipTextFieldListOfParents = "select query file";

	String tooltipOpenInputOntologyFile = "opens an input ontology file";

	// Processor
	String tooltipComputeInference = "compute inference";
	String textMaxNumberOfVar = "max number of vars (e.g. 5)";
	String tooltipTextFieldMaxNumberOfVar = "maximum number of variables";
	String textOutputBayesianNetwork = "output (e.g. network.pl)";
	String tooltipTextFieldOutputFile = "select the output file";

	// Connector
	String tooltipTextFieldOutputBayesianNetwork = "file name of Bayesian network generated according to the given list of parents";
	String textTextFieldListOfParents = "list of parents (e.g. 1,1,2,3,5,8)";

	// Annotator
	String textOutputOntolgoyFile = "output ontology (e.g. myontology.owl)";
	String tooltipTextFieldOutputOntologyFile = "the output ontology is annotated using the given threshold and number of variables";
	String textThreshold = "threshold between 0 and 1 (e.g. 0.3)";
	String tooltipTextFieldThreshold = "the threshold is a number between 0 and 1 to determine the probability to annotate an axiom";
	String tooltipAnnotatorRun = "execute annotator";

}
