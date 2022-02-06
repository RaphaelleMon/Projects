package IG;

import javax.swing.JLabel;
import rayTracing.Lumiere;

public class Lumieres extends JLabel{

	private Lumiere lux;

	public Lumieres(Lumiere nL) {
		super(nL.getNom());
		lux = nL;
	}
	
	public Lumiere getObjet() {
		return this.lux;
	}
	
	public String getNom() {
		return lux.getNom();
	}
	
	@Override
	public String toString(){
		return lux.getNom();
	}
	

}
