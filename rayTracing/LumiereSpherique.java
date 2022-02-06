package rayTracing;

import java.awt.Color;

import utilitaire.Point;
import utilitaire.Vecteur;

/** LumiereSpherique mod�lise une source de lumi�re associ� � un sph�re.
 * Cette classe h�rite de Lumi�re que nous avons d�finie. 
 * */

public class LumiereSpherique extends Lumiere {
	
	private double rayon;
	
	private static int compteur = 0;
	
	/**
	 * Initialiser une lumi�re sph�rique � partir de son centre, sa couleur et son rayon.
	 * @param centre : Centre de la lumi�re.
	 * @param couleur : Couleur de la lumi�re.
	 * @param rayon : Le rayon de la lumi�re.
	 */
	public LumiereSpherique(Point centre, Color couleur, double rayon) {
		super(centre,couleur,"LumiereSpherique" + ++compteur);
		assert rayon > 0;
		this.rayon = rayon;

	}
	
	/**
	 * Initialiser une lumi�re ponctuelle � partir de son centre, sa couleur et son nom.
	 * @param centre : Centre de la lumi�re ponctuelle.
	 * @param couleur : Couleur de la lumi�re ponctuelle.
	 * @param nom : Nom de la lumi�re ponctuelle.
	 */
	public LumiereSpherique(Point centre, Color couleur, double rayon, String nom) {
		super(centre,couleur,nom);
		assert rayon > 0;
		compteur++;
		this.rayon = rayon;

	}

	@Override
	public Point getSource(Point pointImpact) {
		assert pointImpact != null;
		
		// On r�cup�re le point de la sph�re le plus proche d'impact
		// Calcul du vecteur entre le centre de la sph�re et le point d'impact
		Vecteur rayon = new Vecteur(this.getCentre(), pointImpact);
		
		// On r�cup�re le d�placement entre le centre et le point �clairant le point d'imapct
		rayon.normaliser();
		rayon.multiplication(this.rayon);
		
		// Eclairant = centre + rayon
		Point eclairant = this.getCentre();
		eclairant.translater(rayon);
		return eclairant;
	}

	@Override
	public boolean eclaire(Point pointImpact) {
		assert pointImpact != null;
		return true;
	}
	
	@Override
	public boolean estHorsScene(double dimension, Point centre) {
		assert dimension > 0;
		assert centre != null;
		return this.getCentre().distance(centre) > dimension;

	}
	/** 
	 * Obtenir le rayon de la lumi�re sph�rique.
	 * @return le rayon de la lumi�re sph�rique.
	 */
	public double getRayon() {
		return this.rayon;
	}
	
	/**
	 * Modifier la valeur du rayon de la lumi�re sph�rique
	 * @param nouvelle valeur du rayon
	 */
	public void setRayon(double rayon) {
		assert rayon > 0;
		this.rayon = rayon;
	}

}
