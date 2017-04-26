package de.tudresden.inf.lat.born.gui.robot;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.semanticweb.owlapi.apibinding.OWLManager;

import de.tudresden.inf.lat.born.main.BornStarter;

/**
 * This is used to start the graphical user interface and run tests. AssertJ
 * should be also considered to extend these tests.
 * 
 * @author Julian Mendez
 */

public class BornEventPlayer implements Observer {

	public static final String FILE_NAME = "src/test/resources/events.txt";
	
	private JFrame frame;

	/**
	 * Starts the application from the command line. The parameters are ignored.
	 * 
	 * @param args
	 *            parameters (which are ignored)
	 * 
	 */
	public static void main(String[] args) {
		Objects.requireNonNull(args);
		BornEventPlayer instance = new BornEventPlayer();
		BornStarter starter = new BornStarter(OWLManager.createOWLOntologyManager());
		instance.run(starter);
		instance.runTest(starter);
	}

	/**
	 * Starts the graphical user interface.
	 * 
	 * @param starter
	 *            BORN starter
	 */
	public void run(BornStarter starter) {
		frame = new JFrame();
		frame.add(starter.getPanel().getView().getPanel());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(new Dimension(1024, 800));
		frame.setVisible(true);
	}

	public void runTest(BornStarter starter) {
		starter.getPanel().getProcessorController().getView().setConsoleOutput(
				"Hi!\nThis is an automated test.\nYou can stop this test at any time by pressing 'Q'");

		EventPlayer runner = new EventPlayer();
		runner.addObserver(this);
		Toolkit.getDefaultToolkit().addAWTEventListener(runner, AWTEvent.KEY_EVENT_MASK);
		try {
			FileReader reader = new FileReader(FILE_NAME);
			List<Event> records = runner.readEventList(reader);
			runner.run(records);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		finish();
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
