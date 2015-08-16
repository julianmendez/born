package de.tudresden.inf.lat.born.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.tudresden.inf.lat.born.gui.annotator.AnnotatorView;
import de.tudresden.inf.lat.born.gui.connector.BayesianNetworkCreatorView;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.gui.splitter.SplitterView;

/**
 * This is the main panel.
 * 
 * @author Julian Mendez
 */
public class BornView extends JPanel {

	private static final long serialVersionUID = -446334246631666908L;

	private final BornModel model;
	private final ProcessorView processorView;
	private final AnnotatorView annotatorView;
	private final SplitterView splitterView;
	private final BayesianNetworkCreatorView bayesianNetworkCreatorView;

	public BornView(BornModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		this.processorView = new ProcessorView(
				this.model.getProcessorConfiguration());
		this.annotatorView = new AnnotatorView(
				this.model.getAnnotatorConfiguration());
		this.splitterView = new SplitterView(
				this.model.getSplitterConfiguration());
		this.bayesianNetworkCreatorView = new BayesianNetworkCreatorView(
				this.model.getBayesianNetworkCreatorConfiguration());

		add(createPanel());
	}

	private JPanel createPanel() {

		JPanel ret = new JPanel();
		ret.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(600, 400));

		ImageIcon icon = null;

		tabbedPane.addTab("Processor", icon, this.processorView,
				"processes an ontology to get the subsumption value");
		tabbedPane.addTab("Annotator", icon, this.annotatorView,
				"annotates an ontology");
		tabbedPane.addTab("Splitter", icon, this.splitterView,
				"splits an ontology");
		tabbedPane.addTab("Connector", icon, this.bayesianNetworkCreatorView,
				"creates a Bayesian network");

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

	public AnnotatorView getAnnotatorView() {
		return this.annotatorView;
	}

	public SplitterView getSplitterView() {
		return this.splitterView;
	}

	public BayesianNetworkCreatorView getBayesianNetworkCreatorView() {
		return this.bayesianNetworkCreatorView;
	}

	public void update() {
		this.processorView.update();
		this.annotatorView.update();
		this.splitterView.update();
		this.bayesianNetworkCreatorView.update();
	}

}
