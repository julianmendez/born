package de.tudresden.inf.lat.born.gui.examplemaker;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;

/**
 * This is the panel to create Bayesian networks.
 * 
 * @author Julian Mendez
 */
public class ExampleMakerView extends JPanel {

	private static final long serialVersionUID = -3286416871432282437L;

	private JButton buttonSelectInputOntologyFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconOpenFile)));
	private JButton buttonComputeInference = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconRun)));
	private JTextField textListOfParents = new JTextField();
	private JTextField textOutputBayesianNetwork = new JTextField();
	private final BayesianNetworkCreatorConfiguration model;

	public ExampleMakerView(BayesianNetworkCreatorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		createPanel();
	}

	public void addTextFieldListOfParentsListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textListOfParents.addActionListener(listener);
		this.textListOfParents.setActionCommand(actionCommand);
	}

	public void addTextFieldOutputFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textOutputBayesianNetwork.addActionListener(listener);
		this.textOutputBayesianNetwork.setActionCommand(actionCommand);
	}

	void createPanel() {
		setLayout(null);
		buttonComputeInference.setBounds(33, 175, 50, 26);

		buttonComputeInference.setToolTipText(Message.tooltipComputeInference);
		add(buttonComputeInference);

		JLabel labelListOfParents = new JLabel("parents (e.g. 3,5,8)");
		labelListOfParents.setBounds(12, 22, 145, 26);
		labelListOfParents.setAlignmentX(LEFT_ALIGNMENT);
		add(labelListOfParents);
		textListOfParents.setBounds(165, 26, 201, 38);

		textListOfParents.setToolTipText(Message.tooltipTextFieldListOfParents);
		textListOfParents.setAlignmentX(LEFT_ALIGNMENT);
		add(textListOfParents);

		JLabel labelOutputBayesianNetwork = new JLabel("output");
		labelOutputBayesianNetwork.setBounds(0, 100, 98, 26);
		labelOutputBayesianNetwork.setHorizontalAlignment(SwingConstants.CENTER);
		add(labelOutputBayesianNetwork);
		textOutputBayesianNetwork.setBounds(165, 95, 201, 38);

		textOutputBayesianNetwork.setToolTipText(Message.tooltipTextFieldOutputBayesianNetwork);
		textOutputBayesianNetwork.setAlignmentX(LEFT_ALIGNMENT);
		add(textOutputBayesianNetwork);

	}

	public BayesianNetworkCreatorConfiguration getModel() {
		return this.model;
	}

	public void setButtonLoadEnabled(boolean b) {
		this.buttonSelectInputOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonComputeInference.setEnabled(b);
	}

	public void update() {
	}

}
