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

import elements3D.Cube;
import elements3D.Objet3D;
import elements3D.Sphere;
import exception.NomVideException;
import utilitaire.Point;

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

public class FenetreSphere extends FenetreObjet3D {

	private JSpinner x;
	private JSpinner y;
	private JSpinner z;
	private double px,py,pz;
	private String name;
	private double r;
	private JTextField nom;
	private JSpinner rayon;
	private Sphere sphere;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FenetreSphere dialog = new FenetreSphere(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetreSphere(Objet3D objet) {
		
		super("Paramètres de Sphère");
		
		if(objet!=null) {
			this.name = objet.getNom();
		}
		
		if (objet!= null && objet instanceof Sphere) {
			sphere = (Sphere) objet;
			this.r = sphere.getRayon();
			Point c = sphere.getOrigine();
			px = c.getX();
			py = c.getY();
			pz = c.getZ();		
		} else {
			this.r = 0.0;
			px = 0.0;
			py = 0.0;
			pz = 0.0;		
			this.name = "nouvelleSphere";
		}

		{
			JLabel lblNom = new JLabel("Nom");
			lblNom.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblNom.setBounds(135, 13, 61, 14);
			contentPanel.add(lblNom);
		}
		{
			nom = new JTextField();
			nom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			nom.setText(this.name);
			nom.setBounds(206, 10, 96, 20);
			contentPanel.add(nom);
			nom.setColumns(10);
		}
		{
			JLabel lblRayon = new JLabel("Rayon");
			lblRayon.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblRayon.setBounds(135, 44, 61, 14);
			contentPanel.add(lblRayon);
		}
		{
			rayon = new JSpinner();
			rayon.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			rayon.setBounds(206, 41, 56, 20);
			rayon.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			rayon.setValue(this.r);
			contentPanel.add(rayon);
		}
		{
			JLabel lblCentre = new JLabel("Centre");
			lblCentre.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblCentre.setBounds(135, 84, 61, 14);
			contentPanel.add(lblCentre);
		}
		{
			x = new JSpinner();
			x.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			x.setBounds(206, 81, 56, 20);
			x.setToolTipText("x \r\n");
			x.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			x.setValue(px);
			contentPanel.add(x);
		}
		{
			y = new JSpinner();
			y.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			y.setBounds(288, 81, 56, 20);
			y.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			y.setValue(py);
			contentPanel.add(y);
		}
		{
			z = new JSpinner();
			z.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			z.setBounds(371, 81, 56, 20);
			z.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			z.setValue(pz);
			contentPanel.add(z);
		}
		
		JLabel iconSphere = new JLabel("");
		iconSphere.setHorizontalAlignment(SwingConstants.CENTER);
		iconSphere.setIcon(new ImageIcon(FenetreSphere.class.getResource("/IG/1.png")));
		iconSphere.setBounds(10, 16, 109, 82);
		contentPanel.add(iconSphere);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 128, 434, 42);
			contentPanel.add(buttonPane);
			buttonPane.setBorder(new LineBorder(new Color(128, 0, 0)));
			buttonPane.setBackground(new Color(65, 105, 225));
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(230, 11, 134, 24);
				buttonPane.add(cancelButton);
				cancelButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				cancelButton.setBackground(new Color(100, 149, 237));
				cancelButton.addActionListener(new ActionAnnuler());
				cancelButton.setActionCommand("Annuler");
				cancelButton.setBorder(new LineBorder(new Color(0, 0, 128)));
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(374, 11, 50, 24);
				buttonPane.add(okButton);
				okButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				okButton.setBackground(new Color(100, 149, 237));
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
		    	name = nom.getText();
		    
		    	
		    	sphere = new Sphere(new Point(px,py,pz),r, name);
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
	
	public Sphere getDonnees() {
		return this.sphere;
	}
}
