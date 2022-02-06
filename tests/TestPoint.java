package tests;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

//import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utilitaire.Point;
import utilitaire.Vecteur;

class TestPoint {
	
	public final static double EPSILON = 0.001;
	
	private Vecteur u;
	public Point A, B;
	
	/** Verifier si deux points ont memes coordonnees.
	  * @param p1 le premier point
	  * @param p2 le deuxieme point
	  */
	public static void memesCoordonnees(String message, Point p1, Point p2) {
		assertEquals(message + " (x)", p1.getX(), p2.getX(), EPSILON);
		assertEquals(message + " (y)", p1.getY(), p2.getY(), EPSILON);
		assertEquals(message + " (y)", p1.getZ(), p2.getZ(), EPSILON);
	}
	
	public static void bonnesCoordonnees(String message, Point p, double x, double y, double z) {
		assertEquals(message + " (x)", p.getX(), x,  EPSILON);
		assertEquals(message + " (y)", p.getY(), y, EPSILON);
		assertEquals(message + " (z)", p.getZ(), z, EPSILON);
	}

	@BeforeEach public void setUp() {
		u = new Vecteur(1.0, 1.0, 1.0);
	}

	@Test public void testE10() {
		A = new Point(1.0, -3.5, 3.4);
		bonnesCoordonnees("E10 : mauvaise coordonnee", A, 1.0, -3.5, 3.4);
	}

	@Test public void testE11() {
		A = new Point(1.0, -3.5, 3.4);
		memesCoordonnees("E11 : mauvaise coordonnee (copie)", A.copie(), A);
	}

	@Test public void testE12a() {
		A = new Point(1.0,-1.0,0.0);
		A.setX(0.5);
		bonnesCoordonnees("E12 : problème set X", A, 0.5, -1.0, 0.0);
	}

	@Test public void testE12b() {
		A = new Point(1.0,-1.0,0.0);
		A.setY(0.5);
		bonnesCoordonnees("E12 : problème set Y", A, 1.0, 0.5, 0.0);
	}

	@Test public void testE12c() {
		A = new Point(1.0,-1.0,0.0);
		A.setZ(0.5);
		bonnesCoordonnees("E12 : problème set Z", A, 1.0, -1.0, 0.5);
	}

	@Test public void testE13a() {
		A = new Point(1.0,-1.0,0.0);
		A.translater(1.0, 1.0, 1.0);
		bonnesCoordonnees("E13 : problème translater", A, 2.0, 0.0, 1.0);
	}

	@Test public void testE13b() {
		A = new Point(1.0,-1.0,0.0);
		A.translater(u);
		bonnesCoordonnees("E13 : problème translater", A, 2.0, 0.0, 1.0);
	}

	@Test public void testE14a() {
		A = new Point(4.0,-3.0,0.0);
		assertEquals("E14 : mauvais module", A.getModule(), 5,EPSILON);
	}

	@Test public void testE14b() {
		A = new Point(4.0,-3.0,0.0);
		B = new Point(4.0,-3.0,0.0);
		assertEquals("E14 : mauvais module", A.distance(B), 0.0,EPSILON);
	}

	@Test public void testE15a() {
		A = new Point(4.0, 0.0, 3.0);
		B = new Point(2.0, 1.0, 0.0);
		memesCoordonnees("E15 : mauvaises coordonnées somme", A.sommer(B), new Point(6.0, 1.0, 3.0));
	}

	@Test public void testE15b() {
		A = new Point(4.0, 0.0, 3.0);
		B = new Point(2.0, 1.0, 0.0);
		memesCoordonnees("E15 : mauvaises coordonnées somme", A.soustraire(B), new Point(2.0, -1.0, 3.0));
	}

	@Test public void testE15c() {
		B = new Point(4.0, 0.0, 3.0);
		bonnesCoordonnees("E15 : mauvaises coordonnées multiplication", B.multiplier(-0.5), -2.0, 0.0, -1.5);
	}
	
	@Test public void testE16a() {
		A = new Point(-1.0, 0.0, 0.0);
		B = new Point(1.0, 0.0, 0.0);
		Point AxeO = new Point(0.0, 0.0, 0.0);
		Vecteur Axe = new Vecteur(0.0, 0.0, 2.0);
		memesCoordonnees("E16 : mauvaises coordonnées rotation", B.rotationAxe(Axe, AxeO, Math.PI), A);
		}
	
	@Test public void testE16b() {
		A = new Point(1.0, 0.0, 2.0);
		B = new Point(1.0, 0.0, 0.0);
		Point AxeO = new Point(0.0, 0.0, 1.0);
		Vecteur Axe = new Vecteur(2.0, 0.0, 0.0);
		memesCoordonnees("E16 : mauvaises coordonnées rotation", B.rotationAxe(Axe, AxeO, Math.PI), A);
		}
	
	@Test public void testE16c() {
		A = new Point(1.0, 0.0, 1.0);
		B = new Point(1.0, 1.0, 0.0);
		Point AxeO = new Point(1.0, 0.0, 0.0);
		Vecteur Axe = new Vecteur(1.0, 1.0, 1.0);
		memesCoordonnees("E16 : mauvaises coordonnées rotation", B.rotationAxe(Axe, AxeO, 2.0 * Math.PI / 3.0), A);
		}
}
