package FenetreElements3D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import elements3D.Objet3D;

public abstract class FenetreObjet3D extends JDialog {
	
	protected JPanel contentPanel = new JPanel();
	
	public FenetreObjet3D(String titre) {
		setTitle("Su7 - " + titre);
		setBounds(100, 100, 448, 207);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(135, 206, 250));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//Logo 
		URL img = FenetreObjet3D.class.getResource("/IG/logo.png");
		ImageIcon icon = new ImageIcon(img);
		setIconImage(icon.getImage());
		
	}
	
	public Objet3D getDonnees() {
		return null;
	}
}
