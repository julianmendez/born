package de.tudresden.inf.lat.born.main;

import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JFrame;

import org.semanticweb.owlapi.apibinding.OWLManager;

/**
 * This is used to start the graphical user interface.
 * 
 * @author Julian Mendez
 */
public class BornStandalone {

	/**
	 * Starts the application from the command line. The parameters are ignored.
	 * 
	 * @param args
	 *            parameters (which are ignored)
	 */
	public static void main(String[] args) {
		Objects.requireNonNull(args);
		(new BornStandalone()).run();
	}

	/**
	 * Starts the graphical user interface.
	 */
	public void run() {
		BornStarter starter = new BornStarter(OWLManager.createOWLOntologyManager());
		JFrame frame = new JFrame();
		frame.add(starter.getPanel().getView().getPanel());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(new Dimension(1024, 720));
		frame.setVisible(true);
	}

}
