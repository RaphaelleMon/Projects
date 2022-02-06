package FenetreElements3D;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import IG.FenetrePrincipale;
import elements3D.Cone;
import elements3D.Cube;
import elements3D.Objet3D;
import elements3D.Plan;
import elements3D.Sphere;
import exception.NomVideException;
import utilitaire.Point;
import utilitaire.Vecteur;

import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

public class FenetrePlan extends FenetreObjet3D {

	private JSpinner x;
	private JSpinner y;
	private JSpinner z;
	private JSpinner dx;
	private JSpinner dy;
	private JSpinner dz;
	private double px,py,pz;
	private double vx,vy,vz;
	private String name;
	private JTextField nom;
	private Plan plan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FenetrePlan dialog = new FenetrePlan(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetrePlan(Objet3D objet) {
		
		super("Paramètres du Plan");
		setBounds(100, 100, 452, 215);
		
		if(objet!=null) {
			this.name = objet.getNom();
		}
		
		if (objet!= null && objet instanceof Plan) {
			plan = (Plan) objet;

			Point p = plan.getPoint();
			px = p.getX();
			py = p.getY();
			pz = p.getZ();		
			
			Vecteur n = plan.getNormale();
			vx = n.getX();
			vy = n.getY();
			vz = n.getZ();
			
		} else {
			
			px = 0.0;
			py = 0.0;
			pz = 0.0;	
			
			vx = 0.0;
			vy = 0.0;
			vz = 0.0;	
			
			this.name = "Plan";
		}
		
		
		{
			JLabel lblNom = new JLabel("Nom");
			lblNom.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblNom.setBounds(20, 13, 45, 14);
			contentPanel.add(lblNom);
		}
		{
			nom = new JTextField(name);
			nom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			nom.setBounds(115, 10, 96, 20);
			contentPanel.add(nom);
			nom.setColumns(10);
		}
		{
			JLabel lblPoint = new JLabel("Point");
			lblPoint.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblPoint.setBounds(20, 47, 45, 14);
			contentPanel.add(lblPoint);
		}
		{
			x = new JSpinner();
			x.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			x.setBounds(115, 44, 56, 20);
			x.setToolTipText("x \r\n");
			x.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			x.setValue(px);
			contentPanel.add(x);
		}
		{
			y = new JSpinner();
			y.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			y.setBounds(181, 44, 56, 20);
			y.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			y.setValue(py);
			contentPanel.add(y);
		}
		{
			z = new JSpinner();
			z.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			z.setBounds(247, 44, 56, 20);
			z.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			z.setValue(pz);
			contentPanel.add(z);
		}
		{
			JLabel lblVecteur = new JLabel("Vecteur Normal");
			lblVecteur.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
			lblVecteur.setBounds(20, 87, 96, 14);
			contentPanel.add(lblVecteur);
		}
		{
			dx = new JSpinner();
			dx.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dx.setBounds(115, 84, 56, 20);
			dx.setToolTipText("x \r\n");
			dx.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			dx.setValue(vx);
			contentPanel.add(dx);
		}
		{
			dy = new JSpinner();
			dy.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dy.setBounds(181, 84, 56, 20);
			dy.setModel(new SpinnerNumberModel(0.0, null, null,0.1));
			dy.setValue(vy);
			contentPanel.add(dy);
		}
		{
			dz = new JSpinner();
			dz.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			dz.setBounds(247, 84, 56, 20);
			dz.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
			dz.setValue(vz);
			contentPanel.add(dz);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 138, 448, 50);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(new Color(65, 105, 225));
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(228, 11, 134, 24);
				buttonPane.add(cancelButton);
				cancelButton.setBackground(new Color(100, 149, 237));
				cancelButton.setBorder(new LineBorder(new Color(0, 0, 128)));
				cancelButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				cancelButton.addActionListener(new ActionAnnuler());
				cancelButton.setActionCommand("Annuler");
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(372, 11, 54, 24);
				buttonPane.add(okButton);
				okButton.setBackground(new Color(100, 149, 237));
				okButton.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
				okButton.addActionListener(new ActionOK());
				okButton.setBorder(new LineBorder(new Color(0, 0, 128)));
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private class ActionOK implements ActionListener {
		
		public void actionPerformed (ActionEvent ev) {
			
		    try{
		    	px = (double) x.getValue();
		    	py = (double) y.getValue();
		    	pz = (double) z.getValue();
		    	vx = (double) dx.getValue();
		    	vy = (double) dy.getValue();
		    	vz = (double) dz.getValue();
		    	name = nom.getText();
		    
		    	
		    	plan = new Plan(new Vecteur(vx,vy,vz),new Point(px,py,pz),name);
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
	
	public Plan getDonnees() {
		return this.plan;
	}
}
