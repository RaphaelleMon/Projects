package sceneXN;

import java.awt.Color;
import java.awt.image.BufferedImage;

import elements3D.Couleur;
import elements3D.Plan;
import elements3D.Reflectivite;
import elements3D.Sphere;
import rayTracing.Camera;
import rayTracing.Lumiere;
import rayTracing.LumierePonctuelle;
import rayTracing.RayTracing;
import rayTracing.Scene;
import utilitaire.Point;
import utilitaire.Vecteur;
/**
 * 
 * @author jaden
 *
 */
public class SceneMedium2 {
	public static void main(String args[]) {
		
	try {
		// Cr�ation de la sc�ne
	    Scene scene = new Scene(300);
	    scene.setAmbiante(new Couleur( Color.white));
	    
	    	// Ajout d'une sph�re
	    Sphere sphere1 = new Sphere(new Point(5,0,5), 2.5, "Sphere1");
	    Couleur couleur1 = (Couleur)sphere1.getMateriau().getCouleur();
	    couleur1.set(0, 255, 0);
	    Reflectivite reflectivite1 = (Reflectivite)sphere1.getMateriau().getReflectivite();
	    reflectivite1.setOn(true);
	    scene.addObjet3D(sphere1);
	    
	    	// Ajout d'une sph�re
	    Sphere sphere2 = new Sphere(new Point(5,7,5), 2.5, "Sphere2");
	    Couleur couleur2 = (Couleur)sphere2.getMateriau().getCouleur();
	    couleur2.set(255, 255, 0);
	    scene.addObjet3D(sphere2);
	    
	 		// Ajout d'une sph�re
	    Sphere sphere3 = new Sphere(new Point(2,7,5), 2.5, "Sphere3");
	    Couleur couleur3 = (Couleur)sphere3.getMateriau().getCouleur();
	    couleur3.set(255, 0, 0);
	    scene.addObjet3D(sphere3);
	    
			// Ajout d'une sph�re
	    Sphere sphere4 = new Sphere(new Point(12,7,5), 2.5, "Sphere4");
	    Couleur couleur4 = (Couleur)sphere4.getMateriau().getCouleur();
	    couleur4.set(0, 255, 255);
	    scene.addObjet3D(sphere4);
	    
	    	// Ajout d'un plan
	    Plan plan = new Plan(new Vecteur(0,0,1), new Point(0,0,0),"plan");
	    Couleur couleur5 = (Couleur)plan.getMateriau().getCouleur();
	    couleur5.set(0, 0, 255);
	    scene.addObjet3D(plan);
	    
	    	// Ajout d'une lumi�re
	    Lumiere lumiere = new LumierePonctuelle(new Point(5,10,15),new Color(255,255,255));
	    scene.addLumiere(lumiere);
	    
			// Ajout d'une lumi�re
	    Lumiere lumiere2 = new LumierePonctuelle(new Point(10,0,0),new Color(255,255,255));
	    scene.addLumiere(lumiere2);
	    
	    // Cr�ation de la cam�ra
	    Camera camera = new Camera(new Point(20,0,5),new Vecteur(-10,0,0),1000,1000,new Vecteur(0,0,2)); //vHaut = (0,0,10) sur l'exemple geogebra
		
	    // Lancement du ray tracing
		RayTracing raytracing = new RayTracing(scene, camera, 10, true, true);
		raytracing.lancerRayTracing();
		
		// Enregistrement de l'image
		camera.sauvegarderImage("test2");

		
	} catch (Exception e) {
		System.out.println("Erreur dans la gestion de la sc�ne.");
	}
	}
}
