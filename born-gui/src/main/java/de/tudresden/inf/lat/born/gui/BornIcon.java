package de.tudresden.inf.lat.born.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BornIcon {

	public static final String OPEN_FILE_PATH = "icons/openfolder.png";
	public static final String SAVE_FILE_PATH = "icons/floppydisk.png";
	public static final String RUN_PATH = "icons/play.png";

	public static final int DEFAULT_ICON_SIZE = 24;

	public static final ImageIcon OPEN_FILE = createIcon(OPEN_FILE_PATH, DEFAULT_ICON_SIZE);
	public static final ImageIcon SAVE_FILE = createIcon(SAVE_FILE_PATH, DEFAULT_ICON_SIZE);
	public static final ImageIcon RUN = createIcon(RUN_PATH, DEFAULT_ICON_SIZE);

	public static ImageIcon createIcon(String name, int size) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(BornIcon.class.getClassLoader().getResource(name));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Image dimg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}

}
