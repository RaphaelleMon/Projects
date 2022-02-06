package IG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import IG.EnregistrerObjet.ActionEnregistrer;
import elements3D.Objet3D;
import exception.Objet3DHorsSceneException;
import rayTracing.RayTracing;
import rayTracing.Scene;
import utilitaire.ObjectSaver;

public class OuvrirObjet extends FenetreOuvrir {

	private Objet3D objet;
	
	public void actionouvrir() {
		ouvrir.addActionListener(new ActionOuvrir());
	}
	
	/**
	 * @param nom : nom que l'utilisateur choisit de donner ï¿½ les objets.
	 * @param nrt : 
	 */
	public OuvrirObjet() {
		super("Ouvrir Objet");
		this.pack();
		this.actionouvrir();
	}
	
	public Objet3D getObjet() {
		return this.objet;
	}
	
	/**
	 * Classe interne d'instance qui représente l'observateur pour le bouton d'enregistrement
	 * de les objets.
	 */
	public class ActionOuvrir implements ActionListener {
		public void actionPerformed (ActionEvent ev) {
			
			objet = ObjectSaver.openObjet(text.getText());	
			if (objet == null) {
				JOptionPane.showMessageDialog(new JFrame(), "Le fichier demandée n'existe pas ou une erreur s'est produite lors de l'ouverture","Erreur ouverture Objet", JOptionPane.ERROR_MESSAGE);
				
			} else { 
				System.out.printf("Objet ouverte\n");
				dispose();
			}
		}
	}

}
