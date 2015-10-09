package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionListener;

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

	private static final long serialVersionUID = -7460256750941145085L;

	private JButton buttonSelectInputOntologyFile = new JButton();
	private JButton buttonSelectOutputOntologyFile = new JButton();
	private JButton buttonAnnotateOntology = new JButton();

	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textOutputOntologyFile = new JTextField();
	private JTextField textThreshold = new JTextField();
	private JTextField textMaxNumberOfVar = new JTextField();
	private final AnnotatorConfiguration model;
	private final JLabel lblNewLabel = new JLabel("input ontology");
	private final JLabel lblOutputOntology = new JLabel("output ontology");
	private final JLabel lblThr = new JLabel("threshold [0,1]");
	private final JLabel lblVariables = new JLabel("number of vars");

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

		this.buttonSelectInputOntologyFile.addActionListener(listener);
		this.buttonSelectInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonSelectOutputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectOutputOntologyFile.addActionListener(listener);
		this.buttonSelectOutputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonComputeInferenceListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonAnnotateOntology.addActionListener(listener);
		this.buttonAnnotateOntology.setActionCommand(actionCommand);
	}

	public void addTextFieldInputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textInputOntologyFile.addActionListener(listener);
		this.textInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addTextFieldBayesianNetworkFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textOutputOntologyFile.addActionListener(listener);
		this.textOutputOntologyFile.setActionCommand(actionCommand);
	}

	public void addTextFieldThresholdListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textThreshold.addActionListener(listener);
		this.textThreshold.setActionCommand(actionCommand);
	}

	public void addTextFieldMaxNumberOfVarListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textMaxNumberOfVar.addActionListener(listener);
		this.textMaxNumberOfVar.setActionCommand(actionCommand);
	}

	void createPanel() {

		setLayout(null);

		buttonSelectInputOntologyFile.setIcon(BornIcon.OPEN_FILE);
		buttonSelectInputOntologyFile.setBounds(140, 101, 50, 26);
		buttonSelectInputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSelectInputOntologyFile);

		textInputOntologyFile.setBounds(277, 101, 196, 26);
		textInputOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		add(textInputOntologyFile);

		buttonSelectOutputOntologyFile.setIcon(BornIcon.SAVE_FILE);
		buttonSelectOutputOntologyFile.setBounds(140, 184, 50, 26);
		buttonSelectOutputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSelectOutputOntologyFile);

		textOutputOntologyFile.setBounds(277, 186, 196, 26);
		textOutputOntologyFile.setToolTipText(Message.tooltipTextFieldOutputOntologyFile);
		textOutputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		add(textOutputOntologyFile);

		textThreshold.setBounds(277, 265, 196, 24);
		textThreshold.setToolTipText(Message.tooltipTextFieldThreshold);
		textThreshold.setAlignmentX(LEFT_ALIGNMENT);
		add(textThreshold);

		textMaxNumberOfVar.setBounds(282, 339, 191, 24);
		textMaxNumberOfVar.setToolTipText(Message.tooltipTextFieldMaxNumberOfVar);
		textMaxNumberOfVar.setAlignmentX(LEFT_ALIGNMENT);
		add(textMaxNumberOfVar);

		buttonAnnotateOntology.setIcon(BornIcon.RUN);
		buttonAnnotateOntology.setBounds(140, 419, 50, 26);
		buttonAnnotateOntology.setToolTipText(Message.tooltipAnnotatorRun);
		add(buttonAnnotateOntology);
		lblNewLabel.setBounds(140, 144, 120, 15);

		add(lblNewLabel);
		lblOutputOntology.setBounds(140, 222, 120, 15);

		add(lblOutputOntology);
		lblThr.setBounds(140, 274, 126, 15);

		add(lblThr);
		lblVariables.setBounds(140, 348, 124, 15);

		add(lblVariables);

	}

	public AnnotatorConfiguration getModel() {
		return this.model;
	}

	public void setButtonLoadEnabled(boolean b) {
		this.buttonSelectInputOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonAnnotateOntology.setEnabled(b);
	}

	public void update() {
	}

}
