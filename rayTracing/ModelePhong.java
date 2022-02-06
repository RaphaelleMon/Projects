package rayTracing;

import java.awt.Color;
import java.io.Serializable;

import java.util.ArrayList;

import elements3D.Couleur;
import utilitaire.Point;
import utilitaire.Vecteur;


/**
 * Le Modèle de Phong permet de calculer la couleur d'un point sur un objet en
 * fonction des sources de lumière qui éclairent ce point.
 * Il combine les composantes :
 * - ambiante : éclairage uniforme issue des multiples réflexion/réfraction des
 * 	rayons issus des sources de lumière, simplification grossière des techniques de radiosité
 * - diffuse : prend en compte la loi de Lambert qui indique que la diffusion des rayons n'est
 * 	pas uniforme mais dépend de l'angle entre le rayon incident et la direction de la source
 * 	de lumière (l'éclairage est maximal dans la direction de réflexion)
 * - spéculaire : simplification grossière de la réflexion pour prendre en compte les caractèristiques
 * 	des matériaux brillants
 *
 */
public class ModelePhong implements ModeleEclairement, Serializable {
	
	public static final boolean DEBUG = false;
	
	private static final long serialVersionUID = 3175080915696036907L;
	
	/** Indiquer si la composante ambiante est prise en compte dans le calcul du Ray Tracing*/
	private boolean ambiante;
	
	/** Indiquer si la composante diffuse est prise en compte dans le calcul du Ray Tracing */
	private boolean diffuse;
	
	/** Indiquer si la composante speculaire est prise en compte dans le calcul du Ray Tracing */
	private boolean speculaire;

	// Spécularité
		/** Spécificité de la composante spéculaire d'un matériau pour les canaux rouge, vert et bleu.*/
	private Couleur filtreSpeculaire;
	
		// Part de la composante spéculaire dans l'éclairement global
	private double partSpeculaire;	

		/** Brillance d'un matériau.
		 * Plus la brillance est grande, plus le matériau paraît lisse.*/
	private double brillance;
	
	// Ambiance
		/** Spécificité de la composante ambiante d'un matériau pour les canaux rouge, vert et bleu.*/
	private Couleur filtreAmbiante;
	
		// Part de la composante ambiante dans l'éclairement global
	private double partAmbiante;
	
	// Diffusion
		/** Spécificité de la composante diffuse d'un matériau pour les canaux rouge, vert et bleu.*/
	private Couleur filtreDiffuse;
	
		// Part de la composante diffuse dans l'éclairement global
	private double partDiffuse;
	
	/**
	 * Initialiser les paramètres du modèle de Phong par défault
	 */
	public ModelePhong() {
		this.filtreAmbiante = new Couleur( Color.black );
		this.partAmbiante = 0.1;
		this.ambiante = true;
		
		this.filtreDiffuse = new Couleur( Color.black );
		this.partDiffuse = 0.7;
		this.diffuse = true;
		
		this.filtreSpeculaire = new Couleur( Color.black );
		this.partSpeculaire = 0.2;
		this.brillance = 100;
		this.speculaire = true;
	}
	
	/**
	 * 
	 * @param filtreAmbiante : Spécificité de la composante ambiante d'un matériau pour les canaux rouge, vert et bleu
	 * @param partAmbiante : Part de la composante ambiante dans l'éclairement global
	 * @param filtreDiffuse : Spécificité de la composante diffuse d'un matériau pour les canaux rouge, vert et bleu
	 * @param partDiffuse : Part de la composante diffuse dans l'éclairement global
	 * @param filtreSpeculaire : Spécificité de la composante speculaire d'un matériau pour les canaux rouge, vert et bleu
	 * @param partSpeculaire : Part de la composante spéculaire dans l'éclairement global
	 * @param brillance : Brillance d'un matériau
	 */
	public ModelePhong(Couleur filtreAmbiante, double partAmbiante, Couleur filtreDiffuse, double partDiffuse, Couleur filtreSpeculaire, double partSpeculaire, int brillance) {
		this.filtreAmbiante = filtreAmbiante;
		this.partAmbiante = partAmbiante;
		this.ambiante = true;
		
		this.filtreDiffuse = filtreDiffuse;
		this.partDiffuse = partDiffuse;	
		this.diffuse = true;
		
		this.filtreSpeculaire = filtreSpeculaire;
		this.partSpeculaire = partSpeculaire;
		assert brillance >= 0.0;
		this.brillance = brillance;
		this.speculaire = true;
	}
	
	/**
	 * Obtenir la brillance 
	 * @return Valeur de brillance
	 */
	public double getBrillance() {
		return this.brillance;
	}
	
	/**
	 * Obtenir la part de la composante spéculaire dans l'éclairement global
	 * @return valeur de la part de la composante spéculaire
	 */
	public double getPartSpeculaire() {
		return this.partSpeculaire;
	}

	/** Obtenir la part de la composante ambiante dans l'éclairement global
	 * @return valeur de la part de la composante ambiante */
	public double getPartAmbiante() {
		return this.partAmbiante;
	}
	
	/** Obtenir la part de la composante spéculaire dans l'éclairement global
	 * @return valeur de la part de la composante spéculaire */
	public double getPartDiffuse() {
		return this.partDiffuse;
	}
	
	/**
	 * Obtenir le filtre spéculaire
	 * @return Couleur du filtre spéculaire
	 */
	public Couleur getFiltreSpeculaire() {
		return this.filtreSpeculaire;
	}
	
	/**
	 * Modifier la couleur du filtre spéculaire
	 * @param filtre : nouvelle valeur du filtre spéculaire
	 */
	public void setFiltreSpeculaire(Couleur filtre) {
		assert filtre != null;
		this.filtreSpeculaire = filtre;
	}
	
	/**
	 * Obtenir le filtre ambiant
	 * @return Couleur du filtre ambiant
	 */
	public Couleur getFiltreAmbiante() {
		return this.filtreAmbiante;
	}
	
	/**
	 * Modifier la couleur du filtre ambiant
	 * @param filtre : nouvelle valeur du filtre ambiant
	 */
	public void setFiltreAmbiante(Couleur filtre) {
		assert filtre != null;
		this.filtreAmbiante = filtre;
	}
	
	/**
	 * Obtenir le filtre diffuse
	 * @return Couleur du filtre diffuse
	 */
	public Couleur getFiltreDiffuse() {
		return this.filtreDiffuse;
	}
	
	
	/**
	 * Modifier la couleur du filtre diffus
	 * @param filtre : nouvelle valeur du filtre diffus
	 */
	public void setFiltreDiffuse(Couleur filtre) {
		assert filtre != null;
		this.filtreDiffuse = filtre;
	}
	
	/**
	 * Modifier la valeur de la brillance
	 * @param brillance
	 */
	public void setBrillance(int brillance) {
		this.brillance = brillance;
	}
	
	/**
	 * Savoir si la composante de diffusion est active ou non
	 * @return
	 */
	public boolean getSpeculaire() {
		return this.speculaire;
	}

	/**
	 * Savoir si la composante de diffusion est active ou non
	 * @return
	 */
	public boolean getAmbiante() {
		return this.ambiante;
	}
	
	/**
	 * Savoir si la composante de diffusion est active ou non
	 * @return
	 */
	public boolean getDiffuse() {
		return this.diffuse;
	}
	
	/**
	 * Modifier les parts des composantes ambiantes, diffuses et spéculaires dans l'éclairement global
	 * @param ambiante : nouvelle valeur de part de composante ambiante dans l'éclairement global
	 * @param diffuse :  nouvelle valeur de part de composante diffuse dans l'éclairement global
	 * @param speculaire : nouvelle valeur de part de composante speculaire dans l'éclairement global
	 */
	public void setRepartition( double ambiante, double diffuse, double speculaire) {
		assert (ambiante > 0);
		assert (diffuse > 0);
		assert (speculaire > 0);
		this.partAmbiante = ambiante;
		this.partDiffuse = diffuse;
		this.partSpeculaire = speculaire;
	}
	/**
	 * Obtenir les valeurs de chaque part de scomposantes dans l'éclairement global
	 * @return vecteur contenant les valeurs de chaque part des composantes dans l'éclairement global
	 */
	public Vecteur getRepartition() {
		double x = this.partAmbiante;
		double y = this.partDiffuse;
		double z = this.partSpeculaire;
		return new Vecteur(x,y,z);
	}
	
	/**
	 * Activer ou désactiver la composante ambiante du modèle de Phong
	 * @param ambiante : Interruption pour activer la composante ambiante du modèle de Phong
	 */
	public void setAmbiante(boolean ambiante) {
		this.ambiante = ambiante;
	}
	
	/**
	 * Activer ou désactiver la composante diffuse du modèle de Phong
	 * @param diffuse : Interruption pour activer la composante diffuse du modèle de Phong
	 */
	public void setDiffuse(boolean diffuse) {
		this.diffuse = diffuse;
	}
	
	/**
	 * Activer ou désactiver la composante speculaire du modèle de Phong
	 * @param speculaire : Interruption pour activer la composante speculaire du modèle de Phong
	 */
	public void setSpeculaire(boolean speculaire) {
		this.speculaire = speculaire;
	}
	
	public boolean inactif() {
		return ! (this.ambiante || this.diffuse || this.speculaire); 
	}
	
	public Couleur integrerScene(Couleur couleurScene) {
		Couleur composanteAmbiante = null;
		if (this.ambiante) {
			composanteAmbiante = new Couleur(couleurScene);
			composanteAmbiante.attenuer(this.partAmbiante);
		} else {
			composanteAmbiante = new Couleur(Color.BLACK);
		}
		return composanteAmbiante;
	}

	public void integrerLumiere( Couleur composanteDiffuse, Couleur composanteSpeculaire, Vecteur incident, Vecteur normale, Point intersectionObjet, Lumiere lumiere, Couleur eclairementLumiere, Vecteur vecteurLumiere) {
		double diffusion = 0.0;
		double specularite = 0.0;
		vecteurLumiere.normaliser();
		normale.normaliser();
		incident.normaliser(); 
		double cosTheta = normale.produitScalaire(vecteurLumiere);
		// Calcul du vecteur lumière réfléchi
		// TODO : Modifier API de vecteur pour que cette opération y figure
		Vecteur reflexionLumiere = normale.multiplication(2.0 * cosTheta).soustraire(vecteurLumiere);
		reflexionLumiere.normaliser();
		// Détermine la force de la spécularité en ce point pour cette lumière
		double cosOmega = - incident.produitScalaire(reflexionLumiere);
		if (this.diffuse) {			
			diffusion = Math.max(0.0, this.partDiffuse * cosTheta);
		} else {
			diffusion = 0.0;
		}
		if (this.speculaire) {
			specularite = Math.max(0.0, this.partSpeculaire * Math.pow(cosOmega, this.brillance));
			if (DEBUG) {
				if (specularite > 0.5) {
					System.out.println(
							" 2 x " + cosTheta + " * ( " + normale.getX() + ", " + normale.getY() + ", " + normale.getZ() + ") "
									+ "- ( " + vecteurLumiere.getX() + ", " + vecteurLumiere.getY() + ", " + vecteurLumiere.getZ() + ") "
									+ " = ( " + reflexionLumiere.getX() + ", " + reflexionLumiere.getY() + ", " + reflexionLumiere.getZ() + ") "
									+ specularite);
				}	
			}
		} else {
			specularite = 0.0;
		}

		// TODO Idée : modéliser la dispersion de la lumière (prévoir un coefficient de dispersion ambiant)
		double distance = intersectionObjet.distance(lumiere.getSource(intersectionObjet));

		double absorbtion = 1; //100/(distance*distance);

//		Couleur eclairementLumiere = new Couleur( lumiere.getCouleur() );
//		eclairementLumiere.attenuer(absorbtion);

		// Accumuler la composante spéculaire de chaque source de lumière
		// composanteSpeculaire += couleurLumiere * specularite;
		composanteSpeculaire.combiner(eclairementLumiere, specularite);

		// Accumuler la composante diffuse de chaque source de lumière
		// composanteDiffuse += couleurLumiere * diffusion;
		composanteDiffuse.combiner(eclairementLumiere, diffusion);
	}

}
