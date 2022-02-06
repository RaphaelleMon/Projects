/**
 * 
 */
package rayTracing;

import elements3D.Couleur;
import utilitaire.Point;
import utilitaire.Vecteur;

/**
 * @author Chris
 *
 */
public interface ModeleEclairement {
	
	/**
	 * 
	 * @param couleurScene
	 * @return
	 */
	public Couleur integrerScene(Couleur couleurScene);
	
	/**
	 * 
	 * @param composanteDiffuse
	 * @param composanteSpeculaire
	 * @param incident
	 * @param normale
	 * @param intersectionObjet
	 * @param lumiere
	 * @param eclairementLumiere
	 * @param vecteurLumiere
	 */
	public void integrerLumiere( Couleur composanteDiffuse, Couleur composanteSpeculaire, Vecteur incident, Vecteur normale, Point intersectionObjet, Lumiere lumiere, Couleur eclairementLumiere, Vecteur vecteurLumiere);

//	public Couleur finaliser( Couleur composanteAmbiante, Couleur composanteDiffuse, Couleur composanteSpeculaire, Couleur couleurIntersection);
	
	/**
	 * Rendre inactif le modèle d'éclairement utilisé.
	 */
	public boolean inactif();
}
