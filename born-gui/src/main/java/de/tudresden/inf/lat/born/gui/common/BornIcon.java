package de.tudresden.inf.lat.born.gui.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * This manages the icons.
 * 
 * @author Julian Mendez
 *
 */
public interface BornIcon {

	Color BACKGROUND_COLOR = new Color(0, 102, 153);
	Color FOREGROUND_COLOR = new Color(255, 255, 255);
	Color TEXT_BACKGROUND_COLOR = new Color(255, 255, 240);

	Font TITLE_FONT = new Font("Courier New", Font.BOLD, 13);

	String PATH_BACKGROUND = "images/background.png";

	String PATH_OPEN_FILE = "icons/openfolder.png";
	String PATH_SAVE_FILE = "icons/floppydisk.png";
	String PATH_VIEW_FILE = "icons/magnifyingglass.png";
	String PATH_RUN = "icons/play.png";
	String PATH_REFRESH = "icons/refresh.png";
	String PATH_BACK = "icons/back.png";

	int DEFAULT_ICON_SIZE = 24;

	BufferedImage BACKGROUND = ImageManager.createImage(PATH_BACKGROUND).get();

	ImageIcon OPEN_FILE = ImageManager.createIcon(PATH_OPEN_FILE, DEFAULT_ICON_SIZE).get();
	ImageIcon SAVE_FILE = ImageManager.createIcon(PATH_SAVE_FILE, DEFAULT_ICON_SIZE).get();
	ImageIcon VIEW_FILE = ImageManager.createIcon(PATH_VIEW_FILE, DEFAULT_ICON_SIZE).get();
	ImageIcon RUN = ImageManager.createIcon(PATH_RUN, DEFAULT_ICON_SIZE).get();
	ImageIcon REFRESH = ImageManager.createIcon(PATH_REFRESH, DEFAULT_ICON_SIZE).get();
	ImageIcon BACK = ImageManager.createIcon(PATH_BACK, DEFAULT_ICON_SIZE).get();

}
