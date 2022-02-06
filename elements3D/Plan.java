package elements3D;

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

public class Plan extends Objet3D {
	
	private static final long serialVersionUID = 7303995654083808341L;
	private static int compteur = 0; // compteur pour les noms par dï¿½faut

	/** Vecteur normal au plan
	 * Est toujours normalisee */
	private Vecteur normale;
	
	/** Point par lequel le plan passe*/
	private Point point;

	/**
	 * Créer un plan à partir d'un vecteur normal, point et le nom
	 * @param normale : vecteur normal
	 * @param point : point par lequel le plan passe
	 * @param nom : nom du plan
	 * @throws NomVideException
	 */
	public Plan(Vecteur normale, Point point, String nom) throws NomVideException {
		super(nom);
		normale.normaliser();
		this.normale = normale.copie();
		this.point = point.copie();
	}
	
	/**
	 * Créer un plan à partir d'un vecteur normal point et un nom par défaut
	 * @param normale : vecteur normal
	 * @param point : point par lequel le plan passe
	 * @throws NomVideException
	 */
	public Plan(Vecteur normale, Point point) throws NomVideException  { // ne devrait jamais throw l'exception en rï¿½alitï¿½
		this(normale, point, "Plan" + ++compteur);
	}
	
	/**
	 * Créer un plan à partir d'un vecteur normal, point, nom et matériau
	 * @param normale : vecteur normal
	 * @param point : point par lequel passe le plan
	 * @param nom : nom du plan
	 * @param materiau : materiau du plan
	 * @throws NomVideException
	 */
	public Plan(Vecteur normale, Point point, String nom, Materiau materiau) throws NomVideException {
		super(nom);
		normale.normaliser();
		this.normale = normale.copie();
		this.point = point.copie();
	}
	
	//----------------------------------------------------------------------
	
	// Methodes get
	
	/**
	 * Obtenir le point
	 * @return Point
	 */
	public Point getPoint() {
		return this.point;
	}
	
	/**
	 * Changer le point par lequel le plan passe
	 * @param point : nouveau point
	 */
	public void setPoint(Point point) {
		this.point = point.copie();
	}
	
	/**
	 * Obtenir le vecteur normal
	 * @return vecteur normal
	 */
	public Vecteur getNormale() {
		return this.normale;
	}
	
	/**
	 * Modifier le vecteur normal lié au plan
	 * @param normale : nouveau vecteur normal
	 */
	public void setNormale(Vecteur normale) {
		Vecteur vectcopie = normale.copie();
		vectcopie.copie();
		this.normale = vectcopie;
	}

	
	/** Renvoie le point le plus proche du centre de la scene (0, 0, 0)
	 * On va partir du centre et se deplacer selon la normale du plan pour trouver ce point
	 */
	@Override
	public Point getPosition() {
		//Vecteur translation = this.normale.copie();
		//Point centreScene = new Point(0,0,0);
		
		//On teste si la normale est dans le bon sens pour la translation
		//Vecteur pointPlanVersCentreScene = new Vecteur(this.point, new Point(0,0,0));
		//if (pointPlanVersCentreScene.produitScalaire(translation) > 0) {
			//translation.multiplication(-1);
		//}
		
		//On cree un rayon qui passe par le centre de la scene et qui va vers le plan,
		//et paf ! ça fait des chocapics !
		//return this.estTraversePar(new Rayon(translation, centreScene));
		return this.point;
	}
	
	/**
	 * Retourne la normale au point d'impact et aussi son sens grace au rayon
	 * @param pointImpact : Point d'impact du rayon qui appartient au plan
	 * @param rayon : rayon qui va peruter le plan
	 * @return La normale au plan au point d'impact
	 */
	@Override
	public Vecteur getNormal(Point pointImpact, Rayon rayon) {
		assert pointImpact != null && rayon != null;
		//Assert impact appartient au plan
		Vecteur vPtPlanPtImpact = new Vecteur(pointImpact, this.point);
		if (Math.abs(vPtPlanPtImpact.produitScalaire(this.normale)) > Objet3D.EPSILON) {
			System.out.println("Le point d'impact n'appartient pas au plan");
			return null;
		}
		
		Vecteur direction = rayon.getDirection();
		double dot = direction.produitScalaire(this.normale);
		Vecteur retour = new Vecteur(this.normale.getX(), this.normale.getY(), this.normale.getZ());
		

		if (dot > 0) {
			retour.multiplication(-1);
		}
		return retour;
	}
	
	/**
	 * Retourne true si l'objet se fait de l'ombre lui meme
	 * @param pointImpact : Point d'impact du rayon qui appartient au plan
	 * @param rayon : rayon qui va percuter le plan
	 * @param lumiere : Lumiere de la scene
	 * @return true si l'objet se fait de l'ombre lui meme
	 **/
	@Override
	public boolean getSelfOmbre(Point pointImpact, Rayon rayon, Lumiere lumiere) {
		assert pointImpact != null && rayon != null && lumiere != null;
		//Assert impact appartient au plan
		Vecteur vPtPlanPtImpact = new Vecteur(pointImpact, this.point);
		if (Math.abs(vPtPlanPtImpact.produitScalaire(this.normale)) > Objet3D.EPSILON) {
			System.out.println("Le point d'impact n'appartient pas au plan");
			return false;
		}
		
		Vecteur directionRayon = rayon.getDirection();
		double dotRayon = directionRayon.produitScalaire(this.normale);
		
		Vecteur directionLumiere = new Vecteur(lumiere.getCentre(), this.point);
		double dotLumiere = directionLumiere.produitScalaire(this.normale);
		
		return !(Math.signum(dotLumiere) == Math.signum(dotRayon));
	}
	
	
	//----------------------------------------------------------------------
	//Autres methodes

	/** http://nguyen.univ-tln.fr/share/Infographie3D/trans_raytracing.pdf
	 * Determine si le plan est traverse par un rayon
	 * @param rayon : rayon
	 * @return le point d'impact du rayon sur le plan
	 */
	@Override
	public Point estTraversePar(Rayon rayon) {
		assert rayon != null;
		double a,b,c,d,i,j,k,originerx,originery,originerz;
		a = this.normale.getX();
		b = this.normale.getY();
		c = this.normale.getZ();
		d = -( a*point.getX() + b*point.getY() + c*point.getZ() );
		
		i = rayon.getDirection().getX();
		j = rayon.getDirection().getY();
		k = rayon.getDirection().getZ();
		
		originerx = rayon.getOrigine().getX();
		originery = rayon.getOrigine().getY();
		originerz = rayon.getOrigine().getZ();
		
		double denominateur = a*i + b*j + c*k;
		if (Math.abs(denominateur) < Objet3D.EPSILON) {
			//System.out.println("Rayon est parallele au plan");
			return null;
		}
		double t = -(a*originerx + b*originery + c*originerz + d) / denominateur;
		if (t < 0) {
			//System.out.println("Le plan est pas dans le sens du vecteur");
			return null;
		}
		
		Point pointDeCollision = new Point(originerx,originery,originerz);
		pointDeCollision.translater(rayon.getDirection().multiplication(t));
		if (pointDeCollision.equals(rayon.getOrigine(), 0.001)) {
			return null;
		} else {
			return pointDeCollision;
		}
	}

	/** Determine la direction et sens du rayon reflechi contre le plan au point d'impact p
	 * @param rayon : rayon allant frapper le plan
	 * @param pointImpact : ici inutile puisque la direction de reflexion ne depend pas du point d'impact
	 * pour un plan
	 * @return le vecteur reflechi du rayon r au point d'impact p
	 */
	@Override
	public Vecteur directionReflexion(Rayon rayon, Point pointImpact) {
		assert rayon != null && pointImpact != null;
		Vecteur projection = new Vecteur(rayon.getDirection().getX(),rayon.getDirection().getY(),rayon.getDirection().getZ());
		projection.retirerProjection(this.normale);
		
		Vecteur dirReflexion = rayon.getDirection().soustraire(projection.multiplication(2));
		dirReflexion = dirReflexion.soustraire(dirReflexion.multiplication(2));
		return dirReflexion;
	}
	
	/** Determine la direction et sens du rayon refracte contre le plan au point d'impact p
	 * @param rayon : rayon allant frapper le plan
	 * @param pointImpact : ici inutile puisque la direction de reflexion ne depend pas du point d'impact
	 * pour un plan infiniment fin, le rayon passe juste a travers sans etre refracte
	 */
	//@Override
	public Vecteur directionRefraction(Rayon rayon, Point pointImpact, double indiceRefractionExterieur) {
		return rayon.getDirection(); // un plan n'engendre pas de refraction
	}
	
	/** Determine la direction et sens du rayon refracte contre le plan au point d'impact p
	 * Cette fonction va servir pour les autres objets, car sert a simuler une surface
	 * 		de separation entre deux milieux
	 * @param rayon : rayon allant frapper le plan
	 * @param nObjet : Indice de rÃ©fraction de l'objet
	 * @param exterieurVersInterieur : sert a savoir dans quel sens s'effectue la refraction
	 * @return le vecteur directeur du rayon refracte du rayon r
	 */
	public Vecteur directionRefraction(Rayon rayon, double nObjet, Boolean exterieurVersInterieur, double indiceRefractionExterieur) {
		double n1, n2, i1, i2;
		if (exterieurVersInterieur) {
			n1 = indiceRefractionExterieur;
			n2 = nObjet;
		} else {
			n1 = nObjet;
			n2 = indiceRefractionExterieur;
		}
		//System.out.println("indice interieur : "+nObjet + "      indice exterieur : "+ indiceRefractionExterieur);
		
		Vecteur normaleImpact = this.normale.copie();
		//Si la normale est pas dans le bon sens on la retourne
		if (this.normale.produitScalaire(rayon.getDirection()) > 0) {
			normaleImpact = normaleImpact.multiplication(-1);
		}
		
		i1 = rayon.getDirection().produitScalaire(normaleImpact.multiplication(-1)) / ( rayon.getDirection().module() * normaleImpact.module() );
		i1 = Math.acos(i1);
		
		//On obtient i2
		double angleRefTotale = Math.asin(n2/n1);
		if (n2 < n1 & i1 > angleRefTotale) {
			//Il y a reflexion totale
			return null;
		}
		i2 = Math.asin( (n1/n2) * Math.sin(i1) );
		
		//Si on a pas de reflexion totale alors on calcule & retourne le rayon refracte
		Vecteur axeRotation = rayon.getDirection().produitVectoriel(normaleImpact);
		axeRotation.normaliser();
		
		Vecteur directionRefraction = normaleImpact.multiplication(-1);
		directionRefraction = directionRefraction.rotationAxe(axeRotation, i2);
		
		return directionRefraction;
		
	}

	
	/**Translate le plan de dx, dy, dz
	 * @param dx,dy,dz : De combien sur chaque axe le plan va etre translate
	 */
	@Override
	public void translater(double dx, double dy, double dz) {
		this.point.translater(dx, dy, dz);
	}
	
	/**Effectue une rotation autour des axes x, y, z classiques
	 * @param rx : premiere roation effectuee
	 * @param ry : deuxieme roation effectuee
	 * @param rz : troisieme roation effectuee
	 */
	@Override
	public void rotation(double rx, double ry, double rz) {
		this.normale.rotationXYZ(rx,ry,rz);
	}
	
	/**Effectue une rotation autour d'un axe donne en entree
	 * @param rx : premiere roation effectuee
	 * @param ry : deuxieme roation effectuee
	 * @param rz : troisieme roation effectuee
	 */
	public void rotation(Point centre,double rx, double ry, double rz, Vecteur AxeX, Vecteur AxeY, Vecteur AxeZ) {
		this.normale = this.normale.rotationAxe(AxeX, rx);
		this.normale = this.normale.rotationAxe(AxeY, ry);
		this.normale = this.normale.rotationAxe(AxeZ, rz);
		
		this.point = this.point.rotationAxe(AxeX, centre, rx);
		this.point = this.point.rotationAxe(AxeY, centre, ry);
		this.point = this.point.rotationAxe(AxeZ, centre, rz);
	}
	
	/**
	 * Affiche les attributs du plan dans la console
	 */
	@Override
	public String toString() {
		return "Plan(" + this.getNom() + ")@(" + this.point.getX() + ", " + this.point.getY() + ", " + this.point.getZ() + ") de normale : (" + this.normale.getX() + ", " + this.normale.getY() + ", " + this.normale.getZ() + ")";
	}
	
	/**
	 * Retourne la copie du plan pour la robustesse
	 * @return plan
	 */
	public Plan copie() {
		Plan copie = null;
		try {
			copie = new Plan(this.normale, this.point, this.getNom(), this.getMateriau());
		} catch (NomVideException e) {
			// ne devrait pas arriver
		}
		return copie;
	}

	@Override
	public boolean estHorsScene(double dimension, Point centre) {
		return false;
	}
}
