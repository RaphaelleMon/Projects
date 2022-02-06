package IG;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import exception.LumiereHorsSceneException;
import rayTracing.Lumiere;
import rayTracing.LumierePonctuelle;
import rayTracing.LumiereSpherique;
import rayTracing.RayTracing;
import utilitaire.Point;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import IG.ListeLumieres;
import IG.Lumieres;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.border.CompoundBorder;
import java.awt.Cursor;

import javax.swing.ButtonGroup;
import javax.swing.DropMode;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Choice;
import javax.swing.JRadioButton;
import java.awt.SystemColor;
import javax.swing.ImageIcon;

public class FenetreLumiere extends JFrame {
	
	private RayTracing raytracing;
	private ListeLumieres listeL;
	private JList<Lumieres> grilleL;
	
	private JFrame jf;
	
	private JPanel contentPane;
	
	private JSpinner rouge, vert, bleu;
	private JSpinner px, pz, py;
	private JTextField nom;
	private JButton ajouterLumiere;
	
	private int r,b,v;
	private double x,y,z;
	private int i;
	private double rayon;
	
	private Lumieres lux;
	private Lumiere lumiere;
	
	private JRadioButton ponctuelle, spherique;
	private JButton couleurChoisi;
	
	private Color mycolor;
	private JTextField txtIntensit;
	private JSpinner spinner;
	private JSpinner spinnerRayon;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreLumiere frame = new FenetreLumiere(null,null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public FenetreLumiere(RayTracing rt, ListeLumieres l, JList<Lumieres> g) {		
		super("Su7 - Ajouter Lumière");
		this.raytracing = rt; 
		this.listeL = l;
		this.grilleL = g;	
		
		this.contentPane = new JPanel();
		
		this.nom = new JTextField("nouvelleLumiere");
		
		this.r = 255;
		this.v = 255;
		this.b = 255;
		this.mycolor = new Color(r,v,b);
		
		this.rouge = new JSpinner();
		this.vert = new JSpinner();
		this.bleu = new JSpinner();

		
		this.rayon = 0.0;
		
		this.spinnerRayon = new JSpinner();
		
		this.spherique = new JRadioButton("Spherique");
		this.ponctuelle = new JRadioButton("Ponctuelle");

		ponctuelle.setSelected(true);
		spinnerRayon.setEnabled(false);
		
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
		
		this.px = new JSpinner();
		this.pz = new JSpinner();
		this.py = new JSpinner();

		
		this.i = 0;
		this.spinner = new JSpinner();

				
		this.couleurChoisi = new JButton("");
		this.couleurChoisi.setBackground(mycolor);
		
		this.ajouterLumiere = new JButton("Ajouter Lumiere");
		this.ajouterLumiere.addActionListener(new ActionAjouterLumiere());
		
		initialize();
	}
	
	/**
	 * Create the frame.
	 */
	public FenetreLumiere(RayTracing rt, ListeLumieres l, JList<Lumieres> g, Lumieres lu) {		
		super("Su7 - Modifier Lumière");
		this.raytracing = rt; 
		this.listeL = l;
		this.grilleL = g;	
		
		this.lux = lu;
		this.lumiere = lu.getObjet();
		
		Color couleur = lumiere.getCouleur();
		Point centre = lumiere.getCentre();
		
		this.contentPane = new JPanel();
		
		this.nom = new JTextField(lumiere.getNom());
		
		this.spinnerRayon = new JSpinner();
		
		this.spherique = new JRadioButton("Spherique");
		this.ponctuelle = new JRadioButton("Ponctuelle");
		
		ponctuelle.setSelected(true);
		spinnerRayon.setEnabled(false);
		
		
		if (lumiere instanceof LumiereSpherique) {	
			this.rayon = ((LumiereSpherique) lumiere).getRayon();		
			this.spherique.setSelected(true);
			this.spinnerRayon.setEnabled(true);
		} 
		
		this.r = couleur.getRed();
		this.v = couleur.getGreen();
		this.b = couleur.getBlue();
		this.mycolor = new Color(r,v,b);
		
		this.rouge = new JSpinner();
		this.vert = new JSpinner();
		this.bleu = new JSpinner();
		
		this.x = centre.getX();
		this.y = centre.getY();
		this.z = centre.getZ();
		
		this.px = new JSpinner();
		this.pz = new JSpinner();
		this.py = new JSpinner();
		
		this.i = lumiere.getIntensite();
		
		this.spinner = new JSpinner();
		
		couleurChoisi = new JButton("");
		couleurChoisi.setBackground(mycolor);
		
		ajouterLumiere = new JButton("Modifier Lumiere");
		ajouterLumiere.addActionListener(new ActionModifierLumiere());
		
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Caractéristique de la fenêtre (titre, icon,...);
		
		URL img = FenetreLumiere.class.getResource("/IG/logo.png");
		ImageIcon icon = new ImageIcon(img);
		setIconImage(icon.getImage());
		setBackground(new Color(0, 100, 0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 656, 430);

		contentPane.setBackground(new Color(0, 100, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Le plus grand panel 
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(95, 158, 160));
		panel.setBorder(new LineBorder(new Color(173, 255, 47)));
		panel.setBounds(26, 24, 592, 347);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// Image de lumière 
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FenetreLumiere.class.getResource("/IG/lumiereGrande.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(324, 11, 258, 74);
		panel.add(lblNewLabel);
		
		// Panel avec le nom 
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(173, 255, 47)));
		panel_1.setBackground(new Color(46, 139, 87));
		panel_1.setBounds(10, 11, 304, 50);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		nom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		nom.setBounds(127, 12, 161, 25);
		nom.setToolTipText("Nouveau Nom");
		nom.setColumns(10);
		panel_1.add(nom);
		
		JLabel renommer = new JLabel("Renommer :");
		renommer.setForeground(new Color(0, 0, 0));
		renommer.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
		renommer.setBounds(10, 11, 97, 27);
		panel_1.add(renommer);
		renommer.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		// Panel avec les couleurs 
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(173, 255, 47)));
		panel_2.setBackground(new Color(46, 139, 87));
		panel_2.setBounds(10, 72, 306, 132);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JTextField txtCouleurParRgb = new JTextField();
		txtCouleurParRgb.setForeground(new Color(0, 100, 0));
		txtCouleurParRgb.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		txtCouleurParRgb.setBorder(new LineBorder(new Color(173, 255, 47)));
		txtCouleurParRgb.setBackground(new Color(152, 251, 152));
		txtCouleurParRgb.setBounds(10, 11, 286, 20);
		panel_2.add(txtCouleurParRgb);
		txtCouleurParRgb.setHorizontalAlignment(SwingConstants.CENTER);
		txtCouleurParRgb.setText("Couleur par RGB ");
		txtCouleurParRgb.setToolTipText("");
		txtCouleurParRgb.setEditable(false);
		txtCouleurParRgb.setColumns(10);
		

		vert.setBackground(new Color(0, 128, 0));
		vert.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		vert.addChangeListener(new ChangerCouleur());
		vert.setBounds(156, 57, 56, 25);
		vert.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		vert.setValue(v);
		vert.setToolTipText("valeur comprise entre 1 et 255");
		panel_2.add(vert);
		
		bleu.setBackground(new Color(0, 128, 0));
		bleu.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		bleu.addChangeListener(new ChangerCouleur());
		bleu.setBounds(240, 57, 56, 25);
		bleu.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		bleu.setValue(b);
		bleu.setToolTipText("valeur comprise entre 1 et 255");
		panel_2.add(bleu);
		
		rouge.setBackground(new Color(0, 128, 0));
		rouge.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		rouge.addChangeListener(new ChangerCouleur());
		rouge.setBounds(74, 57, 56, 25);
		rouge.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		rouge.setValue(r);
		rouge.setToolTipText("valeur comprise entre 1 et 255");
		panel_2.add(rouge);
	
		couleurChoisi.setBounds(10, 52, 40, 40);
		couleurChoisi.setBorder(new LineBorder(new Color(0,100,0)));
		panel_2.add(couleurChoisi);
		
		JButton choisirCouleur = new JButton("Choisir couleur");
		choisirCouleur.setBorder(new LineBorder(new Color(0, 100, 0)));
		choisirCouleur.setForeground(new Color(0, 0, 0));
		choisirCouleur.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		choisirCouleur.setBackground(new Color(102, 205, 170));
		choisirCouleur.addActionListener(new ActionChoisirCouleur());
		choisirCouleur.setBounds(160, 93, 136, 28);
		panel_2.add(choisirCouleur);
		
		JTextField txtBleue = new JTextField();
		txtBleue.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		txtBleue.setBounds(240, 42, 56, 18);
		txtBleue.setHorizontalAlignment(SwingConstants.CENTER);
		txtBleue.setBorder(null);
		txtBleue.setBackground(null);
		txtBleue.setText("Bleue");
		txtBleue.setEditable(false);
		txtBleue.setColumns(10);
		panel_2.add(txtBleue);
		
		JTextField txtVert = new JTextField();
		txtVert.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		txtVert.setBorder(null);
		txtVert.setBackground(null);
		txtVert.setBounds(156, 42, 56, 18);
		txtVert.setHorizontalAlignment(SwingConstants.CENTER);
		txtVert.setText("Vert");
		txtVert.setEditable(false);
		txtVert.setColumns(10);
		panel_2.add(txtVert);
		
		JTextField txtRouge = new JTextField();
		txtRouge.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		txtRouge.setBounds(74, 42, 56, 18);
		txtRouge.setBackground(null);
		txtRouge.setHorizontalAlignment(SwingConstants.CENTER);
		txtRouge.setBorder(null);
		txtRouge.setText("Rouge");
		txtRouge.setEditable(false);
		txtRouge.setColumns(10);
		panel_2.add(txtRouge);
		

		//Panel avec la position de la lumière
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(173, 255, 47)));
		panel_4.setBackground(new Color(46, 139, 87));
		panel_4.setBounds(10, 215, 304, 90);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JTextField txtPosition = new JTextField();
		txtPosition.setForeground(new Color(0, 100, 0));
		txtPosition.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		txtPosition.setBorder(new LineBorder(new Color(173, 255, 47)));
		txtPosition.setBackground(new Color(152, 251, 152));
		txtPosition.setBounds(10, 11, 284, 20);
		panel_4.add(txtPosition);
		txtPosition.setToolTipText("");
		txtPosition.setText("Position ");
		txtPosition.setHorizontalAlignment(SwingConstants.CENTER);
		txtPosition.setEditable(false);
		txtPosition.setColumns(10);
		
		px.setBackground(new Color(0, 128, 0));
		px.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		px.setModel(new SpinnerNumberModel(0.0, null, null, 0.01));
		px.setValue(x);
		px.setBounds(10, 54, 80, 25);
		panel_4.add(px);
		
		pz.setBackground(new Color(0, 128, 0));
		pz.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		pz.setModel(new SpinnerNumberModel(0.0, null, null, 0.01));
		pz.setValue(z);
		pz.setBounds(214, 54, 80, 25);
		panel_4.add(pz);
		
		py.setBackground(new Color(0, 128, 0));
		py.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		py.setModel(new SpinnerNumberModel(0.0, null, null, 0.01));
		py.setValue(y);
		py.setBounds(116, 54, 80, 25);
		panel_4.add(py);

		JTextField positionY = new JTextField();
		positionY.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		positionY.setBorder(null);
		positionY.setBackground(null);
		positionY.setBounds(116, 37, 80, 18);
		panel_4.add(positionY);
		positionY.setText("y");
		positionY.setHorizontalAlignment(SwingConstants.CENTER);
		positionY.setEditable(false);
		positionY.setColumns(10);
		
		JTextField positionX = new JTextField();
		positionX.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		positionX.setBorder(null);
		positionX.setBackground(null);
		positionX.setBounds(10, 37, 80, 18);
		panel_4.add(positionX);
		positionX.setText("x");
		positionX.setHorizontalAlignment(SwingConstants.CENTER);
		positionX.setEditable(false);
		positionX.setColumns(10);
		
		JTextField positionZ = new JTextField();
		positionZ.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		positionZ.setBorder(null);
		positionZ.setBackground(null);
		positionZ.setBounds(214, 37, 80, 18);
		panel_4.add(positionZ);
		positionZ.setText("z");
		positionZ.setHorizontalAlignment(SwingConstants.CENTER);
		positionZ.setEditable(false);
		positionZ.setColumns(10);
		
		
		// Panel pour choisir forme de lumière
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(173, 255, 47)));
		panel_5.setBackground(new Color(46, 139, 87));
		panel_5.setBounds(324, 96, 258, 119);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		ButtonGroup type = new ButtonGroup();
		
		ponctuelle.setFont(new Font("Century Schoolbook", Font.BOLD, 11));
		ponctuelle.setBackground(new Color(240, 255, 240));
		ponctuelle.addActionListener(new ActionSelectionner());
		ponctuelle.setBorder(new LineBorder(new Color(64, 224, 208)));
		ponctuelle.setHorizontalAlignment(SwingConstants.CENTER);
		ponctuelle.setBounds(10, 38, 107, 25);
		panel_5.add(ponctuelle);
		
		spherique.setFont(new Font("Century Schoolbook", Font.BOLD, 11));
		spherique.setBackground(new Color(240, 255, 240));
		spherique.addActionListener(new ActionSelectionner());
		spherique.setBorder(new LineBorder(new Color(64, 224, 208)));
		spherique.addItemListener(new SpheriqueSelectionner());
		spherique.setHorizontalAlignment(SwingConstants.CENTER);
		spherique.setBounds(141, 38, 107, 25);
		panel_5.add(spherique);
		
		type.add(spherique);
		type.add(ponctuelle);
		
		JTextField textFieldType = new JTextField();
		textFieldType.setForeground(new Color(0, 100, 0));
		textFieldType.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		textFieldType.setBorder(new LineBorder(new Color(173, 255, 47)));
		textFieldType.setBackground(new Color(152, 251, 152));
		textFieldType.setToolTipText("");
		textFieldType.setText("Type lumi\u00E8re ");
		textFieldType.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldType.setEditable(false);
		textFieldType.setColumns(10);
		textFieldType.setBounds(10, 11, 238, 20);
		panel_5.add(textFieldType);
		
		spinnerRayon.setBackground(new Color(0, 128, 0));
		spinnerRayon.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		spinnerRayon.setModel(new SpinnerNumberModel(0.0,0.0,null,0.01));
		spinnerRayon.setValue(rayon);
		spinnerRayon.addChangeListener(new ChangeValeur());
		spinnerRayon.setBounds(195, 83, 53, 25);
		panel_5.add(spinnerRayon);
		
		JLabel lblRayon = new JLabel("Rayon :");
		lblRayon.setForeground(new Color(0, 0, 0));
		lblRayon.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
		lblRayon.setBackground(SystemColor.menu);
		lblRayon.setBounds(10, 83, 172, 25);
		panel_5.add(lblRayon);
		
		// Panel avec l'intensité
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(173, 255, 47)));
		panel_6.setBackground(new Color(46, 139, 87));
		panel_6.setBounds(324, 226, 258, 76);
		panel.add(panel_6);
		panel_6.setLayout(null);
		
		txtIntensit = new JTextField();
		txtIntensit.setForeground(new Color(0, 100, 0));
		txtIntensit.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		txtIntensit.setBorder(new LineBorder(new Color(173, 255, 47)));
		txtIntensit.setBackground(new Color(152, 251, 152));
		txtIntensit.setToolTipText("");
		txtIntensit.setText("Intensit\u00E9 ");
		txtIntensit.setHorizontalAlignment(SwingConstants.CENTER);
		txtIntensit.setEditable(false);
		txtIntensit.setColumns(10);
		txtIntensit.setBounds(10, 11, 236, 20);
		panel_6.add(txtIntensit);
		
		spinner.setBackground(new Color(0, 128, 0));
		spinner.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		spinner.setModel(new SpinnerNumberModel(i, 0, null, 1));
		spinner.addChangeListener(new ChangeValeur());
		spinner.setBounds(83, 40, 80, 25);
		panel_6.add(spinner);
		
		
		//Bouton du panel 
		
		ajouterLumiere.setBorder(new LineBorder(new Color(0,100,0)));
		ajouterLumiere.setForeground(new Color(0, 0, 0));
		ajouterLumiere.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		ajouterLumiere.setBackground(new Color(102, 205, 170));
		ajouterLumiere.setBounds(382, 313, 200, 24);
		panel.add(ajouterLumiere);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionAnnuler());
		btnAnnuler.setBorder(new LineBorder(new Color(0,100,0)));
		btnAnnuler.setForeground(new Color(0, 0, 0));
		btnAnnuler.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		btnAnnuler.setBackground(new Color(102, 205, 170));
		btnAnnuler.setBounds(10, 313, 89, 24);
		panel.add(btnAnnuler);
		
	}
	
	/**
	 * Action annuler pour fermer la fênetre sans réaliser de modification.
	 * @author rapha
	 *
	 */
	private class ActionAnnuler implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			dispose();
		}
	}
	
	/**
	 * Change la couleur du bouton pour montrer la couleur choisie après changement des valeurs de spinner.
	 * @author rapha
	 *
	 */
	class ChangerCouleur implements ChangeListener {

		public void stateChanged(ChangeEvent event) {
			JSpinner valeur = (JSpinner) event.getSource();
			if( valeur == rouge) {
				r = (int)valeur.getValue();
				mycolor = new Color(r,v,b);
				couleurChoisi.setBackground(mycolor);
			} else if (valeur == vert) {
				v = (int)valeur.getValue();
				mycolor = new Color(r,v,b);
				couleurChoisi.setBackground(mycolor);			
			} else {
				b = (int)valeur.getValue();
				mycolor = new Color(r,v,b);
				couleurChoisi.setBackground(mycolor);
			}
		}
	}
	
	/**
	 * Ouvre la fenêtre de choix de la couleur.
	 * @author rapha
	 *
	 */
	private class ActionChoisirCouleur implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mycolor = JColorChooser.showDialog(jf,"Swing color chooser", mycolor);
			if (mycolor != null) {
				r = mycolor.getRed();
				v = mycolor.getGreen();
				b = mycolor.getBlue();
				rouge.setValue(Integer.valueOf(r));
				vert.setValue(Integer.valueOf(v));
				bleu.setValue(Integer.valueOf(b));
				couleurChoisi.setBackground(mycolor);
			}
			
		}
	}
	
	/**
	 * Change la valeur des valeurs correspondant au spinner intensite et rayon.
	 * @author rapha
	 *
	 */
	class ChangeValeur implements ChangeListener {

		public void stateChanged(ChangeEvent event) {
			JSpinner valeur = (JSpinner) event.getSource();
			if (valeur == spinner) {
				i = (int)valeur.getValue();
			} else if (valeur == spinnerRayon) {
				rayon = (double)valeur.getValue();
			}
		}
	}
	
	/**
	 * Afficher la fenêtre setRayon lorsqu'on sélectionne sphérique.
	 * @author rapha
	 *
	 */
	private class ActionSelectionner implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			JRadioButton button = (JRadioButton) ev.getSource();
			
	        if (button == spherique) {
	        	JSpinner R = new JSpinner();
	        	R.setModel(new SpinnerNumberModel(rayon,0.0,null,0.01));
	        	Object[] options = {"OK", "Annuler"};
	        	int option = JOptionPane.showOptionDialog(jf, R, "Choisir un rayon", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, options, options[0]);
	        	if(option == 0) {
	        		if(R.getValue() == null | (double) R.getValue() == 0.0) {
	        			JOptionPane.showMessageDialog(jf, "Veuillez entrer un rayon non nul","Rayon nul", JOptionPane.ERROR_MESSAGE);
		        		spherique.setSelected(false);
		        		ponctuelle.setSelected(true);
	        		} else {
	        			rayon = (double) R.getValue();
	        			spinnerRayon.setValue(rayon);
	        		}
	        	} else if (option != 0 && rayon == 0.0) {
	        		spherique.setSelected(false);
	        		ponctuelle.setSelected(true);
	        	}
	        }
		}
	}
	
	/**
	 * Change l'état de spinner de Rayon quand on sélectionne ou non Sphérique.
	 * @author rapha
	 *
	 */
	public class SpheriqueSelectionner implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			int state = e.getStateChange();
			if (state == ItemEvent.SELECTED) {
				spinnerRayon.setValue(rayon);
				spinnerRayon.setEnabled(true);
			} else if (state == ItemEvent.DESELECTED) {
				spinnerRayon.setEnabled(false);
				rayon = 0.0;
				spinnerRayon.setValue(rayon);
				spinnerRayon.setBackground(Color.GRAY);
			}
		}
	}
	
	/**
	 * Creer une lumiere à partir des valeurs rentrées. 
	 * @return lumiere avec les valeurs ajouter
	 */
	private Lumiere creerLumiere() {
		Point ncentre = new Point((double) px.getValue(),(double) py.getValue(),(double) pz.getValue());
		Color ncolor = new Color(r, v, b);
	    String nnom = (String) nom.getText();
	    Lumiere light;
		if (spherique.isSelected()) {
			light = new LumiereSpherique(ncentre,ncolor,rayon,nnom);
		} else {			
			light = new LumierePonctuelle(ncentre,ncolor, nnom);
		}
		light.setIntensite(i);
		return light;
	}
	
	
	/**
	 * Action pour modifier lumière.
	 * @author rapha
	 *
	 */
	public class ActionModifierLumiere implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Lumiere light = creerLumiere();
		    Object[] options = {"Oui", "Non"};
			if(light.estHorsScene(raytracing.getScene().getDimension(), new Point(0.0,0.0,0.0))) {
		    	int n = JOptionPane.showOptionDialog(new JFrame(),
		    		"Attention ! La lumiere est hors scene.\n Voulez-vous toujours la modifier ? ",
		    	    "Lumiere hors scene  ",
		    	    JOptionPane.WARNING_MESSAGE, 
		    	    JOptionPane.YES_NO_OPTION,
		    	    null,    
		    	    options,  
		    	    options[0]); 
		    	if (n == 0) {
					int index = listeL.getLumieres().indexOf(lux);
					raytracing.getScene().getLumieres().set(index, light);
					listeL.getLumieres().set(index, new Lumieres(light));
		    		grilleL.updateUI();
		    		dispose();
		    	}
			} else {		
				int index = listeL.getLumieres().indexOf(lux);
				raytracing.getScene().getLumieres().set(index, light);
				listeL.getLumieres().set(index, new Lumieres(light));
	    		grilleL.updateUI();
	    		dispose();
			}

		}
	}
	
	
	/**
	 * Action pour ajouter une lumière.
	 * @author rapha
	 *
	 */
	public class ActionAjouterLumiere implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			lumiere = creerLumiere();
			
		    Object[] options = {"Oui", "Non"};
		    
			try {
				raytracing.getScene().addLumiere(lumiere);
	    		listeL.addElement(new Lumieres(lumiere));
	    		grilleL.updateUI();
	    		dispose();
			} catch (LumiereHorsSceneException e) {
		    	int n = JOptionPane.showOptionDialog(new JFrame(),
		    		"Attention ! La lumiere est hors scene.\n Voulez-vous toujours l'ajouter ? ",
		    	    "Lumiere hors scene  ",
		    	    JOptionPane.WARNING_MESSAGE, 
		    	    JOptionPane.YES_NO_OPTION,
		    	    null,    
		    	    options,  
		    	    options[0]); 
		    	if (n == 0) {
		    		listeL.addElement(new Lumieres(lumiere));
		    		grilleL.updateUI();
		    		dispose();
		    	} else {
		    		int p = raytracing.getScene().getLumieres().size() - 1;
		    		raytracing.getScene().getLumieres().remove(p);
		    	}
			}

		}
	}
}
