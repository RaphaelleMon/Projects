package IG;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import FenetreElements3D.FenetreCone;
import FenetreElements3D.FenetreCube;
import FenetreElements3D.FenetreCylindre;
import FenetreElements3D.FenetreObjet3D;
import FenetreElements3D.FenetrePave;
import FenetreElements3D.FenetrePlan;
import FenetreElements3D.FenetreSphere;
import elements3D.Cone;
import elements3D.Couleur;
import elements3D.Cube;
import elements3D.Cylindre;
import elements3D.Objet3D;
import elements3D.Pave;
import elements3D.Plan;
import elements3D.Reflectivite;
import elements3D.Refringence;
import elements3D.Sphere;
import exception.NomVideException;
import exception.Objet3DHorsSceneException;
import rayTracing.RayTracing;
import utilitaire.Point;
import utilitaire.Vecteur;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.JPopupMenu;
import java.awt.Component;
public class FenetreObjet extends JFrame {

private static final long serialVersionUID = 1L;
	
	/** Raytracing initialiser dans fenetre princiaple*/
	private RayTracing rayTracing;
	
	/** Bouton ajouter qui change d'action selon l'instanciation de la fenêtre. */
    private JButton ajouter;
    
    /** List initialiser dans fenetre principale avec l'ensemble de objet présent dans la scène.*/
	private ListeObjets listeO;
	
	/**JList initialiser avec la list objet.*/
	private JList<Objet> grilleO;
	
	/** Parametre de l'objet liee à la refraction et la reflectivite.*/
	private double iRR,iRL,eRR,eRL,indice;
	
	/**Boolen pour dire si la refraction et la reflectivite sont actives.*/
	private boolean onRR,onRL;
	
	/**Couleur de l'objet.*/
	private Color mycolor;
	
	private JFrame jf;
	
	/**Objet3D pour récuperer le clone de l'objet sélectionné ou pour instancier l'objet à créer.*/
	private Objet3D objet; 
	
	/**Index de l'objet selectionner dans la JList.*/
	private int index;
	
	/**Position de l'objet.*/
	private double px,py,pz;
	
	/**Part du modele de phong.*/
	private double ka,kd,ks;
	
	/**Brillance du modele de phong.*/
	private int bl;
	
	/**RVB de la couleur de l'objet.*/
	private int r,b,v;
	
	/**Couleur des filtres du modèle de phong.*/
	private Color cS, cD, cA;
	
	/**Activer parametre modele de phong.*/
	private Boolean offA,offD,offS; 
	
	/**Spinner pour modifier RVG de la couleur de l'objet.*/
	private JSpinner rouge,vert,bleu;
	
	/**Bouton pour afficher la couleur de l'objet.*/
	private JButton couleurObjet;
	
	/**TextField pour modifier le nom de l'objet.*/
	private JTextField nom;
	
	/**Tableau des formes possibles.*/
	private String forme[] = {"Sphère", "Cube","Cone","Plan","Cylindre","Pave"};
	
	/**ComboBox pour choisir la forme de l'objet.*/
	private JComboBox formeO = new JComboBox(forme);
	
	/** Spinner pour modifier les parametres de refraction.*/
	private JSpinner energieRefl, energieRefra, indiceRR;
	
	/** Spinner pour modifier les parametres de reflexivite.*/
	private JSpinner intensiteRefl, intensiteRefr;
	
	/**CheckBox pour modifier activer refrection et reflectivite.*/
	private JCheckBox activeRefl, activeRefr;
	
	/**TextField pour coordonnées de la position.*/
	private JTextField x,y,z;
	
	/** Spinner pour modifier les parametres du modèle de phong.*/
	private JSpinner brillance, sks,ska,skd;
	
	/**CheckBox pour activer les parametre du modèle de phong.*/
	private JCheckBox onS, onD, onA;
	
	/**Bouton pour afficher les couleurs de filtre des parametres du modele de phong.*/
	private JButton couleurS,couleurD,couleurA;
	
	/**Bouton pour choisir les couleurs de filtre des parametres du modele de phong.*/
	private JButton colorS, colorA, colorD;
	

	
	
	/**
	 * Construire fenêtre sans objet.
	 * @param rayTracing
	 * @param oL
	 * @param L
	 * @wbp.parser.constructor
	 */
	public FenetreObjet (RayTracing rayTracing,ListeObjets oL,JList<Objet> L) {
		super("Su7 - Ajouter Objet");
		setResizable(false);
		
		new JColorChooser();
		this.rayTracing = rayTracing;
	    this.listeO = oL; 
	    this.grilleO = L;
	    
		try {
			this.objet = (Objet3D) new Sphere(new Point(0.0,0.0,1.0),1.0);
		} catch (NomVideException e0) {
			e0.printStackTrace();
		}
		
		this.nom = new JTextField(objet.getNom());
		
		this.ajouter = new JButton("Ajouter");
		ajouter.addActionListener(new ActionAjouterObjet());
		
		initialiserFenetre(null);
		
	}
	
	/**
	 * Construit fenetre avec un objet.
	 * @param rayTracing
	 * @param oL
	 * @param L
	 * @param o
	 */
	public FenetreObjet(RayTracing rayTracing,ListeObjets oL,JList<Objet> L, Objet o) {
		super("Su7 - Modifier Objet");
		
		new JColorChooser();
		this.rayTracing = rayTracing;
	    this.listeO = oL; 
	    this.grilleO = L;
	    
		this.objet = (Objet3D) o.getObjet().clone();
		this.index = L.getSelectedIndex();
		
		this.nom = new JTextField(objet.getNom());
	    this.ajouter = new JButton("Modifier");
	    ajouter.addActionListener(new ActionModifierObjet());
	    
	    initialiserFenetre(objet);
	}
	
	/**
	 * Initialiser la fenêtre au complet avec les valeurs, positions....
	 * @param objet
	 */
	private void initialiserFenetre(Objet3D objet) {
	    initialiserComposant();
	    maj(objet);
	    initialise();
	    setValeur();
	}
	
	/**
	 * Set les valeurs des composants en attributs(spinner, checkbox...).
	 */
	private void setValeur() {
		
		this.activeRefl.setSelected(onRL);
		this.activeRefr.setSelected(onRR);
		
		this.energieRefl.setValue((double) this.eRL);
		this.energieRefra.setValue((double) this.eRR);
		this.indiceRR.setValue((double) this.indice);
		this.intensiteRefr.setValue((double) this.iRR);
		this.intensiteRefl.setValue((double) this.iRL);
		
		this.rouge.setValue((int) this.r);
		this.vert.setValue((int) this.v);
		this.bleu.setValue((int) this.b);
				
		this.couleurObjet.setBackground(mycolor);
		
		this.x.setText(((Double) px).toString()); 
		this.y.setText(((Double) py).toString());
		this.z.setText(((Double) pz).toString());
		
		this.brillance.setValue(bl);
		this.sks.setValue(ks);
		this.ska.setValue(ka);
		this.skd.setValue(kd);
		
		this.onA.setSelected(offA);
		this.onD.setSelected(offD);
		this.onS.setSelected(offS);
		
		this.couleurA.setBackground(cA);
		this.couleurD.setBackground(cD);
		this.couleurS.setBackground(cS);
		
	}
	
	/**
	 * Initiliser les composants en attributs.
	 */
	private void initialiserComposant() {
		
		onS = new JCheckBox("On");
		onA = new JCheckBox("On");
		onD = new JCheckBox("On");
		
		
		this.rouge = new JSpinner();
		this.vert = new JSpinner();

		this.bleu = new JSpinner();

		
		this.x = new JTextField();
		this.y = new JTextField();	
		this.z = new JTextField();

		this.brillance = new JSpinner();
		
		this.indiceRR = new JSpinner();
		this.energieRefra = new JSpinner();
		this.intensiteRefr = new JSpinner();
		this.energieRefl = new JSpinner();
		this.intensiteRefl = new JSpinner();
		
		this.activeRefl = new JCheckBox("On");
		this.activeRefr = new JCheckBox("On");
		
		this.couleurA = new JButton();
		this.couleurD = new JButton();
		this.couleurS = new JButton();
		
		this.sks = new JSpinner();
		this.ska = new JSpinner();
		this.skd = new JSpinner();

		this.brillance = new JSpinner();
		
		this.couleurObjet = new JButton();	
		
	}
	
	/**
	 * Selectionner le bon composant de la combobox.
	 * @param classO
	 */
	private void selectionO(String classO) {
		switch(classO) {
		case "class elements3D.Sphere" : 
			formeO.setSelectedIndex(0);
			break;
		case "class elements3D.Cube" :
			formeO.setSelectedIndex(1);
			break;
		case "class elements3D.Cone" :
			formeO.setSelectedIndex(2);
			break;
		case "class elements3D.Plan" :
			formeO.setSelectedIndex(3);
			setRefraction(false);
			break;
		case "class elements3D.Cylindre" :
			formeO.setSelectedIndex(4);
			break;
		case "class elements3D.Pave" :
			formeO.setSelectedIndex(5);
			break;
		}	
	}
	
	/**
	 * Mettre à jour les attributs avec un objet.
	 * @param no
	 */
	private void maj(Objet3D no) {
		if (no != null) {
			this.objet = (Objet3D) no.clone();
			String classO = objet.getClass().toString();
			
			selectionO(classO);
			
			Couleur couleur = objet.getMateriau().getCouleur();
			Reflectivite r = objet.getMateriau().getReflectivite();
			Refringence ra = objet.getMateriau().getRefringence();
			Point position = objet.getPosition();
			
			this.px = position.getX();
			this.py = position.getY();
			this.pz = position.getZ();
		    
			this.r = couleur.getRed();
			this.b = couleur.getBlue();
			this.v = couleur.getGreen();
			this.mycolor = new Color(this.r,this.v,this.b);
			
			this.iRR = ra.getIntensite();
			this.iRL = r.getIntensite();
			this.eRR = ra.getEnergie();
			this.eRL = r.getEnergie();
			this.indice = ra.getIndiceInterieur();
			
			this.onRL = r.isOn();
			this.onRR = ra.isOn();
			
			Vecteur v = objet.getRepartion();
			
			this.ka = v.getX();
			this.kd = v.getY();
			this.ks = v.getZ();
			this.bl = (int) objet.getBrillance();

			this.offA = objet.getAmbianceOn();
			this.offD = objet.getDiffusionOn();
			this.offS = objet.getSpeculariteOn();
			
			this.cA = obtenirVecteur(objet.getFiltreAmbiante());
			this.cD = obtenirVecteur(objet.getFiltreDiffuse());
			this.cS = obtenirVecteur(objet.getFiltreSpeculaire());
			
		} else {
			this.iRR = 0.0;
			this.iRL = 0.0;
			this.eRR = 0.0;
			this.eRL = 0.0;
			this.indice = 1.0;
			
			this.onRL = false;
			this.onRR = false;
			
			this.px = 0.0;
			this.py = 0.0;
			this.pz = 1.0;
			
			this.r = 255;
			this.b = 255;
			this.v = 255;
			
			this.ka = 0.2;
			this.ks = 0.2;
			this.kd = 0.6;
			this.bl = 50;
			
			this.cA = new Color(255,255,255);
			this.cD = new Color(255,255,255);
			this.cS = new Color(255,255,255);
			
			this.mycolor = new Color(255,255,255);
			
			this.offA = true;
			this.offD = true;
			this.offS = true;

		}
	}
	
	/**
	 * Transformer un vecteur en color
	 * @param v
	 * @return couleur.
	 */
	private Color obtenirVecteur(final Vecteur v) {
		return new Color((int) v.getX(),(int) v.getY(),(int) v.getZ());
	}
	
	/**
	 * Initialiser les positions, couleurs des composants de la fenetre.
	 */
	private void initialise() {
		
		jf = new JFrame("objet");
		this.setSize(795, 644);
	    this.setLocation(100, 25);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	    //Création du conteneur 
	    Container maincontainer = this.getContentPane();
		maincontainer.setBackground(new Color(0, 0, 139));
		getContentPane().setLayout(null);
	
	    
	    //Ajout du logo de l'application
		URL img = FenetreObjet.class.getResource("/IG/logo.png");
		ImageIcon icon = new ImageIcon(img);
		setIconImage(icon.getImage());
	    
		//Initalisation de la barre de menu 
	    JMenuBar menuBar = new JMenuBar();
	    menuBar.setBackground(new Color(65, 105, 225));
	    setJMenuBar(menuBar);
	    
	    // Ajout d'un menu fichier 
	    JMenu menu = new JMenu("Fichier");
	    menu.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
	    menu.setBackground(new Color(65, 105, 225));
	    menuBar.add(menu);
	    
	    // Ajout du bouton enregistrer avec l'action correspondante 
	    JMenuItem itemEnregistrer = new JMenuItem("Enregistrer objet");
	    itemEnregistrer.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
	    itemEnregistrer.addActionListener(new ActionEnregistrerO());
	    itemEnregistrer.setBackground(new Color(65, 105, 225));
	    menu.add(itemEnregistrer);
	    
	    //Ajout du bouton ouvrir avec l'action correspondante
	    JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
	    itemOuvrir.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
	    itemOuvrir.addActionListener(new ActionOuvrirObjet());
	    itemOuvrir.setBackground(new Color(65, 105, 225));
	    menu.add(itemOuvrir);
    
	    

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(17, 533, 754, 46);
		bottomPanel.setBorder(new LineBorder(new Color(0, 0, 139)));
		bottomPanel.setBackground(new Color(65, 105, 225));
		maincontainer.add(bottomPanel);
		bottomPanel.setLayout(null);
		
		//Bouton pour annuler et fermer la fenêtre sans modification de la scène
		JButton annuler = new JButton("Annuler");
		annuler.setBorder(new LineBorder(new Color(0, 0, 128)));
		annuler.setBackground(new Color(100, 149, 237));
		annuler.addActionListener(new ActionAnnuler());
		annuler.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		annuler.setBounds(10, 11, 200, 24);
		bottomPanel.add(annuler);
		
		// Bouton pour enregistrer l'objet en cours 
		JButton enregistrer = new JButton("Enregistrer");
		enregistrer.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		enregistrer.setBounds(334, 11, 200, 24);
		enregistrer.addActionListener(new ActionEnregistrerO());
		enregistrer.setBorder(new LineBorder(new Color(0, 0, 128)));
		enregistrer.setBackground(new Color(100, 149, 237));
		bottomPanel.add(enregistrer);
		
		// Boutton pour ajouter ou modifier l'objet créer
		ajouter.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		ajouter.setBounds(544, 11, 200, 24);
		ajouter.setBorder(new LineBorder(new Color(0, 0, 128)));
		ajouter.setBackground(new Color(100, 149, 237));	
		bottomPanel.add(ajouter);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(17, 11, 355, 511);
		leftPanel.setBorder(new LineBorder(new Color(0, 0, 139)));
		leftPanel.setBackground(new Color(65, 105, 225));
		maincontainer.add(leftPanel);
		leftPanel.setLayout(null);

		//Panel de parametre de objet
		JPanel objet = new JPanel();
		objet.setBounds(10, 192, 332, 214);
		objet.setBorder(new LineBorder(new Color(0, 0, 255), 1, true));
		objet.setBackground(new Color(135, 206, 250));
		objet.setLayout(null);
		leftPanel.add(objet);
		
		// Panel avec la couleur de l'objet
		JPanel couleur = new JPanel();
		couleur.setBorder(new LineBorder(Color.BLUE));
		couleur.setBackground(new Color(224, 255, 255));
		couleur.setLayout(null);
		couleur.setBounds(10, 108, 312, 97);
		objet.add(couleur);

		//Bouton pour choisir la couleur avec un JChooseColor
		JButton color = new JButton("Choisir couleur");
		color.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		color.setBorder(new LineBorder(new Color(0, 0, 128)));
		color.setBackground(new Color(100, 149, 237));
		color.setBounds(138, 63, 153, 23);
		color.addActionListener(new ActionChoisirCouleur());
		couleur.add(color);
		
		//Label avec écrit couleur
		JLabel lcouleur = new JLabel("Couleur:",JLabel.LEFT);
		lcouleur.setForeground(new Color(0, 0, 128));
		lcouleur.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
		lcouleur.setBounds(10, 19, 67, 14);
		couleur.add(lcouleur);
		
		//Spinner pour modifier le rouge d'une couleur 
		rouge.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		rouge.setBackground(new Color(100, 149, 237));
		rouge.setBounds(128, 14, 48, 25);
		rouge.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		//ajout de l'action qui modifie la couleur du bouton couleurObjet simultanément avec le spinner
		rouge.addChangeListener(new ChangerCouleur());
		couleur.add(rouge);
		
		// Spinner pour modifier le vert d'une couleur
		vert.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		vert.setBackground(new Color(100, 149, 237));
		vert.setBounds(186, 14, 48, 25);
		vert.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		vert.addChangeListener(new ChangerCouleur());
		couleur.add(vert);
		
		//Spinner pour modifier le bleu d'une couleur
		bleu.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		bleu.setBackground(new Color(100, 149, 237));
		bleu.setBounds(243, 14, 48, 25);
		bleu.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		bleu.addChangeListener(new ChangerCouleur());
		couleur.add(bleu);
		
		//Bouton permettant d'afficher en temps réel la couleur de l'objet
		couleurObjet.setBorder(new LineBorder(new Color(0, 0, 128)));
		couleurObjet.setBackground(this.mycolor);
		couleurObjet.setBounds(87, 14, 25, 25);
		couleurObjet.setLayout(null);
		couleur.add(this.couleurObjet);
		
		//Label rouge, vert, bleu indiquant à quoi correspond chaque spinner
		JLabel labelRouge = new JLabel("Rouge");
		labelRouge.setForeground(new Color(0, 0, 128));
		labelRouge.setHorizontalAlignment(SwingConstants.CENTER);
		labelRouge.setBounds(128, 0, 49, 14);
		couleur.add(labelRouge);
		labelRouge.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		
		JLabel labelVert = new JLabel("Vert");
		labelVert.setForeground(new Color(0, 0, 128));
		labelVert.setHorizontalAlignment(SwingConstants.CENTER);
		labelVert.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelVert.setBounds(185, 0, 49, 14);
		couleur.add(labelVert);
		
		JLabel labelBleu = new JLabel("Bleu");
		labelBleu.setForeground(new Color(0, 0, 128));
		labelBleu.setHorizontalAlignment(SwingConstants.CENTER);
		labelBleu.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelBleu.setBounds(242, 0, 49, 14);
		couleur.add(labelBleu);
		
		//JComboBox permettant de choisir la forme de l'objet
		formeO.addActionListener(new PlanSelectionner());
		formeO.setBackground(new Color(100, 149, 237));
		formeO.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		formeO.setToolTipText("Choisir la forme 3D de l'objet et clique droit pour modifier ses parametres");
		formeO.setBounds(10, 11, 128, 39);
		objet.add(formeO);
		
		//Ajout d'un popupMenu qui s'affiche lorsqu'on fait clique droit sur formeO
		//Il permet de modifier les paramètres de la forme choisie
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(formeO, popupMenu);
	
		JButton modifierPopup = new JButton("Modifier Param\u00E8tre");
		modifierPopup.addActionListener(new OuvrirFenetre());
		modifierPopup.setBackground(new Color(100, 149, 237));
		popupMenu.add(modifierPopup);
		
		//Bouton modifier parametre qui ouvre la page de la forme correspondante 
		JButton modifierParemetre = new JButton("Modifier Param\u00E8tres");
		modifierParemetre.setBorder(new LineBorder(new Color(0,0,205)));
		modifierParemetre.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		modifierParemetre.addActionListener(new OuvrirFenetre());
		modifierParemetre.setBackground(new Color(100, 149, 237));
		modifierParemetre.setBounds(148, 11, 174, 39);
		objet.add(modifierParemetre);
				
				
		//Panel pour le nom de objet
		JPanel name = new JPanel();
		name.setBounds(10, 61, 314, 36);
		name.setBorder(new LineBorder(Color.BLUE));
		name.setBackground(new Color(224, 255, 255));
		name.setLayout(null);
		objet.add(name);
		
		//JLabel avec le nom
		JLabel lname = new JLabel("Nom: ",JLabel.LEFT);
		lname.setForeground(new Color(0, 0, 128));
		lname.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
		lname.setBounds(10, 6, 83, 20);
		name.add(lname);
		
		// TextField qui permet d'entrer le nom choisi ou de l'afficher
		nom.addKeyListener(new ChangeNom());
		nom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		nom.setBounds(85, 6, 148, 25);
		name.add(nom);
		
		// Parametre de type position 
		JPanel parametreBase = new JPanel();
		parametreBase.setBounds(10, 417, 332, 78);
		parametreBase.setBorder(new LineBorder(new Color(0, 0, 255), 1, true));
		parametreBase.setBackground(new Color(135, 206, 250));
		parametreBase.setLayout(null);
		leftPanel.add(parametreBase);
		
		
		//Panel avec position
		JPanel position = new JPanel();
		position.setBounds(10, 11, 314, 51);
		position.setBorder(new LineBorder(Color.BLUE));
		position.setBackground(new Color(224, 255, 255));
		position.setLayout(null);
		parametreBase.add(position);
		
		//Label position
		JLabel lposition = new JLabel("Position:   ");
		lposition.setForeground(new Color(0, 0, 128));
		lposition.setFont(new Font("Century Schoolbook", Font.BOLD, 10));
		lposition.setBounds(10, 20, 66, 14);
		position.add(lposition);
		
		//Label x
		JLabel xnom = new JLabel("x");
		xnom.setForeground(new Color(0, 0, 128));
		xnom.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		xnom.setHorizontalAlignment(SwingConstants.CENTER);
		xnom.setBounds(99, 0, 44, 14);
		position.add(xnom);
		
		//Label y
		JLabel ynom = new JLabel("y");
		ynom.setForeground(new Color(0, 0, 128));
		ynom.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		ynom.setHorizontalAlignment(SwingConstants.CENTER);
		ynom.setBounds(181, 0, 44, 14);
		position.add(ynom);
		
		//Label z 
		JLabel znom = new JLabel("z");
		znom.setForeground(new Color(0, 0, 128));
		znom.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		znom.setHorizontalAlignment(SwingConstants.CENTER);
		znom.setBounds(262, 0, 44, 14);
		position.add(znom);

		//TextFied qui affiche la position en x de l'objet non modifiable
		x.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		x.setBackground(new Color(100, 149, 237));
		x.setEditable(false);
		x.setBackground(Color.WHITE);
		x.setBounds(99, 14, 44, 27);
		x.setBorder(new LineBorder(new Color(0,0,205)));
		x.setPreferredSize(new Dimension(30, 30));	
		position.add(x);
		
		//TextFied qui affiche la position en y de l'objet non modifiable
		y.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		y.setBackground(new Color(100, 149, 237));
		y.setEditable(false);
		y.setBackground(Color.WHITE);
		y.setBounds(181, 14, 44, 25);
		y.setBorder(new LineBorder(new Color(0,0,205)));
		y.setPreferredSize(new Dimension(30, 30));
		position.add(y);
		
		//TextFied qui affiche la position en z de l'objet non modifiable
		z.setFont(new Font("Times New Roman", Font.PLAIN, 9));
		z.setBackground(new Color(100, 149, 237));
		z.setEditable(false);
		z.setBackground(Color.WHITE);
		z.setBounds(262, 14, 44, 25);
		z.setBorder(new LineBorder(new Color(0,0,205)));
		z.setPreferredSize(new Dimension(30, 30));
		position.add(z);

		//Image de la fenetre
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 11, 332, 170);
		lblNewLabel.setIcon(new ImageIcon(FenetreObjet.class.getResource("/IG/class_libraries.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leftPanel.add(lblNewLabel);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 139)));
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(382, 11, 389, 511);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		//Parametre de materiau 
		JPanel materiaus = new JPanel();
		materiaus.setBounds(10, 328, 369, 172);
		panel.add(materiaus);
		materiaus.setBorder(new LineBorder(new Color(0, 0, 255)));
		materiaus.setBackground(new Color(135, 206, 250));
		materiaus.setLayout(null);
		
		//Text pour dire que ce sont les parametres de reflectivite
		JTextField lreflectivite = new JTextField("Reflectivite",JLabel.LEFT);
		lreflectivite.setEditable(false);
		lreflectivite.setHorizontalAlignment(SwingConstants.CENTER);
		lreflectivite.setFont(new Font("Century Schoolbook", Font.PLAIN, 8));
		lreflectivite.setBorder(new LineBorder(new Color(0, 0, 205)));
		lreflectivite.setForeground(new Color(0, 0, 205));
		lreflectivite.setBackground(new Color(224, 255, 255));
		lreflectivite.setBounds(20, 30, 84, 14);
		materiaus.add(lreflectivite);
		
		//Text pour dire que ce sont les parametres de refraction
		JTextField lrefraction = new JTextField("R\u00E9fraction", SwingConstants.LEFT);
		lrefraction.setEditable(false);
		lrefraction.setHorizontalAlignment(SwingConstants.CENTER);
		lrefraction.setFont(new Font("Century Schoolbook", Font.PLAIN, 8));
		lrefraction.setBorder(new LineBorder(new Color(0, 0, 205)));
		lrefraction.setBackground(new Color(224, 255, 255));
		lrefraction.setForeground(new Color(0, 0, 205));
		lrefraction.setBounds(20, 103, 84, 14);
		materiaus.add(lrefraction);
		
		
		//Panel pour reflectivite de materiau
		JPanel reflectivite = new JPanel();
		reflectivite.setBounds(10, 38, 349, 51);
		reflectivite.setBorder(new LineBorder(Color.BLUE));
		reflectivite.setBackground(new Color(224, 255, 255));
		reflectivite.setLayout(null);
		materiaus.add(reflectivite);
		
		//Spinner pour modifier l'intensité
		intensiteRefl.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		intensiteRefl.setBackground(new Color(100, 149, 237));
		intensiteRefl.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
		intensiteRefl.setBounds(92, 14, 50, 25);
		reflectivite.add(intensiteRefl);

		//Spinner pour modifier l'énergie
		energieRefl.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		energieRefl.setBackground(new Color(100, 149, 237));
		energieRefl.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
		energieRefl.setBounds(170, 14, 50, 25);
		reflectivite.add(energieRefl);
		
		//Label pour indiquer à quoi ça correspond intensite et energie 
		JLabel labelIntensiteRF = new JLabel("Intensite ");
		labelIntensiteRF.setForeground(new Color(0, 0, 128));
		labelIntensiteRF.setHorizontalAlignment(SwingConstants.CENTER);
		labelIntensiteRF.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelIntensiteRF.setBounds(93, 0, 49, 14);
		reflectivite.add(labelIntensiteRF);
		
		JLabel labelEnergieRF = new JLabel("Energie ");
		labelEnergieRF.setForeground(new Color(0, 0, 128));
		labelEnergieRF.setHorizontalAlignment(SwingConstants.CENTER);
		labelEnergieRF.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelEnergieRF.setBounds(171, 0, 49, 14);
		reflectivite.add(labelEnergieRF);

		//JCheckbox pour activer ou pas la reflextion 
		activeRefl.setFont(new Font("Century Schoolbook", Font.PLAIN, 11));
		activeRefl.setBounds(6, 14, 46, 25);
		activeRefl.setBackground(new Color(100, 149, 237));
		activeRefl.setSelected(onRL);
		reflectivite.add(activeRefl);
		
		//Titre du panel
		JLabel lobjet = new JLabel("Propri\u00E9t\u00E9 du Mat\u00E9riaux ",JLabel.CENTER);
		lobjet.setForeground(new Color(0, 0, 128));
		lobjet.setFont(new Font("Century Schoolbook", Font.BOLD, 14));
		lobjet.setBounds(10, 0, 349, 27);
		materiaus.add(lobjet);
		
		//Panel pour la refraction
		JPanel refraction = new JPanel();
		refraction.setLayout(null);
		refraction.setBorder(new LineBorder(Color.BLUE));
		refraction.setBackground(new Color(224, 255, 255));
		refraction.setBounds(10, 110, 349, 51);
		materiaus.add(refraction);
		
		//Spinner pour modifier l'intensite
		intensiteRefr.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		intensiteRefr.setBackground(new Color(100, 149, 237));
		intensiteRefr.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
		intensiteRefr.setBounds(92, 14, 50, 25);
		refraction.add(intensiteRefr);
		
		//label pour indiquer qu'il s'agit de l'intensite
		JLabel labelIntensiteRR = new JLabel("Intensite ");
		labelIntensiteRR.setForeground(new Color(0, 0, 128));
		labelIntensiteRR.setHorizontalAlignment(SwingConstants.CENTER);
		labelIntensiteRR.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelIntensiteRR.setBounds(93, 0, 49, 14);
		refraction.add(labelIntensiteRR);

		//Spinner pour modifier l'energie
		energieRefra.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		energieRefra.setBackground(new Color(100, 149, 237));
		energieRefra.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
		energieRefra.setBounds(174, 14, 50, 25);
		refraction.add(energieRefra);
		
		//Label pour indiquer qu'il s'agit de l'intensite
		JLabel labelEnergieRR = new JLabel("Energie ");
		labelEnergieRR.setForeground(new Color(0, 0, 128));
		labelEnergieRR.setHorizontalAlignment(SwingConstants.CENTER);
		labelEnergieRR.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelEnergieRR.setBounds(175, 0, 49, 14);
		refraction.add(labelEnergieRR);
	
		//Spinner pour modifier l'indice de refraction
		indiceRR.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		indiceRR.setBackground(new Color(100, 149, 237));
		indiceRR.setModel(new SpinnerNumberModel(1.0, 1.0, 100, 0.01));
		indiceRR.setBounds(259, 14, 50, 25);
		refraction.add(indiceRR);
		
		//CheckBox pour activer ou non la refraction
		activeRefr.setFont(new Font("Century Schoolbook", Font.PLAIN, 11));
		activeRefr.setBackground(new Color(100, 149, 237));
		activeRefr.setBounds(6, 14, 46, 25);
		activeRefr.setSelected(onRR);
		refraction.add(activeRefr);
		
		//Label pour indiquer qu'il s'agit de l'indice
		JLabel labelIndiceMilieuRR = new JLabel("Indice de r\u00E9fraction");
		labelIndiceMilieuRR.setForeground(new Color(0, 0, 128));
		labelIndiceMilieuRR.setHorizontalAlignment(SwingConstants.CENTER);
		labelIndiceMilieuRR.setFont(new Font("Century Schoolbook", Font.ITALIC, 8));
		labelIndiceMilieuRR.setBounds(234, 0, 91, 14);
		refraction.add(labelIndiceMilieuRR);
		
		//Panel pour les parametres du modele de Phong
		JPanel modelPhong = new JPanel();
		modelPhong.setLayout(null);
		modelPhong.setBorder(new LineBorder(new Color(0, 0, 255)));
		modelPhong.setBackground(new Color(135, 206, 250));
		modelPhong.setBounds(10, 11, 369, 299);
		panel.add(modelPhong);
		
		//Texts non modifiables avec les différentes titres des sections
		
		//Titre specularite
		JTextField lblSpeculaire = new JTextField("Sp\u00E9curalit\u00E9 ");
		lblSpeculaire.setEditable(false);
		lblSpeculaire.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeculaire.setFont(new Font("Century Schoolbook", Font.PLAIN, 8));
		lblSpeculaire.setBorder(new LineBorder(new Color(0, 0, 205)));
		lblSpeculaire.setForeground(new Color(0, 0, 205));
		lblSpeculaire.setBackground(new Color(224, 255, 255));
		lblSpeculaire.setBounds(20, 94, 84, 14);
		modelPhong.add(lblSpeculaire);
		
		//Titre ambiance 
		JTextField lblAmbiante = new JTextField("Ambiance ");
		lblAmbiante.setEditable(false);
		lblAmbiante.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmbiante.setFont(new Font("Century Schoolbook", Font.PLAIN, 8));
		lblAmbiante.setBorder(new LineBorder(new Color(0, 0, 205)));
		lblAmbiante.setBounds(20, 229, 84, 14);
		modelPhong.add(lblAmbiante);
		lblAmbiante.setForeground(new Color(0, 0, 205));
		lblAmbiante.setBackground(new Color(224, 255, 255));
		
		//Titre brillance
		JTextField lblBrillance = new JTextField("Brillance ");
		lblBrillance.setEditable(false);
		lblBrillance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrillance.setFont(new Font("Century Schoolbook", Font.PLAIN, 8));
		lblBrillance.setBorder(new LineBorder(new Color(0, 0, 205)));
		lblBrillance.setForeground(new Color(0, 0, 205));
		lblBrillance.setBackground(new Color(224, 255, 255));
		lblBrillance.setBounds(20, 30, 84, 14);
		modelPhong.add(lblBrillance);
		
		//Titre difusion
		JTextField lblDifusion = new JTextField("Diffusion ");
		lblDifusion.setEditable(false);
		lblDifusion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDifusion.setFont(new Font("Century Schoolbook", Font.PLAIN, 8));
		lblDifusion.setBorder(new LineBorder(new Color(0, 0, 205)));
		lblDifusion.setForeground(new Color(0, 0, 205));
		lblDifusion.setBackground(new Color(224, 255, 255));
		lblDifusion.setBounds(20, 162, 84, 14);
		modelPhong.add(lblDifusion);
		
		//Panel Brillance
		JPanel panelB = new JPanel();
		panelB.setLayout(null);
		panelB.setBorder(new LineBorder(Color.BLUE));
		panelB.setBackground(new Color(224, 255, 255));
		panelB.setBounds(10, 38, 349, 45);
		modelPhong.add(panelB);
		
		//Spinner pour modifier la brillance
		brillance.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		brillance.setBackground(new Color(100, 149, 237));
		brillance.setBounds(117, 11, 93, 25);
		brillance.setModel(new SpinnerNumberModel(0, 0, null, 1));
		panelB.add(brillance);
		
		//Titre de la section Parametre de Phong
		JLabel lblParamtresModelDe = new JLabel("Param\u00E8tres Mod\u00E8le de Phong", SwingConstants.CENTER);
		lblParamtresModelDe.setForeground(new Color(0, 0, 128));
		lblParamtresModelDe.setFont(new Font("Century Schoolbook", Font.BOLD, 14));
		lblParamtresModelDe.setBounds(10, 0, 349, 27);
		modelPhong.add(lblParamtresModelDe);
		
		//Panel pour la spécularite
		JPanel panelS = new JPanel();
		panelS.setLayout(null);
		panelS.setBorder(new LineBorder(Color.BLUE));
		panelS.setBackground(new Color(224, 255, 255));
		panelS.setBounds(10, 102, 349, 45);
		modelPhong.add(panelS);
		
		//Spinner pour modifier la part de spécularité
		sks.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		sks.setBackground(new Color(100, 149, 237));
		sks.setBounds(70, 12, 62, 25);
		sks.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.01));
		panelS.add(sks);		
		
		//Bouton pour ouvrir un JChooseColor pour la couleur du filtre
		colorS = new JButton("Choisir Filtre ");
		colorS.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		colorS.setBorder(new LineBorder(new Color(0, 0, 128)));
		colorS.addActionListener(new ChoisirFiltre());
		colorS.setBackground(new Color(100, 149, 237));
		colorS.setBounds(186, 12, 153, 23);
		panelS.add(colorS);
		
		//Bouton pour voir la couleur du filtre
		couleurS.setBounds(151, 12, 25, 25);	
		couleurS.setBorder(new LineBorder(new Color(0, 0, 128)));
		panelS.add(couleurS);
		
		//Checkbox pour activer la specularite
		onS.setFont(new Font("Century Schoolbook", Font.PLAIN, 11));
		onS.setBackground(new Color(100, 149, 237));
		onS.setBounds(6, 13, 49, 25);
		panelS.add(onS);
		
		//Panel diffusion
		JPanel panelD = new JPanel();
		panelD.setBounds(10, 170, 349, 45);
		panelD.setLayout(null);
		panelD.setBorder(new LineBorder(Color.BLUE));
		panelD.setBackground(new Color(224, 255, 255));
		modelPhong.add(panelD);
		
		//Spinner pour set la part de diffusion
		skd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		skd.setBackground(new Color(100, 149, 237));
		skd.setBackground(new Color(100, 149, 237));
		skd.setBounds(70, 13, 60, 25);
		skd.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.01));
		panelD.add(skd);
		
		//Bouton pour ouvrir un JChooseColor pour la couleur du filtre
		colorD = new JButton("Choisir Filtre");
		colorD.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		colorD.setBorder(new LineBorder(new Color(0, 0, 128)));
		colorD.addActionListener(new ChoisirFiltre());
		colorD.setBackground(new Color(100, 149, 237));
		colorD.setBounds(186, 13, 153, 23);
		panelD.add(colorD);
		
		//Bouton pour voir la couleur du filtre
		couleurD.setBorder(new LineBorder(new Color(0, 0, 128)));
		couleurD.setBounds(151, 13, 25, 25);
		panelD.add(couleurD);
		
		//JCheckBox pour activer ou non la diffusion
		onD.setFont(new Font("Century Schoolbook", Font.PLAIN, 11));
		onD.setBackground(new Color(100, 149, 237));
		onD.setBounds(6, 14, 49, 25);
		panelD.add(onD);

		//Panel ambiance
		JPanel panelA = new JPanel();
		panelA.setLayout(null);
		panelA.setBorder(new LineBorder(Color.BLUE));
		panelA.setBackground(new Color(224, 255, 255));
		panelA.setBounds(10, 236, 349, 45);
		modelPhong.add(panelA);
		
		//Bouton pour ouvrir un JChooseColor pour la couleur du filtre
		colorA = new JButton("Choisir Filtre");
		colorA.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		colorA.setBorder(new LineBorder(new Color(0, 0, 128)));
		colorA.addActionListener(new ChoisirFiltre());
		colorA.setBackground(new Color(100, 149, 237));
		colorA.setBounds(186, 13, 153, 23);
		panelA.add(colorA);
		
		//Spinner pour set la valeur de la part d'ambiance
		ska.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		ska.setBackground(new Color(100, 149, 237));
		ska.setBounds(68, 12, 58, 25);
		ska.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.01));
		ska.setBackground(new Color(100, 149, 237));
		panelA.add(ska);
		
		//Bouton pour avoir un aperçu de la couleur du filtre
		couleurA.setBounds(151, 12, 25, 25);
		couleurA.setBorder(new LineBorder(new Color(0, 0, 128)));
		panelA.add(couleurA);
		
		//JCheckBor pour activer ou non l'ambiance
		onA.setFont(new Font("Century Schoolbook", Font.PLAIN, 11));
		onA.setBackground(new Color(100, 149, 237));
		onA.setBounds(6, 14, 49, 25);
		panelA.add(onA);
		
		
    }	
	
	private class ActionAnnuler implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			dispose();
		}
	}
	
	//-------------------------------------------------------------------------------------------------------------------
	//Rendre enable les spinners lies à la refraction lorsqu'on selectionne plan
	
	/**
	 * Rendre non modifiable la refraction pour un plan.
	 * @author rapha
	 *
	 */
	public class PlanSelectionner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int i = formeO.getSelectedIndex();
			switch(i) {
			case 0 :
				setRefraction(true);
				break;
			case 1 :
				setRefraction(true);
				break;
			case 2 :		
				setRefraction(true);
				break;
			case 5 : 
				setRefraction(true);
				break;
			case 4 :
				setRefraction(true);
				break;
			case 3 : 
				setRefraction(false);
				break;
			}
			
		}
	}
	
	/**
	 * SetEnabled tous les spinners et checkbox lies à la refraction.
	 * @param b
	 */
	private void setRefraction(Boolean b) {
		intensiteRefr.setEnabled(b);
		energieRefra.setEnabled(b);
		activeRefr.setEnabled(b);
		indiceRR.setEnabled(b);
	}
	
	//------------------------------------------------------------------------------------------------------
	
	/**
	 * Change le nom.
	 * Change le nom de l'objet quand on clique sur entree.
	 * @author rapha
	 *
	 */
	class ChangeNom implements KeyListener {
		
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode()==KeyEvent.VK_ENTER){
		    	JTextField valeur = (JTextField) e.getSource();
				if(objet != null) {
					objet.setNom(valeur.getText());
				}
		    }
		}

		@Override
		public void keyTyped(KeyEvent e) {
		    if (e.getKeyCode()==KeyEvent.VK_ENTER){
		    	JTextField valeur = (JTextField) e.getSource();
				if(objet != null) {
					objet.setNom(valeur.getText());
				}
		    }
		}

		@Override
		public void keyReleased(KeyEvent e) {
		    if (e.getKeyCode()==KeyEvent.VK_ENTER){
		    	JTextField valeur = (JTextField) e.getSource();
				if(objet != null) {
					objet.setNom(valeur.getText());
				}
		    }
			
		}
	}
	
	//----------------------------------------------------------------------------------------------------------
	//Actions relatives à la couleur 
	
	/**
	 * Changer la couleur du bouton d'aperçu de la couleur de l'objet.
	 * Ce fait en meme temps que lorsqu'on appuie sur un spinner couleur (vert,rouge ou bleu).
	 * @author rapha
	 *
	 */
	class ChangerCouleur implements ChangeListener {

		public void stateChanged(ChangeEvent event) {
			//Récupération du spinner source
			JSpinner valeur = (JSpinner) event.getSource();
			//identification du spinner et modification de la couleur 
			if( valeur == rouge) {
				r = (int)valeur.getValue();
				mycolor = new Color(r,v,b);
				couleurObjet.setBackground(mycolor);
			} else if (valeur == vert) {
				v = (int)valeur.getValue();
				mycolor = new Color(r,v,b);
				couleurObjet.setBackground(mycolor);			
			} else {
				b = (int)valeur.getValue();
				mycolor = new Color(r,v,b);
				couleurObjet.setBackground(mycolor);
			}
		}
	}
	
	/**
	 * Action pour choisir la couleur de l'objet à l'aide d'un JColorChooser. 
	 * Ouvre la fenêtre de dialog pour choisir une couleur et modification de la couleur du bouton.
	 * @author rapha
	 *
	 */
	private class ActionChoisirCouleur implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mycolor = JColorChooser.showDialog(jf,"Swing color chooser", mycolor);
			//Si une couleur est selectionner et que l'utilisateur a appuyé sur ok, on fait la modification
			if (mycolor != null) {
				int r = mycolor.getRed();
				int g = mycolor.getGreen();
				int b = mycolor.getBlue();
				rouge.setValue(Integer.valueOf(r));
				vert.setValue(Integer.valueOf(g));
				bleu.setValue(Integer.valueOf(b));
				couleurObjet.setBackground(mycolor);
			}
			
		}
	}
	
	/**
	 * Choisir la couleur du filtre.
	 * Ouvre la fenetre de dialog pour chosiir couleur et modifie la couleur du bouton.
	 * @author rapha
	 *
	 */
	private class ChoisirFiltre implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			JButton b = (JButton) ev.getSource();			
			if (b == colorA) {
				Color c = JColorChooser.showDialog(jf,"Swing color chooser", cA);
				if (c != null) {
					couleurA.setBackground(c);
					cA = c;
				}			
			} else if (b == colorD) {
				Color c = JColorChooser.showDialog(jf,"Swing color chooser", cD);
				if (c != null) {
					couleurD.setBackground(c);
					cD = c;
				}	
			} else {
				Color c = JColorChooser.showDialog(jf,"Swing color chooser", cS);
				if (c != null) {
					couleurS.setBackground(c);
					cS = c;
				}	
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//Action pour le popup menu (ajout automatique)
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	//------------------------------------------------------------------------------------------------------------
	//Action pour ouvrir les fenetres de chaque forme 
	
	/**
	 * Recuperer la donnees d'un fenetre forme ouverte.
	 * @param f
	 */
	private void recupererDonnee(FenetreObjet3D f) {
		Objet3D objetcreer11 = f.getDonnees();
		if (objetcreer11 != null) {
			objet = f.getDonnees();
			nom.setText(objet.getNom());
			Point position = objet.getPosition();
			px = position.getX();
			x.setText(((Double)px).toString());
			py = position.getY();
			y.setText(((Double)py).toString());
			pz = position.getZ();
			z.setText(((Double)pz).toString());
		}
	}
	
	/**
	 * Ouvrir fenetre objet.
	 * Ouvre la fenetre correspondant à la forme selectionner dans la combobox.
	 * Recupère la donnée créée.
	 * @author rapha
	 *
	 */
	public class OuvrirFenetre implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			int selected = (int) formeO.getSelectedIndex();
			switch(selected) {
			
			case 0 :
				FenetreSphere f = new FenetreElements3D.FenetreSphere(objet);
				f.setModal(true);
				f.setVisible(true);
				recupererDonnee(f);
				break;
			case 1 :
				FenetreCube f1 = new FenetreCube(objet);
				f1.setModal(true);
				f1.setVisible(true);
				recupererDonnee(f1);
				break;
			case 2 : 
				FenetreCone f2 = new FenetreCone(objet);
				f2.setModal(true);
				f2.setVisible(true);
				recupererDonnee(f2);
				break;
			case 3 : 
				FenetrePlan f3 = new FenetrePlan(objet);
				f3.setModal(true);
				f3.setVisible(true);
				recupererDonnee(f3);
				break;
			case 4 :
				FenetreCylindre f4 = new FenetreCylindre(objet);
				f4.setModal(true);
				f4.setVisible(true);
				recupererDonnee(f4);
				break;
			case 5 : 
				FenetrePave f5 = new FenetrePave(objet);
				f5.setModal(true);
				f5.setVisible(true);
				recupererDonnee(f5);
			default : 
				
			}
				
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//Action pour enregistrer et ouvrir un objet 
	
	/**
	 * Enregistrer un objet.
	 * Ouvre un fenetre enregistrer Objet.
	 * @author rapha
	 *
	 */
	public class ActionEnregistrerO implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			modifierObjet();
			EnregistrerObjet enregistrer = new EnregistrerObjet(objet);
			enregistrer.setVisible(true);
		}
	}
	
	/**
	 * Ouvrir un objet enregistrer.
	 * Ouvre une fenetre ouvrir objet.
	 * @author rapha
	 *
	 */
	public class ActionOuvrirObjet implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			OuvrirObjet ouvrir = new OuvrirObjet();
			ouvrir.setVisible(true);
			ouvrir.addWindowListener(new fenetreFermer(ouvrir));
		}
	}
	

	/**
	 * Action relier à l'état de la fenêtre.
	 * Lorsqu'on ferme une fenetre ouvir objet, permet de faire les modification des spinners.
	 * @author rapha
	 *
	 */
	public class fenetreFermer implements WindowListener {
		
		private OuvrirObjet ouvrir; 
		
		public fenetreFermer(OuvrirObjet o) {
			ouvrir = o;
		}
		public void windowClosed(WindowEvent e) {
			Objet3D o = ouvrir.getObjet();
			maj(o);
			setValeur();
		}
		
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	//Action pour ajouter ou modifier l'objet courant 
	
	/**
	 * Modifie les valeurs de l'objet.
	 */
	private void modifierObjet() {
		
		//Brillance
		objet.setBrillance((int) brillance.getValue());
		
		//Filtre du Modèle de Phong
		objet.setFiltreAmbiante((int) cA.getRed(), cA.getGreen(), cA.getBlue());
		objet.setFiltreDiffuse((int) cD.getRed(), cD.getGreen(), cD.getBlue());
		objet.setFiltreSpeculaire((int) cS.getRed(), cS.getGreen(), cS.getBlue());
		
		//Part du modèle de phong
		objet.setRepartion((double) ska.getValue(), (double) skd.getValue(), (double) sks.getValue());
		
		//Activer ou non les différents parametres du modele de phong
		objet.setAmbianceOn(onA.isSelected());
		objet.setDiffusionOn(onD.isSelected());
		objet.setSpeculariteOn(onS.isSelected());
		
		//Modifier la refringence de l'objet
		Refringence ra = objet.getMateriau().getRefringence();
		ra.setEnergie((double)energieRefra.getValue());
		ra.setIntensite((double)intensiteRefr.getValue());
		ra.setIndiceInterieur((double)indiceRR.getValue());
		ra.setOn(activeRefr.isSelected());
		
		//Modifier la reflectivite de l'objet
		Reflectivite r = objet.getMateriau().getReflectivite(); 
		r.setEnergie((double)energieRefl.getValue());
		r.setIntensite((double) intensiteRefl.getValue());
		r.setOn(activeRefl.isSelected());
		
		//Modifier la couleur
		Couleur c = (Couleur) objet.getMateriau().getCouleur();
		c.set(mycolor);
		
			
	}

	/**
	 * Modifier un objet.
	 * Modifie l'objet selectionné et signaler si des erreurs de remplissages.
	 * @author rapha
	 *
	 */
	public class ActionModifierObjet implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
		
		try {
			modifierObjet();
		    Object[] options = {"Oui", "Non"};
		    //verification que l'objet n'est pas hors scène
			if(objet.estHorsScene(rayTracing.getScene().getDimension(), new Point(0.0,0.0,0.0))) {
		    	int n = JOptionPane.showOptionDialog(new JFrame(),
		    		"Attention ! L'objet est hors scene.\n Voulez-vous toujours la modifier ? ",
		    	    "Objet hors scene  ",
		    	    JOptionPane.WARNING_MESSAGE, 
		    	    JOptionPane.YES_NO_OPTION,
		    	    null,    
		    	    options,  
		    	    options[0]); 
		    	if (n == 0) {;
					rayTracing.getScene().getObjets().set(index, objet);
					listeO.getObjets().set(index, new Objet(objet));
		    		grilleO.updateUI();
		    		dispose();
		    	}
			} else {		
				rayTracing.getScene().getObjets().set(index, objet);
				listeO.getObjets().set(index, new Objet(objet));
	    		grilleO.updateUI();
	    		dispose();
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, e, "Vous devez remplir toutes les informations ! ", JOptionPane.WARNING_MESSAGE);
		}

		}
	}
	
	/**
	 * Ajouter un objet à la scène.
	 * On l'ajoute si aucune erreur n'est detectée.
	 * @author rapha
	 *
	 */
	public class ActionAjouterObjet implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			try {
				modifierObjet();
				rayTracing.getScene().addObjet3D(objet);
		    	listeO.addElement(new Objet(objet));
	    		grilleO.updateUI();
				dispose();
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, e, "Vous devez remplir toutes les informations ! ", JOptionPane.WARNING_MESSAGE);
			} catch (Objet3DHorsSceneException e) {
				Object[] options = {"Oui", "Non"};
		    	int n = JOptionPane.showOptionDialog(new JFrame(),
		    		"Attention ! L'objet que vous voulez ajouter est hors scene.\n Voulez-vous toujours l'ajouter ? ",
		    	    "Objet hors scene  ",
		    	    JOptionPane.WARNING_MESSAGE, 
		    	    JOptionPane.YES_NO_OPTION,
		    	    null,    
		    	    options,  
		    	    options[0]); 
		    	if (n == 0) {
		    		listeO.addElement(new Objet(objet));
		    		grilleO.updateUI();
		    		dispose();
		    	} else {
		    		int p = rayTracing.getScene().getObjets().size() - 1;
		    		rayTracing.getScene().getObjets().remove(p);
		    	}
			}

		}
	}
	
	//---------------------------------------------------------------------------------------------------------
	//Main pour ouvrir une fenetre objet
	public static void main(String[] args) {
		FenetreObjet fenetreobjet = new FenetreObjet(null, null, null);
		fenetreobjet.setVisible(true);
	}	
}
