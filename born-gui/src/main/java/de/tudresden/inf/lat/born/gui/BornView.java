package de.tudresden.inf.lat.born.gui;

import de.tudresden.inf.lat.born.gui.common.JPanelView;
import de.tudresden.inf.lat.born.gui.experimentrunner.ExperimentRunnerView;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerView;

/**
 * This interface models the view of the main panel of the graphical user
 * interface.
 * 
 * @author Julian Mendez
 *
 */
public interface BornView extends JPanelView {

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

}
