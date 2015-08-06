package de.tudresden.inf.lat.born.ui;

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

/**
 * This is the main panel.
 * 
 * @author Julian Mendez
 */
public class BornView extends JPanel {

	private static final long serialVersionUID = 8739938469381396848L;

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
	private JTextField textQueryFile = new JTextField();
	private JTextField textOutputFile = new JTextField();
	private final BornModel model;

	public BornView(BornModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		add(createMainPanel());
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

	public void addTextFieldQueryFileListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textQueryFile.addActionListener(listener);
		this.textQueryFile.setActionCommand(actionCommand);
	}

	public void addTextFieldOutputFileListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textOutputFile.addActionListener(listener);
		this.textOutputFile.setActionCommand(actionCommand);
	}

	private JPanel createMainPanel() {
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

		JLabel labelQueryFile = new JLabel(Message.textQuery);
		labelQueryFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(labelQueryFile);

		this.textQueryFile.setToolTipText(Message.tooltipTextFieldQueryFile);
		this.textQueryFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textQueryFile);

		largePanel.add(Box.createVerticalStrut(gap));

		JLabel labelOutputFile = new JLabel(Message.textOutput);
		labelOutputFile.setHorizontalAlignment(SwingConstants.CENTER);
		largePanel.add(labelOutputFile);

		this.textOutputFile.setToolTipText(Message.tooltipTextFieldOutputFile);
		this.textOutputFile.setMinimumSize(new Dimension(width, height));
		this.textOutputFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textOutputFile);

		ret.add(largePanel);

		return ret;
	}

	public BornModel getModel() {
		return this.model;
	}

	public void setButtonLoadEnabled(boolean b) {
		this.buttonSelectInputOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonComputeInference.setEnabled(b);
	}

	public void update() {
		this.textInputOntologyFile.setText(getModel().getInputOntologyFile()
				.getAbsolutePath());
		this.textBayesianNetworkFile.setText(getModel()
				.getBayesianNetworkFileName());
	}

}
