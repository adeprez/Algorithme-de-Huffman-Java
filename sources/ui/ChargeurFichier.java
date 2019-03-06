package ui;

import io.Fichiers;
import io.Images;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import ui.interfaces.ChoixFichierListener;


public class ChargeurFichier extends Ecran implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JProgressBar avancement;
	private final ChoixFichierListener l;
	private final JButton charger, ok;
	private final JTextField chemin;
	private final boolean ouvre;


	public ChargeurFichier(ChoixFichierListener l, boolean ouvre) {
		this.l = l;
		this.ouvre = ouvre;
		setLayout(new BorderLayout());

		chemin = new JTextField();
		avancement = new JProgressBar();
		charger = new JButton(new ImageIcon(Images.getInstance().getImage(ouvre ? "open.png" : "export.png")
				.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
		ok = new JButton(new ImageIcon(Images.getInstance().getImage("ok.png").getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));

		if(ouvre) {
			chemin.setToolTipText("Chemin vers le fichier à analyser");
			charger.setToolTipText("Choisir un fichier...");
			ok.setToolTipText("Lancer l'analyse du fichier");
		} else {
			chemin.setToolTipText("Chemin vers le fichier d'enregistrement");
			charger.setToolTipText("Choisir un fichier...");
			ok.setToolTipText("Exporter");
		}
		avancement.setValue(100);
		charger.setFont(POLICE);
		chemin.setFont(POLICE);
		ok.setFont(POLICE);

		avancement.setVisible(false);
		avancement.setStringPainted(true);
		avancement.setFont(POLICE);

		charger.addActionListener(this);
		chemin.addActionListener(this);
		ok.addActionListener(this);

		add(avancement, BorderLayout.SOUTH);
		add(chemin, BorderLayout.CENTER);
		add(charger, BorderLayout.WEST);
		add(ok, BorderLayout.EAST);
	}

	public void lancer() {
		File f = new File(chemin.getText());
		if(!ouvre) try {
			f.createNewFile();
		} catch(IOException e1) {
			JOptionPane.showMessageDialog(null, "Impossible de créer le fichier :\n" + e1.getMessage());
		}
		if(!f.exists() || f.isDirectory()) {
			JOptionPane.showMessageDialog(null, "Le fichier \"" + f + "\" n'existe pas");
		} else try {
			l.choix(f);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Erreur. \n" + e.getMessage());
		}
	}

	public void setActif(boolean actif) {
		avancement.setVisible(!actif);
		ok.setEnabled(actif);
		charger.setEnabled(actif);
		chemin.setEnabled(actif);
		validate();
		repaint();
	}

	public JProgressBar getAvancement() {
		return avancement;
	}

	public boolean estActif() {
		return avancement.isVisible();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == charger) try {
			chemin.setText(Fichiers.getInstance().getFichier(ouvre).getAbsolutePath());
		} catch(FileNotFoundException err) {}
		else if(!chemin.getText().trim().isEmpty())
			lancer();
	}

}
