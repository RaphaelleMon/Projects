package tests;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import elements3D.Objet3D;
import rayTracing.Lumiere;
import rayTracing.Scene;

public class TestScene {
    private List<Objet3D> objets3D;
	private List<Lumiere> lumieres; 
	private Scene scene;
	 
	@Before public void setUp() {
		objets3D = new ArrayList<Objet3D>();
		lumieres = new ArrayList<Lumiere>();
		scene = new Scene(300);
	}
	@Test public void testConstructeur() {
		assertEquals("lumiere is not correct!", lumieres,scene.getLumieres());
		assertEquals("Objet3D is not correct!", objets3D,scene.getObjets());
		assertEquals("color is not correct!",Color.black, scene.getAmbiante().get());
	}
}
