package IG;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import IG.FenetreEnregistrer.ActionAnnuler;

public abstract class FenetreOuvrir extends JFrame {

	final static JButton ouvrir = new JButton("ouvrir");
	final static JButton annuler = new JButton("Annuler");
	
	final static JLabel nom = new JLabel("Nom : ");
	final static JTextField  text = new JTextField ("nom");
	
	/**
	 * Création de la fenêtre enregistrer
	 * @param titre 
	 */
	public FenetreOuvrir(String titre) {
		super("Su7 - " + titre);
		
		JPanel contenu = new JPanel();
		
		URL img = FenetreOuvrir.class.getResource("/IG/logo.png");
		ImageIcon icon = new ImageIcon(img);
		setIconImage(icon.getImage());
		
		contenu.add(nom,BorderLayout.PAGE_START);
		text.setPreferredSize(new Dimension(150, 25));
		contenu.add(text, BorderLayout.CENTER);
		
		contenu.add(ouvrir,BorderLayout.PAGE_END);
		
		annuler.addActionListener(new ActionAnnuler());
		contenu.add(annuler);
		
		this.add(contenu);
	}
	
	
	public class ActionAnnuler implements ActionListener {
		public void actionPerformed (ActionEvent ev) {
			dispose();
		}
	}
}
