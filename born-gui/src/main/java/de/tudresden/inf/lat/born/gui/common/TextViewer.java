package de.tudresden.inf.lat.born.gui.common;

import javax.swing.JFrame;

/**
 * An object of this class is a text viewer that only contains a text viewer
 * panel.
 * 
 * @author Julian Mendez
 *
 */
public class TextViewer extends JFrame {

	private static final long serialVersionUID = -3095629699793538821L;

	private TextViewerView view;

	/**
	 * Constructs a new text viewer.
	 */
	public TextViewer() {
		TextViewerPanel view = new TextViewerPanel();
		add(view);
		this.view = view;
	}

	public TextViewerView getView() {
		return this.view;
	}

}
