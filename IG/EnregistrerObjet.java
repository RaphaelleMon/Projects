package IG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import elements3D.Objet3D;
import exception.FichierExisteDejaException;
import rayTracing.RayTracing;
import rayTracing.Scene;
import utilitaire.ObjectSaver;

public class EnregistrerObjet extends FenetreEnregistrer {
	
	final private Objet3D objet;
	
	public void actionenregistrer() {
		enregistrer.addActionListener(new ActionEnregistrer());
	}
	
	/**
	 * @param nom : nom que l'utilisateur choisit de donner ï¿½ les objets.
	 * @param nrt : 
	 */
	public EnregistrerObjet(Objet3D nobjet) {
		super("Enregistrer Objet");
		this.objet = nobjet;
		this.pack();
		this.actionenregistrer();
	}
	
	/**
	 * Classe interne d'instance qui reprï¿½sente l'observateur pour le bouton d'enregistrement
	 * de les objets.
	 */
	public class ActionEnregistrer implements ActionListener {
		public void actionPerformed (ActionEvent ev) {
			try {
				ObjectSaver.saveObjet(objet,text.getText());
				System.out.printf("Objet3D sauvegardé \n");
				dispose();
			} catch (FichierExisteDejaException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Fichier déjà existant","Veuillez entrer un autre nom !", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
