package tests;
import org.junit.*;
import static org.junit.Assert.*;

import java.awt.Color;

import utilitaire .*;
import rayTracing .*;
import elements3D .*;
import exception.NomVideException;

/** @author tibo */

public class TestPlan {
	// precision pour les comparaisons entre reels
	public final static double EPSILON = 0.001;
	
	private Point pointPlan, pointRayon;
	private Vecteur normale, directionRayon;
	private Rayon rayon;
	private Pixel pixelPere;
	
	private Plan plan;
	private Vecteur v1,v2,v3, v4;
	private Point p2,p3, p4;
	private Rayon r2, r4;
	private Lumiere lumiere;
	
	@Before public void setUp() {
		pointPlan = new Point(0,0,0);
		normale = new Vecteur(0,0,1);
		pointRayon = new Point(1,2,1);
		directionRayon = new Vecteur(-1,-2,-1);
		pixelPere = new Pixel(new Point(0,0,0));
		rayon = new Rayon(directionRayon,pointRayon,pixelPere);
		
		v2 = new Vecteur(-3,-5,-1);
		p2 = new Point(6,4,1);
		r2 = new Rayon(v2,p2,pixelPere);
		
		p3 = new Point(300,250,0);
		
		lumiere = new LumierePonctuelle(p2, Color.BLUE, "Theo de Silverberg");
		
		v4 = new Vecteur(-1,-1,-1);
		p4 = new Point(6,4,1);
		r4 = new Rayon(v4,p4,pixelPere);
	}
	
	@Test public void test() {
		plan = null;
		try {
		plan = new Plan(normale, pointPlan, "Hubert le plan");
		} catch (NomVideException e) { // shouldn't happen
		}
		
		v1 = plan.directionReflexion(rayon, pointPlan);
		//v1.afficher();
		assertEquals("Abs de v1 fausse", v1.getX(), -1, EPSILON);
		assertEquals("Ordonnee de v1 fausse", v1.getY(), -2, EPSILON);
		assertEquals("Profondeur de v1 fausse", v1.getZ(), 1, EPSILON);
		
		
		
		p2 = plan.estTraversePar(r2);
		//p2.afficher();
		assertEquals("Abs de p2 fausse", p2.getX(), 3, EPSILON);
		assertEquals("Ordonnee de p2 fausse", p2.getY(), -1, EPSILON);
		assertEquals("Profondeur de p2 fausse", p2.getZ(), 0, EPSILON);
		
		
		v3 = plan.getNormal(p3, r2);
		//v3.afficher();
		assertEquals("Abs de p2 fausse", v3.getX(), 0, EPSILON);
		assertEquals("Ordonnee de p2 fausse", v3.getY(), 0, EPSILON);
		assertEquals("Profondeur de p2 fausse", v3.getZ(), 1, EPSILON);
		
		assertTrue(!plan.getSelfOmbre(p3, r2, lumiere));
		
		Vecteur Vrefracte = plan.directionRefraction(r4, 2, true, 1);
		Vrefracte.afficher();
		
	}
		
}
