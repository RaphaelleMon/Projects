package tests;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.Color;

import utilitaire.*;
import elements3D.*;
import exception.*;
import rayTracing.*;

/** Teste les enregistrements et lectures des fichiers .scene et .objet
 * @author Edgar
 *
 */
public class TestObjectSaver {
	
	// pr�cision pour les comparaisons entre reels
	public final static double EPSILON = 0.001;
	
	Sphere sph1, sph2;
	Point origine1;
	LumierePonctuelle lum1;
	Scene scene1, scene2;
	
	@Before
	public void setUp() {
		try {
		origine1 = new Point(1.0, 2.0, 3.0);
		sph1 = new Sphere(origine1, 4.0, "sphere_1");
		scene1 = new Scene(100.0);
		lum1 = new LumierePonctuelle(origine1, Color.WHITE);
		scene1.addLumiere(lum1);
		scene1.addObjet3D(sph1);
		} catch (NomVideException e) { // shouldn't happen
		} catch (Objet3DHorsSceneException e) { // shouldn't happen
		} catch (LumiereHorsSceneException e) { // shouldn't happen
		}
	}
	
	@Test
	public void testEnregistrementObjet3D() {
		try {
		ObjectSaver.saveObjet(sph1, "test_save_sphere");
		sph2 = (Sphere) ObjectSaver.openObjet("test_save_sphere");
		assertTrue("la sph�re enregistr�e n'a pas la bonne origine", sph2.getOrigine().equals(origine1, TestObjectSaver.EPSILON));
		assertEquals("la sph�re enregistr�e n'a pas le bon rayon", sph2.getRayon(), 4.0, TestObjectSaver.EPSILON);
		assertTrue("la sph�re enregistr�e n'as pas le bon nom", sph2.getNom().equals("sphere_1"));
		} catch (FichierExisteDejaException e) {
			// si le fichier test_save_sphere.objet existe d�j� ne va pas l'overwrite
			// et il pourrait alors avoir une diff�rence entre la sph�re enregistr�e
			// et la sph�re lue
		}
	}
	
	@Test
	public void testEnregistrementScene() {
		try {
		ObjectSaver.saveScene(scene1, "test_save_scene");
		scene2 = ObjectSaver.openScene("test_save_scene");
		assertEquals("la sc�ne enregistr�e n'a pas la bonne dimension", scene2.getDimension(), 100.0, TestObjectSaver.EPSILON);
		
		assertTrue("la sc�ne enregistr�e ne contient pas un objet3D", scene2.getObjets().size() == 1);
		assertTrue("la sc�ne enregistr�e ne contient pas une lumi�re", scene2.getLumieres().size() == 1);
		} catch (FichierExisteDejaException e) {
			// si le fichier test_save_scene.scene existe d�j� ne va pas l'overwrite
			// et il pourrait alors avoir une diff�rence entre la sc�ne enregistr�e
			// et la sc�ne lue
		}
	}
	
	

}
