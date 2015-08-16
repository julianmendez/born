package de.tudresden.inf.lat.born.gui.splitter;

import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.splitter.SplitterConfiguration;

/**
 * This is the panel to split annotations in an ontology.
 * 
 * @author Julian Mendez
 */
public class SplitterView extends JPanel {

	private static final long serialVersionUID = -7105856642163451498L;

	private JButton buttonSelectInputOntologyFile = new JButton(new ImageIcon(
			this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));

	private JButton buttonSelectBayesianNetworkFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));
	private JButton buttonComputeInference = new JButton(
			Message.textComputeInference);
	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textBayesianNetworkFile = new JTextField();
	private final SplitterConfiguration model;

	public SplitterView(SplitterConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		add(createPanel());
	}

	public void addButtonSelectInputOntologyFileListener(
			ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectInputOntologyFile.addActionListener(listener);
		this.buttonSelectInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonSelectBayesianNetworkFileListener(
			ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectBayesianNetworkFile.addActionListener(listener);
		this.buttonSelectBayesianNetworkFile.setActionCommand(actionCommand);
	}

	public void addButtonComputeInferenceListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonComputeInference.addActionListener(listener);
		this.buttonComputeInference.setActionCommand(actionCommand);
	}

	public void addTextFieldInputOntologyFileListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textInputOntologyFile.addActionListener(listener);
		this.textInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addTextFieldBayesianNetworkFileListener(
			ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textBayesianNetworkFile.addActionListener(listener);
		this.textBayesianNetworkFile.setActionCommand(actionCommand);
	}

	private JPanel createPanel() {

		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));

		int gap = 4;

		JPanel smallPanel = new JPanel();
		smallPanel.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonComputeInference
				.setToolTipText(Message.tooltipComputeInference);
		smallPanel.add(this.buttonComputeInference);
		ret.add(smallPanel);

		ret.add(Box.createVerticalStrut(gap));

		JPanel largePanel = new JPanel();
		largePanel.setAlignmentX(CENTER_ALIGNMENT);
		largePanel.setLayout(new BoxLayout(largePanel, BoxLayout.Y_AXIS));

		JLabel labelInputOntologyFile = new JLabel(
				Message.textInputOntologyFile);
		labelInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);

		largePanel.add(labelInputOntologyFile);

		this.buttonSelectInputOntologyFile
				.setToolTipText(Message.tooltipOpenInputOntologyFile);
		largePanel.add(this.buttonSelectInputOntologyFile);

		this.textInputOntologyFile
				.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		this.textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textInputOntologyFile);

		largePanel.add(Box.createVerticalStrut(gap));

		JLabel labelBayesianNetworkFile = new JLabel(
				Message.textBayesianNetworkFile);
		labelBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);

		largePanel.add(labelBayesianNetworkFile);

		this.buttonSelectBayesianNetworkFile
				.setToolTipText(Message.tooltipOpenInputOntologyFile);

		largePanel.add(this.buttonSelectBayesianNetworkFile);

		this.textBayesianNetworkFile
				.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		this.textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textBayesianNetworkFile);

		largePanel.add(Box.createVerticalStrut(gap));

		ret.add(largePanel);

		return ret;
	}

	public SplitterConfiguration getModel() {
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
