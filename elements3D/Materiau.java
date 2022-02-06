/**
 *  
 */
package elements3D;

import java.io.Serializable;

import rayTracing.ModeleEclairement;
import rayTracing.ModelePhong;


/** Properties regroupe l'ensemble des Mat�riaux en une classe 
 * qui stocke les propri�t�s de chaque Objet3D.
 * @author Edgar
 */
public class Materiau implements Serializable {
	
	private static final long serialVersionUID = 385803258376068043L;
	
	/** La couleur du mat�riau : repr�sente le compl�mentaire de la couleur absorb�e */
	private Couleur couleur;
	
	/** Le coefficient d'absorption de la lumi�re par le mat�riau
	// Permet de repr�senter les mat�riaux partiellement transparents
	// Simplification de la refringence pour les sources de lumi�res */
	private double absorption;
	
	/** La r�flectivit� du mat�riau */
	private Reflectivite reflectivite;
	
	/** La r�fringence du mat�riau */
	private Refringence refringence;
	
	/** Les param�tres du mat�riau li�s au mod�le de Phong */
	private ModeleEclairement eclairement;

	/** Cr�er l'ensemble des propri�t�s d'un objet et initialiser chacune.*/
	public Materiau() {
		this.couleur = new Couleur();
		this.reflectivite = new Reflectivite(1, false);
		this.refringence = new Refringence(1.52, 1, false);
		this.eclairement = new ModelePhong();
		// Ajouter ici l'initialisation d'un nouveau type de mat�riau
	}
	
	/**
	 * @return couleur du mat�riau
	 */
	public Couleur getCouleur() {
		return this.couleur;
	}

	/**
	 * @return la reflectivite
	 */
	public Reflectivite getReflectivite() {
		return this.reflectivite;
	}

	/**
	 * @return la r�fringence
	 */
	public Refringence getRefringence() {
		return this.refringence;
	}
	
	/**
	 * Obtenir le mod�le d'�clairement
	 * @return Mod�le d'�clairement utilis�
	 */
	public ModeleEclairement getEclairement() {
		return this.eclairement;
	}

	// TODO : revoir, l'id�e est bonne mais cela semble incorrect ainsi
	//public void setOnRefraction() {
		//this.reflectivite.setOn(true);
		//if (this.couleur.isOn()) {
			//this.couleur.setEnergie(0.5);
			//this.reflectivite.setEnergie(0.5);
		//} else {
			//this.reflectivite.setEnergie(1);
		//}
	//}
	
	/**
	 * D�sactiver la r�flectivit�
	 */
	public void setOffReflectivite() {
		this.reflectivite.setOn(false);
	}
	
	/**
	 * D�sactiver la r�fringence
	 */
	public void setOffRefringence() {
		this.refringence.setOn(false);
	}
	
	// TODO : revoir, l'id�e est bonne mais cela semble incorrect ainsi
	public void setEnergieRefraction(double energie) {
		double energie_reflexion = this.reflectivite.getEnergie();
		if (energie_reflexion + energie <= 1) {
			this.refringence.setEnergie(energie);
		} //Rajouter le else avec une erreur

	}
	
	// TODO : revoir, l'id�e est bonne mais cela semble incorrect ainsi
	public void setEnergieReflexion(double energie) {
		double energie_refraction = this.refringence.getEnergie();
		if (energie_refraction + energie <= 1) {
			this.reflectivite.setEnergie(energie);
		} //Rajouter le else avec une erreur
	}

	
}
