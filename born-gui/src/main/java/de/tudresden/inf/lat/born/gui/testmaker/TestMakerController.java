package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.common.ResourceUtil;
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
	private static final String actionSelectInputBayesianNetworkFile = "open input Bayesian network";
	private static final String actionSelectOutputOntologyFile = "save output ontology";
	private static final String actionSaveBayesianNetwork = "save Bayesian network";

	private final OWLOntologyManager owlOntologyManager;
	private final TestMakerView view;
	private File lastPath = null;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public TestMakerController(TestMakerView view, OWLOntologyManager ontologyManager) {
		this.view = Objects.requireNonNull(view);
		this.owlOntologyManager = Objects.requireNonNull(ontologyManager);
		init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Objects.requireNonNull(e);
		String cmd = e.getActionCommand();
		if (cmd.equals(actionSelectInputOntologyFile)) {
			executeActionSelectInputOntologyFile();
		} else if (cmd.equals(actionSelectInputBayesianNetworkFile)) {
			executeActionSelectInputBayesianNetworkFile();
		} else if (cmd.equals(actionSelectOutputOntologyFile)) {
			executeActionSelectOutputOntologyFile();
		} else if (cmd.equals(actionSaveBayesianNetwork)) {
			executeActionSaveBayesianNetwork();
		} else {
			throw new IllegalStateException();
		}
	}

	void executeActionSelectInputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setInputOntologyFile(file.getAbsolutePath());
			getView().updateInputOntologyFile();
			this.lastPath = file.getParentFile();
		}
	}

	void executeActionSelectInputBayesianNetworkFile() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setInputBayesianNetworkFile(file.getAbsolutePath());
			getView().updateInputBayesianNetworkFile();
			this.lastPath = file.getParentFile();
		}
	}

	void annotateOntology(OutputStream output) {
		Objects.requireNonNull(output);
		getView().updateThreshold();
		AnnotatorConfiguration conf = getModel();
		conf.setOutputOntology(output);
		AnnotatorCore core = new AnnotatorCore();
		core.run(conf);
	}

	void executeActionSelectOutputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showSaveDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			try {
				annotateOntology(new FileOutputStream(ResourceUtil.ensurePath(file)));
				this.lastPath = file.getParentFile();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	List<Integer> parseIntegers(String str) {
		Objects.requireNonNull(str);
		List<Integer> ret = new ArrayList<>();
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
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showSaveDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			try {
				createBayesianNetwork(new FileOutputStream(ResourceUtil.ensurePath(file)));
				this.lastPath = file.getParentFile();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
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
		getView().addButtonSelectInputBayesianNetworkFileListener(this, actionSelectInputBayesianNetworkFile);
		getView().addButtonSelectOutputOntologyFileListener(this, actionSelectOutputOntologyFile);
		getView().addButtonSaveBayesianNetworkListener(this, actionSaveBayesianNetwork);
	}

}
