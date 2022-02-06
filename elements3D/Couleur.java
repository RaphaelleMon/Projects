/**
 * 
 */
package elements3D;

import java.awt.Color;
import java.io.Serializable;

import utilitaire.Vecteur;


/** Couleur definit le materiau representant la couleur des objets 3D
 * qui sont manipulés dans la scene.
 * Les couleurs devront être liées aux couleurs Java (classe Color) pour
 * afficher les images.
 * @author Edgar
 *
 */
public class Couleur implements Serializable {
	
	private static final long serialVersionUID = 1341431705338909785L;

	/** Couleur par défaut définie comme gris foncé.*/
	private static final Color COULEUR_DEFAUT = Color.DARK_GRAY; 
	
	private Color couleur;
	
	private boolean coherente;
	
	/**
	 * Composante rouge d'une couleur
	 */
	private double rouge;
	
	/**
	 * Composante verte d'une couleur
	 */
	private double verte;
	
	/**
	 * Composante bleue d'une couleur
	 */
	private double bleue;
	
	/** Creer une couleur a partir de valeurs RGB
	 * @param rouge : valeur de la composante red/rouge
	 * @param vert : valeur de la composante green/verte
	 * @param bleu : valeur de la composante blue/bleu
	 */
	public Couleur(int rouge, int vert, int bleu) {
		this.couleur = new Color(rouge, vert, bleu);
		this.rouge = (double) rouge;
		this.verte = (double) vert;
		this.bleue = (double) bleu;
		coherente = true;
	}
	
	/** Creer une couleur a partir d'une instance de Color
	 * @param couleur
	 */
	public Couleur(Color couleur) {
		this.couleur = new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue());
		this.rouge = (double) couleur.getRed();
		this.verte = (double) couleur.getGreen();
		this.bleue = (double) couleur.getBlue();
		coherente = true;
	}
	
	public Couleur(Couleur autre) {
		this.couleur = new Color( autre.getRed(), autre.getGreen(), autre.getBlue() );
		this.rouge = autre.rouge;
		this.verte = autre.verte;
		this.bleue = autre.bleue;
		this.coherente = autre.coherente;
	}
	
	/** Creer une couleur à partir de la valeur par défaut définie.
	 */
	public Couleur() {
		this(COULEUR_DEFAUT);
	}
	
	public Couleur(double rouge, double verte, double bleue) {
		this.rouge = rouge;
		this.verte = verte;
		this.bleue = bleue;
		coherente = false;
	}

	/** Obtenir la valeur de la composante Red/rouge de la couleur du récepteur (this).
	 * @return valeur de la composante rouge entière (comprise entre 0 et 255)
	 */
	public int getRed() {
		assert this.coherente;
		return this.couleur.getRed();
	}
	
	/** Obtenir la valeur de la composante green/vert de la couleur du récepteur (this).
	 * @return valeur de la composante verte entière (comprise entre 0 et 255)
	 */
	public int getGreen() {
		assert this.coherente;
		return this.couleur.getGreen();
	}
	
	/** Obtenir la valeur de la composante green/vert de la couleur du récepteur (this).
	 * @return valeur de la composante verte entière (comprise entre 0 et 255)
	 */
	public int getBlue() {
		assert this.coherente;
		return this.couleur.getBlue();
	}
	
	/**
	 * Attribuer une nouvelle valeur RGB a la couleur
	 * @param rouge : nouvelle valeur de la composante Rouge (comprise entre 0 et 255)
	 * @param vert : nouvelle valeur de la composante Verte (comprise entre 0 et 255)
	 * @param bleu : nouvelle valeur de la composante Bleu (comprise entre 0 et 255)
	 */
	public void set(int rouge, int vert, int bleu) {
		this.couleur = new Color(rouge, vert, bleu);
		this.rouge = (double) rouge;
		this.verte = (double) vert;
		this.bleue = (double) bleu;
		coherente = true;
	}
	
	/**
	 * Attribuer une nouvelle Couleur
	 * @param couleur
	 */
	public void set(Color couleur) {
		this.couleur = new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue());
		this.rouge = (double) couleur.getRed();
		this.verte = (double) couleur.getGreen();
		this.bleue = (double) couleur.getBlue();
		coherente = true;
	}
	
	/**
	 * Obtenir la Couleur
	 */
	public Color get() {
		assert this.coherente;
		return new Color(this.couleur.getRed(), this.couleur.getGreen(), this.couleur.getBlue());
	}

	public void combiner(Couleur couleur2) {
		coherente = false;
		this.rouge += couleur2.rouge;
		this.verte += couleur2.verte;
		this.bleue += couleur2.bleue;
		this.normaliser();
	}
	
	public void combiner(Couleur couleur2, double facteur) {
		coherente = false;
		this.rouge += facteur * couleur2.rouge;
		this.verte += facteur * couleur2.verte;
		this.bleue += facteur * couleur2.bleue;
		this.normaliser();
	}
	
	public void attenuer(double facteurAttenuation) {
		coherente = false;
		this.rouge *= facteurAttenuation;
		this.verte *= facteurAttenuation;
		this.bleue *= facteurAttenuation;
		this.normaliser();
	}
	
	public void attenuer(Vecteur facteurAttenuation) {
		coherente = false;
		this.rouge *= facteurAttenuation.getX();
		this.verte *= facteurAttenuation.getY();
		this.bleue *= facteurAttenuation.getZ();
		this.normaliser();
	}
	
	public void attenuer(Couleur facteurAttenuation) {
		coherente = false;
		this.rouge *= facteurAttenuation.rouge;
		this.verte *= facteurAttenuation.verte;
		this.bleue *= facteurAttenuation.bleue;
		this.normaliser();
	}
	
	public void filtrer(Couleur filtre) {
		coherente = false;
		this.rouge = Math.min(this.rouge, filtre.rouge);
		this.verte = Math.min(this.verte, filtre.verte);
		this.bleue = Math.min(this.bleue, filtre.bleue);
		this.normaliser();
	}
	
	public void normaliser() {
		if (! coherente) {
			this.rouge = Math.min(this.rouge, 255);
			this.verte = Math.min(this.verte, 255);
			this.bleue = Math.min(this.bleue, 255);
			this.couleur = new Color((int)Math.round(this.rouge), (int)Math.round(this.verte), (int)Math.round(this.bleue));
			this.coherente = true;
		}
	}
	
	public String toString() {
		return "Couleur ( " + this.rouge + ", " + this.verte + ", " + this.bleue + ") " + this.couleur;
	}

}
