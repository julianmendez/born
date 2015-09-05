package de.tudresden.inf.lat.born.gui.examplemaker;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;

/**
 * This is the panel to create Bayesian networks.
 * 
 * @author Julian Mendez
 */
public class ExampleMakerView extends JPanel {

	private static final long serialVersionUID = -3286416871432282437L;

	private JButton buttonLoadExample = new JButton();
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

		JLabel labelListOfParents = new JLabel("parents (e.g. 3,5,8)");
		labelListOfParents.setBounds(12, 26, 145, 26);
		labelListOfParents.setAlignmentX(LEFT_ALIGNMENT);
		add(labelListOfParents);
		
		textListOfParents.setBounds(165, 26, 201, 28);
		textListOfParents.setToolTipText(Message.tooltipTextFieldListOfParents);
		textListOfParents.setAlignmentX(LEFT_ALIGNMENT);
		add(textListOfParents);

		JLabel labelOutputBayesianNetwork = new JLabel("output");
		labelOutputBayesianNetwork.setBounds(12, 95, 98, 28);
		labelOutputBayesianNetwork.setHorizontalAlignment(SwingConstants.LEFT);
		add(labelOutputBayesianNetwork);
		
		textOutputBayesianNetwork.setBounds(165, 95, 201, 28);
		textOutputBayesianNetwork.setToolTipText(Message.tooltipTextFieldOutputBayesianNetwork);
		textOutputBayesianNetwork.setAlignmentX(LEFT_ALIGNMENT);
		add(textOutputBayesianNetwork);

		buttonLoadExample.setIcon(BornIcon.RUN);
		buttonLoadExample.setBounds(25, 175, 50, 28);
		buttonLoadExample.setToolTipText(Message.tooltipComputeInference);
		add(buttonLoadExample);
	}

	public BayesianNetworkCreatorConfiguration getModel() {
		return this.model;
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonLoadExample.setEnabled(b);
	}

	public void update() {
	}

}
