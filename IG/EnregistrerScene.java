package IG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exception.FichierExisteDejaException;
import rayTracing.RayTracing;
import utilitaire.ObjectSaver;

public class EnregistrerScene extends FenetreEnregistrer {
	
	final private RayTracing rt;
	
	public void actionenregistrer() {
		enregistrer.addActionListener(new ActionEnregistrer());
	}
	
	/**
	 * @param nom : nom que l'utilisateur choisit de donner à l'image.
	 * @param nrt : 
	 */
	public EnregistrerScene(RayTracing nrt) {
		super("Enregistrer Scene");
		this.rt = nrt;
		this.pack();
		this.actionenregistrer();
	}
	
	/**
	 * Classe interne d'instance qui représente l'observateur pour le bouton d'enregistrement
	 * de l'image.
	 */
	public class ActionEnregistrer implements ActionListener {
		public void actionPerformed (ActionEvent ev) {
			try {
				ObjectSaver.saveScene(rt.getScene(),text.getText());
				System.out.printf("Image sauvegardée \n");
				dispose();
			} catch (FichierExisteDejaException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Fichier déjà existant","Veuillez entrer un autre nom !", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
