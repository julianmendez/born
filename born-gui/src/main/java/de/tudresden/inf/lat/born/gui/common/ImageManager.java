package de.tudresden.inf.lat.born.gui.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * An object of this class manages images.
 * 
 * @author Julian Mendez
 *
 */
public class ImageManager {

	/**
	 * Returns an optional containing an image if the path is valid, or an empty
	 * optional if the path is invalid.
	 * 
	 * @param path
	 *            path of image
	 * @return an optional containing an image if the path is valid, or an empty
	 *         optional if the path is invalid
	 */
	public static Optional<BufferedImage> createImage(String path) {
		Objects.requireNonNull(path);
		Optional<BufferedImage> ret = Optional.empty();
		try {
			URL url = BornIcon.class.getClassLoader().getResource(path);
			if (Objects.isNull(url)) {
				try {
					throw new IllegalArgumentException("Image has an invalid path: '" + path + "'.");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

			} else {
				BufferedImage image = ImageIO.read(url);
				ret = Optional.of(image);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

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
			Optional<BufferedImage> optImage = createImage(path);
			if (optImage.isPresent()) {
				BufferedImage image = optImage.get();
				ret = Optional.of(new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
			}
		}
		return ret;
	}

}
