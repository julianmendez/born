package de.tudresden.inf.lat.born.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.tudresden.inf.lat.born.gui.common.BornIcon;
import de.tudresden.inf.lat.born.gui.common.Message;
import de.tudresden.inf.lat.born.gui.experimentrunner.ExperimentRunnerPanel;
import de.tudresden.inf.lat.born.gui.experimentrunner.ExperimentRunnerView;
import de.tudresden.inf.lat.born.gui.processor.ProcessorPanel;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerPanel;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerView;

/**
 * This is the view of the graphical user interface.
 * 
 * @author Julian Mendez
 */
public class BornPanel extends JPanel implements BornView {

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
	public BornPanel(BornModel model) {
		Objects.requireNonNull(model);
		this.model = model;
		this.processorView = new ProcessorPanel(this.model.getProcessorConfiguration());
		this.testMakerView = new TestMakerPanel(this.model.getAnnotatorConfiguration());
		this.experimentMakerView = new ExperimentRunnerPanel(this.model.getMultiProcessorConfiguration());

		createPanel();
	}

	/**
	 * Returns the panel.
	 * 
	 * @return the panel
	 */
	void createPanel() {
		setLayout(new GridLayout(1, 1));

		setBackground(BornIcon.BACKGROUND_COLOR);

		ImageIcon icon = null;
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(1024, 768));
		tabbedPane.addTab(Message.tabProcessor, icon, this.processorView.getPanel(), Message.tooltipProcessor);
		tabbedPane.addTab(Message.tabTestMaker, icon, this.testMakerView.getPanel(), Message.tooltipTestMaker);
		tabbedPane.addTab(Message.tabExperminentMaker, icon, this.experimentMakerView.getPanel(),
				Message.tooltipExperimentMaker);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane);

	}

	@Override
	public BornModel getModel() {
		return this.model;
	}

	@Override
	public ProcessorView getProcessorView() {
		return this.processorView;
	}

	@Override
	public TestMakerView getTestMakerView() {
		return this.testMakerView;
	}

	@Override
	public ExperimentRunnerView getExperimentMakerView() {
		return this.experimentMakerView;
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
