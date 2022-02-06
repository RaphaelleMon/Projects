package rayTracing;

import java.awt.Color;

import utilitaire.Point;
import utilitaire.Vecteur;

/** LumiereSpherique modélise une source de lumière associé à un sphère.
 * Cette classe hérite de Lumière que nous avons définie. 
 * */

public class LumiereSpherique extends Lumiere {
	
	private double rayon;
	
	private static int compteur = 0;
	
	/**
	 * Initialiser une lumière sphérique à partir de son centre, sa couleur et son rayon.
	 * @param centre : Centre de la lumière.
	 * @param couleur : Couleur de la lumière.
	 * @param rayon : Le rayon de la lumière.
	 */
	public LumiereSpherique(Point centre, Color couleur, double rayon) {
		super(centre,couleur,"LumiereSpherique" + ++compteur);
		assert rayon > 0;
		this.rayon = rayon;

	}
	
	/**
	 * Initialiser une lumière ponctuelle à partir de son centre, sa couleur et son nom.
	 * @param centre : Centre de la lumière ponctuelle.
	 * @param couleur : Couleur de la lumière ponctuelle.
	 * @param nom : Nom de la lumière ponctuelle.
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
		
		// On récupère le point de la sphère le plus proche d'impact
		// Calcul du vecteur entre le centre de la sphère et le point d'impact
		Vecteur rayon = new Vecteur(this.getCentre(), pointImpact);
		
		// On récupère le déplacement entre le centre et le point éclairant le point d'imapct
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
	 * Obtenir le rayon de la lumière sphérique.
	 * @return le rayon de la lumière sphérique.
	 */
	public double getRayon() {
		return this.rayon;
	}
	
	/**
	 * Modifier la valeur du rayon de la lumière sphérique
	 * @param nouvelle valeur du rayon
	 */
	public void setRayon(double rayon) {
		assert rayon > 0;
		this.rayon = rayon;
	}

}
