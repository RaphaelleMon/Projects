/**
 * 
 */
package elements3D;

import rayTracing .*;
import utilitaire .*;

import java.io.Serializable;

import exception.NomVideException;

/** Objet3D représente le type general des objets 3D presents dans une scene
 * @author Edgar
 */
public abstract class Objet3D implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 4118304767337591448L;

	public static final double EPSILON = 10e-4;
	
	/** Ensembles des proprietes de la pyramide.*/
	private Materiau materiau;
	
	/** Nom de la pyramide */
	private String nom;
	
	/**
	 * Créer un Objet 3D à partir d'un nom
	 * @param nom : Nom de l'objet 3D
	 * @throws NomVideException : l'utilisateur n'a pas donné de nom à l'objet 
	 */
	public Objet3D(String nom) throws NomVideException {
		
		if (NomVideException.estVide(nom)) {
			throw new NomVideException();
		}
		this.nom = nom;
		
		this.materiau = new Materiau();
		
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/** Obtenir le nom de l'objet
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}
	/** redéfinir le nom de l'objet
	 * @param nom : nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/** Indiquer si une objet est traversé par un rayon, et si oui en quel point
	 * @param rayon : rayon lumineux
	 * @pre rayon != null
	 * @return point de contact (vaut null si le rayon ne traverse pas l'objet)
	 */
	public abstract Point estTraversePar(Rayon rayon);
	
	/** Calculer le vecteur directeur du rayon r réfléchi sur l'objet
	 * @param rayon : rayon incident
	 * @param pointImpact : point d'impact/d'incidence
	 * @pre r != null && p != null
	 * @return vecteur directeur du rayon réfléchi
	 */
	public abstract Vecteur directionReflexion(Rayon rayon, Point pointImpact);
	
	/** Calculer le vecteur directeur du rayon r réfracté sur l'objet
	 * @param rayon : rayon incident
	 * @param pointImpact : point d'impact/d'incidence
	 * @param indiceRefractionExterieur
	 * @pre rayon != null && pointImpact != null
	 * @return vecteur directeur du rayon réfléchi
	 */
	public abstract Vecteur directionRefraction(Rayon rayon, Point pointImpact, double indiceRefractionExterieur);
	
	/** Indique si l'objet fait de l'ombre au point
	 * @param pointImpact : point considéré
	 * @param rayon : rayon qui touche l'objet en impact
	 * @param lumiere : source à considérer
	 * @pre impact != null && rayon != null && lumiere != null
	 * @return
	 */
	public abstract boolean getSelfOmbre(Point pointImpact, Rayon rayon, Lumiere lumiere);
	
	/** Déplacer un objet dans l'espace
	 * @param dx : translation selon l'axe des x
	 * @param dy : translation selon l'axe des y
	 * @param dz : translation selon l'axe des y
	 */
	public abstract void translater(double dx, double dy, double dz);
	
	/** Appliquer une rotation de l'objet dans l'espace
	 * @param rx : rotation autour de l'axe des x
	 * @param ry : rotation autour de l'axe des y
	 * @param rz : rotation autour de l'axe des z
	 */
	public abstract void rotation(double rx, double ry, double rz);
	
	/** Obtenir un matériau contenant des informations sur l'objet
	 * @param num identificateur de matériau
	 * @pre 0 <= num < Properties.NB_MATERIAUX
	 * @return matériau demandé de l'objet
	 */
	public Materiau getMateriau() {
		return this.materiau;
	}
	
	/** Obtenir le vecteur normal à l'objet au point d'impact
	 * @param pointImpact : point d'impact
	 * @pre impact != null && rayon != null
	 * @return vecteur normal
	 */
	public abstract Vecteur getNormal(Point pointImpact, Rayon rayon);
	
	/** Renvoie le barycentre de l'objet */
	public abstract Point getPosition();
	
	/** Indique si l'objet se trouve hors de la scène en fonction de sa taille
	 * @param dimension : dimension de la scène
	 * @param centre : centre de la scène
	 * @return
	 */
	public abstract boolean estHorsScene(double dimension, Point centre);
	
	
	///////////////// ICI GET ET SET POUR L'IG //////////////////////////////
	public Vecteur getFiltreSpeculaire() {
		double r = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreSpeculaire().getRed();
		double v = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreSpeculaire().getGreen();
		double b = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreSpeculaire().getBlue();
		return new Vecteur(r, v, b);
	}
	
	public void setFiltreSpeculaire(int rouge, int vert, int bleu) {
		assert 0<= rouge && rouge <= 255;
		assert 0<= vert && vert <= 255;
		assert 0<= bleu && bleu <= 255;
		Couleur filtre = new Couleur(rouge, vert, bleu);
		((ModelePhong) this.getMateriau().getEclairement()).setFiltreSpeculaire(filtre);
	}
	
	public Vecteur getFiltreAmbiante() {
		double rouge = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreAmbiante().getRed();
		double vert = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreAmbiante().getGreen();
		double bleu = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreAmbiante().getBlue();
		return new Vecteur(rouge, vert, bleu);
	}
	
	public void setFiltreAmbiante(int rouge, int vert, int bleu) {
		assert 0<= rouge && rouge <= 255;
		assert 0<= vert && vert <= 255;
		assert 0<= bleu && bleu <= 255;
		Couleur filtre = new Couleur(rouge, vert, bleu);
		((ModelePhong) this.getMateriau().getEclairement()).setFiltreAmbiante(filtre);
	}
	
	public Vecteur getFiltreDiffuse() {
		double rouge = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreDiffuse().getRed();
		double vert = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreDiffuse().getGreen();
		double bleu = ((ModelePhong) this.getMateriau().getEclairement()).getFiltreDiffuse().getBlue();
		return new Vecteur(rouge, vert, bleu);
	}
	
	public void setFiltreDiffuse(int rouge, int vert, int bleu) {
		assert 0<= rouge && rouge <= 255;
		assert 0<= vert && vert <= 255;
		assert 0<= bleu && bleu <= 255;
		Couleur filtre = new Couleur(rouge, vert, bleu);
		((ModelePhong) this.getMateriau().getEclairement()).setFiltreDiffuse(filtre);
	}
	
	public double getBrillance() {
		return ((ModelePhong)this.getMateriau().getEclairement()).getBrillance();
	}
	
	public void setBrillance(int brillance) {
		((ModelePhong)this.getMateriau().getEclairement()).setBrillance(brillance);
	}
	
	//Demander à Christophe les intervalles des valeurs
	public void setRepartion(double ambiante, double diffuse, double speculaire) {
		//assert 0<= ambiante && ambiante <= 255;
		//assert 0<= diffuse && diffuse <= 255;
		//assert 0<= speculaire && speculaire <= 255;
		((ModelePhong) this.getMateriau().getEclairement()).setRepartition(ambiante, diffuse, speculaire);
	}
	
	public Vecteur getRepartion() {
		return ((ModelePhong) this.getMateriau().getEclairement()).getRepartition();
	}
	
	public void setAmbianceOn(boolean on) {
		((ModelePhong) this.getMateriau().getEclairement()).setAmbiante(on);
	}
	
	public boolean getAmbianceOn() {
		return ((ModelePhong) this.getMateriau().getEclairement()).getAmbiante();
	}
	
	public void setSpeculariteOn(boolean on) {
		((ModelePhong) this.getMateriau().getEclairement()).setSpeculaire(on);
	}
	
	public boolean getSpeculariteOn() {
		return ((ModelePhong) this.getMateriau().getEclairement()).getSpeculaire();
	}
	
	public void setDiffusionOn(boolean on) {
		((ModelePhong) this.getMateriau().getEclairement()).setDiffuse(on);
	}
	
	public boolean getDiffusionOn() {
		return ((ModelePhong) this.getMateriau().getEclairement()).getDiffuse();
	}
}
