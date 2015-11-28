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

	public static final String PATH_OPEN_FILE = "icons/openfolder.png";
	public static final String PATH_SAVE_FILE = "icons/floppydisk.png";
	public static final String PATH_RUN = "icons/play.png";
	public static final String PATH_REFRESH = "icons/refresh.png";

	public static final int DEFAULT_ICON_SIZE = 24;

	public static final ImageIcon OPEN_FILE = createIcon(PATH_OPEN_FILE);
	public static final ImageIcon SAVE_FILE = createIcon(PATH_SAVE_FILE);
	public static final ImageIcon RUN = createIcon(PATH_RUN);
	public static final ImageIcon REFRESH = createIcon(PATH_REFRESH);

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

	/**
	 * Returns an icon created with the default size for the given path.
	 * 
	 * @param path
	 *            of icon
	 * @return an icon created with the default size for the given path
	 */
	public static ImageIcon createIcon(String path) {
		return createIcon(path, DEFAULT_ICON_SIZE);
	}

}
