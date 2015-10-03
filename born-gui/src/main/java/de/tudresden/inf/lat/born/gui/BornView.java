package de.tudresden.inf.lat.born.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.tudresden.inf.lat.born.gui.examplemaker.ExampleMakerView;
import de.tudresden.inf.lat.born.gui.experimentmaker.ExperimentMakerView;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerView;

/**
 * This is the main panel.
 * 
 * @author Julian Mendez
 */
public class BornView extends JPanel {

	private static final long serialVersionUID = -446334246631666908L;

	private final BornModel model;
	private final ProcessorView processorView;
	private final TestMakerView testMakerView;
	private final ExperimentMakerView experimentMakerView;
	private final ExampleMakerView exampleMakerView;

	public BornView(BornModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		this.processorView = new ProcessorView(
				this.model.getProcessorConfiguration());
		this.testMakerView = new TestMakerView(
				this.model.getAnnotatorConfiguration());
		this.experimentMakerView = new ExperimentMakerView(
				this.model.getSplitterConfiguration());
		this.exampleMakerView = new ExampleMakerView(
				this.model.getBayesianNetworkCreatorConfiguration());

		add(createPanel());
	}

	private JPanel createPanel() {

		JPanel ret = new JPanel();
		ret.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(1024, 768));

		ImageIcon icon = null;

		tabbedPane.addTab("Processor", icon, this.processorView,
				"processes an ontology to get the subsumption value");
		tabbedPane.addTab("Test", icon, this.testMakerView,
				"annotates an ontology");
		tabbedPane.addTab("Experiments", icon, this.experimentMakerView,
				"creates and runs experiments");

		add(tabbedPane);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		return ret;
	}

	public BornModel getModel() {
		return this.model;
	}

	public ProcessorView getProcessorView() {
		return this.processorView;
	}

	public TestMakerView getTestMakerView() {
		return this.testMakerView;
	}

	public ExperimentMakerView getExperimentMakerView() {
		return this.experimentMakerView;
	}

	public ExampleMakerView getExampleMakerView() {
		return this.exampleMakerView;
	}

	public void update() {
		this.testMakerView.update();
		this.experimentMakerView.update();
		this.exampleMakerView.update();
	}

}
