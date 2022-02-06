/**
 * 
 */
package elements3D;

import rayTracing .*;
import utilitaire .*;
import exception.NomVideException;

import java.lang.Math;

/** Sphere est une implantation de l'interface Objet3D. Cette classe permet
 * de definir la forme 3D specifique d'une sphere à partir d'un ensemble de
 * propriétés materielles (couleur...), d'une origine (point) et d'un rayon
 * (distance à l'origine).
 * @author Edgar
 *
 */
public class Sphere extends Objet3D {
	
	private static final long serialVersionUID = -5841917298616385596L;
	
	// compteur pour les noms par défaut
	private static int compteur = 0; 
	
	/** Point d'origine de la sphere.*/
	private Point origine;
	
	/** Rayon de la sphere.*/
	private double rayon;
	
	// Couleur pour la lumière ambiante
	
	/**
	 * Creer une sphere à partir d'un point d'origine, d'un rayon et d'un nom
	 * @param origine : coordonnees du centre de la sphere
	 * @param rayon : rayon de la sphere
	 * @param nom : nom donné à la sphère
	 * @pre origine != null && rayon > 0.0
	 */
	public Sphere(Point origine, double rayon, String nom) throws NomVideException {
		super(nom);
		assert origine != null && rayon > 0.0;
		if (NomVideException.estVide(nom)) {
			throw new NomVideException();
		}
		double x = origine.getX();
		double y = origine.getY();
		double z = origine.getZ();
		this.origine = new Point(x, y, z);
		this.rayon = rayon;
	}
	
	/**
	 * Creer une sphere à partir d'un point d'origine et d'un rayon
	 * @param origine : coordonnees du centre de la sphere
	 * @param rayon : rayon de la sphere
	 * @pre origine != null && rayon > 0.0
	 */
	public Sphere(Point origine, double rayon) throws NomVideException { // ne devrait jamais throw l'exception en réalité
		this(origine, rayon, "Sphere" + ++compteur);
	}
	
	@Override
	public Point estTraversePar(Rayon r) {
		assert r != null;
		// le rayon se décrit comme r = p + td, avec :
		// p origine de r
		double px = r.getOrigine().getX();
		double py = r.getOrigine().getY();
		double pz = r.getOrigine().getZ();
		 // d vect directeur de r
		double dx = r.getDirection().getX();
		double dy = r.getDirection().getY();
		double dz = r.getDirection().getZ();
		
		// l'équation de la sphère est (x-l)²+ (y-m)²+ (z-n)² = rayon²,
		// avec l,m,n les coordonnées de son origine.
		double l = this.origine.getX();
		double m = this.origine.getY();
		double n = this.origine.getZ();
		
		// alors l'existence de solutions t donnant 0,1 ou 2 points de r
		// traversant la sphère dépend des solutions de l'équation
		// at² + bt + c = 0, avec :
		double a = dx*dx + dy*dy + dz*dz;
		double b = 2.0 * (dx * (px - l) + dy * (py - m) + dz * (pz - n));
		double c = Math.pow(px - l, 2) + Math.pow(py - m, 2) + Math.pow(pz - n, 2) - this.rayon*this.rayon;
		
		double delta = b * b - 4 * a * c;
		if (delta >= 0.0) {
			
			// cas 2 racines ou 1 racine double:
			double t1 = (-b + Math.sqrt(delta)) / (2.0 * a);
			double t2 = (-b - Math.sqrt(delta)) / (2.0 * a);
			
			// on cherche la solution t positive la plus petite
			double t;
			// (car on cherche dans le même sens que celui du rayon)
			if (t1 < 0.0 && t2 < 0.0) {
				return null; // pas de solution t positive
			} else if (t1 >= 0.0 && t2 >= 0.0) {
				t = Math.min(t1, t2); // 2 solutions positives
			} else {
				t = Math.max(t1, t2); // 2 solutions de signe opposé
			}
			Point intersection = new Point(px, py, pz);
			Vecteur deplacement = new Vecteur(dx, dy, dz);
			deplacement = deplacement.multiplication(t);
			// on récupère bien le point d'intersection à p+t*deplacement
			intersection.translater(deplacement);
			if (intersection.equals(r.getOrigine(), 0.001)) {
				return null;
			} else {
				return intersection;
			}
		} else {
			// cas où il n'y a pas de solution:
			return null;
		}
	}
	
	/**
	 * Obtenir le rayon de la sphère
	 * @return Rayon de la sphère 
	 */
	public double getRayon() {
		return this.rayon;
	}
	
	/**
	 * Obtenir l'origine de la sphère
	 * @return Point d'origine de la sphère
	 */
	public Point getOrigine() {
		return new Point(this.origine.getX(), this.origine.getY(), this.origine.getZ());
	}
	
	/**
	 * Obtenir la position du point d'origine de la sphère
	 * @return Point d'origine de la sphère
	 */
	@Override
	public Point getPosition() {
		return this.origine;
	}
	
	@Override
	public Vecteur directionReflexion(Rayon rayon, Point pointImpact) {
		assert rayon != null && pointImpact != null;
		Vecteur projection = new Vecteur(rayon.getDirection().getX(),rayon.getDirection().getY(),rayon.getDirection().getZ());
		projection.retirerProjection(this.getNormal(pointImpact, null));
		
		Vecteur dir_reflexion = rayon.getDirection().soustraire(projection.multiplication(2));
		dir_reflexion = dir_reflexion.soustraire(dir_reflexion.multiplication(2));
		return dir_reflexion;
	}
	
	public Vecteur directionRefraction(Rayon rayon, Point pointImpact, double indiceRefractionExterieur) {
		Vecteur relatifCentreImpact = new Vecteur(this.origine, pointImpact);
		assert Math.abs(relatifCentreImpact.module() - this.rayon) < Objet3D.EPSILON;
		try {
			Plan plan = new Plan(relatifCentreImpact, pointImpact, "Plan temporaire");
			Boolean exterieurVersInterieur;
			if (rayon.getDirection().produitScalaire(relatifCentreImpact) < 0 ) {
				exterieurVersInterieur = true;
			} else {
				exterieurVersInterieur = false;
			}
			return plan.directionRefraction(rayon, this.getMateriau().getRefringence().getIndiceInterieur(), exterieurVersInterieur, indiceRefractionExterieur);
		} catch (NomVideException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public boolean getSelfOmbre(Point pointImpact, Rayon rayon, Lumiere lumiere) {
		assert pointImpact != null && lumiere != null; // rayon peut être null pour la sphère
		// la sphére fait de l'ombre au point si le vecteur qui va de la
		// lumière vers l'impact traverse un autre point de la sphère
		Rayon lumVersSphere = new Rayon(new Vecteur(lumiere.getCentre(), pointImpact), lumiere.getCentre());
		Point impactReel = this.estTraversePar(lumVersSphere);
		return !((impactReel == null) || (impactReel.equals(pointImpact,Objet3D.EPSILON)));
	}
	
	/**
	 * Translater la sphère autour des axes X, Y et Z.
	 * @param dx : valeur de translation sur l'axe des X
	 * @param dy : valeur de translation sur l'axe des Y
	 * @param dz : valeur de translation sur l'axe des Z 
	 */
	@Override
	public void translater(double dx, double dy, double dz) {
		this.origine.translater(dx, dy, dz);
	}

	/**
	 * Rotater la sphère autour des axes X, Y et Z.
	 * @param rx : angle de rotation autour de X
	 * @param ry : angle de rotation autour de Y
	 * @param rz : angle de rotation autour de Z
	 */
	@Override
	public void rotation(double rx, double ry, double rz) {
		// La rotation d'une sphère n'implique pas de changement
	}

	/**
	 * @param pointImpact : Point d'impact du rayon sur la sphère
	 * @param rayon : Rayon impacté sur la sphère
	 * @return Vecteur normal au point d'impact
	 */
	@Override
	public Vecteur getNormal(Point pointImpact, Rayon rayon) {
		assert pointImpact != null; // rayon peut être null pour la sphere
		return new Vecteur(this.origine, pointImpact);
	}
	
	@Override
	public String toString() {
		return "Sphere(" + this.getNom() + ")@(" + this.origine.getX() + ", " + this.origine.getY() + ", " + this.origine.getZ() + ")";
	}
	
	/**
	 * Indique si la sphère est en dehors de la scène
	 * @param dimension : Dimension de la scène
	 * @param centreScene : Centre de la scène
	 * @return indication 
	 */
	@Override
	public boolean estHorsScene(double dimension, Point centreScene) {
		return this.origine.distance(centreScene) > dimension;
	}

}
