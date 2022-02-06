package IG;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import elements3D.*;
import exception.FocaleNegativeException;
import rayTracing.Camera;
import rayTracing.Lumiere;
import rayTracing.RayTracing;
import rayTracing.Scene;
import utilitaire.Point;
import utilitaire.Vecteur;

import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JTextField;

public class FenetrePrincipale {
	
	/** Fenetre de l'application.*/
	private JFrame frame;
	
	/**Liste des objets présents dans la scène.*/
	private ListeObjets listeO;
	
	/**Liste des lumières présentes dans la scène.*/
	private ListeLumieres listeL;
	
	/**JList affichant la liste d'objet et associé à ListeObjets.*/
	private JList<Objet> listObjets;
	
	/**JList affichant la liste de lumières et associé à ListeLumieres.*/
	private JList<Lumieres> listLumieres;
	
	/**RayTracing de la fenêtre ouvert.*/
	private RayTracing rayTracing;
	
	/**Label affichant le résultat du Ray Tracing (image).*/
	private JLabel imageRT;
	
	/**Barre de changement du calcul.*/
	private JProgressBar progressBar;
	
	
	/**Panel contenant imageRT et permettant d'ajouter/supprimer la barre de progression.*/
	private JPanel panelImage;
	
	//---------------------------------------------------------------------------------------------------------------
	//Main 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scene scene = new Scene(300);
					scene.setAmbiante(new Couleur( Color.white));
					Camera camera = new Camera(new Point(20,0,5),new Vecteur(-10,0,0),1000,1000,new Vecteur(0,0,10)); //vHaut = (0,0,10) sur l'exemple geogebra
					RayTracing raytracing = new RayTracing(scene, camera, 10, true, true);
					FenetrePrincipale window = new FenetrePrincipale(raytracing);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	//Initialisation et construction de la fenêtre 
	
	/**
	 * rendre visible la fenetre de l'application.
	 */
	public void lancerFenetre() {
		this.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public FenetrePrincipale(RayTracing nrt) {
		this.rayTracing = nrt;
		this.listeO = new ListeObjets();
		listeO.initialiser(rayTracing.getScene().getObjets());
		this.listeL = new ListeLumieres();
		listeL.initialiser(rayTracing.getScene().getLumieres());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Su7");
		URL img = FenetrePrincipale.class.getResource("/IG/logo.png");
		ImageIcon icon = new ImageIcon(img);
		frame.setIconImage(icon.getImage());
		frame.setSize(new Dimension(1087, 674));
		frame.setResizable(false);
		frame.setBackground(new Color(32, 178, 170));
		frame.getContentPane().setForeground(new Color(102, 205, 170));
		frame.getContentPane().setBackground(new Color(32, 178, 170));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuBar.setBackground(new Color(32, 178, 170));
		frame.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Fichier");
		menuFile.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		menuFile.setForeground(new Color(0, 0, 0));
		menuFile.setMnemonic( 'F' );
		menuFile.setBackground(new Color(32, 178, 170));
		menuBar.add(menuFile);
		
		JMenuItem itemEnregistrerS = new JMenuItem("Enregistrer Sc\u00E8ne");
		itemEnregistrerS.setForeground(new Color(0, 0, 0));
		itemEnregistrerS.setBackground(new Color(32, 178, 170));
		itemEnregistrerS.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		itemEnregistrerS.addActionListener(new ActionEnregistrerS());
		itemEnregistrerS.setMnemonic( 'S' );
		menuFile.add( itemEnregistrerS);
		
		JMenuItem  itemOuvrir = new JMenuItem("Ouvrir ");
		itemOuvrir.setForeground(new Color(0, 0, 0));
		itemOuvrir.setBackground(new Color(32, 178, 170));
		itemOuvrir.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		itemOuvrir.addActionListener(new ActionOuvrirScene());
		itemOuvrir.setMnemonic( 'O' );
		menuFile.add(itemOuvrir);
		
		JMenuItem  itemEnregistrerI = new JMenuItem("Capture Image");
		itemEnregistrerI.setForeground(new Color(0, 0, 0));
		itemEnregistrerI.setBackground(new Color(32, 178, 170));
		itemEnregistrerI.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		itemEnregistrerI.addActionListener(new ActionEnregistrerI());
		itemEnregistrerI.setMnemonic( 'C' );
		menuFile.add( itemEnregistrerI);
		
		JMenu menuEditer = new JMenu("Editer");
		menuEditer.setBackground(new Color(32, 178, 170));
		menuEditer.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		menuEditer.setForeground(new Color(0, 0, 0));
		menuEditer.setMnemonic( 'E' );
		menuBar.add(menuEditer);
		
		JMenuItem itemAjouterO = new JMenuItem("Ajouter Objet");
		itemAjouterO.setForeground(new Color(0, 0, 0));
		itemAjouterO.setBackground(new Color(32, 178, 170));
		itemAjouterO.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		itemAjouterO.setMnemonic( 'B' );
	    itemAjouterO.addActionListener(new ActionAjouterObjet());
		menuEditer.add(itemAjouterO);
		
		JMenuItem itemAjouterL = new JMenuItem("Ajouter Lumiere");
		itemAjouterL.setForeground(new Color(0, 0, 0));
		itemAjouterL.setBackground(new Color(32, 178, 170));
		itemAjouterL.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		itemAjouterL.setMnemonic( 'L' );
		itemAjouterL.addActionListener(new ActionAjouterLumiere());
		menuEditer.add(itemAjouterL);
		
		frame.getContentPane().setLayout(null);
		
		panelImage = new JPanel();
		panelImage.setForeground(new Color(64, 224, 208));
		panelImage.setBorder(new LineBorder(new Color(64, 224, 208)));
		panelImage.setBackground(Color.WHITE);
		panelImage.setBounds(10, 71, 826, 473);
		frame.getContentPane().add(panelImage);
		panelImage.setLayout(null);
		
		JSlider zoom = new JSlider();
		zoom.setFont(new Font("Century Schoolbook", Font.ITALIC, 10));
		zoom.setSnapToTicks(true);
		zoom.setBorder(new TitledBorder(new LineBorder(new Color(47, 79, 79)), "Zoom", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(47, 79, 79)));
		zoom.setMaximum(1000);
		zoom.addChangeListener(new ActionZoom());
		zoom.setForeground(new Color(0, 139, 139));
		
		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(0, 0, 0));
		progressBar.setIndeterminate(true);
		progressBar.setBounds(346, 235, 146, 9);
		
		zoom.setValue((int) rayTracing.getCamera().getFocale());
		zoom.setMinimum(1);
		zoom.setMajorTickSpacing(10);
		zoom.setMinorTickSpacing(1);
		zoom.setBackground(new Color(72, 209, 204));
		zoom.setBounds(578, 415, 238, 47);
		panelImage.add(zoom);
		
		this.imageRT = new JLabel("");
		imageRT.setBounds(0, 0, 826, 473);
		panelImage.add(imageRT);
		
		JPanel panelInformation = new JPanel();
		panelInformation.setBackground(new Color(72, 209, 204));
		panelInformation.setBorder(new LineBorder(new Color(64, 224, 208), 1, true));
		panelInformation.setBounds(846, 71, 217, 507);
		frame.getContentPane().add(panelInformation);
		panelInformation.setLayout(null);
		
		JButton plusObjet = new JButton("+");
		plusObjet.setBorder(new LineBorder(new Color(47, 79, 79)));
		plusObjet.setFont(new Font("Century Schoolbook", Font.BOLD, 11));
	   	plusObjet.addActionListener(new ActionAjouterObjet());
		plusObjet.setBackground(new Color(0, 139, 139));
		plusObjet.setBounds(166, 11, 41, 42);
		panelInformation.add(plusObjet);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 52, 197, 125);
		panelInformation.add(scrollPane);
		listObjets = new JList<Objet>(this.listeO);
		listObjets.setBorder(new LineBorder(new Color(47, 79, 79)));
		scrollPane.setViewportView(listObjets);
		
		listObjets.addMouseListener(new ActionOuvrirObjet());
		listObjets.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		JPopupMenu popupMenuO = new JPopupMenu();
		popupMenuO.setBorder(new LineBorder(new Color(47, 79, 79)));
		addPopup(listObjets, popupMenuO);
		
		JMenuItem supprimerObjet = new JMenuItem("Supprimer");
		supprimerObjet.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		supprimerObjet.addActionListener(new ActionSupprimerObjet());
		popupMenuO.add(supprimerObjet);
		
		JMenuItem dupliquerObjet = new JMenuItem("Dupliquer");
		dupliquerObjet.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		dupliquerObjet.addActionListener(new DupliquerObjet());
		popupMenuO.add(dupliquerObjet);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setBounds(10, 222, 197, 125);
		panelInformation.add(scrollPane_1);
		listLumieres = new JList<Lumieres>(this.listeL);
		listLumieres.setBorder(new LineBorder(new Color(47, 79, 79)));
		scrollPane_1.setViewportView(listLumieres);
		
		listLumieres.addMouseListener(new ActionOuvrirLumiere());
		listLumieres.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		JPopupMenu popupMenuL = new JPopupMenu();
		popupMenuL.setBorder(new LineBorder(new Color(47, 79, 79)));
		popupMenuL.setForeground(new Color(0, 0, 0));
		popupMenuL.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		popupMenuL.setBorderPainted(false);
		addPopup(listLumieres, popupMenuL);
		
		JMenuItem supprimerLumiere = new JMenuItem("Supprimer");
		supprimerLumiere.setHorizontalAlignment(SwingConstants.LEFT);
		supprimerLumiere.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		supprimerLumiere.addActionListener(new ActionSupprimerLumiere());
		popupMenuL.add(supprimerLumiere);
		
		JMenuItem dupliquerLumiere = new JMenuItem("Dupliquer");
		dupliquerLumiere.setHorizontalAlignment(SwingConstants.LEFT);
		dupliquerLumiere.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
		dupliquerLumiere.addActionListener(new DupliquerLumiere());
		popupMenuL.add(dupliquerLumiere);
		
		JButton plusLumieres = new JButton("+");
		plusLumieres.setBorder(new LineBorder(new Color(47, 79, 79)));
		plusLumieres.setFont(new Font("Century Schoolbook", Font.BOLD, 11));
	   	plusLumieres.addActionListener(new ActionAjouterLumiere());
		plusLumieres.setBackground(new Color(0, 128, 128));
		plusLumieres.setBounds(166, 180, 41, 42);
		panelInformation.add(plusLumieres);
		
		JTextField objets = new JTextField("Objets 3D");
		objets.setBorder(new LineBorder(new Color(64, 224, 208)));
		objets.setEditable(false);
		objets.setFont(new Font("Century Schoolbook", Font.BOLD, 14));
		objets.setHorizontalAlignment(SwingConstants.CENTER);
		objets.setBackground(new Color(32, 178, 170));
		objets.setForeground(new Color(0, 0, 0));
		objets.setBounds(10, 11, 156, 42);
		panelInformation.add(objets);
		
		JTextField lumiere = new JTextField("Lumi\u00E8res");
		lumiere.setEditable(false);
		lumiere.setBorder(new LineBorder(new Color(64, 224, 208)));
		lumiere.setForeground(new Color(0, 0, 0));
		lumiere.setFont(new Font("Century Schoolbook", Font.BOLD, 14));
		lumiere.setHorizontalAlignment(SwingConstants.CENTER);
		lumiere.setBackground(new Color(32, 178, 170));
		lumiere.setBounds(10, 180, 156, 42);
		panelInformation.add(lumiere);
		
		JButton btnParametre = new JButton("Param\u00E9trer Ray Tracing ");
		btnParametre.setBorder(new LineBorder(new Color(47, 79, 79)));
		btnParametre.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		btnParametre.setForeground(new Color(0, 0, 0));
		btnParametre.addActionListener(new ActionParametrer());
		btnParametre.setBackground(new Color(0, 128, 128));
		btnParametre.setBounds(10, 360, 197, 24);
		panelInformation.add(btnParametre);
		
		JPanel panelTranslation = new JPanel();
		panelTranslation.setBackground(new Color(32, 178, 170));
		panelTranslation.setBorder(new LineBorder(new Color(47, 79, 79)));
		panelTranslation.setBounds(10, 395, 197, 101);
		panelInformation.add(panelTranslation);
		panelTranslation.setLayout(null);
		
		JButton flecheDroite = new JButton("");
		flecheDroite.setBackground(new Color(0, 139, 139));
		flecheDroite.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/IG/fleche droite.png")));
		flecheDroite.addMouseListener(new translaterDroite() );
		flecheDroite.setBounds(126, 51, 28, 28);
		panelTranslation.add(flecheDroite);
		
		JLabel lblNewLabel = new JLabel("Translater Camera");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 0, 177, 23);
		panelTranslation.add(lblNewLabel);
		
		JButton flecheGauche = new JButton("");
		flecheGauche.setBackground(new Color(0, 139, 139));
		flecheGauche.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/IG/fleche gauche.png")));
		flecheGauche.addMouseListener(new translaterGauche() );
		flecheGauche.setBounds(49, 51, 28, 28);
		panelTranslation.add(flecheGauche);
		
		JButton flecheHaut = new JButton("");
		flecheHaut.setBackground(new Color(0, 139, 139));
		flecheHaut.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/IG/fleche  haut.png")));
		flecheHaut.addMouseListener(new translaterArriere() );
		flecheHaut.setBounds(88, 22, 28, 28);
		panelTranslation.add(flecheHaut);
		
		JButton flecheBas = new JButton("");
		flecheBas.setBackground(new Color(0, 139, 139));
		flecheBas.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/IG/fleche  bas.png")));
		flecheBas.addMouseListener(new translaterAvant() );
		flecheBas.setBounds(88, 62, 28, 28);
		panelTranslation.add(flecheBas);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBorder(new LineBorder(new Color(47, 79, 79)));
		btnAnnuler.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		btnAnnuler.setForeground(new Color(0, 0, 0));
		btnAnnuler.addActionListener(new ActionQuitter());
		btnAnnuler.setBackground(new Color(0, 128, 128));
		btnAnnuler.setBounds(974, 589, 89, 24);
		frame.getContentPane().add(btnAnnuler);
		
		JButton btnEnregistrerS = new JButton("Enregistrer Sc\u00E8ne");
		btnEnregistrerS.setBorder(new LineBorder(new Color(47, 79, 79)));
		btnEnregistrerS.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		btnEnregistrerS.setForeground(new Color(0, 0, 0));
		btnEnregistrerS.addActionListener(new ActionEnregistrerS());
		btnEnregistrerS.setBackground(new Color(0, 128, 128));
		btnEnregistrerS.setBounds(764, 589, 200, 24);
		frame.getContentPane().add(btnEnregistrerS);
		
		JButton btnEnregistrerI = new JButton("Capture Image");
		btnEnregistrerI.setBorder(new LineBorder(new Color(47, 79, 79)));
		btnEnregistrerI.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		btnEnregistrerI.setForeground(new Color(0, 0, 0));
		btnEnregistrerI.addActionListener(new ActionEnregistrerI());
		btnEnregistrerI.setBackground(new Color(0, 128, 128));
		btnEnregistrerI.setBounds(553, 589, 200, 24);
		frame.getContentPane().add(btnEnregistrerI);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFocusable(false);
		toolBar.setEnabled(false);
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setBorder(new LineBorder(new Color(64, 224, 208), 1, true));
		toolBar.setBackground(new Color(72, 209, 204));
		toolBar.setBounds(10, 21, 1053, 39);
		frame.getContentPane().add(toolBar);
		
		JButton ajouterObjet = new JButton("");
		ajouterObjet.addActionListener(new ActionAjouterObjet());
		ajouterObjet.setBackground(new Color(72, 209, 204));
		ajouterObjet.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/IG/objet.png")));
		toolBar.add(ajouterObjet);
		
		toolBar.addSeparator();
		
		JButton ajouterLumiere = new JButton("");
		ajouterLumiere.addActionListener(new ActionAjouterLumiere());
		ajouterLumiere.setBackground(new Color(72, 209, 204));
		ajouterLumiere.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/IG/lumiere.png")));
		toolBar.add(ajouterLumiere);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(64, 224, 208)));
		panel_2.setBackground(new Color(72, 209, 204));
		panel_2.setBounds(10, 547, 826, 31);
		panel_2.setLayout(null);
		frame.getContentPane().add(panel_2);
		
		JButton lancerRT = new JButton("Lancer RayTracing");
		lancerRT.setBorder(new LineBorder(new Color(47, 79, 79)));
		lancerRT.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		lancerRT.setForeground(new Color(0, 0, 0));
		lancerRT.setToolTipText("Lance le ray tracing ");
		lancerRT.setBounds(10, 589, 200, 24);
		lancerRT.addActionListener(new ActionLancerCalcul());
		lancerRT.setBackground(new Color(0, 128, 128));
		frame.getContentPane().add(lancerRT);
	
	}
	
	//---------------------------------------------------------------------------------------------------
	//Fonction pour lancer le raytracing et maj l'image de l'application avec une barre de chargement.
	
	/**
	 * lancer le calcul du raytracing avec une barre de progression et maj de l'image.
	 */
	private void lancerRT() {
		Thread t1 = new Thread(){
			public void run() {
				panelImage.add(progressBar);
				rayTracing.lancerRayTracing();
			}
		};
		
		t1.start();

		Thread t2 = new Thread(){
			public void run() {
			   try {
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			   try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			   panelImage.remove(progressBar);
			   imageRT.setIcon(new ImageIcon(rayTracing.getCamera().creerImage()));
			}
		};
		t2.start();
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	//Action popup menu(ajout automatique)
	
	/**
	 * Activer le double clique pour ouvrir popup menu pour le composant conserné.
	 * @param component
	 * @param popup
	 */
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
	
	//-------------------------------------------------------------------------------------------------------------------
	//Action relative à ajout, modification, copie, suppression d'objets et de lumiere dans la scene.
	
	/**
	 * Action pour dupliquer une lumière et l'ajouter dans la liste de lumière.
	 * @author rapha
	 *
	 */
	private class DupliquerLumiere implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(listLumieres.getSelectedValue() != null) {
				Object[] options = {"Oui", "Non"};
				int n = JOptionPane.showOptionDialog(new JFrame(),
			    		"Êtes-vous sûre de vouloir dubliquer cet lumière : " + listLumieres.getSelectedValue().getNom(),
			    		"Dupliquer une lumière",
			    		JOptionPane.WARNING_MESSAGE, 
			    	    JOptionPane.YES_NO_OPTION,
			    	    null,    
			    	    options,  
			    	    options[0]); 
			    	if (n == 0) {
						rayTracing.getScene().getLumieres().add((Lumiere) listLumieres.getSelectedValue().getObjet().clone());
						listeL.getLumieres().add(new Lumieres((Lumiere) listLumieres.getSelectedValue().getObjet().clone()));
						listLumieres.updateUI();
						if(imageRT.getIcon() != null) {
							lancerRT();
						}
			    	}
			}
		}
	}
	
	/**
	 * Action pour dupliquer une objet et l'ajouter dans la liste de objet.
	 * @author rapha
	 *
	 */
	private class DupliquerObjet implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(listObjets.getSelectedValue() != null) {
				Object[] options = {"Oui", "Non"};
				int n = JOptionPane.showOptionDialog(new JFrame(),
			    		"Êtes-vous sûre de vouloir dubliquer cet objet : " + listObjets.getSelectedValue().getNom(),
			    		"Dupliquer un objet",
			    		JOptionPane.WARNING_MESSAGE, 
			    	    JOptionPane.YES_NO_OPTION,
			    	    null,    
			    	    options,  
			    	    options[0]); 
			    	if (n == 0) {
						rayTracing.getScene().getObjets().add((Objet3D) listObjets.getSelectedValue().getObjet().clone());
						listeO.getObjets().add(new Objet((Objet3D) listObjets.getSelectedValue().getObjet().clone()));
						listObjets.updateUI();
						if(imageRT.getIcon() != null) {
							lancerRT();
						}
			    	}
			}
		}
	}
	
	/**
	 * Action pour ouvrir une fenêtre lumière suite au double clique.
	 * @author rapha
	 *
	 */
	private class ActionOuvrirLumiere extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			FenetreLumiere lux  = null;
	        if (evt.getClickCount() == 2) {
				lux = new FenetreLumiere(rayTracing,listeL,listLumieres,listLumieres.getSelectedValue());
				lux.setVisible(true);
				lux.addWindowListener(new fenetreFermer());
	        } else if (evt.getClickCount() == 3) {
				lux = new FenetreLumiere(rayTracing,listeL,listLumieres,listLumieres.getSelectedValue());
				lux.setVisible(true);
				lux.addWindowListener(new fenetreFermer());
	        }
		}
	}
	
	/**
	 * Action pour ouvrir une fenêtre objet suite au double clique.
	 * @author rapha
	 *
	 */	
	private class ActionOuvrirObjet extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			FenetreObjet objet = null;
	        if (evt.getClickCount() == 2) {
				objet = new FenetreObjet(rayTracing,listeO,listObjets,listObjets.getSelectedValue());
				objet.setVisible(true);
				objet.addWindowListener(new fenetreFermer());
	        } else if (evt.getClickCount() == 3) {
				objet = new FenetreObjet(rayTracing,listeO,listObjets,listObjets.getSelectedValue());
				objet.setVisible(true);
				objet.addWindowListener(new fenetreFermer());
	        }
		}
	}
	
	/**
	 * Action pour supprimer un objet de la liste d'objet.
	 * @author rapha
	 *
	 */
	private class ActionSupprimerObjet implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			if(listObjets.getSelectedValue() != null) {
				Object[] options = {"Oui", "Non"};
				int n = JOptionPane.showOptionDialog(new JFrame(),
			    		"Êtes-vous sûre de vouloir supprimer cet objet : " + listObjets.getSelectedValue().getNom(),
			    		"Supprimer un objet",
			    		JOptionPane.WARNING_MESSAGE, 
			    	    JOptionPane.YES_NO_OPTION,
			    	    null,    
			    	    options,  
			    	    options[0]); 
			    	if (n == 0) {
						int io = listObjets.getSelectedIndex();
						rayTracing.getScene().getObjets().remove(io);
						listeO.getObjets().remove(io);
						listObjets.updateUI();
						if(imageRT.getIcon() != null) {
							lancerRT();
						}
			    	}
			}
		}
	}
	
	/**
	 * Action pour supprimer une lumière de la liste de lumières.
	 * @author rapha
	 *
	 */
	private class ActionSupprimerLumiere implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			if(listLumieres.getSelectedValue() != null) {
				Object[] options = {"Oui", "Non"};
				int n = JOptionPane.showOptionDialog(new JFrame(),
			    		"Êtes-vous sûre de vouloir supprimer la lumiere : " + listLumieres.getSelectedValue().getNom(),
			    		"Supprimer une lumière",
			    		JOptionPane.WARNING_MESSAGE, 
			    	    JOptionPane.YES_NO_OPTION,
			    	    null,    
			    	    options,  
			    	    options[0]); 
			    	if (n == 0) {
						int il = listLumieres.getSelectedIndex();
						rayTracing.getScene().getLumieres().remove(il);
						listeL.getObjets().remove(il);
						listLumieres.updateUI();
						if(imageRT.getIcon() != null) {
							lancerRT();
							
						}
			    	}
			}
			

		}
	}
	
	/**
	 * Action ajouter objet.
	 * @author rapha
	 *
	 */
	private class ActionAjouterObjet implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			FenetreObjet objet = new FenetreObjet(rayTracing,listeO,listObjets);
			objet.setVisible(true);
			objet.addWindowListener(new fenetreFermer());
		}
	}
	
	/**
	 * Action ajouter lumière.
	 * @author rapha
	 *
	 */
	private class ActionAjouterLumiere implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			FenetreLumiere lux = new FenetreLumiere(rayTracing,listeL,listLumieres);
			lux.setVisible(true);
			lux.addWindowListener(new fenetreFermer());
		}
	}
	
	/**
	 * Action pour maj l'image suite à la fermeture des fenêtres objet ou lumière.
	 * @author rapha
	 *
	 */
	private class fenetreFermer implements WindowListener {
			
			public void windowClosed(WindowEvent e) {
				if(imageRT.getIcon() != null) {
					lancerRT();
				}
			}
			
			@Override
			public void windowOpened(WindowEvent e) {			
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		}
	
	
	//-------------------------------------------------------------------------------------------------------------
	//Action pour saugarder, ouvrir, etc...
	
	
	/**
	 * Action pour enregistrer image.
	 * Ouvre une fenetre enregistrement pour entrer le nom du fichier.
	 * @author rapha
	 *
	 */
	private class ActionEnregistrerI implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			EnregistrerImage enregistrer = new EnregistrerImage(rayTracing);
			enregistrer.setVisible(true);
			
		}
	}
	
	/**
	 * Action pour enregistrer scene.
	 * Ouvre une fenetre enregistrement pour entrer le nom du fichier.
	 * @author rapha
	 *
	 */
	private class ActionEnregistrerS implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			EnregistrerScene enregistrer = new EnregistrerScene(rayTracing);
			enregistrer.setVisible(true);
			
		}
	}
	
	/**
	 * Action pour ouvrir une scene enregistree.
	 * Ouvre une scène enregistrer à partir du nom du fichier.
	 * Listes des objets et lumieres mises à jours.
	 * @author rapha
	 *
	 */
	private class ActionOuvrirScene implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			OuvrirScene ouvrir = new OuvrirScene(rayTracing,listeO,listeL,listLumieres,listObjets);
			ouvrir.setVisible(true);
			
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//Action relative au ray tracing : lancement de calcul et parametrage
	
	/**
	 * Action lancer calcul du raytracing.
	 * @author rapha
	 *
	 */
	private class ActionLancerCalcul implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			lancerRT();
		}
	}
	
	/**
	 * Action pour ouvrir la fenêtre de paramétrage.
	 * Modifie le parametrage du raytracing en fonction de ce qui est selectionne ou non.
	 * @author rapha
	 *
	 */
	private class ActionParametrer implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Parametrage parametre = new Parametrage(rayTracing);
			parametre.setVisible(true);
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	//Action pour quitter l'application
	
	/**
	 * Action pour quitter l'application.
	 * @author rapha
	 *
	 */
	private class ActionQuitter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.exit(0);
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	//Action relative à la caméra 
	
	
	/**
	 * Action pour zoomer.
	 * @author rapha
	 *
	 */
	private class ActionZoom implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
		    JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        double i = 1 + (double)source.getValue()/100;
		        try {
					rayTracing.getCamera().setFocale(i);
					if (imageRT != null && imageRT.getIcon() != null) {
						lancerRT();
					}
				} catch (FocaleNegativeException e1) {
					e1.printStackTrace();
				}
		    }
		}
	}
	
	/**
	 * Actions pour pour deplacer camera suivant Y avec -1.
	 *
	 */
	private class translaterDroite extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			rayTracing.getCamera().translater(0, 1, 0);
			if(imageRT.getIcon() != null) {
				lancerRT();
			}
		}

	}
	
	/**
	 * Actions pour pour deplacer camera suivant Y avec 1.
	 *
	 */
	private class translaterGauche extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			rayTracing.getCamera().translater(0, -1, 0);
			if(imageRT.getIcon() != null) {
				lancerRT();
			}
		}

	}
	
	/**
	 * Actions pour pour deplacer camera suivant X avec 1.
	 *
	 */
	private class translaterAvant extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			rayTracing.getCamera().translater(1, 0, 0);
			if(imageRT.getIcon() != null) {
				lancerRT();
			}
		}

	}
	
	/**
	 * Actions pour pour deplacer camera suivant Y -1.
	 *
	 */
	private class translaterArriere extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			rayTracing.getCamera().translater(-1, 0, 0);
			if(imageRT.getIcon() != null) {
				lancerRT();
			}
		}

	}
}
