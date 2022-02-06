package rayTracing;
import elements3D .*;
import utilitaire .*;
import java.awt.Color;
import java.io.Serializable;

/**
 * Lumiere est une classe abstraite décrivant une source de lumière.
 * @author jaden
 */
public abstract class Lumiere implements Cloneable, Serializable {

	private static final long serialVersionUID = -3292404395438711430L;
	
	/** Les coordonnées du centre de la lumière.*/
	private Point centre;
	
	/** La couleur de la lumière.*/
	private Color couleur;
	
	/** Nom de la lumiere.*/
	private String nom;
	
	/** Intensité de la lumire.*/
	private int intensite;
	
	/**
	 * Créer une lumière à partir de son centre, sa couleur et son nom.
	 * @param centre Point de départ de la lumière.
	 * @param couleur Couleur de la lumière.
	 * @param nom Nom de la lumière.
	 */
	public Lumiere(Point centre, Color couleur, String nom) {
		assert centre != null;
		assert couleur != null;
		assert nom != null;
		assert ! nom.equals("");
		this.centre = centre;
		this.couleur = couleur;
		this.nom = nom;
		this.intensite = 1000;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/** Obtenir l'intensité de la lumière.*/
	public int getIntensite() {
		return this.intensite;
	}
	
	/** Modifier l'intensité de la lumière.
	 * @param nouvelleIntensité : nouvelle intensité que l'on veut attribué à la lumière
	 * */
	public void setIntensite(int nouvelleIntensite) {
		assert nouvelleIntensite > 0;
		this.intensite = nouvelleIntensite;
	}
	
	/** Obtenir le centre de la lumière.
	 * @return centre de la lumière
	 * */
	public Point getCentre() {
		return this.centre.copie();
	}
	
	/** Obtenir la couleur de la lumière.
	 * @return couleur de la lumière
	 * */
	public Color getCouleur() {
		return this.couleur;
	}
	
	/** Obtenir le nom de la lumière.
	 * @return nom de la lumière
	 * */
	public String getNom() {
		return this.nom;
	}
	
	/** Modifier le centre de la lumière.
	 * @param nouveauCentre : Nouveau centre attribué à la lumière.
	 * */
	public void setCentre(Point nouveauCentre) {
		this.centre = nouveauCentre.copie();
	}
	
	/** Modifier la couleur de la lumière.
	 * @param couleur : Nouvelle couleur attribuée à la lumière.
	 * */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	/** Modifier le nom de la lumière.
	 * @param nom : Nouveau nom attribué à la lumière
	 * */
	public void setNom(String nom) {
		assert nom != null;
		assert !nom.equals("");
		this.nom = nom;
	}
	
	/** Translater la lumière selon les trois axes x, y et z.
	 * 
	 * @param dx translation en x
	 * @param dy translation en y
	 * @param dz translation en z
	 */
	public void translater(double dx, double dy, double dz) {
		this.centre.translater(dx, dy, dz);
	}
	
	/** Translater la lumière d'un vecteur.
	 * @param translation le vecteur de translation 
	 */
	public void translater(Vecteur translation) {
		this.centre.translater(translation);
	}
	
	/** Renvoie le point précis appartenant à la lumière éclairant le point d'impact. 
	 * @param pointImpact le point d'impact du rayon sur un objet
	 * @return le point de la lumière éclairant impact
	 */
	public abstract Point getSource(Point pointImpact);
	
	/** Indique si le point impact est dans le champ de la lumière.
	 * 
	 * @param pointImpact le point d'impact du rayon sur un objet
	 * @return true si impact peut être éclairé par la lumière
	 */
	public abstract boolean eclaire(Point pointImpact);
	
	/** Indique si la lumière est hors de la scene.
	 * 
	 * @param dimension le rayon de la scene
	 * @parma centre le centre de la scene
	 * @return true si la lumiere est hors de la scene, false sinon.
	 */
	public abstract boolean estHorsScene(double dimension, Point centre);
	

}
