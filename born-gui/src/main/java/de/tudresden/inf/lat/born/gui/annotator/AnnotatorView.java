package de.tudresden.inf.lat.born.gui.annotator;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * This is the panel for the annotator.
 * 
 * @author Julian Mendez
 */
public class AnnotatorView extends JPanel {

	private static final long serialVersionUID = -7460256750941145085L;

	private JButton buttonSelectInputOntologyFile = new JButton(new ImageIcon(
			this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));

	private JButton buttonSelectBayesianNetworkFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));
	private JButton buttonComputeInference = new JButton(
			Message.textComputeInference);
	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textOutputOntologyFile = new JTextField();
	private JTextField textThreshold = new JTextField();
	private JTextField textMaxNumberOfVar = new JTextField();
	private final AnnotatorConfiguration model;

	public AnnotatorView(AnnotatorConfiguration model) {
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

		this.textOutputOntologyFile.addActionListener(listener);
		this.textOutputOntologyFile.setActionCommand(actionCommand);
	}

	public void addTextFieldThresholdListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textThreshold.addActionListener(listener);
		this.textThreshold.setActionCommand(actionCommand);
	}

	public void addTextFieldMaxNumberOfVarListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textMaxNumberOfVar.addActionListener(listener);
		this.textMaxNumberOfVar.setActionCommand(actionCommand);
	}

	private JPanel createPanel() {

		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));

		int width = 280;
		int height = 28;
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
				Message.textOutputOntolgoyFile);
		labelBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);

		largePanel.add(labelBayesianNetworkFile);

		this.textOutputOntologyFile
				.setToolTipText(Message.tooltipTextFieldOutputOntologyFile);
		this.textOutputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textOutputOntologyFile);

		largePanel.add(Box.createVerticalStrut(gap));

		JLabel labelQueryFile = new JLabel(Message.textThreshold);
		labelQueryFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(labelQueryFile);

		this.textThreshold.setToolTipText(Message.tooltipTextFieldThreshold);
		this.textThreshold.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textThreshold);

		largePanel.add(Box.createVerticalStrut(gap));

		JLabel labelOutputFile = new JLabel(Message.textMaxNumberOfVar);
		labelOutputFile.setHorizontalAlignment(SwingConstants.CENTER);
		largePanel.add(labelOutputFile);

		this.textMaxNumberOfVar
				.setToolTipText(Message.tooltipTextFieldMaxNumberOfVar);
		this.textMaxNumberOfVar.setMinimumSize(new Dimension(width, height));
		this.textMaxNumberOfVar.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textMaxNumberOfVar);

		ret.add(largePanel);

		return ret;
	}

	public AnnotatorConfiguration getModel() {
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
