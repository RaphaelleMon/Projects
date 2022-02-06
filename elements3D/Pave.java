package elements3D;
import java.util.Iterator;

import exception.NomVideException;

import java.util.ArrayList;

import rayTracing.Rayon;
import utilitaire.Point;
import utilitaire.Vecteur;

import rayTracing.Lumiere;

/**
 * Classe permettant d'utiliser / manipuler un Pave pour le raytracing
 * @author tibo
 *
 */

public class Pave extends Objet3D {
	
	private static final long serialVersionUID = -5565642816688199212L;

	private static int compteur = 0; // compteur pour les noms par défaut
	
	/** Centre du pave */
	private Point centre;
	
	/** Arete du pave */
	private double areteX;
	private double areteY;
	private double areteZ;
	
	
	/** Axes propres du pave */
	private Vecteur X;
	private Vecteur Y;
	private Vecteur Z;
	
	/** Liste des plans constituants le pave
	 * Toutes les normales sont dirigees vers l'exterieur du cube */
	private ArrayList<Plan> plans;

	/** Construit le pave en precisant la longueur des trois aretes */
	public Pave(Point centre, double areteX, double areteY, double areteZ, String nom) throws NomVideException {
		super(nom);
		
		this.areteX = areteX;
		this.areteY = areteY;
		this.areteZ = areteZ;
		this.centre = centre.copie();
		
		finConstructionPave();
	}
	
	/** Construit un cube */
	public Pave(Point centre, double arete, String nom) throws NomVideException {
		super(nom);
		
		this.areteX = arete;
		this.areteY = arete;
		this.areteZ = arete;
		this.centre = centre.copie();
		
		finConstructionPave();
	}
	
	/** Utile pour ne pas repeter le même code dans la construction du cube ou pave */
	private void finConstructionPave () throws NomVideException {
		X = new Vecteur(1, 0, 0);
		Y = new Vecteur(0, 1, 0);
		Z = new Vecteur(0, 0, 1);
		
		Plan plan;
		Point point;
		this.plans = new ArrayList<Plan>();
		
		point = new Point(this.centre.getX() + this.areteX/2, this.centre.getY(), this.centre.getZ());
		plan = new Plan(new Vecteur(1,0,0), point, "0", this.getMateriau());
		plans.add(plan.copie());
		
		point = new Point(this.centre.getX() - this.areteX/2, this.centre.getY(), this.centre.getZ());
		plan = new Plan(new Vecteur(-1,0,0), point, "1", this.getMateriau());
		plans.add(plan.copie());
		
		point = new Point(this.centre.getX(), this.centre.getY() + this.areteY/2, this.centre.getZ());
		plan = new Plan(new Vecteur(0,1,0), point, "2", this.getMateriau());
		plans.add(plan.copie());
		
		point = new Point(this.centre.getX(), this.centre.getY() - this.areteY/2, this.centre.getZ());
		plan = new Plan(new Vecteur(0,-1,0), point, "3", this.getMateriau());
		plans.add(plan.copie());
		
		point = new Point(this.centre.getX(), this.centre.getY(), this.centre.getZ() + this.areteZ/2);
		plan = new Plan(new Vecteur(0,0,1), point, "4", this.getMateriau());
		plans.add(plan.copie());
		
		point = new Point(this.centre.getX(), this.centre.getY(), this.centre.getZ() - this.areteZ/2);
		plan = new Plan(new Vecteur(0,0,-1), point, "5", this.getMateriau());
		plans.add(plan.copie());
	}
	
	/** Constructeur alternatif de pave qui ne demande pas de nom en entree */
	public Pave(Point centre, double areteX, double areteY, double areteZ) throws NomVideException {
		this(centre, areteX, areteY, areteZ, "Pave" + ++compteur);
	}
	
	//----------------------------------------------------------------------
	// Methodes get
	
	@Override
	public Point getPosition() {
		return this.centre;
	}
	
	public double getAreteX() {
		return this.areteX;
	}
	
	public double getAreteY() {
		return this.areteY;
	}
	
	public double getAreteZ() {
		return this.areteZ;
	}
	
	public Point getCentreBase() {
		return this.centre;
	}
	
	/**
	 * Retourne la normale au point d'impact et aussi son sens grace au rayon
	 * @param pointImpact : Point d'impact du rayon qui appartient au pave
	 * @param rayon : rayon qui va peruter le pave
	 * @return la normale au point d'impact
	 */
	@Override
	public Vecteur getNormal(Point pointImpact, Rayon rayon) {
		assert pointImpact != null && rayon != null;
		int indice = appartientPave(pointImpact);
		
		//Assert pt d'impact appartient au pave
		if (indice == -1) {
			System.out.println("getNormal : Le point n'appartient pas au pave");
			return null;
		}
		
		return this.plans.get(indice).getNormal(pointImpact, rayon);
	}
	
	/**
	 * Retourne true si l'objet se fait de l'ombre lui meme
	 * 
	 * On deduit la face qui est touchee, et puis applique la methode du plan
	 * 
	 * @param pointImpact : Point d'impact du rayon qui appartient au pave
	 * @param rayon : rayon qui va percuter le pave
	 * @param lumiere : Lumiere de la scene
	 **/
	@Override
	public boolean getSelfOmbre(Point pointImpact, Rayon rayon, Lumiere lumiere) {
		assert pointImpact != null && rayon != null && lumiere != null;
		int indice = appartientPave(pointImpact);
		
		//Assert pt d'impact appartient au pave
		if (indice == -1) {
			System.out.println("getSelfOmbre : Le point n'appartient pas au pave");
			return false;
		}
		
		return this.plans.get(indice).getSelfOmbre(pointImpact, rayon, lumiere);
	}
	
	
	//----------------------------------------------------------------------
	//Autres methodes

	/**
	 * Determine si le pave est traverse par un rayon et son premier point d'impact
	 * (il y en aura presque toujours deux)
	 * @param rayon : rayon
	 * @return Le point d'impact du rayon r sur le pave
	 */
	@Override
	public Point estTraversePar(Rayon rayon) {
		assert rayon != null;
		int i, indice;
		double distanceMin = 0.0;
		Point point;
		Point pointReturn = null;
		
		for (i = 0; i<6; i++) {
			point = this.plans.get(i).estTraversePar(rayon);
			indice = this.appartientPave(point);
			
			if (point != null & indice != -1) {
				if ((distanceMin > point.distance(rayon.getOrigine()) | distanceMin < Objet3D.EPSILON) ) {
					pointReturn = point.copie();
					distanceMin = pointReturn.distance(rayon.getOrigine());
				}
			}
		}
		
		return pointReturn;
	}

	/** Determine la direction et sens du rayon reflechi contre le pave au point d'impact p
	 * @param rayon : rayon allant frapper le pave
	 * @param pointImpact : point où a lieu la collision
	 * @return le vecteur de direction du rayon reflechi de r au point d'impact p
	 */
	@Override
	public Vecteur directionReflexion(Rayon rayon, Point pointImpact) {
		assert rayon != null && pointImpact != null;
		int indice = appartientPave(pointImpact);
		
		//Assert pt d'impact appartient au pave
		if (indice == -1) {
			System.out.println("Dir Reflexion : Le point n'appartient pas au pave");
			return null;
		}
		
		return this.plans.get(indice).directionReflexion(rayon, pointImpact);
	}
	
	/** Determine la direction et sens du rayon refracte contre le pave au point d'impact p
	 * @param rayon : rayon allant frapper le pave
	 * @param pointImpact : point où a lieu la collision
	 * @return Le vecteur du rayon refract du rayon r au point d'impact p
	 */
	@Override
	public Vecteur directionRefraction(Rayon rayon, Point pointImpact, double indiceRefractionExterieur) {
		assert rayon != null && pointImpact != null;
		int indice = appartientPave(pointImpact);
		
		//Assert pt d'impact appartient au pave
		if (indice == -1) {
			System.out.println("Dir Reflexion : Le point n'appartient pas au pave");
			return null;
		}
		
		Vecteur centreVersPlan = new Vecteur(this.centre, this.plans.get(indice).getPoint());
		Boolean exterieurVersInterieur;
		if (centreVersPlan.produitScalaire(rayon.getDirection()) < 0) {
			exterieurVersInterieur = true;
		} else {
			exterieurVersInterieur = false;
		}
		
		return this.plans.get(indice).directionRefraction(rayon
				, this.getMateriau().getRefringence().getIndiceInterieur()
				, exterieurVersInterieur
				, this.getMateriau().getRefringence().getIndiceExterieur());
	}

	/**Translate le pave de dx, dy, dz
	 * @param dx,dy,dz : De combien sur chaque axe le pave va etre translate
	 */
	@Override
	public void translater(double dx, double dy, double dz) {
		int i;
		
		for (i = 0; i<6; i++) {
			this.plans.get(i).translater(dx, dy, dz);
		}
	}

	/**Effectue une rotation autour des axes x, y, z classiques
	 * @param rx : premiere rotation effectuee
	 * @param ry : deuxieme rotation effectuee
	 * @param rz : troisieme rotation effectuee
	 */
	@Override
	public void rotation(double rx, double ry, double rz) {
		int i;
		
		for (i = 0; i<6; i++) {
			this.plans.get(i).rotation(this.centre,rx, ry, rz, X, Y, X);
		}
		
		X = X.rotationXYZ(rx, ry, rz);
		Y = Y.rotationXYZ(rx, ry, rz);
		Z = Z.rotationXYZ(rx, ry, rz);
		
		X.normaliser();
		Y.normaliser();
		Z.normaliser();
		
		assert(X.produitScalaire(Y) < Objet3D.EPSILON);
		assert(X.produitScalaire(Z) < Objet3D.EPSILON);
		assert(Z.produitScalaire(Y) < Objet3D.EPSILON);
	}
	
	/**
	 * @param point : point d'impact
	 * @return Renvoie l'indice du plan correspondant
	 *  			Si le point n'appartient pas au pave --> renvoie -1
	 * 				Si le point appartient à une arete/coin --> renvoie un plan au hasard
	 * 					entre ceux qu'il touche
	 */
	public int appartientPave(Point point) {
		int i;
		Vecteur relatifPointPlan;
		double produitScalaire;
		
		if (point == null) {
			return -1;
		}
		
		for (i = 0 ; i<6 ; i++) {
			relatifPointPlan = new Vecteur(point, this.plans.get(i).getPoint());
			produitScalaire = this.plans.get(i).getNormale().produitScalaire(relatifPointPlan);
			
			if ( Math.abs(produitScalaire) < Objet3D.EPSILON) {
				//on sait que le point appartient au plan, mais on ne sait pas si il appartient
				//a la face du carre
								
				Vecteur vecteurDeTest = new Vecteur(1,1,1);
				if ( i == 0 | i == 1 ) {
					vecteurDeTest = new Vecteur(0.0, 1.0, 1.0);
				} else if ( i == 2 | i == 3 ) {
					vecteurDeTest = new Vecteur(1.0, 0.0, 1.0);
				} else if ( i == 4 | i == 5 ) {
					vecteurDeTest = new Vecteur(1.0, 1.0, 0.0);
				} else {
					System.out.println("ES GIBT EIN PROBLEM ACH");
				}
				//VecteurDeTest va servir a pouvoir tester sur les deux autres coordonnees autres
				//que la normale
				
				//Check si il appartient au segment [-arete ; arete] sur chaque coordonnee
				if ( 	(vecteurDeTest.getX() < Objet3D.EPSILON | 
							Math.abs(relatifPointPlan.produitScalaire(X)) < this.areteX/2 ) &
						(vecteurDeTest.getY() < Objet3D.EPSILON | 
							Math.abs(relatifPointPlan.produitScalaire(Y)) < this.areteY/2 ) &
						(vecteurDeTest.getZ() < Objet3D.EPSILON | 
							Math.abs(relatifPointPlan.produitScalaire(Z)) < this.areteZ/2 ) ) {
					return i;
				}
				
			}
		}
		
		return -1;
	}
	
	
	
	/**
	 * Affiche les attributs du pave dans la console
	 */
	@Override
	public String toString() {
		return "Cube(" + this.getNom() + ")@(" + this.centre.getX() + ", " + this.centre.getY() + ", " + this.centre.getZ() + "), cotes : ("+this.areteX+","+this.areteY+","+this.areteZ+")";
	}
	
	public void afficherCollection() {
		Iterator iterator = this.plans.iterator();
	    while (iterator.hasNext()) {
	      System.out.println(iterator.next());
	    }
	}

	@Override
	public boolean estHorsScene(double dimension, Point centreScene) {
		return this.centre.distance(centreScene) > dimension;
	}
	
	public void setAreteX(double x) {
		this.areteX = x;
		
		Point point1 = new Point(this.centre.getX() + this.areteX/2, this.centre.getY(), this.centre.getZ());
		this.plans.get(0).setPoint(point1);
		
		Point point2 = new Point(this.centre.getX() - this.areteX/2, this.centre.getY(), this.centre.getZ());
		this.plans.get(1).setPoint(point2);
	}
	
	public void setAreteY(double y) {
		this.areteY = y;
		
		Point point1 = new Point(this.centre.getX(), this.centre.getY() + this.areteY/2, this.centre.getZ());
		this.plans.get(2).setPoint(point1);
		
		Point point2 = new Point(this.centre.getX(), this.centre.getY() - this.areteY/2, this.centre.getZ());
		this.plans.get(3).setPoint(point2);
	}

	public void setAreteZ(double z) {
		this.areteZ = z;
		
		Point point1 = new Point(this.centre.getX(), this.centre.getY(), this.centre.getZ() + this.areteZ/2);
		this.plans.get(4).setPoint(point1);
		
		Point point2 = new Point(this.centre.getX(), this.centre.getY(), this.centre.getZ() - this.areteZ/2);
		this.plans.get(5).setPoint(point2);
	}
}
