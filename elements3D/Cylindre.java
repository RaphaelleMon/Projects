/**
 * 
 */
package elements3D;

import rayTracing .*;
import utilitaire .*;

import java.lang.Math;

import exception.NomVideException;

/** Cylindre est une implantation de l'interface Objet3D. Cette classe permet
 * de definir la forme 3D specifique d'un cylindre à partir d'un ensemble de
 * propriétés materielles (couleur...), d'une origine (point) et d'un rayon
 * de sa base inferieure (distance à l'origine de la base inferieur et
 * non superieure , on prendera cette convention) et une hauteur.
 * @author Aymane
 *
 */
public class Cylindre extends Objet3D {
	
	private static final long serialVersionUID = -5841917298616385596L;
	private static int compteur = 0; // compteur pour les noms par défaut
	
	/** rayon de la base du cylindre */
	private double rayonBase;
	
	/** Point d'origine de la base du cylindre.*/
	private Point origine;
	
	/** vecteur dirigeur du cylindre.*/
	private Vecteur vecteurDirecteur;
	
	/** hauteur du cylindre.*/
	private double hauteur;
	
	/**
	 * Creer un cylindre à partir d'un point d'origine de sa
	 *  base, du rayon de sa base et d'un nom
	 * @param origine : coordonnees du centre 
	 * de la base du cylindre
	 * @param rayonBase : rayon de la base cylidre
	 * @param hauteur : hauteur du cylindre
	 * @param nom : nom du cylindre
	 * @throws NomVideException 
	 * @pre origine != null && rayon > 0.0
	 */
	public Cylindre(Point origine, double rayonBase, double hauteur, Vecteur vecteurDirecteur, String nom) throws NomVideException {
		super(nom);
		assert origine != null && rayonBase > 0.0;
		double x = origine.getX();
		double y = origine.getY();
		double z = origine.getZ();
		this.origine = new Point(x, y, z);
		this.rayonBase = rayonBase;
		this.hauteur = hauteur;
		this.vecteurDirecteur = vecteurDirecteur;
	}
	
	/**
	 * Creer un cylindre à partir d'un point d'origine de sa
	 *  base, du rayon de sa base .
	 * @param origine : coordonnees du centre 
	 * de la base du cylindre
	 * @param rayonBase : rayon de la base cylidre
	 * @param hauteur : hauteur du cylindre
	 * @throws NomVideException 
	 * @pre origine != null && rayon > 0.0
	 */
	public Cylindre(Point origine, double rayonBase, double hauteur, Vecteur vecteurDirecteur) throws NomVideException {
		this(origine, rayonBase, hauteur, vecteurDirecteur, "Cylindre" + ++compteur);
	}
	
	// obtenir un point p = p + dir*t pfv : point à travers le vecteur
	private Point pfv(Point point, Vecteur dir,double t) {
		Point po = new Point(dir.getX(),dir.getY(),dir.getZ());
		return point.sommer(po.multiplier(t));
	}
	
	/**
	 * @param point : point d'impact
	 * @return Renvoie :
	 * 				Si le point est à la surface du cylindre  --> renvoie 1
	 * 				Si le point n'appartient pas au cylindre --> renvoie 0
	 */
	
	public int appartientsurflong(Point pointImpact) {
		//Test si ça appartient a la base circulaire
		Vecteur va = this.getVectdir();
		Vecteur relatif_PointCentreBaseinf = new Vecteur(this.getOriginebase(),pointImpact);
		Vecteur relatif_PointCentreBasesup = new Vecteur(this.getPointbasesup(),pointImpact);
		if (va.produitScalaire(relatif_PointCentreBaseinf) == 0 || va.produitScalaire(relatif_PointCentreBasesup) == 0) {
			//On sait qu'on est dans le plan
			if (pointImpact.distance(this.getOriginebase()) < this.getRayonbase() ||  pointImpact.distance(this.getPointbasesup()) < this.getRayonbase())  {
				return 1;
			}
		}
		
		//Test si ca appartient au reste
		Vecteur vecteurOrdonnee = new Vecteur(this.getOriginebase(), pointImpact);
		vecteurOrdonnee.retirerProjection(va);
		double ordonnee = vecteurOrdonnee.module();
		if ( va.produitScalaire(relatif_PointCentreBaseinf) > 0.0 && va.produitScalaire(relatif_PointCentreBasesup) < 0.0 &&
		  Math.abs( ordonnee - this.getRayonbase() ) < Objet3D.EPSILON) {
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public Point estTraversePar(Rayon rayon) {
		assert rayon != null;
	
		// le rayon se décrit comme q = p + tv, avec :
		// p origine de q
		Point p = rayon.getOrigine();
		 // v vect directeur de r
		Vecteur v = rayon.getDirection();
		
		// l'équation du cylindre  est (q - pa - (va,q - pa)va)² - r² = 0 ,
		// avec pa le point origine du cylindre.
		Point pa = this.getOriginebase();

		//hauteur du cylindre 
		
		double h = this.getHauteur() ;
		// et va le vecteur de direction du cylindre normalisé 
		Vecteur va = this.getVectdir();
		va.normaliser();
		
		//obtenir le point du haut du cylindre : le centre de la deuxieme base 
		Point som = new Point(va.multiplication(h/va.module()).getX(),va.multiplication(h/va.module()).getY(),va.multiplication(h/va.module()).getZ());
		Point p2 = pa.sommer(som);
		

		//deltap : delp = p-pa
		Vecteur delp = new Vecteur(pa,p);
		
		//(.,.) designe le produit scalaire
		// alors l'existence de solutions t donnant 0,1 ou 2 points de r
		// traversant le cylindre dépend des solutions de l'équation
		// at² + bt + c = 0, avec :
		double a = Math.pow(v.soustraire(va.multiplication(v.produitScalaire(va))).module(), 2);
		double b = 2*(v.soustraire(va.multiplication(v.produitScalaire(va)))).produitScalaire(delp.soustraire(va.multiplication(delp.produitScalaire(va))));
		double c = Math.pow(delp.soustraire(va.multiplication(delp.produitScalaire(va))).module(),2) - this.rayonBase*this.rayonBase;
		
		double delta = b * b - 4 * a * c;
		if (delta >= 0.0) {
			// cas 2 racines ou 1 racine double:
			double t1 = (-b + Math.sqrt(delta)) / (2.0 * a);
			double t2 = (-b - Math.sqrt(delta)) / (2.0 * a);
			
			
			// on cherche la solution t positive la plus petite
			/* on initialise tsol avec une valeur superieur au dimention de l'ecran
			 * pour que si il n'y a pas de solution et on prend le mi et on le trouve 
			 * 10e10 par exemple càd qu'l n'y a pas d'intersection 
			 */
			double tsol1 = 10e10; 
			double tsol2 = 10e10;
			double tsol3 = 10e10;
			double tsol4 = 10e10;
			double t;
			
			// on definit deux plan avec des conditions limite 
			// c'est ce qui va determiner nos deux bases
			Plan plan1 = null;
			Plan plan2 = null;
			
			try {
				plan1 = new Plan(va,pa);
				plan2 = new Plan(va,p2);
			} catch (NomVideException e) {
				e.printStackTrace();
			}
			
			Point inter1 = plan1.estTraversePar(rayon);
			Point inter2 = plan2.estTraversePar(rayon);
			
			// on trouve les 4 solutions s'il existe 
			// s'il n'existe pas alors le min des 4 solutions sera 
			// 10e10
			
			if (inter1 != null ) {
				if (pa.distance(inter1) <= this.rayonBase) {
					tsol1 = (inter1.soustraire(p)).getX()/v.getX();
				}
			}
			if (inter2 != null ) {
				if (p2.distance(inter2) <= this.rayonBase) {
					tsol2 = (inter2.soustraire(p)).getX()/v.getX();
				}
			}
			

			if (t1 > 0.0 ) {
				Vecteur test1 = new Vecteur(pa,pfv(p, v, t1));
				Vecteur test2 = new Vecteur(p2,pfv(p, v, t1));
				if (va.produitScalaire(test1) > 0.0 && va.produitScalaire(test2) < 0.0  ) {
					tsol3 = t1;
				}
			}
			if (t2 > 0.0 ) {
				Vecteur test1 = new Vecteur(pa,pfv(p, v, t2));
				Vecteur test2 = new Vecteur(p2,pfv(p, v, t2));
				if (va.produitScalaire(test1) > 0.0 && va.produitScalaire(test2) < 0.0  ) {
					tsol4 = t2;
				} else {
					
				}
			}
			
			// la solution est le min des 4 solution 
			
				t = Math.min(Math.min(tsol1,tsol2), Math.min(tsol3,tsol4)); // min des 4 solutions
			
			if (t != 10e10) {
				return pfv(p, v, t);
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * @return Renvoie : rayon du cylindre
	 */
	
	public double getRayonbase() {
		return this.rayonBase;
	}
	
	/**
	 * @return Renvoie : point origine de base du cylindre
	 */
	
	public Point getOriginebase() {
		return new Point(this.origine.getX(), this.origine.getY(), this.origine.getZ());
	}
	
	/**
	 * @return Renvoie : hauteur du cylindre
	 */
	
	public double getHauteur() {
		return this.hauteur;
	}
	
	/**
	 * @return Renvoie : vecteur directeur du cylindre
	 */
	
	
	public Vecteur getVectdir() {
		return this.vecteurDirecteur;
	}
	
	/**
	 * @param point : point d'impact
	 * @param rayon : rayon se dirigeant vers le cylindre
	 * @return Renvoie : vecteur reflichi sur le cylindre
	 */
	
	@Override
	public Vecteur directionReflexion(Rayon rayon, Point pointImpact) {
		assert rayon != null && pointImpact != null;
		Vecteur projection = new Vecteur(rayon.getDirection().getX(),rayon.getDirection().getY(),rayon.getDirection().getZ());
		projection.retirerProjection(this.getNormal(pointImpact, null));
		
		Vecteur dir_reflexion = rayon.getDirection().soustraire(projection.multiplication(2));
		dir_reflexion = dir_reflexion.soustraire(dir_reflexion.multiplication(2));
		dir_reflexion.afficher();
		return dir_reflexion;
	}
	
	/**
	 * @param point : point d'impact
	 * @param rayon : rayon se dirigeant vers le cylindre
	 * @param Lumiere : lumiere avec quoi on va determiner
	 *  si l'ombre existe ou pas
	 * @return Renvoie : si le cylindre fait de l'ombre au point -> true
	 * 					 si le cylindre ne fait pas de l'ombre au point -> false
	 */
	
	
	@Override
	public boolean getSelfOmbre(Point pointImpact, Rayon rayon, Lumiere lumiere) {
		assert pointImpact != null && lumiere != null; // rayon peut être null pour le cylindre
		// le cylindre fait de l'ombre au point si le vecteur qui va de la
		// lumière vers l'impact traverse un autre point du cylindre
		Rayon lumVerscylindre = new Rayon(new Vecteur(lumiere.getCentre(), pointImpact), lumiere.getCentre());
		Point impactReel = this.estTraversePar(lumVerscylindre);
		return !((impactReel == null) || (impactReel.equals(pointImpact,Objet3D.EPSILON)));
	}
	
	/**
	 * Translater le cylindre autour des axes X, Y et Z.
	 * @param dx : valeur de translation sur l'axe des X
	 * @param dy : valeur de translation sur l'axe des Y
	 * @param dz : valeur de translation sur l'axe des Z 
	 */
	
	
	
	@Override
	public void translater(double dx, double dy, double dz) {
		this.origine.translater(dx, dy, dz);
	}

	
	/**
	 * tourner le cylindre autour des axes X, Y et Z.
	 * @param rx : angle de rotation autour de X
	 * @param ry : angle de rotation autour de Y
	 * @param rz : angle de rotation autour de Z
	 */
	
	
	@Override
	public void rotation(double rx, double ry, double rz) {
		this.vectdir.rotationXYZ(rx, ry, rz);
	}
	
	
	/**
	 * @param impact : Point d'impact du rayon sur le cylindre
	 * @param rayon : Rayon impacté sur le cylindre
	 * @return Vecteur normal au point d'impact
	 */
	
	
	
	@Override
	public Vecteur getNormal(Point pointImpact, Rayon rayon) {
		assert pointImpact != null; // rayon peut être null pour le cylindre
		Point pa = this.getOriginebase();
		double h = this.getHauteur();
		Vecteur va = this.getVectdir();
		//obtenir le point du haut du cylindre : le centre de la deuxiele base 
		Point som = new Point(va.multiplication(h/va.module()).getX(),va.multiplication(h/va.module()).getY(),va.multiplication(h/va.module()).getZ());
		Point p2 = pa.sommer(som);
		

		double distance1 = Math.pow(this.getOriginebase().distance(pointImpact), 2)-Math.pow(this.getRayonbase(), 2);
		double distance2 = Math.pow(p2.distance(pointImpact), 2)-Math.pow(this.getRayonbase(), 2);
		double distance;
		if (distance1 > 0.0 && distance2 > 0.0) {
			distance = Math.sqrt(Math.pow(this.getOriginebase().distance(pointImpact), 2)-Math.pow(this.getRayonbase(), 2));
		} else if (distance1 <= 0.0 && distance2 > 0.0) {
			return va.multiplication(-1);
		} else if (distance2 <= 0.0 && distance1 > 0.0 ) {
			return va;
		} else {
			if (distance1 < distance2) {
				return va.multiplication(-1);
			} else {
				return va;
			}
		}
		// calcul du vecteur normal à la surface verticale du cylindre 
		Point p = new Point(pointImpact.getX(),pointImpact.getY(),pointImpact.getZ());
		p.translater(this.getVectdir().multiplication(-distance/this.getVectdir().module()));
		
		return new Vecteur(this.getOriginebase(),p);
	}
	
	
	@Override
	public String toString() {
		return "Cylindre(" + this.getNom() + ")@(base : (" + this.origine.getX() + ", " + this.origine.getY() 
		+ ", " + this.origine.getZ() + ") rayon de base : " + this.getRayonbase() + ") hauteur :" 
				+ this.getHauteur() + ")";
	}
	
	/**
	 * Indique si le cylindre est en dehors de la scène
	 * @param dimension : Dimension de la scène
	 * @param centreScene : Centre de la scène
	 * @return indication 
	 */
	
	
	@Override
	public boolean estHorsScene(double dimension, Point centre) {
		return this.origine.distance(centre) + this.getRayonbase() > dimension;
	}
	
	/**
	 * @param point : point d'impact
	 * @param rayon : rayon se dirigeant vers le cylindre
	 * @return Renvoie : vecteur refracté sur le cylindre
	 */

	@Override
	public Vecteur directionRefraction(Rayon r, Point p, double indiceRefractionExterieur) {
		assert r != null && p != null;
		int indice = this.appartientsurflong(p);
		
		//Assert pt d'impact appartient au cube
		if (indice == 0) {
			System.out.println("Dir Refraction : Le point n'appartient pas aux cylindre ");
			p.afficher();
			return null;
		} else {
			//Le point appartient a la base
			Vecteur normale = this.getNormal(p,r);
			Plan plan;
			try {
				plan = new Plan(normale, p, "plan temporaire");
				Boolean exterieurVersInterieur;
				if (this.getNormal(p, null).produitScalaire(r.getDirection()) > 0 ) {
					exterieurVersInterieur = false;
				} else {
					exterieurVersInterieur = true;
				}
				
				return plan.directionRefraction(r, this.getMateriau().getRefringence().getIndiceInterieur(), exterieurVersInterieur, this.getMateriau().getRefringence().getIndiceExterieur());
			} catch (NomVideException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
	/**
	 * Obtenir la position du point d'origine du cylindre
	 * @return Point d'origine du cylindre
	 */
	
	@Override
	public Point getPosition() {
		return this.origine;
	}
	
	
	//obtenir le point du haut du cylindre : le centre de la deuxieme base 
	public Point getPointbasesup() {
		Vecteur va = this.getVectdir();
		va.normaliser();
		double h = this.getHauteur();
		Point som = new Point(va.multiplication(h/va.module()).getX(),va.multiplication(h/va.module()).getY(),va.multiplication(h/va.module()).getZ());
		Point p2 = this.getOriginebase().sommer(som);
		return p2;
	}

}
