package elements3D;
import java.util.Iterator;
import java.util.ArrayList;

import rayTracing.Rayon;
import utilitaire.Point;
import utilitaire.Vecteur;
import exception.NomVideException;

import rayTracing.Lumiere;

/**
 * Classe permettant d'utiliser / manipuler un plan pour le raytracing
 * @author tibo, en partenariat avec ses erreurs de programmation
 *
 */

public class Cube extends Objet3D {
	
	private static final long serialVersionUID = 5907153455163217165L;
	private static int compteur = 0; // compteur pour les noms par dï¿½faut
	
	/** Centre du cube */
	private Point centre;
	
	/** Arete du cube */
	private double arete;
	
	/** Liste des plans constituants le cube 
	 * Toutes les normales sont dirigees vers l'exterieur du cube */
	private ArrayList<Plan> plans;

	/**
	 * Créer un cube à partir du centre, de la longueur de l'arête et d'un nom
	 * @param centre : centre du cube
	 * @param arete : arête du cube
	 * @param nom : nom de l'objet 
	 * @throws NomVideException
	 */
	public Cube(Point centre, double arete , String nom) throws NomVideException {
		super(nom);
		
		this.arete = arete;
		this.centre = centre.copie();
		
		Plan plan;
		Point point;
		this.plans = new ArrayList<Plan>();
		
		point = new Point(this.centre.getX() + this.arete/2, this.centre.getY(), this.centre.getZ());
		
		try {
			plan = new Plan(new Vecteur(1,0,0), point, "0", this.getMateriau());
			plans.add(plan.copie());
		
			point = new Point(this.centre.getX() - this.arete/2, this.centre.getY(), this.centre.getZ());
			plan = new Plan(new Vecteur(-1,0,0), point, "1", this.getMateriau());
			plans.add(plan.copie());
		
			point = new Point(this.centre.getX(), this.centre.getY() + this.arete/2, this.centre.getZ());
			plan = new Plan(new Vecteur(0,1,0), point, "2", this.getMateriau());
			plans.add(plan.copie());
		
			point = new Point(this.centre.getX(), this.centre.getY() - this.arete/2, this.centre.getZ());
			plan = new Plan(new Vecteur(0,-1,0), point, "3", this.getMateriau());
			plans.add(plan.copie());
		
			point = new Point(this.centre.getX(), this.centre.getY(), this.centre.getZ() + this.arete/2);
			plan = new Plan(new Vecteur(0,0,1), point, "4", this.getMateriau());
			plans.add(plan.copie());
		
			point = new Point(this.centre.getX(), this.centre.getY(), this.centre.getZ() - this.arete/2);
			plan = new Plan(new Vecteur(0,0,-1), point, "5", this.getMateriau());
			plans.add(plan.copie());
		
		} catch (NomVideException e) { // shouldn't happen
		}
	}
	
	/**
	 * Créer un cube à partir du centre, de la longueur de l'arête et d'un nom
	 * @param centre : centre du cube
	 * @param arete : arête du cube
	 * @throws NomVideException
	 */
	public Cube(Point centre, double arete) throws NomVideException { // ne devrait jamais throw l'exception en rï¿½alitï¿½
		this(centre, arete, "Cube" + ++compteur);
	}
	
	//----------------------------------------------------------------------
	// Methodes get
	
	@Override
	/**
	 * Obtenir le centre du cube
	 */
	public Point getPosition() {
		return this.centre;
	}
	
	public double getArete() {
		return this.arete;
	}
	
	
	/**
	 * Retourne la normale au point d'impact et aussi son sens grace au rayon
	 * @param pointImpact : Point d'impact du rayon qui appartient au plan
	 * @param rayon : rayon qui va percuter le plan
	 */
	@Override
	public Vecteur getNormal(Point pointImpact, Rayon rayon) {
		assert pointImpact != null && rayon != null;
		int indice = appartientCube(pointImpact);
		
		//Assert pt d'impact appartient au cube
		if (indice == -1) {
			System.out.println("getNormal : Le point n'appartient pas au cube");
			return null;
		}
		
		return this.plans.get(indice).getNormal(pointImpact, rayon);
	}
	
	/**
	 * Retourne true si l'objet se fait de l'ombre lui meme
	 * 
	 * On deduit la face qui est en jeu.
	 * 
	 * @param pointImpact : Point d'impact du rayon qui appartient au plan
	 * @param rayon : rayon qui va percuter le plan
	 * @param lumiere : Lumiere de la scene
	 **/
	@Override
	public boolean getSelfOmbre(Point pointImpact, Rayon rayon, Lumiere lumiere) {
		assert pointImpact != null && rayon != null && lumiere != null;
		int indice = appartientCube(pointImpact);
		
		//Assert pt d'impact appartient au cube
		if (indice == -1) {
			System.out.println("getSelfOmbre : Le point n'appartient pas au cube");
			return false;
		}
		
		return this.plans.get(indice).getSelfOmbre(pointImpact, rayon, lumiere);
	}
	
	
	//----------------------------------------------------------------------
	//Autres methodes

	/**
	 * Determine si le cube est traverse par un rayon et son premier point d'impact
	 * (il y en aura presque toujours deux)
	 * @param rayon : rayon
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
			indice = this.appartientCube(point);
			
			if (point != null & indice != -1) {
				if ((distanceMin > point.distance(rayon.getOrigine()) | distanceMin < Objet3D.EPSILON) ) {
					pointReturn = point.copie();
					distanceMin = pointReturn.distance(rayon.getOrigine());
				}
			}
		}
		
		return pointReturn;
	}

	/** Determine la direction et sens du rayon reflechi contre le cube au point d'impact p
	 * @param rayon : rayon allant frapper le cube
	 * @param pointImpact : point oï¿½ a lieu la collision
	 */
	@Override
	public Vecteur directionReflexion(Rayon rayon, Point pointImpact) {
		assert rayon != null && pointImpact != null;
		int indice = appartientCube(pointImpact);
		
		//Assert pt d'impact appartient au cube
		if (indice == -1) {
			System.out.println("Dir Reflexion : Le point n'appartient pas au cube");
			return null;
		}
		
		return this.plans.get(indice).directionReflexion(rayon, pointImpact);
	}
	
	/** Determine la direction et sens du rayon refracte contre le pave au point d'impact p
	 * @param rayon : rayon allant frapper le pave
	 * @param pointImpact : point où a lieu la collision
	 */
	@Override
	public Vecteur directionRefraction(Rayon rayon, Point pointImpact, double indiceRefractionExterieur) {
		assert rayon != null && pointImpact != null;
		int indice = appartientCube(pointImpact);
		
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

	/**Translate le cube de dx, dy, dz
	 * @param dx,dy,dz : De combien sur chaque axe le plan va etre translate
	 */
	@Override
	public void translater(double dx, double dy, double dz) {
		int i;
		
		for (i = 0; i<6; i++) {
			this.plans.get(i).translater(dx, dy, dz);
		}
	}

	/**Effectue une rotation autour des axes x, y, z classiques
	 * @param rx : premiere roation effectuee
	 * @param ry : deuxieme roation effectuee
	 * @param rz : troisieme roation effectuee
	 */
	@Override
	public void rotation(double rx, double ry, double rz) {
		int i;
		
		for (i = 0; i<6; i++) {
			this.plans.get(i).rotation(rx, ry, rz);
		}
		//Ca me parait un peu foireux comme methode faudra voir apres ave l'interface graphique si
		//ï¿½a marche comme ï¿½a.
	}

	/**
	 * @param point : point d'impact
	 * @return Renvoie l'indice du plan correspondant
	 *  			Si le point n'appartient pas au cube --> renvoie -1
	 * 				Si le point appartient ï¿½ une arete/coin --> renvoie un plan au hasard
	 * 					entre ceux qu'il touche
	 */
	public int appartientCube(Point point) {
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
								
				Vecteur vecteurDeTest = new Vecteur(1.0, 1.0, 1.0);
				vecteurDeTest = vecteurDeTest.soustraire(this.plans.get(i).getNormale().abs());
				//VecteurDeTest va servir a pouvoir tester sur les deux autres coordonnees autres
				//que la normale
				
				//Check si il appartient au segment [-arete ; arete] sur chaque coordonnee
				if ( 	(vecteurDeTest.getX() < Objet3D.EPSILON | 
							Math.abs(relatifPointPlan.getX()) < this.arete/2 ) &
						(vecteurDeTest.getY() < Objet3D.EPSILON | 
							Math.abs(relatifPointPlan.getY()) < this.arete/2 ) &
						(vecteurDeTest.getZ() < Objet3D.EPSILON | 
							Math.abs(relatifPointPlan.getZ()) < this.arete/2 ) ) {
					return i;
				}
				
			}
		}
		
		return -1;
	}
	
	/**
	 * Affiche les attributs du cube dans la console
	 */
	@Override
	public String toString() {
		return "Cube(" + this.getNom() + ")@(" + this.centre.getX() + ", " + this.centre.getY() + ", " + this.centre.getZ() + ") arï¿½te : "+this.arete;
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
	
	

}
