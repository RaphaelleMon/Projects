/**
 * 
 */
package rayTracing;
import elements3D .*;
import exception.MaxRebondsNegatifException;
import utilitaire .*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe contenant l'implantation de l'algorithme du Ray Tracing
 *
 */
public class RayTracing {
	  
	private static final boolean DEBUG = false;
	
	/** Scene dans laquelle sont definies les objets et les lumieres */
	private Scene scene;
	
	/** Camera associee a la scene */
	private Camera camera;
	
	/** Nombre maximum de rebonds que peut faire un rayon*/
	private int maxRebond;
	
	/** Indique si l'on représente les ombres pour le rendu.*/
	private boolean ombreIsOn;
	
	/** Indique si l'on utilise la technique de shadding pour le rendu.*/
	private boolean shadingIsOn;
	
	/** Indique si l'on utilise la sp�cularit� pour le rendu.*/
	private boolean speculariteIsOn;
	
	/** Indique si l'on utilise l'ambiance pour le rendu.*/
	private boolean ambianceIsOn;

	/** 
	 * @param nscene : Scene dans laquelle sont definies les objets et les lumieres
	 * @param ncamera : Camera associee a la scene
	 * @param rebonds : Nombre maximum de rebonds que peut faire un rayon
	 */
	public RayTracing(Scene nscene, Camera ncamera, int rebonds, boolean ombre, boolean shadding) 
												throws MaxRebondsNegatifException {
		if (rebonds < 0) {
			throw new MaxRebondsNegatifException();
		};

		assert scene != null;
		assert camera != null;

		this.scene = nscene;
		this.camera = ncamera;
		this.maxRebond = rebonds;
		this.ombreIsOn = ombre;
		this.shadingIsOn = shadding;
		this.ambianceIsOn = false;
		this.speculariteIsOn = false;
	}
	/**
	 * Obtenir la scène.
	 * @return Scène sur laquelle le Ray Tracing opère.
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	/**
	 * modifier la scène.
	 * @param nouvelle Scène sur laquelle le Ray Tracing va opère.
	 */
	public void setScene(Scene s) {
		this.scene = s;
	}
	
	
	/**
	 * Obtenir la camera.
	 * @return Camera avec laquelle Ray Tracing opere.
	 */
	public Camera getCamera() {
		return this.camera;
	}
	
	/**
	 * Obtenir si les ombres sont activ�es.
	 * @return etat des ombres.
	 */
	public boolean getOmbre() {
		return this.ombreIsOn;
	}
	
	/**
	 * Obtenir le nombre de rebonds
	 * @return nombre de rebonds.
	 */
	public int getRebond() {
		return this.maxRebond;
	}
	
	
	/**
	 * Obtenir si le shadding est activ�e.
	 * @return shadding actif ou non.
	 */
	public boolean getShadding() {
		return this.shadingIsOn;
	}
	
	/**
	 * Modifier ombreIsOn.
	 * @param o
	 */
	public void setOmbre(boolean o) {
		this.ombreIsOn = o;
	}
	
	/**
	 * Obtenir le nombre de rebonds.
	 * @param r.
	 */
	public void setRebond(int r) {
		this.maxRebond = r;
	}
	
	/**
	 * Modifier shadding.
	 * @param o
	 */
	public void setShadding(boolean o) {
		this.shadingIsOn = o;
	}
	
	/**
	 * Modifier sp�cularit�.
	 * @param o
	 */
	public void setSpecularite(boolean o) {
		this.speculariteIsOn = o;
	}
	
	/**
	 * Modifier ambiance.
	 * @param o
	 */
	public void setAmbiance(boolean o) {
		this.ambianceIsOn = o;
	}
	
	/**
	 * Obtenir si la sp�cularit� est activ�e.
	 * @return sp�cularit� active ou non.
	 */
	public boolean getSpecularite() {
		return this.speculariteIsOn;
	}
	
	/**
	 * Obtenir si l'ambiance est activ�e.
	 * @return ambiance active ou non.
	 */
	public boolean getAmbiance() {
		return this.ambianceIsOn;
	}
	
	/** Obtenir la couleur finale d'un pixel a partir de ses rayons fils.
	 * @param listeRayonsFinaux la liste des rayons fils finaux
	 * @return la couleur finale du pixel
	 */
	private static Color getCouleurFinale(ArrayList <Rayon> listeRayonsFinaux) {
		Color couleurFinale = Color.black;
		double pE;
		double r, g, b;
		int tailleListeRayons = listeRayonsFinaux.size();
		Rayon rayon;
		for (int i = 0; i < tailleListeRayons; i++) {
			rayon = listeRayonsFinaux.get(i);
			pE = rayon.getPartEnergie();
			r = Math.max(couleurFinale.getRed(), pE * rayon.getCouleur().getRed());
			g = Math.max(couleurFinale.getGreen(), pE * rayon.getCouleur().getGreen());
			b = Math.max(couleurFinale.getBlue(), pE * rayon.getCouleur().getBlue());
			couleurFinale = new Color((int)r,(int)g,(int)b);
			//System.out.println(pE);
		}
		return couleurFinale;
	}
	
	
	/** Modifier la couleur d'un rayon fils par rapport à sa couleur et à la couleur de son père.
	 * @param rayon le rayon dont on veut modifier la couleur
	 * @param couleur la couleur du rayon
	*/
	private static void setCouleurRayon(Rayon rayon, Couleur couleur) {
		
		double pC = rayon.getPartCouleur();
		double rouge = Math.min(rayon.getCouleur().getRed(), pC * couleur.getRed());
		double vert = Math.min( rayon.getCouleur().getGreen(), pC * couleur.getGreen());
		double bleu = Math.min( rayon.getCouleur().getBlue(), pC * couleur.getBlue());
		rayon.setCouleur((int)rouge, (int)vert, (int)bleu);
		
	}

	/** 
	 * Lance le calcul du rendu en utilisant la technique de ray tracing.
	 */
	public void lancerRayTracing() {
		int pixelHauteur = this.camera.getPixelHauteur(); //le nombre de pixels sur la hauteur de l'écran
		int pixelLongueur = this.camera.getPixelLongueur(); //le nombre de pixels sur la largeur de l'écran
		
		//  Constantes de la boucle
		Pixel pixelCourant; //pixel dont on calcule la couleur
		Rayon rayonCourant; //rayon père que l'on envoie à partir du pixel père pixelCourant
		Color couleurCourante; //couleur du pixel pixelCourant
		ArrayList<Rayon> listeRayonsFinaux; //liste des rayons fils finaux lancés à partir de pixelCourant
																			//Peut-être changer la collection
		
		// Parcours de tous les pixels de l'écran
		for (int i=0; i < pixelHauteur; i++) {
			for (int j=0; j <pixelLongueur; j++) {
				
				listeRayonsFinaux = new ArrayList<Rayon>();
				
				pixelCourant = camera.getPixel(i,j);
				rayonCourant = new Rayon(camera.getCentre(), pixelCourant.getCoordonnee(), pixelCourant);
				rayonCourant.setCouleur(255, 255, 255); // on initialise la couleur du rayon à BLANC
				
				//lancement du rayon, ie recherche d'une intersection avec un objet
				this.lancerRayon(rayonCourant,0,listeRayonsFinaux);
				
				//calcul de la couleur finale à partir des rayons fils
				couleurCourante = getCouleurFinale(listeRayonsFinaux); // à faire

				//mise à jour de la couleur du pixel
				pixelCourant.setCouleur(couleurCourante);
			}
		}
	}
	
	/** Lancer un rayon, ie recherche d'une intersection avec un objet.
	 * @param rayon
	 * @param compteur le nombre de rebond déjà effectués par rayon
	 * @param listeRayonsFinaux liste des rayons finaux associés au pixel père de rayon
	 */
	private void lancerRayon(Rayon rayon, int compteur, ArrayList <Rayon> listeRayonsFinaux) {

		
		// Intersection la plus proche avec le rayon
		Objet3D objetIntersection = null;
		Point intersection = null;
		double distanceMin = this.scene.getDimension();
		
		// Objet que l'on teste
		
		Point intersectionCourante = null;
		double distanceCourante;
		
		//parcours des Materiau
		Materiau materiauCourant;
		
		//Parcours des objets de la scene pour d�terminer s'il y a une intersection avec le rayon
		// TODO : � transf�rer dans Scene en d�finissant une classe intersection (Point, Objet, etc)
		for (Objet3D objetCourant : this.scene.getObjets()) {
			
			
			// Si on objetCourant est l'objet dont est issu le rayon
			// On translate le point d'origine du rayon de 2*Objet3D.EPSILON pour �viter les erreur d'approximation
			if ( rayon.getObjetOrigine() == objetCourant) {
				Vecteur vecteurApproximation = rayon.getDirection().multiplication(2 * Objet3D.EPSILON);
				Point origineApproximation = rayon.getOrigine().copie();
				origineApproximation.translater(vecteurApproximation);
				Rayon rayonApproximation = new Rayon(vecteurApproximation, origineApproximation);
				intersectionCourante = objetCourant.estTraversePar(rayonApproximation);
				
				
			} else {
				intersectionCourante = objetCourant.estTraversePar(rayon);
			}
			
			
			// On détermine si l'objet est plus proche de l'origine du rayon
			if (intersectionCourante != null) {
				distanceCourante = intersectionCourante.distance(rayon.getOrigine()); //distance à ajouter dans Point
				if (distanceCourante < distanceMin) {
					objetIntersection = objetCourant;
					intersection = intersectionCourante;
					distanceMin = distanceCourante;
				}
			}
		}
		
		// Traitement de l'impact du rayon sur l'objet 
		if (objetIntersection != null && intersection != null) {

			Couleur couleurObjetInt = objetIntersection.getMateriau().getCouleur();

			// Détection de l'ombre
			if (this.ombreIsOn) {
				Vecteur normal = objetIntersection.getNormal(intersection, rayon);
				lancerShadowRay(rayon, couleurObjetInt, rayon.getDirection(), normal, intersection, objetIntersection);
			} else {
				setCouleurRayon(rayon, couleurObjetInt);	
			}

			//listeRayonsFinaux.add(rayon); // à modifier après la première itération
			
			// Lancer des rayons issus de rayon selon les propriétés de l'objet intersecté
			// TODO : Tester aussi la contribution en �nergie
			if (compteur < this.maxRebond) {
				
				// TODO : est ce vraiment correct ?
				boolean allOff = true;
				
				//Parcours des Materiau, ie propriétés de l'objet intersecté
				materiauCourant = objetIntersection.getMateriau();
				if (materiauCourant.getReflectivite().isOn()) {
						
					allOff = false;
						
					// Lancé du rayon issu de la propriété materiauCourant de l'objet intersecté
					ArrayList<Rayon> listeRayonsMateriau = materiauCourant.getReflectivite().creerRayon(rayon, intersection, objetIntersection);
					for (Rayon rayonFils : listeRayonsMateriau) {
						lancerRayon(rayonFils, compteur +1, listeRayonsFinaux);
					}
				}
				
				if (materiauCourant.getRefringence().isOn()) {
					
					allOff = false;
						
					// Lancé du rayon issu de la propriété materiauCourant de l'objet intersecté
					ArrayList<Rayon> listeRayonsMateriau = materiauCourant.getRefringence().creerRayon(rayon, intersection, objetIntersection);
					
					for (Rayon rayonFils : listeRayonsMateriau) {
						if (rayonFils != null) {
							lancerRayon(rayonFils, compteur +1, listeRayonsFinaux);
					
						}
					}
				}
				
				if (allOff) {
					listeRayonsFinaux.add(rayon);	
				}

			} else {
				listeRayonsFinaux.add(rayon);
			}
		} else {
			// TODO : V�rifier � quoi correspond ce cas
			setCouleurRayon(rayon, this.scene.getAmbiante());
			listeRayonsFinaux.add(rayon);
		}
	}

	/** 
	 * Calcul de la couleur d'un point sur un objet � partir des sources de lumi�re.
	 * Utilisation du mod�le de Phong
	 * @param rayon rayon à partir duquel on lance un shadow ray
	 * @param couleurIntersection couleur de l'objet intersecté
	 * @param normale vecteur normal à la surface de l'objet intersecté au point d'impact
	 * @param intersectionObjet objet intersecté
	 */
	private void lancerShadowRay(Rayon rayon, Couleur couleurIntersection, Vecteur incident, Vecteur normale, Point intersectionObjet, Objet3D objetIntersection) {

		
		Materiau materiau = objetIntersection.getMateriau();
		ModeleEclairement eclairement = materiau.getEclairement();
		
		// Couleur ambiante � fournir (caract�ristique de la sc�ne)
		Couleur ambiante = this.scene.getAmbiante();
		
		
		//paramètres de la boucle for
		Rayon shadowRay;
		Color couleurLumiere;
		double distanceLumiere;
		double distanceCourante;

		//paramètres de la boucle while
		int objet;
		boolean ombre;
		Objet3D objetCourant;
		Point intersectionCourante;
		
		// TODO : refactoring n�cessaire car r�v�le trop de chose sur Phong
		Couleur composanteDiffuse = new Couleur( 0.0, 0.0, 0.0);
		Couleur composanteSpeculaire = new Couleur( 0.0, 0.0, 0.0);
		Couleur composanteAmbiante = eclairement.integrerScene(ambiante);

		int nbObjets = this.scene.getObjets().size();

		// On parcourt toutes les lumières
		for (Lumiere lumiere : this.scene.getLumieres()) {
			
			if (lumiere.eclaire(intersectionObjet)) {
			

				distanceLumiere = intersectionObjet.distance(lumiere.getSource(intersectionObjet));
			
			
				// Création du shadow ray pour la lumière courante
				Vecteur vecteurLumiere = new Vecteur(intersectionObjet, lumiere.getSource(intersectionObjet));
				// Je pense pas que �a vaille le coup de mettre �a dans Lumiere car on a besoin de vecteurLumiere 
				// donc �a revient � juste une ligne de code
				shadowRay = new Rayon(vecteurLumiere, intersectionObjet);

				objet = 0; // indice de la boucle while
				//ombre = false; // indique si l'objet est à l'ombre par rapport à la lumière courante
				couleurLumiere = lumiere.getCouleur();
			
				// Id�e : Mod�liser une version simplifi�e de la r�fraction pour simuler les objets transparents
				// Il faut calculer la contribution transmises par la source de lumi�re � travers chaque objet.
				// Le code suivant d�tecte les objets qui se trouvent entre le point et la source de lumi�re
				// Pour chaque objet, il faut d�terminer la longueur du chemin dans l'objet, et la multiplier
				// par le facteur d'att�nuation du Materiau qui compose l'objet, il faut ensuite multiplier cela
				// avec la puissance de la lumi�re pour d�terminer la quantit� de lumi�re qui traverse l'objet.
				// Et il faut faire cela pour tous les objets travers�s.
				// Cela doit modifier l'intensit� de �clairement lumi�re � travers le facteur attenuation exploit� ensuite.
				
				
				double attenuation = 1.0;
				// On vérifie que l'objet ne se fait pas de l'ombre lui-même
				ombre = objetIntersection.getSelfOmbre(intersectionObjet, rayon, lumiere);
				
				ArrayList<Objet3D> listeCreateursDOmbre = new ArrayList<Objet3D>();
				if (ombre && objetIntersection.getMateriau().getRefringence().isOn()) {
					listeCreateursDOmbre.add(objetIntersection);
					ombre = false;
				}
			
				if (!ombre) {
					// On parcourt tous les objets pour déterminer si l'objet est à l'ombre
					while (objet < nbObjets && !ombre) {
						objetCourant = this.scene.getObjets().get(objet);
						intersectionCourante = objetCourant.estTraversePar(shadowRay);
						if (intersectionCourante != null && objetCourant != objetIntersection) {
							distanceCourante = intersectionCourante.distance(shadowRay.getOrigine());
							if (distanceCourante < distanceLumiere) {
								if (objetCourant.getMateriau().getRefringence().isOn()) {
									listeCreateursDOmbre.add(objetCourant);
								} else {
									ombre = true;
								}
							}
						}
						objet++;
					}
				}
				
				boolean flag = false;
				// Si l'objet n'est pas à l'ombre par rapport à la lumière courante
				if (!ombre) {
					
					Couleur eclairementLumiere = new Couleur( couleurLumiere );
					Couleur couleurCreateur;
					if (listeCreateursDOmbre.size() != 0) {
						
						for (Objet3D createurDOmbre : listeCreateursDOmbre) {
							couleurCreateur = createurDOmbre.getMateriau().getCouleur();
							couleurCreateur.attenuer(createurDOmbre.getMateriau().getRefringence().getTransparence());
							eclairementLumiere.filtrer(couleurCreateur); //ou couleur diffuse ?
							flag = true;
						}
					}
					
					// TODO Id�e : mod�liser la dispersion de la lumi�re (pr�voir un coefficient de dispersion ambiant)
					double distance = intersectionObjet.distance(lumiere.getCentre());
				
					double absorbtion = attenuation; //100/(distance*distance);
				
					
					eclairementLumiere.attenuer(absorbtion);
					
					eclairement.integrerLumiere(composanteDiffuse, composanteSpeculaire, incident, normale, intersectionObjet, 
							lumiere, eclairementLumiere, vecteurLumiere);
				}
			}
			
		}
		
		// TODO : Refactoring n�cessaire car r�vele trop d'�l�ments de Phong
		Couleur couleurRayon = null;
		
		if (eclairement.inactif()) {
			couleurRayon = couleurIntersection;
		} else {
			couleurRayon = new Couleur( composanteAmbiante );
			couleurRayon.combiner(composanteDiffuse);
			
			// Filtrage de la lumi�re re�ue par l'objet : prise en compte de la couleur de l'objet
			couleurRayon.filtrer(couleurIntersection);
		
			// Prise en compte du maximum de chaque composante
			// TODO : faire un mod�le plus r�aliste de la perception logarithmique par l'oeil
			// Id�e : calculer l'�nergie re�ue par chaque point
			// Mettre � l'�chelle 0..255 � partir de l'�nergie maximale et d'une �chelle logarithmique
			// Calculer le logarithme de l'�nergie re�ue
			// Normaliser en 0 et 255
			// Pr�voir un seuil de saturation pour �viter que rien ne soit blanc
			couleurRayon.combiner(composanteSpeculaire);
			couleurRayon.normaliser();
			// System.out.println(couleurRayon);
			// Si l'objet est à l'ombre pour toutes les lumières, le rayon est noir.
		}
		
		setCouleurRayon(rayon, couleurRayon);
	}
			
	/**
	 * 
	 * @param lumiere
	 * @param couleurObjet
	 * @param objet
	 * @param rayon
	 * @param intersection
	 * @return
	 */
	public Color getCouleurUneLumiere(Lumiere lumiere, Color couleurObjet, Objet3D objet, Rayon rayon, Point intersection) {
		
		if (lumiere.eclaire(null)) {
			Vecteur vecteurLumiere = new Vecteur(intersection, lumiere.getSource(intersection));
		}
		return Color.black;
	}
}
