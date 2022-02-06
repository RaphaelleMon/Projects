package sceneXN;

import java.awt.Color;

import IG.FenetrePrincipale;
import elements3D.Couleur;
import elements3D.Plan;
import elements3D.Reflectivite;
import elements3D.Refringence;
import elements3D.Sphere;
import rayTracing.Camera;
import rayTracing.Lumiere;
import rayTracing.LumierePonctuelle;
import rayTracing.LumiereSpherique;
import rayTracing.RayTracing;
import rayTracing.Scene;
import utilitaire.Point;
import utilitaire.Vecteur;

public class SceneMedium5 {
	public static void main(String args[]) {
		
	try {
		// Création de la scène
	    Scene scene = new Scene(3000);
	    scene.setAmbiante(new Couleur( Color.white));
	    
	    	// Ajout d'une sphère
	    Sphere sphere1 = new Sphere(new Point(5,0,5), 2.5, "Sphere1");
	    Couleur couleur1 = (Couleur)sphere1.getMateriau().getCouleur();
	    couleur1.set(255, 255, 255); // Blanc
	    //Reflectivite reflectivite1 = (Reflectivite)sphere1.getMateriau().getReflectivite();
	    //reflectivite1.setOn(false);
	    //reflectivite1.setIntensite(1);
	    //reflectivite1.setEnergie(0.5);
	    Refringence refraction1 = (Refringence)sphere1.getMateriau().getRefringence();
	    refraction1.setOn(true);
	    refraction1.setIntensite(1);
	    refraction1.setEnergie(1);
	    scene.addObjet3D(sphere1);
	    
		// Ajout d'une sphère
	    Sphere sphere2 = new Sphere(new Point(5,10,5), 2.5, "Sphere2");
	    Couleur couleur2 = (Couleur)sphere2.getMateriau().getCouleur();
	    couleur2.set(255, 255, 0); // Rouge
	    //Reflectivite reflectivite2 = (Reflectivite)sphere2.getMateriau().getReflectivite();
	    //reflectivite2.setOn(true);
	    //reflectivite2.setIntensite(1);
	    //reflectivite2.setEnergie(1);
	    scene.addObjet3D(sphere2);
	    
	    Sphere sphere;
	    Couleur couleur;
	    Reflectivite reflectivite;
	    for (int k = -20 ; k < 20 ; k = k + 4) {
	    	sphere = new Sphere(new Point(-5,k,5), 2, "Sphere2");
		    couleur = (Couleur)sphere.getMateriau().getCouleur();
		    couleur.set(255, 255, 255); // Rouge
		    reflectivite = (Reflectivite)sphere.getMateriau().getReflectivite();
		    reflectivite.setOn(true);
		    reflectivite.setIntensite(1);
		    reflectivite.setEnergie(1);
		    scene.addObjet3D(sphere);
	    }
	    
	 	
	    	// Ajout d'un plan
	    Plan plan = new Plan(new Vecteur(0,0,1), new Point(0,0,0),"plan");
	    Couleur couleur5 = (Couleur)plan.getMateriau().getCouleur();
	    couleur5.set(255, 100, 255); // Blanc
	    //Reflectivite reflectivite5 = (Reflectivite)plan.getMateriau().getReflectivite();
	    //reflectivite5.setOn(true);
	    scene.addObjet3D(plan);
	    
	    	// Ajout d'une lumière

	    Lumiere lumiere1 = new LumiereSpherique(new Point(20,0,20),new Color(255,0,255), 5); // Rouge
	    scene.addLumiere(lumiere1);
	    
	    //Lumiere lumiere4 = new LumiereSpherique(new Point(0,0,20),new Color(255,255,255), 5); // Rouge
	    //scene.addLumiere(lumiere4);
	    
    	// Ajout d'une lumière
	    Lumiere lumiere2 = new LumierePonctuelle(new Point(5,20,20),new Color(255,255,255)); // Rouge
	    scene.addLumiere(lumiere2);
	    
	 // Ajout d'une lumière
	    //Lumiere lumiere3 = new LumierePonctuelle(new Point(0,-10,20),new Color(255,255,255)); // Rouge
	    //scene.addLumiere(lumiere3);
	    
	    Lumiere lumiere5 = new LumierePonctuelle(new Point(5,-10,5),new Color(255,0,0)); // Rouge
	    scene.addLumiere(lumiere5);

	    
	    // Création de la caméra
	    Camera camera = new Camera(new Point(25,0,5),new Vecteur(-2,0,0),1000,1000,new Vecteur(0,0,2)); //vHaut = (0,0,10) sur l'exemple geogebra
		
	    // Lancement du ray tracing
		RayTracing raytracing = new RayTracing(scene, camera, 10, true, false);
		raytracing.lancerRayTracing();
		camera.sauvegarderImage("test4");
		FenetrePrincipale p = new FenetrePrincipale(raytracing);
		p.lancerFenetre();
	} catch (Exception e) {
		System.out.println("Erreur dans la gestion de la scène.");
	}
	}
}
