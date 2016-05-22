package de.tudresden.inf.lat.born.gui.common;

/**
 * This interface models a file viewer.
 * 
 * @author Julian Mendez
 *
 */
public interface TextViewerView extends JPanelView {

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	String getModel();

	/**
	 * Sets the model. A <code>null</code> is taken as an empty string.
	 * 
	 * @param model
	 *            the model
	 */
	void setModel(String model);

}
