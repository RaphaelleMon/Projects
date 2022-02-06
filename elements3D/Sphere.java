/**
 * 
 */
package elements3D;

import rayTracing .*;
import utilitaire .*;
import exception.NomVideException;

import java.lang.Math;

/** Sphere est une implantation de l'interface Objet3D. Cette classe permet
 * de definir la forme 3D specifique d'une sphere � partir d'un ensemble de
 * propri�t�s materielles (couleur...), d'une origine (point) et d'un rayon
 * (distance � l'origine).
 * @author Edgar
 *
 */
public class Sphere extends Objet3D {
	
	private static final long serialVersionUID = -5841917298616385596L;
	
	// compteur pour les noms par d�faut
	private static int compteur = 0; 
	
	/** Point d'origine de la sphere.*/
	private Point origine;
	
	/** Rayon de la sphere.*/
	private double rayon;
	
	// Couleur pour la lumi�re ambiante
	
	/**
	 * Creer une sphere � partir d'un point d'origine, d'un rayon et d'un nom
	 * @param origine : coordonnees du centre de la sphere
	 * @param rayon : rayon de la sphere
	 * @param nom : nom donn� � la sph�re
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
	 * Creer une sphere � partir d'un point d'origine et d'un rayon
	 * @param origine : coordonnees du centre de la sphere
	 * @param rayon : rayon de la sphere
	 * @pre origine != null && rayon > 0.0
	 */
	public Sphere(Point origine, double rayon) throws NomVideException { // ne devrait jamais throw l'exception en r�alit�
		this(origine, rayon, "Sphere" + ++compteur);
	}
	
	@Override
	public Point estTraversePar(Rayon r) {
		assert r != null;
		// le rayon se d�crit comme r = p + td, avec :
		// p origine de r
		double px = r.getOrigine().getX();
		double py = r.getOrigine().getY();
		double pz = r.getOrigine().getZ();
		 // d vect directeur de r
		double dx = r.getDirection().getX();
		double dy = r.getDirection().getY();
		double dz = r.getDirection().getZ();
		
		// l'�quation de la sph�re est (x-l)�+ (y-m)�+ (z-n)� = rayon�,
		// avec l,m,n les coordonn�es de son origine.
		double l = this.origine.getX();
		double m = this.origine.getY();
		double n = this.origine.getZ();
		
		// alors l'existence de solutions t donnant 0,1 ou 2 points de r
		// traversant la sph�re d�pend des solutions de l'�quation
		// at� + bt + c = 0, avec :
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
			// (car on cherche dans le m�me sens que celui du rayon)
			if (t1 < 0.0 && t2 < 0.0) {
				return null; // pas de solution t positive
			} else if (t1 >= 0.0 && t2 >= 0.0) {
				t = Math.min(t1, t2); // 2 solutions positives
			} else {
				t = Math.max(t1, t2); // 2 solutions de signe oppos�
			}
			Point intersection = new Point(px, py, pz);
			Vecteur deplacement = new Vecteur(dx, dy, dz);
			deplacement = deplacement.multiplication(t);
			// on r�cup�re bien le point d'intersection � p+t*deplacement
			intersection.translater(deplacement);
			if (intersection.equals(r.getOrigine(), 0.001)) {
				return null;
			} else {
				return intersection;
			}
		} else {
			// cas o� il n'y a pas de solution:
			return null;
		}
	}
	
	/**
	 * Obtenir le rayon de la sph�re
	 * @return Rayon de la sph�re 
	 */
	public double getRayon() {
		return this.rayon;
	}
	
	/**
	 * Obtenir l'origine de la sph�re
	 * @return Point d'origine de la sph�re
	 */
	public Point getOrigine() {
		return new Point(this.origine.getX(), this.origine.getY(), this.origine.getZ());
	}
	
	/**
	 * Obtenir la position du point d'origine de la sph�re
	 * @return Point d'origine de la sph�re
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
		assert pointImpact != null && lumiere != null; // rayon peut �tre null pour la sph�re
		// la sph�re fait de l'ombre au point si le vecteur qui va de la
		// lumi�re vers l'impact traverse un autre point de la sph�re
		Rayon lumVersSphere = new Rayon(new Vecteur(lumiere.getCentre(), pointImpact), lumiere.getCentre());
		Point impactReel = this.estTraversePar(lumVersSphere);
		return !((impactReel == null) || (impactReel.equals(pointImpact,Objet3D.EPSILON)));
	}
	
	/**
	 * Translater la sph�re autour des axes X, Y et Z.
	 * @param dx : valeur de translation sur l'axe des X
	 * @param dy : valeur de translation sur l'axe des Y
	 * @param dz : valeur de translation sur l'axe des Z 
	 */
	@Override
	public void translater(double dx, double dy, double dz) {
		this.origine.translater(dx, dy, dz);
	}

	/**
	 * Rotater la sph�re autour des axes X, Y et Z.
	 * @param rx : angle de rotation autour de X
	 * @param ry : angle de rotation autour de Y
	 * @param rz : angle de rotation autour de Z
	 */
	@Override
	public void rotation(double rx, double ry, double rz) {
		// La rotation d'une sph�re n'implique pas de changement
	}

	/**
	 * @param pointImpact : Point d'impact du rayon sur la sph�re
	 * @param rayon : Rayon impact� sur la sph�re
	 * @return Vecteur normal au point d'impact
	 */
	@Override
	public Vecteur getNormal(Point pointImpact, Rayon rayon) {
		assert pointImpact != null; // rayon peut �tre null pour la sphere
		return new Vecteur(this.origine, pointImpact);
	}
	
	@Override
	public String toString() {
		return "Sphere(" + this.getNom() + ")@(" + this.origine.getX() + ", " + this.origine.getY() + ", " + this.origine.getZ() + ")";
	}
	
	/**
	 * Indique si la sph�re est en dehors de la sc�ne
	 * @param dimension : Dimension de la sc�ne
	 * @param centreScene : Centre de la sc�ne
	 * @return indication 
	 */
	@Override
	public boolean estHorsScene(double dimension, Point centreScene) {
		return this.origine.distance(centreScene) > dimension;
	}

}
