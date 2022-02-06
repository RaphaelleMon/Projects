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
import javax.swing.border.LineBorder;

import IG.FenetrePrincipale;
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
import java.awt.Font;
import java.awt.Color;

public class FenetreCube extends FenetreObjet3D {

	private JSpinner x;
	private JSpinner y;
	private JSpinner z;
	private double px,py,pz;
	private String name;
	private double a;
	private JTextField nom;
	private JSpinner arete;
	private Cube cube;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FenetreCube dialog = new FenetreCube(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetreCube(Objet3D objet) {
		
		super("Paramètres du Cube");
		setBounds(100, 100, 464, 219);
		
		if(objet!=null) {
			this.name = objet.getNom();
		}
		
		if (objet!= null && objet instanceof Cube ) {
			cube = (Cube) objet;
			this.a = cube.getArete();
			Point p = cube.getPosition();
			px = p.getX();
			py = p.getY();
			pz = p.getZ();		
		} else {
			this.a = 0.0;
			px = 0.0;
			py = 0.0;
			pz = 0.0;		
			this.name = "nouveauCube";
		}
		
		{
			JLabel lblNom = new JLabel("Nom");
			lblNom.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblNom.setBounds(103, 13, 46, 14);
			contentPanel.add(lblNom);
		}
		{
			nom = new JTextField(name);
			nom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			nom.setBounds(159, 10, 96, 20);
			contentPanel.add(nom);
			nom.setColumns(10);
		}
		{
			JLabel lblArete= new JLabel("Arete");
			lblArete.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblArete.setBounds(103, 55, 46, 14);
			contentPanel.add(lblArete);
		}
		{
			arete = new JSpinner();
			arete.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			arete.setBounds(159, 52, 56, 20);
			arete.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			arete.setValue(a);
			contentPanel.add(arete);
		}
		{
			JLabel lblCentre = new JLabel("Centre");
			lblCentre.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblCentre.setBounds(103, 100, 46, 14);
			contentPanel.add(lblCentre);
		}
		{
			x = new JSpinner();
			x.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			x.setBounds(159, 97, 56, 20);
			x.setToolTipText("x \r\n");
			x.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			x.setValue(px);
			contentPanel.add(x);
		}
		{
			y = new JSpinner();
			y.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			y.setBounds(240, 97, 56, 20);
			y.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			y.setValue(py);
			contentPanel.add(y);
		}
		{
			z = new JSpinner();
			z.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			z.setBounds(323, 97, 56, 20);
			z.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			z.setValue(pz);
			contentPanel.add(z);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setIcon(new ImageIcon(FenetreCube.class.getResource("/IG/2.png")));
			lblNewLabel.setBounds(0, 13, 93, 99);
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 138, 460, 54);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(new Color(65, 105, 225));
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(240, 11, 134, 24);
				buttonPane.add(cancelButton);
				cancelButton.setBackground(new Color(100, 149, 237));
				cancelButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				cancelButton.addActionListener(new ActionAnnuler());
				cancelButton.setActionCommand("Annuler");
				cancelButton.setBorder(new LineBorder(new Color(0, 0, 128)));
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(384, 11, 50, 24);
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
		    	a = (double) arete.getValue();
		    	px = (double) x.getValue();
		    	py = (double) y.getValue();
		    	pz = (double) z.getValue();
		    	name = nom.getText();
		    
		    	
		    	cube = new Cube(new Point(px,py,pz),a, name);
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
	
	public Cube getDonnees() {
		return this.cube;
	}


}

