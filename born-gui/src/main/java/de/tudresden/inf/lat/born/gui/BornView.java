package de.tudresden.inf.lat.born.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.tudresden.inf.lat.born.gui.experimentrunner.ExperimentRunnerView;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerView;

/**
 * This is the view of the graphical user interface.
 * 
 * @author Julian Mendez
 */
public class BornView extends JPanel {

	private static final long serialVersionUID = -446334246631666908L;

	private final BornModel model;
	private final ProcessorView processorView;
	private final TestMakerView testMakerView;
	private final ExperimentRunnerView experimentMakerView;

	/**
	 * Constructs a new view.
	 * 
	 * @param model
	 *            model
	 */
	public BornView(BornModel model) {
		Objects.requireNonNull(model);
		this.model = model;
		this.processorView = new ProcessorView(this.model.getProcessorConfiguration());
		this.testMakerView = new TestMakerView(this.model.getAnnotatorConfiguration());
		this.experimentMakerView = new ExperimentRunnerView(this.model.getMultiProcessorConfiguration());

		add(createPanel());
	}

	/**
	 * Returns the panel.
	 * 
	 * @return the panel
	 */
	JPanel createPanel() {

		JPanel ret = new JPanel();
		ret.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(1024, 768));

		ImageIcon icon = null;

		tabbedPane.addTab(Message.tabProcessor, icon, this.processorView, Message.tooltipProcessor);
		tabbedPane.addTab(Message.tabTestMaker, icon, this.testMakerView, Message.tooltipTestMaker);
		tabbedPane.addTab(Message.tabExperminentMaker, icon, this.experimentMakerView, Message.tooltipExperimentMaker);

		add(tabbedPane);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		return ret;
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public BornModel getModel() {
		return this.model;
	}

	/**
	 * Returns the processor view.
	 * 
	 * @return the processor view
	 */
	public ProcessorView getProcessorView() {
		return this.processorView;
	}

	/**
	 * Returns the test maker view.
	 * 
	 * @return the test maker view
	 */
	public TestMakerView getTestMakerView() {
		return this.testMakerView;
	}

	/**
	 * Returns the experiment maker view.
	 * 
	 * @return the experiment maker view
	 */
	public ExperimentRunnerView getExperimentMakerView() {
		return this.experimentMakerView;
	}

}
