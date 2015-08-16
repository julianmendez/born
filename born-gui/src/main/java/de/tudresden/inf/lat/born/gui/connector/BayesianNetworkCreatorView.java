package de.tudresden.inf.lat.born.gui.connector;

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
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;

/**
 * This is the panel to create Bayesian networks.
 * 
 * @author Julian Mendez
 */
public class BayesianNetworkCreatorView extends JPanel {

	private static final long serialVersionUID = -3286416871432282437L;

	private JButton buttonSelectInputOntologyFile = new JButton(new ImageIcon(
			this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));
	private JButton buttonComputeInference = new JButton(
			Message.textComputeInference);
	private JTextField textListOfParents = new JTextField();
	private JTextField textOutputFile = new JTextField();
	private final BayesianNetworkCreatorConfiguration model;

	public BayesianNetworkCreatorView(BayesianNetworkCreatorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		add(createPanel());
	}

	public void addTextFieldListOfParentsListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textListOfParents.addActionListener(listener);
		this.textListOfParents.setActionCommand(actionCommand);
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

		JLabel labelQueryFile = new JLabel(Message.textQuery);
		labelQueryFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(labelQueryFile);

		this.textListOfParents
				.setToolTipText(Message.tooltipTextFieldListOfParents);
		this.textListOfParents.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textListOfParents);

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
