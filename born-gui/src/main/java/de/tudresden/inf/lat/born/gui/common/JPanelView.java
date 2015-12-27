package de.tudresden.inf.lat.born.gui.common;

import javax.swing.JPanel;

/**
 * 
 * This interface models a view that is or contains a {@link JPanel}.
 * 
 * @author Julian Mendez
 *
 */
@FunctionalInterface
public interface JPanelView {

	/**
	 * Returns the {@link JPanel}.
	 * 
	 * @return the {@link JPanel}
	 */
	JPanel getPanel();

}
