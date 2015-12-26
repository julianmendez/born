package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;

/**
 * This is the panel for the test maker. This class contains a model for the
 * annotator ({@link AnnotatorConfiguration}), but not for the Bayesian network
 * creator ({@link BayesianNetworkCreatorConfiguration}).
 * 
 * @author Julian Mendez
 */
public class TestMakerPanel extends JPanel implements TestMakerView {

	static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! --> ";

	private static final long serialVersionUID = -7460256750941145085L;

	private JButton buttonSelectInputOntologyFile = new JButton();
	private JButton buttonSaveOntologyFile = new JButton();
	private JButton buttonSaveBayesianNetwork = new JButton();

	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textThreshold = new JTextField();
	private JTextField textMaxNumberOfVars = new JTextField();
	private JTextField textListOfParents = new JTextField();

	private final JLabel lblInputOntology = new JLabel("input ontology");
	private final JLabel lblThreshold = new JLabel("threshold [0,1]");
	private final JLabel lblMaxNumberOfVars = new JLabel("maximum number of variables");
	private final JLabel lblListOfParents = new JLabel("list of parents (e.g. \"1,1,2,3,5,8\")");

	private final AnnotatorConfiguration model;

	/**
	 * Constructs a new test maker view.
	 * 
	 * @param model
	 *            model
	 */
	public TestMakerPanel(AnnotatorConfiguration model) {
		Objects.requireNonNull(model);
		this.model = model;
		createPanel();
	}

	/**
	 * Adds an action listener to the button to select the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addButtonSelectInputOntologyFileListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonSelectInputOntologyFile.addActionListener(listener);
		buttonSelectInputOntologyFile.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the button to select the output ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addButtonSelectOutputOntologyFileListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonSaveOntologyFile.addActionListener(listener);
		buttonSaveOntologyFile.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the button to save the Bayesian network.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addButtonSaveBayesianNetworkListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonSaveBayesianNetwork.addActionListener(listener);
		buttonSaveBayesianNetwork.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the text field of the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addTextFieldInputOntologyFileListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		textInputOntologyFile.addActionListener(listener);
		textInputOntologyFile.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the text field of the list of parents.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addTextFieldListOfParentsListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		textListOfParents.addActionListener(listener);
		textListOfParents.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the text field of the threshold.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addTextFieldThresholdListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		textThreshold.addActionListener(listener);
		textThreshold.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the text field of the maximum number of
	 * variables.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	@Override
	public void addTextFieldMaxNumberOfVarListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		textMaxNumberOfVars.addActionListener(listener);
		textMaxNumberOfVars.setActionCommand(actionCommand);
	}

	/**
	 * Creates the panel.
	 */
	void createPanel() {

		setLayout(null);

		buttonSelectInputOntologyFile.setIcon(BornIcon.OPEN_FILE);
		buttonSelectInputOntologyFile.setBounds(215, 101, 50, 26);
		buttonSelectInputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSelectInputOntologyFile);

		textInputOntologyFile.setBounds(277, 101, 688, 28);
		textInputOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		textInputOntologyFile.setEditable(false);
		add(textInputOntologyFile);

		buttonSaveOntologyFile.setIcon(BornIcon.SAVE_FILE);
		buttonSaveOntologyFile.setBounds(215, 265, 50, 26);
		buttonSaveOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSaveOntologyFile);

		textThreshold.setBounds(277, 186, 259, 28);
		textThreshold.setToolTipText(Message.tooltipTextFieldThreshold);
		textThreshold.setAlignmentX(LEFT_ALIGNMENT);
		add(textThreshold);

		textMaxNumberOfVars.setBounds(586, 186, 259, 28);
		textMaxNumberOfVars.setToolTipText(Message.tooltipTextFieldMaxNumberOfVar);
		textMaxNumberOfVars.setAlignmentX(LEFT_ALIGNMENT);
		add(textMaxNumberOfVars);

		lblInputOntology.setBounds(287, 139, 120, 15);
		add(lblInputOntology);

		lblThreshold.setBounds(277, 222, 259, 15);
		add(lblThreshold);

		lblMaxNumberOfVars.setBounds(596, 222, 249, 15);
		add(lblMaxNumberOfVars);

		lblListOfParents.setBounds(277, 473, 328, 15);
		add(lblListOfParents);

		buttonSaveBayesianNetwork.setIcon(BornIcon.SAVE_FILE);
		buttonSaveBayesianNetwork.setBounds(215, 505, 50, 26);
		buttonSaveBayesianNetwork.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSaveBayesianNetwork);

		textListOfParents.setBounds(277, 425, 688, 28);
		textListOfParents.setToolTipText(Message.tooltipTextFieldThreshold);
		textListOfParents.setAlignmentX(LEFT_ALIGNMENT);
		add(textListOfParents);

	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	@Override
	public AnnotatorConfiguration getModel() {
		return model;
	}

	/**
	 * Changes the state of the load button to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the button is enabled
	 */
	@Override
	public void setButtonLoadEnabled(boolean status) {
		buttonSelectInputOntologyFile.setEnabled(status);
	}

	/**
	 * Changes the state of the compute button to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the button is enabled
	 */
	@Override
	public void setButtonComputeInferenceEnabled(boolean status) {
		buttonSaveBayesianNetwork.setEnabled(status);
	}

	/**
	 * Updates the ontology file.
	 */
	@Override
	public void updateOntologyFile() {
		String inputOntologyFile = getInputOntologyFile();
		if (!Objects.isNull(inputOntologyFile) && !inputOntologyFile.trim().isEmpty()) {
			try {
				getModel().setInputOntology(new FileInputStream(inputOntologyFile));
			} catch (IOException e) {
				setInputOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Updates the threshold.
	 */
	@Override
	public void updateThreshold() {
		try {
			double th = Double.parseDouble(textThreshold.getText());
			getModel().setThreshold(th);
		} catch (NumberFormatException e) {
		}
	}

	/**
	 * Updates the maximum number of variables.
	 */
	@Override
	public void updateMaxNumberOfVars() {
		try {
			int maxNumberOfVars = Integer.parseInt(textMaxNumberOfVars.getText());
			getModel().setMaxNumberOfVars(maxNumberOfVars);
		} catch (NumberFormatException e) {
		}
	}

	/**
	 * Returns the input ontology file.
	 * 
	 * @return the input ontology file
	 */
	@Override
	public String getInputOntologyFile() {
		return textInputOntologyFile.getText();
	}

	/**
	 * Sets the input ontology file.
	 * 
	 * @param fileName
	 *            file name
	 */
	@Override
	public void setInputOntologyFile(String fileName) {
		Objects.requireNonNull(fileName);
		textInputOntologyFile.setText(fileName);
	}

	/**
	 * Returns the threshold.
	 * 
	 * @return the threshold
	 */
	@Override
	public String getThreshold() {
		return textThreshold.getText();
	}

	/**
	 * Sets the threshold.
	 * 
	 * @param threshold
	 *            the threshold
	 */
	@Override
	public void setThreshold(String threshold) {
		Objects.requireNonNull(threshold);
		textThreshold.setText(threshold);
	}

	/**
	 * Returns the maximum number of variables.
	 * 
	 * @return the maximum number of variables
	 */
	@Override
	public String getMaxNumberOfVar() {
		return textMaxNumberOfVars.getText();
	}

	/**
	 * Sets the maximum number of variables.
	 * 
	 * @param maxNumberOfVars
	 *            the maximum number of variables
	 */
	@Override
	public void setMaxNumberOfVar(String maxNumberOfVars) {
		Objects.requireNonNull(maxNumberOfVars);
		textMaxNumberOfVars.setText(maxNumberOfVars);
	}

	/**
	 * Returns the list of parents.
	 * 
	 * @return the list of parents
	 */
	@Override
	public String getListOfParents() {
		return textListOfParents.getText();
	}

	/**
	 * Sets the list of parents.
	 * 
	 * @param listOfParents
	 *            the list of parents
	 */
	@Override
	public void setListOfParents(String listOfParents) {
		Objects.requireNonNull(listOfParents);
		textListOfParents.setText(listOfParents);
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

}
