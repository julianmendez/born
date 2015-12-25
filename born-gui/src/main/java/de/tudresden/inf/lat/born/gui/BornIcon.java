package de.tudresden.inf.lat.born.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

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

	public static final ImageIcon OPEN_FILE = createIcon(PATH_OPEN_FILE).get();
	public static final ImageIcon SAVE_FILE = createIcon(PATH_SAVE_FILE).get();
	public static final ImageIcon RUN = createIcon(PATH_RUN).get();
	public static final ImageIcon REFRESH = createIcon(PATH_REFRESH).get();

	/**
	 * Returns an optional containing an icon with the given size if the path is
	 * valid, or an empty optional if the size or the path is invalid.
	 * 
	 * @param path
	 *            path of icon
	 * @param size
	 *            size of icon
	 * @return an optional containing an icon with the given size if the path is
	 *         valid, or an empty optional if the size or the path is invalid
	 */
	public static Optional<ImageIcon> createIcon(String path, int size) {
		Objects.requireNonNull(path);
		Optional<ImageIcon> ret = Optional.empty();
		if (size >= 0) {
			try {
				URL url = BornIcon.class.getClassLoader().getResource(path);
				if (Objects.isNull(url)) {
					try {
						throw new IllegalArgumentException("Icon has an invalid path: '" + path + "'.");
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}

				} else {
					BufferedImage img = ImageIO.read(url);
					ret = Optional.of(new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH)));

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * Returns an optional containing an icon created with the default size for
	 * the given path, or an empty optional if the size or the path is invalid.
	 * 
	 * @param path
	 *            path of icon
	 * @return an optional containing an icon created with the default size for
	 *         the given path, or an empty optional if the size or the path is
	 *         invalid
	 */
	public static Optional<ImageIcon> createIcon(String path) {
		Objects.requireNonNull(path);
		return createIcon(path, DEFAULT_ICON_SIZE);
	}

}
