package rayTracing;

import java.awt.Color;
import java.awt.EventQueue;

import elements3D .*;
import exception.LumiereHorsSceneException;
import exception.MaxRebondsNegatifException;
import exception.NomVideException;
import exception.Objet3DHorsSceneException;
import exception.SauvegarderFichierException;
import exception.TaillePixelsImageException;
import utilitaire .*;

import java.awt.image.BufferedImage;

import IG.FenetrePrincipale;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scene scene = new Scene(300);
					scene.setAmbiante(new Couleur( Color.white));
					Camera camera = new Camera();
					RayTracing raytracing = new RayTracing(scene, camera, 10, true, true);
					FenetrePrincipale window = new FenetrePrincipale(raytracing);
					window.lancerFenetre();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
