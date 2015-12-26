package de.tudresden.inf.lat.born.gui;

import javax.swing.JPanel;

import de.tudresden.inf.lat.born.gui.experimentrunner.ExperimentRunnerView;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerView;

public interface BornView {

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	BornModel getModel();

	/**
	 * Returns the processor view.
	 * 
	 * @return the processor view
	 */
	ProcessorView getProcessorView();

	/**
	 * Returns the test maker view.
	 * 
	 * @return the test maker view
	 */
	TestMakerView getTestMakerView();

	/**
	 * Returns the experiment maker view.
	 * 
	 * @return the experiment maker view
	 */
	ExperimentRunnerView getExperimentMakerView();

	/**
	 * Returns the panel.
	 * 
	 * @return the panel
	 */
	JPanel getPanel();

}
