package IG;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rayTracing.RayTracing;

import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

public class Parametrage extends JFrame{

	private final JPanel contentPanel = new JPanel();
	private RayTracing rt;
	private JSpinner nbRebond;
	private JCheckBox activeOmbres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Parametrage dialog = new Parametrage(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Parametrage(RayTracing nrt) {
		super("Su7 - Paramètrer Ray Tracing");
		setResizable(false);
		
		this.rt = nrt;
		
		URL img = Parametrage.class.getResource("/IG/logo.png");
		ImageIcon icon = new ImageIcon(img);
		setIconImage(icon.getImage());
		setBackground(new Color(32, 178, 170));
		setBounds(100, 100, 417, 158);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(32, 178, 170));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(64, 224, 208)));
		panel.setBackground(new Color(72, 209, 204));
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel nombreRebond = new JLabel("Nombre de rebonds ");
		nombreRebond.setBounds(90, 13, 160, 17);
		panel.add(nombreRebond);
		nombreRebond.setHorizontalAlignment(SwingConstants.LEFT);
		nombreRebond.setBackground(new Color(240, 240, 240));
		nombreRebond.setEnabled(true);
		
		nbRebond = new JSpinner();
		nbRebond.setBounds(204, 11, 85, 20);
		panel.add(nbRebond);
		nbRebond.setModel(new SpinnerNumberModel(0, 0, null, 1));
		nbRebond.setToolTipText("Nombre de rebonds");
		nbRebond.setValue(nrt.getRebond());
		
		activeOmbres = new JCheckBox("Activer Ombres");
		activeOmbres.setBackground(new Color(0, 139, 139));
		activeOmbres.setBounds(89, 35, 200, 23);
		panel.add(activeOmbres);
		activeOmbres.setSelected(rt.getOmbre());
		activeOmbres.addActionListener(new ActiverOmbre());
		activeOmbres.setMnemonic('O');
		nbRebond.addChangeListener(new setRebond());
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new LineBorder(new Color(64, 224, 208)));
		buttonPane.setBackground(new Color(32, 178, 170));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.setBackground(new Color(0, 139, 139));
		okButton.addActionListener(new ActionOk());
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
	}
	
	public class setRebond implements ChangeListener {
		
		public void stateChanged(ChangeEvent event) {
			JSpinner valeur = (JSpinner) event.getSource();
			rt.setRebond((int)valeur.getValue());
		}
	}
	
	
	
	public class ActiverOmbre implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			boolean state = activeOmbres.isSelected();
			rt.setOmbre(state);
		}
	}
	
	public class ActionOk implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			dispose();
		}
	}
}
