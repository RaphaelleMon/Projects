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

import IG.EnregistrerScene.ActionEnregistrer;
import rayTracing.RayTracing;
import rayTracing.Scene;
import utilitaire.ObjectSaver;

public class OuvrirScene extends FenetreOuvrir {

    private RayTracing rt;
	private ListeObjets listeO;
	private ListeLumieres listeL;
	private JList<Objet> listObjets;
	private JList<Lumieres> listLumieres;
	
	public void actionouvrir() {
		ouvrir.addActionListener(new ActionOuvrir());
	}
	
	/**
	 * @param nom : nom que l'utilisateur choisit de donner à l'image.
	 * @param nrt : 
	 */
	public OuvrirScene(RayTracing nrt, ListeObjets o, ListeLumieres l, JList<Lumieres> jl,JList<Objet> jo) {
		super("Ouvrir Scene");
		this.rt = nrt;
		this.listeL = l;
		this.listeO = o;
		this.listLumieres = jl;
		this.listObjets = jo;
		this.pack();
		this.actionouvrir();
	}
	
	/**
	 * Classe interne d'instance qui représente l'observateur pour le bouton d'enregistrement
	 * de l'image.
	 */
	public class ActionOuvrir implements ActionListener {
		public void actionPerformed (ActionEvent ev) {
			
			Scene scene = ObjectSaver.openScene(text.getText());	
			if (scene == null) {
				JOptionPane.showMessageDialog(new JFrame(), "Le fichier demandé n'existe pas ou une erreur s'est produite lors de l'ouverture","Erreur ouverture Scène", JOptionPane.ERROR_MESSAGE);
			} else { 
				rt.setScene(scene);
				listeO.reinitialiser(rt.getScene().getObjets());
				listeL.reinitialiser(rt.getScene().getLumieres());
				listLumieres.updateUI();
				listObjets.updateUI();
				System.out.printf("Scène ouverte\n");
				dispose();
			}
		}
	}

}
