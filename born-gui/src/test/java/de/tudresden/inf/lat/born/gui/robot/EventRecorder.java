package de.tudresden.inf.lat.born.gui.robot;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Observable;

public class EventRecorder extends Observable implements AWTEventListener {

	public static final char QUIT_KEY = 'q';

	private boolean running = true;
	private BufferedWriter writer;

	public EventRecorder(Writer writer) {
		this.writer = new BufferedWriter(writer);
	}

	void add(Event record) {
		if (this.running) {
			try {
				this.writer.write(record.asString());
				this.writer.newLine();
				this.writer.flush();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	public boolean isRecording() {
		return this.running;
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		if (event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent) event;
			if (e.getID() == MouseEvent.MOUSE_PRESSED || e.getID() == MouseEvent.MOUSE_RELEASED
					|| e.getID() == MouseEvent.MOUSE_MOVED) {
				Event r = new Event(e);
				add(r);
			}
		}
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
