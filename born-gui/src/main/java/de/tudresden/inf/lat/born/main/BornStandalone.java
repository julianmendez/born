package de.tudresden.inf.lat.born.main;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.semanticweb.owlapi.apibinding.OWLManager;

/**
 * This is used to start the system without Protege.
 * 
 * @author Julian Mendez
 */
public class BornStandalone {

	public static void main(String[] args) {
		(new BornStandalone()).run();
	}

	public void run() {
		BornStarter starter = new BornStarter(
				OWLManager.createOWLOntologyManager());
		JFrame frame = new JFrame();
		frame.add(starter.getPanel().getView());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(new Dimension(1024, 400));
		frame.setVisible(true);
	}

}
