package de.tudresden.inf.lat.born.gui.common;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * An object of this class is the text viewer panel.
 * 
 * @author Julian Mendez
 *
 */
public class TextViewerPanel extends JPanel implements TextViewerView {

	private static final long serialVersionUID = -486147305343218431L;

	public static final int MARGIN = 8;

	private final JTextArea textContent = new JTextArea();
	private final JScrollPane scrollContent = new JScrollPane();

	private String model;

	/**
	 * Constructs a new text viewer panel.
	 */
	public TextViewerPanel() {
		this("");
	}

	/**
	 * Constructs a new text viewer panel.
	 * 
	 * @param model
	 *            model
	 */
	public TextViewerPanel(String model) {
		setModel(model);
		createPanel();
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public void setModel(String model) {
		this.model = (model == null) ? "" : model;
		textContent.setText(this.model);
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public void resizeContent(Dimension dimension) {
		int xSize = Math.max(dimension.width - 2 * MARGIN, 2 * MARGIN);
		int ySize = Math.max(dimension.height - 2 * MARGIN, 2 * MARGIN);
		scrollContent.setBounds(MARGIN, MARGIN, xSize, ySize);
	}

	void createPanel() {
		setLayout(null);

		setBackground(Color.WHITE);

		textContent.setBackground(Color.WHITE);
		textContent.setToolTipText("Content");
		textContent.setAlignmentX(LEFT_ALIGNMENT);
		textContent.setLineWrap(true);
		textContent.setWrapStyleWord(true);

		scrollContent.setBorder(null);
		scrollContent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollContent.setViewportView(textContent);
		add(scrollContent);
	}

}
