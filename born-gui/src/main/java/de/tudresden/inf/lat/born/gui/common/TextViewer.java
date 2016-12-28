package de.tudresden.inf.lat.born.gui.common;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				view.resizeContent(getContentPane().getSize());
			}
		});
	}

	/**
	 * Returns the view.
	 * 
	 * @return the view
	 */
	public TextViewerView getView() {
		return this.view;
	}

}
