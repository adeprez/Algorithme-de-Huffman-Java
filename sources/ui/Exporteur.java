package ui;

import io.FluxFichier;
import io.Images;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import ui.interfaces.ChoixFichierListener;

public class Exporteur extends Ecran implements ChoixFichierListener {
	private static final long serialVersionUID = 1L;
	private static final String[] CHOIX = new String[] {"Fichier", "Contenu codé", "Table de codage (.csv)", "Statistiques (.html)"};
	private final ChargeurFichier fichier;
	private final ButtonGroup groupe;
	private List<Character> contenu, code;
	private Statistiques<Character> stats;
	private TableModel table;

	
	public Exporteur() {
		setLayout(new BorderLayout());
		fichier = new ChargeurFichier(this, false);
		fichier.setPreferredSize(new Dimension(450, 50));
		add(fichier, BorderLayout.NORTH);
		JPanel centre = new JPanel(new GridLayout(CHOIX.length, 1));
		groupe = new ButtonGroup();
		for(final String s : CHOIX) {
			JPanel p = new JPanel();
			p.setBorder(new TitledBorder(""));
			AbstractButton b = new JRadioButton(s);
			b.setActionCommand(s);
			b.setFont(POLICE);
			p.add(new JLabel(new ImageIcon(Images.getInstance().getImage(s + ".png"))));
			p.add(Box.createRigidArea(new Dimension(50, 50)));
			p.add(b);
			centre.add(p);
			groupe.add(b);
			groupe.setSelected(b.getModel(), true);
		}
		add(centre, BorderLayout.CENTER);
	}

	@Override
	public void choix(File fichier) throws Exception {
		for(int i=0 ; i<CHOIX.length; i++) {
			if(CHOIX[i].equals(groupe.getSelection().getActionCommand())) {
				choix(fichier, i);
				break;
			}
		}
	}

	public void choix(File fichier, int i) throws IOException {
		FluxFichier flux = new FluxFichier(fichier);
		switch(i) {
		case 0:
			for(final char c : contenu)
				flux.ecrire(c);
			break;
		case 1:
			for(final char c : code)
				flux.ecrire(c);
			break;
		case 2:
			for(int l = 0; l < table.getRowCount() ; l++) {
				for(int c = 0 ; c < table.getColumnCount() ; c++) {
					String s = table.getValueAt(l, c) + "";
					if(";".equals(s))
						s = "Point-virgule";
					else if("\n".equals(s))
						s = "Retour ligne";
					else if(" ".equals(s))
						s = "Espace";
					for(final byte b : s.getBytes())
						flux.ecrire(b);
					flux.ecrire(';');
				}
				flux.ecrire('\n');
			}
			break;
		case 3:
			flux.write(stats.getTexte().getBytes());
			break;
		}
		flux.getOut().close();
		JOptionPane.showMessageDialog(null, fichier.getName() + " exporté avec succès");
	}

	public void set(List<Character> contenu, List<Character> code, Statistiques<Character> stats, TableModel table) {
		this.contenu = contenu;
		this.code = code;
		this.stats = stats;
		this.table = table;
	}
	
}
