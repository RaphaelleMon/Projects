/**
 * 
 */
package elements3D;

import java.io.Serializable;
import java.util.ArrayList;

import rayTracing.Rayon;
import utilitaire.Vecteur;
import utilitaire.Point;

/**
 * @author Christophe
 */
public class Refringence implements Propriete, Serializable {

	private static final long serialVersionUID = -1287556454361523445L;
	
	private double indiceRefractionInterieur;
	private double indiceRefractionExterieur;
	private double intensite; // entre 0 et 1 (1 = réfléchit tout)
	private double energie; // entre 0 et 1 
	private boolean on;
	private Vecteur transparence;
	
	/**
	 * Initialiser les paramètres de la refringence 
	 * @param indice 
	 * @param intensite
	 * @param on
	 */
	public Refringence(double indice, double intensite, boolean on) {
		assert (0 <= intensite  && intensite <= 1);
		assert (1 <= indice);
		//assert (0 <= transparence && transparence <= 1);
		this.indiceRefractionInterieur = indice;
		this.indiceRefractionExterieur = 1;
		this.intensite = intensite;
		this.energie = 0;
		this.on = on;
		this.transparence = new Vecteur(1,1,1);
	}
	
	/**
	 * Obtenir la valeur de transparence
	 * @return Vecteur contenant les paramètres de transparence pour chaque canel de couleur
	 */
	public Vecteur getTransparence() {
		return this.transparence;
	}
	
	/**
	 * Modifier les composantes du vecteur de transparence
	 * @param canalRouge : nouvelle valeur de la composante rouge
	 * @param canalVert : nouvelle valeur de la composante verte
	 * @param canalBleu : nouvelle valeur de la composante bleue
	 */
	public void setTransparence(double canalRouge, double canalVert, double canalBleu) {
		assert (0 <= canalRouge && canalRouge <= 1);
		assert (0 <= canalVert && canalVert <= 1);
		assert (0 <= canalBleu && canalBleu <= 1);
		this.transparence = new Vecteur(canalRouge,canalVert,canalBleu);
	}
	@Override
	public boolean isOn() {
		// TODO Auto-generated method stub
		return on;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
	
	public void setOn(boolean on) {
		this.on = on;
	}
	
	@Override
	public double getEnergie() {
		return this.energie;
	}
	
	/**
	 * Obtenir l'indice de réfraction intérieur d'un objet
	 * @return Indice de réfraction intérieur
	 */
	public double getIndiceInterieur() {
		return this.indiceRefractionInterieur;
	}
	
	/**
	 * Modifier l'indice de réfraction intérieur d'un objet
	 * @param indice nouvelle indice de réfraction intérieur
	 */
	public void setIndiceInterieur(double indice) {
		assert (1 <= indice);
		this.indiceRefractionInterieur = indice;
		
	}
	
	/**
	 * Obtenir l'indice de réfraction extérieur d'un objet
	 * @return Indice de réfraction extérieur
	 */
	public double getIndiceExterieur() {
		return this.indiceRefractionExterieur;
	}
	
	/**
	 * Modifier l'indice de réfraction intérieur d'un objet
	 * @param indice nouvelle indice de réfraction intérieur
	 */
	public void setIndiceExterieur(double indice) {
		assert (1 <= indice);
		this.indiceRefractionInterieur = indice;
		
	}
	
	/**
	 * Obtenir la valeur de l'intensité 
	 * @return Valeur de l'intensité
	 */
	public double getIntensite() {
		return this.intensite;
	}
	
	/**
	 * Modifier la valeur de l'intensité 
	 * @param intensite : nouvelle valeur attribuée à l'intensité
	 */
	public void setIntensite(double intensite) {
		assert (0 <= intensite  && intensite <= 1);
		this.intensite = intensite;
	}
	
	public void setEnergie(double energie) {
		assert (0 <= energie  && energie <= 1);
		this.energie = energie;
	}

	public ArrayList<Rayon> creerRayon(Rayon rayon, Point intersection, Objet3D objetIntersection) {
		assert (rayon != null && intersection != null && objetIntersection != null);
		Vecteur direction = objetIntersection.directionRefraction(rayon, intersection, indiceRefractionExterieur);
		ArrayList<Rayon> listeRayons = new ArrayList<Rayon>();
		if (direction != null) {
		listeRayons.add( new Rayon(direction, intersection, rayon, this.intensite, energie*rayon.getPartEnergie(), objetIntersection) );
				// partEnergie à modifier 
		}
		return listeRayons;
	}

}
