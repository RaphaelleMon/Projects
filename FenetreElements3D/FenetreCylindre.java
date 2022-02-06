package FenetreElements3D;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import elements3D.Cylindre;
import elements3D.Cube;
import elements3D.Objet3D;
import elements3D.Sphere;
import exception.NomVideException;
import utilitaire.Point;
import utilitaire.Vecteur;

import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;

import IG.FenetrePrincipale;

import java.awt.Font;

public class FenetreCylindre extends FenetreObjet3D {

	private JSpinner x;
	private JSpinner y;
	private JSpinner z;
	private JSpinner dx;
	private JSpinner dy;
	private JSpinner dz;
	private double px,py,pz;
	private double vx,vy,vz;
	private String name;
	private double r;
	private double h;
	private JTextField nom;
	private JSpinner rayon;
	private JSpinner hauteur;
	private Cylindre cylindre;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FenetreCylindre dialog = new FenetreCylindre(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetreCylindre(Objet3D objet) {
		
		super("Paramètres du Cylindre");
		setBounds(100, 100, 448, 294);
		
		if(objet!=null) {
			this.name = objet.getNom();
		}
		
		if (objet!= null && objet instanceof Cylindre) {
			cylindre = (Cylindre) objet;
			r = cylindre.getRayonbase();
			
			Point cb = cylindre.getOriginebase();
			px = cb.getX();
			py = cb.getY();
			pz = cb.getZ();		
			
			Vecteur vh = cylindre.getVectdir();
			vx = vh.getX();
			vy = vh.getY();
			vz = vh.getZ();
			
			h = cylindre.getHauteur();
			
		} else {
			r = 0.0;
			
			px = 0.0;
			py = 0.0;
			pz = 0.0;	
			
			vx = 0.0;
			vy = 0.0;
			vz = 0.0;	
			
			h = 0.0;
			
			this.name = "nouveauCylindre";
		}
		
		
		{
			JLabel lblNom = new JLabel("Nom");
			lblNom.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblNom.setBounds(143, 13, 64, 14);
			contentPanel.add(lblNom);
		}
		{
			nom = new JTextField(name);
			nom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			nom.setBounds(217, 10, 96, 20);
			contentPanel.add(nom);
			nom.setColumns(10);
		}
		{
			JLabel lblRayon = new JLabel("Rayon");
			lblRayon.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblRayon.setBounds(143, 51, 62, 14);
			contentPanel.add(lblRayon);
		}
		{
			rayon = new JSpinner();
			rayon.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			rayon.setBounds(217, 49, 56, 20);
			rayon.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			rayon.setValue(r);
			contentPanel.add(rayon);
		}
		{
			JLabel lblHauteur = new JLabel("Hauteur");
			lblHauteur.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblHauteur.setBounds(143, 97, 65, 14);
			contentPanel.add(lblHauteur);
		}
		{
			hauteur = new JSpinner();
			hauteur.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			hauteur.setBounds(217, 95, 56, 20);
			hauteur.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			hauteur.setValue(h);
			contentPanel.add(hauteur);
		}
		{
			JLabel lblCentre = new JLabel("Centre");
			lblCentre.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblCentre.setBounds(10, 138, 49, 14);
			contentPanel.add(lblCentre);
		}
		{
			x = new JSpinner();
			x.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			x.setBounds(120, 136, 56, 20);
			x.setToolTipText("x \r\n");
			x.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			x.setValue(px);
			contentPanel.add(x);
		}
		{
			y = new JSpinner();
			y.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			y.setBounds(194, 136, 56, 20);
			y.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			y.setValue(py);
			contentPanel.add(y);
		}
		{
			z = new JSpinner();
			z.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			z.setBounds(268, 136, 56, 20);
			z.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			z.setValue(pz);
			contentPanel.add(z);
		}
		{
			JLabel lblVH = new JLabel("Vecteur directeur");
			lblVH.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblVH.setBounds(10, 185, 100, 14);
			contentPanel.add(lblVH);
		}
		{
			dx = new JSpinner();
			dx.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dx.setBounds(120, 182, 56, 20);
			dx.setToolTipText("dx \r\n");
			dx.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			dx.setValue(vx);
			contentPanel.add(dx);
		}
		{
			dy = new JSpinner();
			dy.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dy.setBounds(194, 182, 56, 20);
			dy.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			dy.setValue(vy);
			contentPanel.add(dy);
		}
		{
			dz = new JSpinner();
			dz.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dz.setBounds(268, 182, 56, 20);
			dz.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			dz.setValue(vz);
			contentPanel.add(dz);
		}
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setIcon(new ImageIcon(FenetreCylindre.class.getResource("/IG/6.png")));
			lblNewLabel.setBounds(10, 13, 121, 109);
			contentPanel.add(lblNewLabel);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 213, 444, 54);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(new Color(65, 105, 225));
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(211, 11, 134, 24);
				buttonPane.add(cancelButton);
				cancelButton.setBackground(new Color(100, 149, 237));
				cancelButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				cancelButton.addActionListener(new ActionAnnuler());
				cancelButton.setActionCommand("Annuler");
				cancelButton.setBorder(new LineBorder(new Color(0, 0, 128)));
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(369, 11, 50, 24);
				buttonPane.add(okButton);
				okButton.setBackground(new Color(100, 149, 237));
				okButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				okButton.addActionListener(new ActionOK());
				okButton.setActionCommand("OK");
				okButton.setBorder(new LineBorder(new Color(0, 0, 128)));
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private class ActionOK implements ActionListener {
		
		public void actionPerformed (ActionEvent ev) {
			
		    try{
		    	r = (double) rayon.getValue();
		    	px = (double) x.getValue();
		    	py = (double) y.getValue();
		    	pz = (double) z.getValue();
		    	vx = (double) dx.getValue();
		    	vy = (double) dy.getValue();
		    	vz = (double) dz.getValue();
		    	name = nom.getText();
		    	h = (double) hauteur.getValue();
		    
		    	
		    	cylindre = new Cylindre(new Point(px,py,pz), r, h, new Vecteur(vx,vy,vz), name);
		    	dispose();
	
		    }catch(NomVideException nfe){
		    	JOptionPane.showMessageDialog(null, nfe, "Veuillez remplir les cases ! ", JOptionPane.WARNING_MESSAGE);
		    }
		}
	}
	
	private class ActionAnnuler implements ActionListener {
		
		public void actionPerformed (ActionEvent ev) {
			dispose();
		}
	}
	
	public Cylindre getDonnees() {
		return this.cylindre;
	}


}
