package de.tudresden.inf.lat.born.gui.robot;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.semanticweb.owlapi.apibinding.OWLManager;

import de.tudresden.inf.lat.born.main.BornStarter;

public class BornEventRecorder implements Observer {

	public static final String FILE_NAME = "events.txt";
	private JFrame frame;

	public BornEventRecorder() {
	}

	public static void main(String[] args) throws IOException {
		Objects.requireNonNull(args);
		BornEventRecorder instance = new BornEventRecorder();
		instance.startRecorder();
		instance.run();
	}

	public void startRecorder() throws IOException {
		EventRecorder eventListener = new EventRecorder(new FileWriter(FILE_NAME));
		eventListener.addObserver(this);
		Toolkit.getDefaultToolkit().addAWTEventListener(eventListener,
				AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
	}

	public void run() {
		BornStarter starter = new BornStarter(OWLManager.createOWLOntologyManager());
		frame = new JFrame();
		JPanel panel = starter.getPanel().getView().getPanel();
		panel.getComponents();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(new Dimension(1024, 800));
		frame.setVisible(true);
	}

	void finish() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void update(Observable observed, Object arg) {
		observed.deleteObservers();
		finish();
	}

}
