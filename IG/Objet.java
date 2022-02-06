package IG;
import elements3D.*;
import javax.swing.*;

public class Objet extends JLabel {
	
	private Objet3D objet;

	public Objet(Objet3D nobjet) {
		super(nobjet.getNom());
		objet = nobjet;
	}
	
	public Objet3D getObjet() {
		return this.objet;
	}
	
	public String getNom() {
		return objet.getNom();
	}
	
	
	@Override
	public String toString(){
		return objet.getNom();
	}

}
