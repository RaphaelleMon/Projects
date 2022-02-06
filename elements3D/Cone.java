package elements3D;

import rayTracing.Rayon;
import utilitaire.Point;
import utilitaire.Vecteur;
import exception.NomVideException;


import rayTracing.Lumiere;

/**
 * Classe permettant d'utiliser / manipuler une pyramide pour le raytracing
 * @author tibo
 */

public class Cone extends Objet3D {
	
	private static final long serialVersionUID = -789255897531917008L;

	// compteur pour les noms par défaut
	private static int compteur = 0;
	
	/** Centre du cercle de la pyramide */
	private Point centreBase;
	
	/** Sommet du Cone*/
	private Point sommet;
	
	/** Hauteur de la pyramide */
	private double hauteur;
	
	/** rayon de la base de la pyramide */
	private double rayon;
	
	/** Hauteur de la pyramide */
	private Vecteur vecteurHaut;
	
	/** Plan associe a la base du cercle
	 * Sa normale est orientee vers la pointe du cone */
	private Plan planCercle;
	
	/** Créer un cone à partir du centre du cercle de la pyramide, du vecteur associé à la hauteur de la pyramide,
	 * de la hauteur de la pyramide, du rayon de la base de la pyramide et d'un nom 
	 * @param centreBase : Centre du cercle formant la base de la pyramide
	 * @param vecteurHaut : Vecteur directeur du cone
	 * @param hauteur : Hauteur de la pyramide
	 * @param rayon : rayon de la base de la pyramide
	 * @param nom : Nom donné par l'utilisateur
	 * @throws NomVideException : le nom de l'objet n'est pas indiqué
	 */
	public Cone(Point centreBase, Vecteur vecteurHaut, double hauteur, double rayon, String nom) throws NomVideException {
		super(nom);
		
		assert centreBase != null && vecteurHaut != null && hauteur > 0 && rayon > 0;

		this.centreBase = centreBase.copie();
		
		this.planCercle = new Plan(vecteurHaut.copie(), centreBase.copie(), "Plan associe a la base du cercle");
		
		this.vecteurHaut = vecteurHaut.copie();
		this.vecteurHaut.normaliser();
		
		this.hauteur = hauteur;
		this.rayon = rayon;
		
		this.sommet = this.centreBase.copie();
		this.sommet.translater(vecteurHaut.multiplication(hauteur));
	}
	/**
	 * Créer un cone à partir du centre du cercle de la pyramide, du vecteur associé à la hauteur de la pyramide,
	 * de la hauteur de la pyramide, du rayon de la base de la pyramide
	 * @param centreBase : Centre du cercle de la pyramide
	 * @param vecteurHaut : Vecteur directeur de la pyramide
	 * @param hauteur : Hauteur de la pyramide
	 * @param rayon : rayon de la base de la pyramide
	 * @throws NomVideException : le nom de l'objet n'est pas indiqué
	 */
	public Cone(Point centreBase, Vecteur vecteurHaut, double hauteur, double rayon) throws NomVideException {
		this(centreBase, vecteurHaut, hauteur, rayon, "Pyramide" + ++compteur);
	}
	
	//----------------------------------------------------------------------
	// Methodes get
	
//	@Override
//	public Propriete getMateriau(int num) {
//		assert 0 <= num && num < Materiau.NB_PROPRIETES;
//		return this.properties.getMateriau(num);
//	}
	

	/**
	 * Obtenir le Centre du cercle de la pyramide
	 * @return Point du centre du cercle de la pyramide
	 */
	public Point getCentreBase() {
		return this.centreBase.copie();
	}
	
	/**
	 * Obtenir le Vecteur directeur de la hauteur de la pyramide
	 * @return Vecteur directeur de la pyramide
	 */
	public Vecteur getVecteurDirecteur() {
		return this.vecteurHaut.copie();
	}
	
	/**
	 * Obtenir la hauteur de la pyramide
	 * @return Valeur de la hauteur de la pyramide
	 */
	public double getHauteur() {
		return this.hauteur;
	}
	
	/**
	 * Obtenir le rayon de la Pyramide
	 * @return valeur du rayon de la Pyramide
	 */
	public double getRayon() {
		return this.rayon;
	}
	
	/**
	 * Obtenir le barycentre du cone dans l'espace
	 * @return Barycentre du cone 
	 */
	@Override
	public Point getPosition() {
		Vecteur ajout = this.vecteurHaut.multiplication(hauteur/4);
		Point retour = this.centreBase.copie();
		retour.translater(ajout);
		return retour;
	}
	
	/**
	 * Retourne la normale au point d'impact et aussi son sens grace au rayon
	 * 
	 * Deux cas :
	 * 		Si il touche la base du cercle : On utilise la fonction du plan associe
	 * 		Si il touche le reste : On va proceder a des projections
	 * 		
	 * @param impact : Point d'impact du rayon qui appartient a la pyramide
	 * @param rayon : rayon qui va percuter la pyramide
	 */
	@Override
	public Vecteur getNormal(Point impact, Rayon rayon) {
		assert impact != null && rayon != null;
		int indice = appartientPyramide(impact);
		
		//Assert pt d'impact appartient a la pyramide
		if (indice == 0) {
			System.out.println("getNormal : Le point n'appartient pas a la pyramide");
		} else if( indice == 1) {
			//Le point appartient a la base
			return this.planCercle.getNormal(impact, rayon);
		} else if( indice == 2) {
			//Le point appartient au reste du cone
			// Methode : 
			//On part du vecteur centreBase -> impact et on va enlever sa projection sur l'axe
			//qui doit etre perpendiculaire a la normale en sortie, cad 
			//point Peripherie Base <-> Pointe du cone
			Vecteur normale = new Vecteur(this.centreBase.copie(), impact);
			
			Vecteur projectionBase = normale.copie();
			projectionBase.retirerProjection(vecteurHaut);
			projectionBase.multiplication(this.rayon / projectionBase.module());
			
			Point pointPeripherieBase = this.centreBase.copie();
			pointPeripherieBase.translater(projectionBase);
			
			Vecteur VPeripherieSommet = new Vecteur(pointPeripherieBase, this.sommet);
			normale.retirerProjection(VPeripherieSommet);
			
			//On a plus qu'a prendre l'opposee de la normale si le rayon venait de l'interieur
			if (normale.produitScalaire(rayon.getDirection()) > 0 ) {
				normale = normale.multiplication(-1);
			}
			
			return normale;
		}
		
		return null;
	}
	
	/**
	 * Retourne true si l'objet se fait de l'ombre lui meme
	 * 
	 * Pour le reste, autre de la base du plan :
	 * On calcule le plan associe au pt d'impact et sa normale. Et on utilise la methode de ce plan
	 * 
	 * @param impact : Point d'impact du rayon qui appartient a la pyramide
	 * @param rayon : rayon qui va percuter la pyramide
	 * @param lumiere : Lumiere de la scene
	 **/
	@Override
	public boolean getSelfOmbre(Point impact, Rayon rayon, Lumiere lumiere) {
		assert impact != null && rayon != null && lumiere != null;
		int indice = appartientPyramide(impact);
		
		//Assert pt d'impact appartient a la pyramide
		if (indice == 0) {
			System.out.println("getSelfOmbre : Le point n'appartient pas a la pyramide");
			return false;
		} else if (indice == 1) {
			//Le point appartient a la base
			return this.planCercle.getSelfOmbre(impact, rayon, lumiere);
		} else if (indice == 2) {
			//Le point appartient au reste
			Vecteur normale = getNormal(impact, rayon);
			Plan plan = null;
			try {
			plan = new Plan(normale, impact);
			} catch (NomVideException e) { // shouldn't happen
				}
			return plan.getSelfOmbre(impact, rayon, lumiere);
		}
		
		return false;
	}
	
	
	//----------------------------------------------------------------------
	//Autres methodes

	/** https://www.geometrictools.com/Documentation/IntersectionLineCone.pdf
	 * Determine si la pyramide est traversee par un rayon et son premier point d'impact
	 * (il y en aura presque toujours deux)
	 * 
	 * Methode : On va calculer trois points : 
	 * 		Un pour indiquer si il traverse la base
	 * 		Deux pour indiquer les points qui traversent (ou non) le reste du cone
	 * 
	 * @param rayon : rayon
	 * @return Point d'impact du rayon sur le cone
	 */
	@Override
	public Point estTraversePar(Rayon rayon) {
		assert rayon != null;
		
		//Testons si il traverse la base :
		Point impactBase = this.planCercle.estTraversePar(rayon);
		if ( impactBase != null && impactBase.distance(this.centreBase) > this.rayon) {
			impactBase = null;
		}
		
		//Test si il traverse le reste
		//Ces equations servent a trouver t1 et t2, deux reels tq : 
		//		ptImpact[1/2] = t[1/2] * vecteurRayon + origineRayon
		Vecteur D = this.vecteurHaut.multiplication(-1);
		Vecteur U = rayon.getDirection().copie();
		double gamma = this.hauteur / Math.sqrt(this.hauteur*this.hauteur + this.rayon*this.rayon);
		Point P = rayon.getOrigine().copie();
		Point V = this.centreBase.copie();
		V.translater(vecteurHaut.multiplication(hauteur));
		Vecteur delta = new Vecteur(V,P);
		double c2 = Math.pow(D.produitScalaire(U), 2) - gamma * gamma * U.produitScalaire(U);
		double c1 = (D.produitScalaire(U))*(D.produitScalaire(delta)) - gamma*gamma * U.produitScalaire(delta);
		double c0 = Math.pow(D.produitScalaire(delta), 2) - gamma*gamma * delta.produitScalaire(delta);
		
		double t1 = 0;
		double t2 = 0;
		
		if (Math.abs(c2) > Objet3D.EPSILON) {
			double petitDelta = c1*c1 - c0*c2;
			if (petitDelta > 0) {
				//Cela veut dire que le rayon intersecte bien le double-sided cone
				t1 = (-c1 + Math.sqrt(petitDelta))/c2;
				t2 = (-c1 - Math.sqrt(petitDelta))/c2;
			}
		} else if (Math.abs(c2) < Objet3D.EPSILON & Math.abs(c1) > Objet3D.EPSILON) {
			t1 = -c0 / (2*c1);
			t2 = t1;
		} else if (Math.abs(c2) < Objet3D.EPSILON & Math.abs(c1) < Objet3D.EPSILON) {
			if (Math.abs(c0) < Objet3D.EPSILON) {
				//System.out.println("Rayon se confond avec le cote du cone");
			}
		}
		
		//if (t1 < 0 | t2 < 0) {System.out.println("t1 ou t2 est negatif chef : "+t1+"   "+t2);}
		
		Point point1 = null;
		if (t1 > Objet3D.EPSILON) { //Si t1 ou t2 < 0, alors le point d'impact n'est pas dans le 
			//sens du rayon. On ne le prend donc pas en compte
			point1 = rayon.getOrigine().copie();
			point1.translater(rayon.getDirection().multiplication(t1));
			if (appartientPyramide(point1) != 2) {//Test important car les equations
				//utilisees sont pour un double-sided cone
				point1 = null;
			}
		}
		
		Point point2 = null;
		if (t2 > Objet3D.EPSILON) {
			point2 = rayon.getOrigine().copie();
			point2.translater(rayon.getDirection().multiplication(t2));
			if (appartientPyramide(point2) != 2) {
				point2 = null;
			}
		}
			
		//J'ai tous les points, il faut donc tester lequel est le plus proche de l'origine
		double distanceMin = 0;
		Point lePlusProche = null;
		if (impactBase != null) {
			double distanceBase = impactBase.distance(rayon.getOrigine());
			lePlusProche = impactBase;
			distanceMin = distanceBase;
		}
		if (point1 != null) {
			double distancep1 = point1.distance(rayon.getOrigine());
			if (distanceMin < Objet3D.EPSILON | distanceMin > distancep1) {
				lePlusProche = point1;
				distanceMin = distancep1;
			}
		}
		if (point2 != null) {
			double distancep2 = point2.distance(rayon.getOrigine());
			if (distanceMin < Objet3D.EPSILON | distanceMin > distancep2) {
				lePlusProche = point2;
				distanceMin = distancep2;
			}
		}
		
		return lePlusProche;
	}

	/** Determine la direction et sens du rayon reflechi contre la pyramide au point d'impact p
	 * @param rayon : rayon allant frapper la pyramide
	 * @param pointImpact : point où a lieu la collision
	 * @return Vecteur reflechi du rayon rayon au point d'impact pointImpact
	 */
	@Override
	public Vecteur directionReflexion(Rayon rayon, Point pointImpact) {
		assert rayon != null && pointImpact != null;
		int indice = appartientPyramide(pointImpact);
		
		//Assert pt d'impact appartient au cube
		if (indice == 0) {
			System.out.println("Dir Reflexion : Le point n'appartient pas a la pyramide");
			return null;
		} else if (indice == 1) {
			//Le point appartient a la base
			return this.planCercle.directionReflexion(rayon, pointImpact);
		} else if (indice == 2) {
			//Le point appartient au reste
			Vecteur projection = new Vecteur(rayon.getDirection().getX(),rayon.getDirection().getY(),rayon.getDirection().getZ());
			projection.retirerProjection(getNormal(pointImpact, rayon));
			
			Vecteur dirReflexion = rayon.getDirection().soustraire(projection.multiplication(2));
			dirReflexion = dirReflexion.soustraire(dirReflexion.multiplication(2));
			return dirReflexion;
		}
		return null;
	}
	
	/** Determine la direction et sens du rayon refracte contre le cone au point d'impact pointImpact
	 * @param rayon : rayon allant frapper le cone
	 * @param pointImpact : point d'impact du rayon contre le cone
	 * @return Vecteur diffract du rayon rayon au point d'impact pointImpact
	 */
	@Override
	public Vecteur directionRefraction(Rayon rayon, Point pointImpact, double indiceRefractionExterieur) {
		assert rayon != null && pointImpact != null;
		int indice = appartientPyramide(pointImpact);
		
		//Assert pt d'impact appartient au cube
		if (indice == 0) {
			System.out.println("Dir Refraction : Le point n'appartient pas a la pyramide");
			pointImpact.afficher();
			return null;
		} else if (indice == 1) {
			//Le point appartient a la base
			Boolean exterieurVersInterieur;
			if (planCercle.getNormale().produitScalaire(rayon.getDirection()) > 0 ) {
				exterieurVersInterieur = true;
			} else {
				exterieurVersInterieur = false;
			}
			return this.planCercle.directionRefraction(rayon, this.getMateriau().getRefringence().getIndiceInterieur() , exterieurVersInterieur, this.getMateriau().getRefringence().getIndiceExterieur());
		} else if (indice == 2) {
			//Le point appartient au reste
			Vecteur normale = getNormal(pointImpact,rayon);
			Plan plan;
			try {
				plan = new Plan(normale, pointImpact, "plan temporaire");
				Boolean exterieurVersInterieur;
				if (normale.produitScalaire(vecteurHaut) > 0) {
					exterieurVersInterieur = true;
				} else {
					exterieurVersInterieur = false;
				}
				
				return plan.directionRefraction(rayon, this.getMateriau().getRefringence().getIndiceInterieur(), exterieurVersInterieur, this.getMateriau().getRefringence().getIndiceExterieur());
			} catch (NomVideException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	/**Translate la pyramide de dx, dy, dz
	 * @param dx,dy,dz : De combien sur chaque axe la pyramide va etre translate
	 */
	@Override
	public void translater(double dx, double dy, double dz) {
		Vecteur deplacement = new Vecteur(dx,dy,dz);
		
		
		this.centreBase.translater(deplacement);
		this.planCercle.translater(dx, dy, dz);
	}

	/**Effectue une rotation autour des axes x, y, z classiques
	 * @param rx : premiere roation effectuee
	 * @param ry : deuxieme roation effectuee
	 * @param rz : troisieme roation effectuee
	 */
	@Override
	public void rotation(double rx, double ry, double rz) {
		this.vecteurHaut.rotationXYZ(rx, ry, rz);
		Point barycentre = this.getPosition();
		this.centreBase.rotationAxe(new Vecteur(1,0,0), barycentre, rx);
		this.centreBase.rotationAxe(new Vecteur(0,1,0), barycentre, ry);
		this.centreBase.rotationAxe(new Vecteur(0,0,1), barycentre, rz);
		try {
			this.planCercle = new Plan(vecteurHaut.copie(), centreBase.copie(), "Plan associe a la base du cercle");
		} catch (NomVideException e) {
			// Ne doit jamais arriver
		}
	}


	
	/**
	 * @param point : point d'impact
	 * @return Renvoie l'indice de la pyramide correspondant
	 * 				Si le point n'appartient pas a la pyramide --> renvoie 0
	 *  			Si le point appartient a la base --> renvoie 1
	 * 				Si le point appartient au reste --> renvoie 2
	 */
	public int appartientPyramide(Point point) {
		if (point == null) {
			return 0;
		}
		//Test si ça appartient a la base circulaire
		Vecteur relatif_PointCentreBase = new Vecteur(this.centreBase,point);
		if (this.vecteurHaut.produitScalaire(relatif_PointCentreBase) < Objet3D.EPSILON) {
			//On sait qu'on est dans le plan
			if (point.distance(this.centreBase) < this.rayon) {
				return 1;
			}
		}
		
		//Test si ca appartient au reste
		double abscisse = this.vecteurHaut.produitScalaire(relatif_PointCentreBase);
		Vecteur vecteurOrdonnee = new Vecteur(this.centreBase, point);
		vecteurOrdonnee.retirerProjection(vecteurHaut);
		double ordonnee = vecteurOrdonnee.module();
		if ( abscisse > 0 & abscisse < this.hauteur &
		  Math.abs( ordonnee - (this.rayon - abscisse * this.rayon/this.hauteur)) < Objet3D.EPSILON) {
			return 2;
		}
		
		return 0;
	}
	
	/**
	 * Affiche les attributs de la pyramide dans la console
	 */
	@Override
	public String toString() {
		return "Pyramide(" + this.getNom() + ")@(" + this.centreBase.getX() + ", " + this.centreBase.getY() 
			+ ", " + this.centreBase.getZ() + ") hauteur : "+this.hauteur + "   rayon : " + this.rayon;
	}
	
	/**
	 * Indique si la sphère est en dehors de la scène
	 * @param dimension : Dimension de la scène
	 * @param centreScene : Centre de la scène
	 * @return indication booleenne
	 */
	@Override
	public boolean estHorsScene(double dimension, Point centreScene) {
		return this.centreBase.distance(centreScene) > dimension;
	}
	
	/**
	 * Modifier le point du centre de la base 
	 * @param point : nouveau point centre de la base du cone
	 */
	public void setCentreBase(Point point) {
		assert point != null;
		this.centreBase = point.copie();
		this.planCercle.setPoint(point.copie());
	}
	
	/** 
	 * Modifier la direction du vecteur directeur de la hauteur de la Pyramide
	 * @param vecteur : nouvelle direction du vecteur directeur de la hauteur de la pyramide
	 */
	public void setVecteurDirecteur(Vecteur vecteur) {
		assert vecteur != null;
		Vecteur vectcopie = vecteur.copie();
		vectcopie.normaliser();
		this.vecteurHaut = vectcopie.copie();
		this.planCercle.setNormale(vectcopie.copie());
	}
	
	/**
	 * Modifier la valeur de la hauteur du cone
	 * @param hauteur : hauteur du cone 
	 */
	public void setHauteur(double hauteur) {
		assert hauteur > 0;
		this.hauteur = hauteur;
	}
	
	/**
	 * Modifier la valeur du rayon du la base du cone
	 * @param rayon : rayon de la base du cone 
	 */
	public void setRayon(double rayon) {
		assert rayon > 0;
		this.rayon = rayon;
	}


	

}
