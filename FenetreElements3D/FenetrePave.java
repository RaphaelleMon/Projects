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

import elements3D.Pave;
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

public class FenetrePave extends FenetreObjet3D {

	private JSpinner x;
	private JSpinner y;
	private JSpinner z;
	private double px,py,pz;
	private String name;
	private double areteX;
	private double areteY;
	private double areteZ;
	private JTextField nom;
	private JSpinner areteXJ;
	private JSpinner areteYJ;
	private JSpinner areteZJ;
	private Pave pave;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FenetrePave dialog = new FenetrePave(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetrePave(Objet3D objet) {
		
		super("Paramètres du Pave");
		setBounds(100, 100, 469, 280);
		
		if(objet!=null) {
			this.name = objet.getNom();
		}
		
		if (objet!= null && objet instanceof Pave) {
			pave = (Pave) objet;
			areteX = pave.getAreteX();
			areteY = pave.getAreteY();
			areteZ = pave.getAreteZ();
			
			Point cb = pave.getCentreBase();
			px = cb.getX();
			py = cb.getY();
			pz = cb.getZ();		
			
		} else {
			areteX = 0.0;
			areteY = 0.0;
			areteZ = 0.0;
			
			px = 0.0;
			py = 0.0;
			pz = 0.0;
			
			this.name = "nouveauPave";
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
			JLabel lblAreteX = new JLabel("arete X");
			lblAreteX.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblAreteX.setBounds(145, 52, 62, 14);
			contentPanel.add(lblAreteX);
		}
		{
			areteXJ = new JSpinner();
			areteXJ.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			areteXJ.setBounds(217, 49, 56, 20);
			areteXJ.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			areteXJ.setValue(areteX);
			contentPanel.add(areteXJ);
		}
		{
			JLabel lblAreteY = new JLabel("arete Y");
			lblAreteY.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblAreteY.setBounds(145, 77, 62, 14);
			contentPanel.add(lblAreteY);
		}
		{
			areteYJ = new JSpinner();
			areteYJ.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			areteYJ.setBounds(217, 74, 56, 20);
			areteYJ.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			areteYJ.setValue(areteY);
			contentPanel.add(areteYJ);
		}
		{
			JLabel lblAreteZ = new JLabel("arete Z");
			lblAreteZ.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblAreteZ.setBounds(145, 102, 62, 14);
			contentPanel.add(lblAreteZ);
		}
		{
			areteZJ = new JSpinner();
			areteZJ.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			areteZJ.setBounds(217, 99, 56, 20);
			areteZJ.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.1));
			areteZJ.setValue(areteZ);
			contentPanel.add(areteZJ);
		}
		{
			JLabel lblCentre = new JLabel("Centre");
			lblCentre.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblCentre.setBounds(10, 159, 49, 14);
			contentPanel.add(lblCentre);
		}
		{
			x = new JSpinner();
			x.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			x.setBounds(69, 156, 56, 20);
			x.setToolTipText("x \r\n");
			x.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			x.setValue(px);
			contentPanel.add(x);
		}
		{
			y = new JSpinner();
			y.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			y.setBounds(143, 156, 56, 20);
			y.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			y.setValue(py);
			contentPanel.add(y);
		}
		{
			z = new JSpinner();
			z.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			z.setBounds(217, 156, 56, 20);
			z.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			z.setValue(pz);
			contentPanel.add(z);
		}
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setIcon(new ImageIcon(FenetrePave.class.getResource("/IG/2.png")));
			lblNewLabel.setBounds(10, 13, 121, 109);
			contentPanel.add(lblNewLabel);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 201, 468, 52);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(new Color(65, 105, 225));
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(396, 11, 50, 24);
				buttonPane.add(okButton);
				okButton.setBackground(new Color(100, 149, 237));
				okButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				okButton.addActionListener(new ActionOK());
				okButton.setActionCommand("OK");
				okButton.setBorder(new LineBorder(new Color(0, 0, 128)));
				getRootPane().setDefaultButton(okButton);
			}
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
		}
	}
	
	private class ActionOK implements ActionListener {
		
		public void actionPerformed (ActionEvent ev) {
			
		    try{
		    	areteX = (double) areteXJ.getValue();
		    	areteY = (double) areteYJ.getValue();
		    	areteZ = (double) areteZJ.getValue();
		    	px = (double) x.getValue();
		    	py = (double) y.getValue();
		    	pz = (double) z.getValue();
		    	name = nom.getText();
		    	
		    	pave = new Pave(new Point(px,py,pz),areteX, areteY, areteZ, name);
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
	
	public Pave getDonnees() {
		return this.pave;
	}


}
