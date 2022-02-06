/**
 *  
 */
package elements3D;

import java.io.Serializable;

import rayTracing.ModeleEclairement;
import rayTracing.ModelePhong;


/** Properties regroupe l'ensemble des Matériaux en une classe 
 * qui stocke les propriétés de chaque Objet3D.
 * @author Edgar
 */
public class Materiau implements Serializable {
	
	private static final long serialVersionUID = 385803258376068043L;
	
	/** La couleur du matériau : représente le complémentaire de la couleur absorbée */
	private Couleur couleur;
	
	/** Le coefficient d'absorption de la lumière par le matériau
	// Permet de représenter les matériaux partiellement transparents
	// Simplification de la refringence pour les sources de lumières */
	private double absorption;
	
	/** La réflectivité du matériau */
	private Reflectivite reflectivite;
	
	/** La réfringence du matériau */
	private Refringence refringence;
	
	/** Les paramètres du matériau liés au modèle de Phong */
	private ModeleEclairement eclairement;

	/** Créer l'ensemble des propriétés d'un objet et initialiser chacune.*/
	public Materiau() {
		this.couleur = new Couleur();
		this.reflectivite = new Reflectivite(1, false);
		this.refringence = new Refringence(1.52, 1, false);
		this.eclairement = new ModelePhong();
		// Ajouter ici l'initialisation d'un nouveau type de matériau
	}
	
	/**
	 * @return couleur du matériau
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
	 * @return la réfringence
	 */
	public Refringence getRefringence() {
		return this.refringence;
	}
	
	/**
	 * Obtenir le modèle d'éclairement
	 * @return Modèle d'éclairement utilisé
	 */
	public ModeleEclairement getEclairement() {
		return this.eclairement;
	}

	// TODO : revoir, l'idée est bonne mais cela semble incorrect ainsi
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
	 * Désactiver la réflectivité
	 */
	public void setOffReflectivite() {
		this.reflectivite.setOn(false);
	}
	
	/**
	 * Désactiver la réfringence
	 */
	public void setOffRefringence() {
		this.refringence.setOn(false);
	}
	
	// TODO : revoir, l'idée est bonne mais cela semble incorrect ainsi
	public void setEnergieRefraction(double energie) {
		double energie_reflexion = this.reflectivite.getEnergie();
		if (energie_reflexion + energie <= 1) {
			this.refringence.setEnergie(energie);
		} //Rajouter le else avec une erreur

	}
	
	// TODO : revoir, l'idée est bonne mais cela semble incorrect ainsi
	public void setEnergieReflexion(double energie) {
		double energie_refraction = this.refringence.getEnergie();
		if (energie_refraction + energie <= 1) {
			this.reflectivite.setEnergie(energie);
		} //Rajouter le else avec une erreur
	}

	
}
