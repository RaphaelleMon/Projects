/**
 * 
 */
package elements3D;

import java.util.ArrayList;

import rayTracing .*;
import utilitaire .*;

/** Propriété est une interface qui permet de mettre en place les caractéristiques des matériaux/objets
 * @author Edgar
 */
public interface Propriete {
		
	/** Indiquer si le matériau d'un objet est à prendre en compte
	 * @return statut du matériau
	 */
	public boolean isOn();
	
	/** Réinitialise la propriété à ses valeurs par défaut
	 */
	public void reset();
	
	/**
	 * Activer la propriété
	 * @param on : interrupteur pour lancer ou éteindre la propriété
	 */
	public void setOn(boolean on);
	
	/**
	 * Modifier la valeur de l'énergie
	 * @param energie : nouvelle valeur de l'énergie
	 */
	public void setEnergie(double energie);
	
	/**
	 * Obtenir la valeur de l'énergie
	 * @return Valeur de l'énergie
	 */
	public double getEnergie();
	
	/**
	 * Créer les rayons fils issus de la collision un objet.
	 * @param rayon : rayon qui a intersecté avec un objet
	 * @param intersection : point d'intersection avec l'objet
	 * @param objetIntersection : objet avec lequel le rayon a une intersection
	 * @return liste de rayons fils	
	 */
	public ArrayList<Rayon> creerRayon(Rayon rayon, Point intersection, Objet3D objetIntersection);
}
