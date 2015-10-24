package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;

/**
 * This is the panel for the annotator.
 * 
 * @author Julian Mendez
 */
public class TestMakerView extends JPanel {

	public static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! --> ";

	private static final long serialVersionUID = -7460256750941145085L;

	private JButton buttonSelectInputOntologyFile = new JButton();
	private JButton buttonSaveOntologyFile = new JButton();
	private JButton buttonSaveBayesianNetwork = new JButton();

	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textThreshold = new JTextField();
	private JTextField textMaxNumberOfVar = new JTextField();
	private JTextField textListOfParents = new JTextField();

	private final JLabel lblInputOntology = new JLabel("input ontology");
	private final JLabel lblThreshold = new JLabel("threshold [0,1]");
	private final JLabel lblNumberOfVariables = new JLabel("number of vars");
	private final JLabel lblListOfParents = new JLabel("list of parents (e.g. \"1,1,2,3,5,8\")");

	private final AnnotatorConfiguration model;

	public TestMakerView(AnnotatorConfiguration model, BayesianNetworkCreatorConfiguration model1) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		// TODO add second model
		// FIXME model1 is not considered

		this.model = model;
		createPanel();
	}

	public void addButtonSelectInputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonSelectInputOntologyFile.addActionListener(listener);
		buttonSelectInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonSelectOutputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonSaveOntologyFile.addActionListener(listener);
		buttonSaveOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonSaveBayesianNetworkListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonSaveBayesianNetwork.addActionListener(listener);
		buttonSaveBayesianNetwork.setActionCommand(actionCommand);
	}

	public void addTextFieldInputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		textInputOntologyFile.addActionListener(listener);
		textInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addTextFieldListOfParentsListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		textListOfParents.addActionListener(listener);
		textListOfParents.setActionCommand(actionCommand);
	}

	public void addTextFieldThresholdListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		textThreshold.addActionListener(listener);
		textThreshold.setActionCommand(actionCommand);
	}

	public void addTextFieldMaxNumberOfVarListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		textMaxNumberOfVar.addActionListener(listener);
		textMaxNumberOfVar.setActionCommand(actionCommand);
	}

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

		textMaxNumberOfVar.setBounds(586, 186, 259, 28);
		textMaxNumberOfVar.setToolTipText(Message.tooltipTextFieldMaxNumberOfVar);
		textMaxNumberOfVar.setAlignmentX(LEFT_ALIGNMENT);
		add(textMaxNumberOfVar);

		lblInputOntology.setBounds(287, 139, 120, 15);
		add(lblInputOntology);

		lblThreshold.setBounds(291, 222, 126, 15);
		add(lblThreshold);

		lblNumberOfVariables.setBounds(596, 222, 124, 15);
		add(lblNumberOfVariables);

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

	public AnnotatorConfiguration getModel() {
		return model;
	}

	public void setButtonLoadEnabled(boolean b) {
		buttonSelectInputOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		buttonSaveBayesianNetwork.setEnabled(b);
	}

	void updateOntologyFile() {
		String inputOntologyFile = getInputOntologyFile();
		if (inputOntologyFile != null && !inputOntologyFile.trim().isEmpty()) {
			try {
				getModel().setInputOntology(new FileInputStream(inputOntologyFile));
			} catch (IOException e) {
				setInputOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	public void update() {
	}

	public String getInputOntologyFile() {
		return textInputOntologyFile.getText();
	}

	public void setInputOntologyFile(String fileName) {
		textInputOntologyFile.setText(fileName);
	}

	public String getThreshold() {
		return textThreshold.getText();
	}

	public void setThreshold(String fileName) {
		textThreshold.setText(fileName);
	}

	public String getMaxNumberOfVar() {
		return textMaxNumberOfVar.getText();
	}

	public void setMaxNumberOfVar(String fileName) {
		textMaxNumberOfVar.setText(fileName);
	}

	public String getListOfParents() {
		return textListOfParents.getText();
	}

	public void setListOfParents(String fileName) {
		textListOfParents.setText(fileName);
	}

}
