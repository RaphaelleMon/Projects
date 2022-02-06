package rayTracing;
import java.awt.Color;
import java.awt.color.*;
import elements3D .*;
import utilitaire .*;

/**
 * Classe permettant de mod�liser le pixel d'un �cran
 */

public class Pixel {

	/** Couleur du pixel*/
	private Color couleur;
	
	/** Coordonn�es du pixel*/
	private Point coordonnee; 
	
	/**
	 * Cr�er un pixel � partir d'un point
	 * @param coordonnees : point correspond � la position du pixel
	 */
	public Pixel (Point coordonnees) {
		this(coordonnees, Color.BLACK);
	}
	
	/**
	 * Cr�er un pixel � partir d'un point et d'un couleur
	 * @param coordonnees : point correspond � la position du pixel
	 * @param couleur : couleur attribu�e au pixel
	 */
	public Pixel (Point coordonnees, Color couleur) {
		assert coordonnees != null;
		assert couleur != null;
		this.couleur = couleur;
		this.coordonnee = new Point (coordonnees.getX(), coordonnees.getY(), coordonnees.getZ());
	}
	
	/**
	 * Cr�er une copie du pixel
	 * @return copie du pixel
	 */
	public Pixel copie() {
		return new Pixel(this.coordonnee,this.couleur);
	}
	
	/**
	 * Modifier la couleur du pixel
	 * @param nouvelleCouleur : nouvelle couleur attribu�e au pixel
	 */
	public void setCouleur (Color nouvelleCouleur) {
		assert ( nouvelleCouleur != null );
		this.couleur = nouvelleCouleur; 
	}
	
	/**
	 * Modifier les coordonnees du point du pixel 
	 * @param nouvellesCoordonnees
	 */
	public void setCoordonnee(Point nouvellesCoordonnees) {
		assert nouvellesCoordonnees != null;
		this.coordonnee = new Point (nouvellesCoordonnees.getX(), nouvellesCoordonnees.getY(), nouvellesCoordonnees.getZ());
	}
	
	/**
	 * Obtenir la couleur du pixel
	 * @return Couleur du pixel
	 */
	public Color getCouleur() {
		return this.couleur;
	}
	
	/**
	 * Obtenir les coordonnees du pixel 
	 * @return Point avec les coordonn�es du pixel
	 */
	public Point getCoordonnee() {
		return new Point(this.coordonnee.getX(), this.coordonnee.getY(), this.coordonnee.getZ());
	}
	
	/**
	 * Translater le pixel
	 * @param vecteur : vecteur de la translation du point
	 */
	public void translater(Vecteur vecteur) {
		this.coordonnee.translater(vecteur);
	}
}
