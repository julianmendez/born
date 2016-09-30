package de.tudresden.inf.lat.born.gui.robot;

import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

public class Event {

	private long time;
	private int id;
	private int x;
	private int y;

	static final String SPACE = " ";

	public Event(MouseEvent e) {
		this.time = e.getWhen();
		this.id = e.getID();
		this.x = e.getXOnScreen();
		this.y = e.getYOnScreen();

	}

	public Event(String line) {
		StringTokenizer stok = new StringTokenizer(line);
		this.time = Long.parseLong(stok.nextToken());
		this.id = Integer.parseInt(stok.nextToken());
		this.x = Integer.parseInt(stok.nextToken());
		this.y = Integer.parseInt(stok.nextToken());
	}

	public long getTime() {
		return this.time;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getId() {
		return this.id;
	}

	public String asString() {
		return this.time + SPACE + this.id + SPACE + this.x + SPACE + this.y;
	}

	@Override
	public int hashCode() {
		return ((int) this.time) + 0x1F * (this.id + 0x1F * (this.x + 0x1F * this.y));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Event)) {
			return false;
		} else {
			Event other = (Event) obj;
			return getTime() == other.getTime() && getId() == other.getId() && getX() == other.getX()
					&& getY() == other.getY();
		}
	}

	@Override
	public String toString() {
		return asString();
	}

}
