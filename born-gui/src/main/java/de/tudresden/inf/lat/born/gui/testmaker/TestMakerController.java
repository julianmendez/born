package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorCore;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfigurationImpl;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorCore;

/**
 * This class is a controller for the test maker.
 * 
 * @author Julian Mendez
 */
public class TestMakerController implements ActionListener {

	private static final String actionSelectInputOntologyFile = "open input ontology";
	private static final String actionSelectOutputOntologyFile = "save output ontology";
	private static final String actionSaveBayesianNetwork = "save Bayesian network";

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;

	private final TestMakerView view;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public TestMakerController(TestMakerView view, OWLOntologyManager ontologyManager) {
		Objects.requireNonNull(view);
		Objects.requireNonNull(ontologyManager);
		this.view = view;
		this.owlOntologyManager = ontologyManager;
		init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Objects.requireNonNull(e);
		String cmd = e.getActionCommand();
		if (cmd.equals(actionSelectInputOntologyFile)) {
			executeActionSelectInputOntologyFile();
		} else if (cmd.equals(actionSelectOutputOntologyFile)) {
			executeActionSelectOutputOntologyFile();
		} else if (cmd.equals(actionSaveBayesianNetwork)) {
			executeActionSaveBayesianNetwork();
		} else {
			throw new IllegalStateException();
		}
	}

	void executeActionSelectInputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			getView().setInputOntologyFile(file.getAbsolutePath());
			getView().updateOntologyFile();
		}
	}

	void annotateOntology(OutputStream output) {
		Objects.requireNonNull(output);
		getView().updateThreshold();
		getView().updateMaxNumberOfVars();
		AnnotatorConfiguration conf = getModel();
		conf.setOutputOntology(output);
		AnnotatorCore core = new AnnotatorCore();
		core.run(conf);
	}

	void executeActionSelectOutputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			try {
				annotateOntology(new FileOutputStream(file));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	List<Integer> parseIntegers(String str) {
		Objects.requireNonNull(str);
		List<Integer> ret = new ArrayList<Integer>();
		StringTokenizer stok = new StringTokenizer(str, ",");
		while (stok.hasMoreTokens()) {
			try {
				ret.add(Integer.parseInt(stok.nextToken()));
			} catch (NumberFormatException e) {
			}
		}
		return ret;
	}

	void createBayesianNetwork(OutputStream output) {
		Objects.requireNonNull(output);
		List<Integer> listOfParents = parseIntegers(getView().getListOfParents());
		BayesianNetworkCreatorConfiguration conf = new BayesianNetworkCreatorConfigurationImpl();
		conf.setDependencies(listOfParents);
		conf.setOutput(output);
		BayesianNetworkCreatorCore core = new BayesianNetworkCreatorCore();
		core.run(conf);
	}

	void executeActionSaveBayesianNetwork() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			try {
				createBayesianNetwork(new FileOutputStream(file));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public AnnotatorConfiguration getModel() {
		return getView().getModel();
	}

	/**
	 * Returns the OWL ontology manager.
	 * 
	 * @return the OWL ontology manager
	 */
	public OWLOntologyManager getOWLOntologyManager() {
		return this.owlOntologyManager;
	}

	/**
	 * Returns the view.
	 * 
	 * @return the view
	 */
	public TestMakerView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	private void init() {
		getView().addButtonSelectInputOntologyFileListener(this, actionSelectInputOntologyFile);
		getView().addButtonSelectOutputOntologyFileListener(this, actionSelectOutputOntologyFile);
		getView().addButtonSaveBayesianNetworkListener(this, actionSaveBayesianNetwork);
	}

}
