package tests;


import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;

import elements3D.*;
import exception.NomVideException;
import utilitaire.*;
import rayTracing.*;

class TestCreateurDOmbre {
	
	public final static double EPSILON = 0.001;

	private CreateurDOmbre cDO1;
	private CreateurDOmbre cDO2;
	private CreateurDOmbre cDO3;
	private CreateurDOmbre cDO4;
	private CreateurDOmbre cDO5;
	private ArrayList<CreateurDOmbre> listeCreateurs;
	
	@Before
	public void setUp() {
		try {
			cDO1 = new CreateurDOmbre(new Sphere(new Point(1,1,1),5, "S1"),1);
			cDO2 = new CreateurDOmbre(new Sphere(new Point(1,1,1),5,"S2"),2.5);
			cDO3 = new CreateurDOmbre(new Sphere(new Point(1,1,1),5, "S3"),3);
			cDO4 = new CreateurDOmbre(new Sphere(new Point(1,1,1),5.1, "S4"),60.23);
			cDO5 = new CreateurDOmbre(new Sphere(new Point(1,1,1),5, "S5"),3);
		} catch (NomVideException e) {
			e.printStackTrace(); //ne devrait pas arriver
		}
		listeCreateurs = new ArrayList<CreateurDOmbre>();
		listeCreateurs.add(cDO4);
		listeCreateurs.add(cDO5);
		listeCreateurs.add(cDO3);
		listeCreateurs.add(cDO2);
		listeCreateurs.add(cDO1);
	}
		
	@Test
	void testConstructeur() {
		assertEquals("Problème de distance", cDO4.getDistanceImpact(), 60.23, EPSILON);
		assertEquals("Problème d'objet", ((Sphere) cDO4.getObstructeur()).getRayon(), 5.1, EPSILON);
	}
	
	@Test
	void testTrie() {
		Collections.sort(listeCreateurs);
		System.out.println(listeCreateurs.get(0));
		System.out.println(listeCreateurs.get(1));
		System.out.println(listeCreateurs.get(2));
		System.out.println(listeCreateurs.get(3));
		System.out.println(listeCreateurs.get(4));
		assertEquals("cDO1 n'est pas trié", cDO1, listeCreateurs.get(0));
		assertEquals("cDO2 n'est pas trié", cDO2, listeCreateurs.get(1));
		assertEquals("cDO3 n'est pas trié", cDO3, listeCreateurs.get(3));
		assertEquals("cDO4 n'est pas trié", cDO4, listeCreateurs.get(4));
		assertEquals("cDO5 n'est pas trié", cDO5, listeCreateurs.get(2));
	}
	
	public static void main(String args[]) {
		TestCreateurDOmbre self = new TestCreateurDOmbre();
		self.setUp();
		self.testConstructeur();
		self.testTrie();
	}
	
		
}