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

import elements3D.Cone;
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

public class FenetreCone extends FenetreObjet3D {

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
	private Cone cone;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FenetreCone dialog = new FenetreCone(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetreCone(Objet3D objet) {
		
		super("Paramètres du Cône");
		setBounds(100, 100, 448, 294);
		
		if(objet!=null) {
			this.name = objet.getNom();
		}
		
		if (objet!= null && objet instanceof Cone) {
			cone = (Cone) objet;
			r = cone.getRayon();
			
			Point cb = cone.getCentreBase();
			px = cb.getX();
			py = cb.getY();
			pz = cb.getZ();		
			
			Vecteur vh = cone.getVecteurDirecteur();
			vx = vh.getX();
			vy = vh.getY();
			vz = vh.getZ();
			
			h = cone.getHauteur();
			
		} else {
			r = 0.0;
			
			px = 0.0;
			py = 0.0;
			pz = 0.0;	
			
			vx = 0.0;
			vy = 0.0;
			vz = 0.0;	
			
			h = 0.0;
			
			this.name = "nouveauCone";
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
			lblRayon.setBounds(145, 52, 62, 14);
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
			lblHauteur.setBounds(142, 98, 65, 14);
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
			lblCentre.setBounds(10, 139, 49, 14);
			contentPanel.add(lblCentre);
		}
		{
			x = new JSpinner();
			x.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			x.setBounds(69, 136, 56, 20);
			x.setToolTipText("x \r\n");
			x.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			x.setValue(px);
			contentPanel.add(x);
		}
		{
			y = new JSpinner();
			y.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			y.setBounds(143, 136, 56, 20);
			y.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			y.setValue(py);
			contentPanel.add(y);
		}
		{
			z = new JSpinner();
			z.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			z.setBounds(217, 136, 56, 20);
			z.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			z.setValue(pz);
			contentPanel.add(z);
		}
		{
			JLabel lblVH = new JLabel("VHaut");
			lblVH.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblVH.setBounds(10, 185, 49, 14);
			contentPanel.add(lblVH);
		}
		{
			dx = new JSpinner();
			dx.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dx.setBounds(69, 182, 56, 20);
			dx.setToolTipText("dx \r\n");
			dx.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			dx.setValue(vx);
			contentPanel.add(dx);
		}
		{
			dy = new JSpinner();
			dy.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dy.setBounds(143, 182, 56, 20);
			dy.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			dy.setValue(vy);
			contentPanel.add(dy);
		}
		{
			dz = new JSpinner();
			dz.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dz.setBounds(217, 182, 56, 20);
			dz.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			dz.setValue(vz);
			contentPanel.add(dz);
		}
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setIcon(new ImageIcon(FenetreCone.class.getResource("/IG/4.png")));
			lblNewLabel.setBounds(10, 13, 121, 109);
			contentPanel.add(lblNewLabel);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 213, 434, 42);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(new Color(65, 105, 225));
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(203, 11, 134, 24);
				buttonPane.add(cancelButton);
				cancelButton.setBackground(new Color(100, 149, 237));
				cancelButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				cancelButton.addActionListener(new ActionAnnuler());
				cancelButton.setActionCommand("Annuler");
				cancelButton.setBorder(new LineBorder(new Color(0, 0, 128)));
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(360, 11, 50, 24);
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
		    
		    	
		    	cone = new Cone(new Point(px,py,pz),new Vecteur(vx,vy,vz),h,r, name);
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
	
	public Cone getDonnees() {
		return this.cone;
	}


}
