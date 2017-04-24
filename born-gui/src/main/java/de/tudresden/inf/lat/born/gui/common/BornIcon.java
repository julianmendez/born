package de.tudresden.inf.lat.born.gui.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * This interface contains constants for the icons and other images used in the
 * application.
 * 
 * @author Julian Mendez
 *
 */
public interface BornIcon {

	/** Background color */
	Color BACKGROUND_COLOR = new Color(0, 102, 153);

	/** Foreground color */
	Color FOREGROUND_COLOR = new Color(255, 255, 255);

	/** Background color of text area */
	Color TEXT_BACKGROUND_COLOR = new Color(255, 255, 240);

	/** Title font */
	Font TITLE_FONT = new Font("Courier New", Font.BOLD, 13);

	/** Path for background image */
	String PATH_BACKGROUND = "images/background.png";

	/** Path for Open File icon */
	String PATH_OPEN_FILE = "icons/openfolder.png";

	/** Path for Save File icon */
	String PATH_SAVE_FILE = "icons/floppydisk.png";

	/** Path for View File icon */
	String PATH_VIEW_FILE = "icons/magnifyingglass.png";

	/** Path for Run icon */
	String PATH_RUN = "icons/play.png";

	/** Path for Refresh icon */
	String PATH_REFRESH = "icons/refresh.png";

	/** Path for Go Back icon */
	String PATH_BACK = "icons/back.png";

	/** Default icon size */
	int DEFAULT_ICON_SIZE = 24;

	/** Background image */
	BufferedImage BACKGROUND = ImageManager.createImage(PATH_BACKGROUND).get();

	/** Open File icon */
	ImageIcon OPEN_FILE = ImageManager.createIcon(PATH_OPEN_FILE, DEFAULT_ICON_SIZE).get();

	/** Save File icon */
	ImageIcon SAVE_FILE = ImageManager.createIcon(PATH_SAVE_FILE, DEFAULT_ICON_SIZE).get();

	/** View File icon */
	ImageIcon VIEW_FILE = ImageManager.createIcon(PATH_VIEW_FILE, DEFAULT_ICON_SIZE).get();

	/** Run icon */
	ImageIcon RUN = ImageManager.createIcon(PATH_RUN, DEFAULT_ICON_SIZE).get();

	/** Refresh icon */
	ImageIcon REFRESH = ImageManager.createIcon(PATH_REFRESH, DEFAULT_ICON_SIZE).get();

	/** Go Back icon */
	ImageIcon BACK = ImageManager.createIcon(PATH_BACK, DEFAULT_ICON_SIZE).get();

}
