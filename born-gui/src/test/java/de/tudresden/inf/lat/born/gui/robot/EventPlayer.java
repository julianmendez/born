package de.tudresden.inf.lat.born.gui.robot;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class EventPlayer extends Observable implements AWTEventListener {

	public static final char QUIT_KEY = 'q';

	static final String COMMENT_PREFIX = "#";

	static final int MOUSE_PRESSED_TIME = 200;

	private boolean running = true;

	public EventPlayer() {
	}

	List<Event> readEventList(Reader reader) {
		List<Event> ret = new ArrayList<>();
		try {
			BufferedReader input = new BufferedReader(reader);
			for (String line = input.readLine(); line != null; line = input.readLine()) {
				if (line.trim().isEmpty() || line.startsWith(COMMENT_PREFIX)) {
					// skip
				} else {
					ret.add(new Event(line));
				}
			}
			input.close();
			return ret;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	Robot getRobot() {
		Robot robot = null;
		if (robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				throw new RuntimeException(e);
			}
		}
		return robot;
	}

	void runEvent(Event record) {
		Robot robot = getRobot();
		if (record.getId() == MouseEvent.MOUSE_PRESSED) {
			moveMouse(robot, record.getX(), record.getY());
			pressMouse(robot);
		} else if (record.getId() == MouseEvent.MOUSE_MOVED) {
			moveMouse(robot, record.getX(), record.getY());
		} else {
			// skip
		}
	}

	void pressMouse(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(MOUSE_PRESSED_TIME);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	void moveMouse(Robot robot, int x, int y) {
		robot.mouseMove(x, y);
	}

	public void run(List<Event> events) {
		Robot robot = getRobot();
		events.forEach(event -> {
			if (this.running) {
				runEvent(event);
				robot.delay(1000);
			}
		});
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent e = (KeyEvent) event;
			if ((e.getKeyChar() == Character.toLowerCase(QUIT_KEY))
					|| (e.getKeyChar() == Character.toUpperCase(QUIT_KEY))) {
				finish();
			}
		}
	}

	public void finish() {
		this.running = false;
		setChanged();
		notifyObservers();
	}

}
