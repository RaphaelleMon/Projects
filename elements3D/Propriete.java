/**
 * 
 */
package elements3D;

import java.util.ArrayList;

import rayTracing .*;
import utilitaire .*;

/** Propri�t� est une interface qui permet de mettre en place les caract�ristiques des mat�riaux/objets
 * @author Edgar
 */
public interface Propriete {
		
	/** Indiquer si le mat�riau d'un objet est � prendre en compte
	 * @return statut du mat�riau
	 */
	public boolean isOn();
	
	/** R�initialise la propri�t� � ses valeurs par d�faut
	 */
	public void reset();
	
	/**
	 * Activer la propri�t�
	 * @param on : interrupteur pour lancer ou �teindre la propri�t�
	 */
	public void setOn(boolean on);
	
	/**
	 * Modifier la valeur de l'�nergie
	 * @param energie : nouvelle valeur de l'�nergie
	 */
	public void setEnergie(double energie);
	
	/**
	 * Obtenir la valeur de l'�nergie
	 * @return Valeur de l'�nergie
	 */
	public double getEnergie();
	
	/**
	 * Cr�er les rayons fils issus de la collision un objet.
	 * @param rayon : rayon qui a intersect� avec un objet
	 * @param intersection : point d'intersection avec l'objet
	 * @param objetIntersection : objet avec lequel le rayon a une intersection
	 * @return liste de rayons fils	
	 */
	public ArrayList<Rayon> creerRayon(Rayon rayon, Point intersection, Objet3D objetIntersection);
}
