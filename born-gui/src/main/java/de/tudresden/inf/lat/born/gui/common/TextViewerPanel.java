package de.tudresden.inf.lat.born.gui.common;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class TextViewerPanel extends JPanel implements TextViewerView {

	private static final long serialVersionUID = -486147305343218431L;

	private final JTextArea textContent = new JTextArea();
	private final JScrollPane scrollContent = new JScrollPane();

	private String model;

	public TextViewerPanel() {
		this("");
	}

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

	void createPanel() {
		setLayout(null);

		setBackground(BornIcon.BACKGROUND_COLOR);

		textContent.setBackground(new Color(255, 255, 240));
		textContent.setToolTipText("Content");
		textContent.setAlignmentX(LEFT_ALIGNMENT);

		scrollContent.setBounds(32, 32, 400, 400);
		scrollContent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollContent.setViewportView(textContent);
		add(scrollContent);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BornIcon.BACKGROUND, 0, 0, null);
	}

}
