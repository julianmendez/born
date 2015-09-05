package de.tudresden.inf.lat.born.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This manages the icons.
 * 
 * @author Julian Mendez
 *
 */
public class BornIcon {

	public static final String OPEN_FILE_PATH = "icons/openfolder.png";
	public static final String SAVE_FILE_PATH = "icons/floppydisk.png";
	public static final String RUN_PATH = "icons/play.png";

	public static final int DEFAULT_ICON_SIZE = 24;

	public static final ImageIcon OPEN_FILE = createIcon(OPEN_FILE_PATH, DEFAULT_ICON_SIZE);
	public static final ImageIcon SAVE_FILE = createIcon(SAVE_FILE_PATH, DEFAULT_ICON_SIZE);
	public static final ImageIcon RUN = createIcon(RUN_PATH, DEFAULT_ICON_SIZE);

	/**
	 * Creates an icon with the given size. If the path is invalid, this method
	 * returns <code>null</code>.
	 * 
	 * @param path
	 *            path of icon
	 * @param size
	 *            size of icon
	 * @return an icon with the given size, or <code>null</code> if the path is
	 *         invalid
	 */
	public static ImageIcon createIcon(String path, int size) {
		ImageIcon ret = null;
		try {
			URL url = BornIcon.class.getClassLoader().getResource(path);
			if (url == null) {
				try {
					throw new IllegalArgumentException("Icon has an invalid path: '" + path + "'.");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

			} else {
				BufferedImage img = ImageIO.read(url);
				ret = new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
