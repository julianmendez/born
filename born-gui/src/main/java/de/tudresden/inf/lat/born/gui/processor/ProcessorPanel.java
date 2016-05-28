package de.tudresden.inf.lat.born.gui.processor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.gui.common.BornIcon;
import de.tudresden.inf.lat.born.gui.common.FormatTool;
import de.tudresden.inf.lat.born.gui.common.Message;
import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;

/**
 * This is the panel to compute inference.
 * 
 * @author Julian Mendez
 */
public class ProcessorPanel extends JPanel implements ProcessorView {

	private static final long serialVersionUID = -3489883631448640992L;

	static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! ";

	private final FormatTool formatTool = new FormatTool();
	private final JButton buttonOntologyFile = new JButton();
	private final JButton buttonViewOntology = new JButton();
	private final JButton buttonBayesianNetworkFile = new JButton();
	private final JButton buttonViewBayesianNetwork = new JButton();
	private final JButton buttonResetCompletionRules = new JButton();
	private final JButton buttonGoToPreviousCompletionRules = new JButton();
	private final JButton buttonConsoleInput = new JButton();
	private final JButton buttonConsoleOutput = new JButton();
	private final JButton buttonComputeInference = new JButton();
	private final JButton buttonUpdateExample = new JButton();
	private final JLabel labelProgress = new JLabel("computing ...");
	private final JTextField textOntologyFile = new JTextField();
	private final JTextField textBayesianNetworkFile = new JTextField();
	private final JTextArea textCompletionRules = new JTextArea();
	private final JTextArea textConsoleInput = new JTextArea();
	private final JTextArea textConsoleOutput = new JTextArea();
	private final JScrollPane scrollCompletionRules = new JScrollPane();
	private final JScrollPane scrollConsoleInput = new JScrollPane();
	private final JScrollPane scrollConsoleOutput = new JScrollPane();
	private final JComboBox<String> comboBoxExample = new JComboBox<String>();
	private final ProcessorConfiguration model;

	public ProcessorPanel(ProcessorConfiguration model) {
		Objects.requireNonNull(model);
		this.model = model;
		setLayout(null);
		createPanel();
	}

	@Override
	public void addButtonOntologyFileListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonOntologyFile.addActionListener(listener);
		buttonOntologyFile.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonViewOntologyListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonViewOntology.addActionListener(listener);
		buttonViewOntology.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonBayesianNetworkFileListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonBayesianNetworkFile.addActionListener(listener);
		buttonBayesianNetworkFile.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonViewBayesianNetworkListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonViewBayesianNetwork.addActionListener(listener);
		buttonViewBayesianNetwork.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonResetCompletionRulesListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonResetCompletionRules.addActionListener(listener);
		buttonResetCompletionRules.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonGoToPreviousCompletionRulesListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonGoToPreviousCompletionRules.addActionListener(listener);
		buttonGoToPreviousCompletionRules.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonConsoleInputListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonConsoleInput.addActionListener(listener);
		buttonConsoleInput.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonConsoleOutputListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonConsoleOutput.addActionListener(listener);
		buttonConsoleOutput.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonComputeInferenceListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonComputeInference.addActionListener(listener);
		buttonComputeInference.setActionCommand(actionCommand);
	}

	@Override
	public void addComboBoxExampleListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		comboBoxExample.addActionListener(listener);
		comboBoxExample.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonUpdateExampleListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		buttonUpdateExample.addActionListener(listener);
		buttonUpdateExample.setActionCommand(actionCommand);
	}

	void createPanel() {

		setBackground(BornIcon.BACKGROUND_COLOR);

		// ontology

		JLabel lblOntology = new JLabel("ONTOLOGY");
		lblOntology.setFont(new Font("Courier New", Font.BOLD, 13));
		lblOntology.setForeground(new Color(255, 255, 255));
		lblOntology.setBounds(40, 48, 96, 15);
		add(lblOntology);

		buttonOntologyFile.setIcon(BornIcon.OPEN_FILE);
		buttonOntologyFile.setBounds(192, 40, 54, 28);
		buttonOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		buttonOntologyFile.setOpaque(false);
		buttonOntologyFile.setContentAreaFilled(false);
		buttonOntologyFile.setBorderPainted(false);
		add(buttonOntologyFile);

		textOntologyFile.setBackground(new Color(255, 255, 240));
		textOntologyFile.setBounds(32, 77, 400, 28);
		textOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		textOntologyFile.setEditable(false);
		add(textOntologyFile);

		// Bayesian network

		JLabel lblBayesianNetwork = new JLabel("BAYESIAN NETWORK");
		lblBayesianNetwork.setFont(new Font("Courier New", Font.BOLD, 13));
		lblBayesianNetwork.setForeground(new Color(255, 255, 255));
		lblBayesianNetwork.setBounds(40, 144, 128, 15);
		add(lblBayesianNetwork);

		buttonBayesianNetworkFile.setIcon(BornIcon.OPEN_FILE);
		buttonBayesianNetworkFile.setBounds(192, 136, 54, 28);
		buttonBayesianNetworkFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		buttonBayesianNetworkFile.setOpaque(false);
		buttonBayesianNetworkFile.setContentAreaFilled(false);
		buttonBayesianNetworkFile.setBorderPainted(false);
		add(buttonBayesianNetworkFile);

		textBayesianNetworkFile.setBackground(new Color(255, 255, 240));
		textBayesianNetworkFile.setBounds(32, 180, 400, 28);
		textBayesianNetworkFile.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);
		textBayesianNetworkFile.setEditable(false);
		add(textBayesianNetworkFile);

		// rules

		JLabel lblRules = new JLabel("RULES");
		lblRules.setFont(new Font("Courier New", Font.BOLD, 13));
		lblRules.setForeground(new Color(255, 255, 255));
		lblRules.setBounds(40, 248, 70, 15);
		add(lblRules);

		buttonResetCompletionRules.setIcon(BornIcon.REFRESH);
		buttonResetCompletionRules.setBounds(284, 240, 54, 28);
		buttonResetCompletionRules.setToolTipText(Message.tooltipButtonResetCompletionRules);
		buttonResetCompletionRules.setOpaque(false);
		buttonResetCompletionRules.setContentAreaFilled(false);
		buttonResetCompletionRules.setBorderPainted(false);
		add(buttonResetCompletionRules);

		buttonGoToPreviousCompletionRules.setIcon(BornIcon.BACK);
		buttonGoToPreviousCompletionRules.setBounds(192, 240, 54, 28);
		buttonGoToPreviousCompletionRules.setToolTipText(Message.tooltipButtonGoToPreviousCompletionRules);
		buttonGoToPreviousCompletionRules.setOpaque(false);
		buttonGoToPreviousCompletionRules.setContentAreaFilled(false);
		buttonGoToPreviousCompletionRules.setBorderPainted(false);
		add(buttonGoToPreviousCompletionRules);

		textCompletionRules.setBackground(new Color(255, 255, 240));
		textCompletionRules.setToolTipText(Message.tooltipTextFieldListOfParents);
		textCompletionRules.setAlignmentX(LEFT_ALIGNMENT);

		scrollCompletionRules.setBounds(32, 284, 400, 400);
		scrollCompletionRules.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollCompletionRules.setViewportView(textCompletionRules);
		add(scrollCompletionRules);

		// query

		JLabel lblInput = new JLabel("QUERY");
		lblInput.setFont(new Font("Courier New", Font.BOLD, 13));
		lblInput.setForeground(new Color(255, 255, 255));
		lblInput.setBounds(552, 144, 70, 15);
		add(lblInput);

		buttonConsoleInput.setIcon(BornIcon.OPEN_FILE);
		buttonConsoleInput.setBounds(704, 136, 54, 28);
		buttonConsoleInput.setToolTipText(Message.tooltipOpenInputOntologyFile);
		buttonConsoleInput.setOpaque(false);
		buttonConsoleInput.setContentAreaFilled(false);
		buttonConsoleInput.setBorderPainted(false);
		add(buttonConsoleInput);

		textConsoleInput.setBackground(new Color(255, 255, 240));
		textConsoleInput.setToolTipText(Message.tooltipTextFieldListOfParents);
		textConsoleInput.setAlignmentX(LEFT_ALIGNMENT);

		scrollConsoleInput.setBounds(544, 180, 448, 193);
		scrollConsoleInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollConsoleInput.setViewportView(textConsoleInput);
		add(scrollConsoleInput);

		// result

		JLabel lblOutput = new JLabel("RESULT");
		lblOutput.setFont(new Font("Courier New", Font.BOLD, 13));
		lblOutput.setForeground(new Color(255, 255, 255));
		lblOutput.setBounds(552, 456, 70, 15);
		add(lblOutput);

		buttonConsoleOutput.setIcon(BornIcon.SAVE_FILE);
		buttonConsoleOutput.setBounds(704, 448, 54, 28);
		buttonConsoleOutput.setToolTipText(Message.tooltipOpenInputOntologyFile);
		buttonConsoleOutput.setOpaque(false);
		buttonConsoleOutput.setContentAreaFilled(false);
		buttonConsoleOutput.setBorderPainted(false);
		add(buttonConsoleOutput);

		textConsoleOutput.setToolTipText(Message.tooltipTextFieldOutputFile);
		textConsoleOutput.setAlignmentX(LEFT_ALIGNMENT);
		textConsoleOutput.setBackground(new Color(255, 255, 240));

		scrollConsoleOutput.setBounds(544, 492, 448, 192);
		scrollConsoleOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollConsoleOutput.setViewportView(textConsoleOutput);
		add(scrollConsoleOutput);

		// execution

		buttonComputeInference.setIcon(BornIcon.RUN);
		buttonComputeInference.setBounds(796, 136, 54, 28);
		buttonComputeInference.setToolTipText(Message.tooltipComputeInference);
		buttonComputeInference.setOpaque(false);
		buttonComputeInference.setContentAreaFilled(false);
		buttonComputeInference.setBorderPainted(false);
		add(buttonComputeInference);

		labelProgress.setFont(new Font("Courier New", Font.BOLD, 13));
		labelProgress.setForeground(new Color(255, 255, 255));
		labelProgress.setBounds(796, 456, 96, 15);
		labelProgress.setVisible(false);
		add(labelProgress);

		// examples

		comboBoxExample.setBounds(796, 40, 192, 28);
		comboBoxExample.setToolTipText(Message.tooltipComboBoxExample);
		add(comboBoxExample);

		buttonUpdateExample.setIcon(BornIcon.OPEN_FILE);
		buttonUpdateExample.setBounds(704, 40, 54, 28);
		buttonUpdateExample.setToolTipText(Message.tooltipButtonUpdateExample);
		buttonUpdateExample.setOpaque(false);
		buttonUpdateExample.setContentAreaFilled(false);
		buttonUpdateExample.setBorderPainted(false);
		add(buttonUpdateExample);

		buttonViewOntology.setIcon(BornIcon.VIEW_FILE);
		buttonViewOntology.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonViewOntology.setToolTipText("view ontology file");
		buttonViewOntology.setBounds(284, 40, 54, 28);
		buttonViewOntology.setOpaque(false);
		buttonViewOntology.setContentAreaFilled(false);
		buttonViewOntology.setBorderPainted(false);
		add(buttonViewOntology);

		buttonViewBayesianNetwork.setIcon(BornIcon.VIEW_FILE);
		buttonViewBayesianNetwork.setToolTipText("view Bayesian network file");
		buttonViewBayesianNetwork.setBounds(284, 136, 54, 28);
		buttonViewBayesianNetwork.setOpaque(false);
		buttonViewBayesianNetwork.setContentAreaFilled(false);
		buttonViewBayesianNetwork.setBorderPainted(false);
		add(buttonViewBayesianNetwork);

		JLabel lblExamples = new JLabel("EXAMPLES");
		lblExamples.setFont(new Font("Courier New", Font.BOLD, 13));
		lblExamples.setForeground(new Color(255, 255, 255));
		lblExamples.setBounds(552, 48, 70, 15);
		add(lblExamples);

	}

	@Override
	public ProcessorConfiguration getModel() {
		return model;
	}

	@Override
	public String getOntologyFile() {
		return textOntologyFile.getText().trim();
	}

	@Override
	public void setOntologyFile(String fileName) {
		Objects.requireNonNull(fileName);
		textOntologyFile.setText(formatTool.formatText(fileName));
	}

	@Override
	public String getBayesianNetworkFile() {
		return textBayesianNetworkFile.getText().trim();
	}

	@Override
	public void setBayesianNetworkFile(String fileName) {
		Objects.requireNonNull(fileName);
		textBayesianNetworkFile.setText(formatTool.formatText(fileName));
	}

	@Override
	public String getCompletionRules() {
		return formatTool.trimText(textCompletionRules.getText());
	}

	@Override
	public void setCompletionRules(String text) {
		Objects.requireNonNull(text);
		textCompletionRules.setText(formatTool.formatText(text));
	}

	@Override
	public String getConsoleInput() {
		return formatTool.trimText(textConsoleInput.getText());
	}

	@Override
	public void setConsoleInput(String text) {
		Objects.requireNonNull(text);
		textConsoleInput.setText(formatTool.formatText(text));
	}

	@Override
	public void readConsoleInput(String consoleInputFile) {
		Objects.requireNonNull(consoleInputFile);
		if (Objects.nonNull(consoleInputFile) && !consoleInputFile.trim().isEmpty()) {
			try {
				String text = ProcessorConfigurationImpl.read(new FileReader(consoleInputFile));
				this.textConsoleInput.setText(formatTool.formatText(text));
				updateQuery();
			} catch (IOException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void writeConsoleOutput(String consoleOutputFile) {
		Objects.requireNonNull(consoleOutputFile);
		if (Objects.nonNull(consoleOutputFile) && !consoleOutputFile.trim().isEmpty()) {
			try {
				String text = formatTool.trimText(this.textConsoleOutput.getText());
				ProcessorConfigurationImpl.write(new StringReader(text), new FileWriter(consoleOutputFile));
			} catch (IOException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	@Override
	public String getConsoleOutput() {
		return formatTool.trimText(textConsoleOutput.getText());
	}

	@Override
	public void setConsoleOutput(String text) {
		Objects.requireNonNull(text);
		textConsoleOutput.setText(formatTool.formatText(text));
	}

	@Override
	public void setButtonLoadEnabled(boolean b) {
		buttonOntologyFile.setEnabled(b);
	}

	@Override
	public void setButtonGoToPreviousCompletionRulesEnabled(boolean b) {
		buttonGoToPreviousCompletionRules.setEnabled(b);
	}

	@Override
	public void setButtonComputeInferenceEnabled(boolean b) {
		buttonComputeInference.setEnabled(b);
	}

	@Override
	public void setComboBoxExampleEnabled(boolean b) {
		comboBoxExample.setEnabled(b);
	}

	@Override
	public int getComboBoxExampleIndex() {
		return comboBoxExample.getSelectedIndex();
	}

	@Override
	public void updateCompletionRules() {
		String completionRules = formatTool.trimText(textCompletionRules.getText());
		if (Objects.nonNull(completionRules) && !completionRules.trim().isEmpty()) {
			getModel().setAdditionalCompletionRules(completionRules);
		}
	}

	@Override
	public void updateOntologyFile() {
		String inputOntologyFile = getOntologyFile();
		if (Objects.nonNull(inputOntologyFile) && !inputOntologyFile.trim().isEmpty()) {
			try {
				getModel().setOntology(ProcessorConfigurationImpl.readOntology(new FileInputStream(inputOntologyFile)));
			} catch (IOException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			} catch (OWLOntologyCreationException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void updateBayesianNetworkFile() {
		String bayesianNetworkFile = getBayesianNetworkFile();
		if (Objects.nonNull(bayesianNetworkFile) && !bayesianNetworkFile.trim().isEmpty()) {
			try {
				getModel().setBayesianNetwork(ProcessorConfigurationImpl.read(new FileReader(bayesianNetworkFile)));
			} catch (IOException e) {
				setBayesianNetworkFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void updateQuery() {
		String query = formatTool.trimText(textConsoleInput.getText());
		if (Objects.nonNull(query) && !query.trim().isEmpty()) {
			getModel().setQuery(query);
		}
	}

	@Override
	public void setResult(String result) {
		Objects.requireNonNull(result);
		textConsoleOutput.setText(formatTool.formatText(result));
	}

	@Override
	public void setComputing(boolean status) {
		labelProgress.setVisible(status);
	}

	@Override
	public void setButtonsEnabled(boolean status) {
		buttonOntologyFile.setEnabled(status);
		buttonViewOntology.setEnabled(status);
		buttonBayesianNetworkFile.setEnabled(status);
		buttonViewBayesianNetwork.setEnabled(status);
		buttonResetCompletionRules.setEnabled(status);
		buttonGoToPreviousCompletionRules.setEnabled(status);
		buttonConsoleInput.setEnabled(status);
		buttonConsoleOutput.setEnabled(status);
		buttonComputeInference.setEnabled(status);
		buttonUpdateExample.setEnabled(status);
		comboBoxExample.setEnabled(status);
	}

	@Override
	public void addExamples(Collection<ExampleConfiguration> examples) {
		Objects.requireNonNull(examples);
		examples.forEach(
				configuration -> comboBoxExample.addItem(formatTool.formatText(configuration.getOntologyName())));
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BornIcon.BACKGROUND, 0, 0, null);
	}

}
