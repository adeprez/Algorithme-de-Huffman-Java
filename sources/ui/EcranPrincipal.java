package ui;

import huffman.CodageHuffman;
import huffman.Texte;
import io.Fichiers;
import io.Images;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultTreeModel;

import ui.interfaces.ChoixFichierListener;

public class EcranPrincipal extends Ecran implements Runnable, ChoixFichierListener {
	private static final long serialVersionUID = 1L;
	private final TableCodageGraphique<Character> table;
	private final Statistiques<Character> stats;
	private final Apercu apercu, decodage, code;
	private final ChargeurFichier chargeur;
	private final JTabbedPane panelOnglets;
	private final Exporteur exporteur;
	private final JTree arbre2;
	private final Arbre arbre;
	private InputStream flux;


	public EcranPrincipal() {
		setLayout(new BorderLayout());
		setName("Huffman");

		chargeur = new ChargeurFichier(this, true);
		apercu = new Apercu();
		code = new Apercu();
		decodage = new Apercu();
		stats = new Statistiques<Character>();
		table = new TableCodageGraphique<Character>();
		arbre = new Arbre();
		arbre2 = new JTree();
		exporteur = new Exporteur();

		panelOnglets = new JTabbedPane(SwingConstants.LEFT);
		panelOnglets.add(apercu);
		panelOnglets.add(table);
		panelOnglets.add(code);
		panelOnglets.add(decodage);
		panelOnglets.add(arbre);
		panelOnglets.add(new JScrollPane(arbre2));
		panelOnglets.add(stats);
		panelOnglets.add(exporteur);
		setOnglet(0, "Contenu fichier", "Fichier");
		setOnglet(1, "Table", "Table de codage (.csv)");
		setOnglet(2, "Contenu codé", "Contenu codé");
		setOnglet(3, "Décodage", "Fichier");
		setOnglet(4, "Schéma", "build");
		setOnglet(5, "Arbre", "arbre");
		setOnglet(6, "Statistiques", "Statistiques (.html)");
		setOnglet(7, "Exporter", "save");
		panelOnglets.setEnabled(false);

		add(panelOnglets, BorderLayout.CENTER);
		add(chargeur, BorderLayout.NORTH);
	}
	
	public void analyse(InputStream flux) {
		if(!chargeur.estActif()) {
			this.flux = flux;
			chargeur.setActif(false);
			new Thread(this).start();
		}
	}

	public void setEtape(int no, String texte) {
		chargeur.getAvancement().setString("(Etape " + no + "/3) " + texte + "...");
	}

	public void setAvancement(int rang) {
		chargeur.getAvancement().setValue((rang * 100)/7);
	}
	
	public void setOnglet(int index, String titre, String image) {
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(175, 50));
		p.setOpaque(false);
		p.add(new JLabel(icone(image)), BorderLayout.WEST);
		JLabel l = new JLabel(titre, SwingConstants.RIGHT);
		l.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		p.add(l, BorderLayout.EAST);
		panelOnglets.setTabComponentAt(index, p);
	}

	@Override
	public void run() {
		try {
			setEtape(1, "Lecture du fichier...");
			List<Character> contenu = Fichiers.lireFichier(flux);
			long global = System.currentTimeMillis();
			
			CodageHuffman<Character> c = new CodageHuffman<Character>(contenu);
			Texte txt = new Texte();
			long temps = 0;
			setEtape(2, "Ecriture du fichier codé");
			temps = System.currentTimeMillis();
			c.getFlux().ecrire(txt);
			temps = System.currentTimeMillis() - temps;
			setEtape(3, "Décodage du texte");
			long temps2 = System.currentTimeMillis();
			List<Character> decode = c.getTable().decoder(c.getFlux());
			temps2 = System.currentTimeMillis() - temps2;
			global = System.currentTimeMillis() - global;

			//Mise a jour de l'interface graphique
			chargeur.getAvancement().setString("Mise à jour de l'interface graphique...");
			panelOnglets.setEnabled(true);
			setAvancement(0);
			apercu.setTexte(contenu);
			setAvancement(1);
			code.setTexte(txt.getCaracteres());
			setAvancement(2);
			decodage.setTexte(decode);
			setAvancement(3);
			table.set(c);
			setAvancement(4);
			stats.set(c, txt.getCaracteres().size(), temps, temps2, global);
			setAvancement(5);
			exporteur.set(contenu, txt.getCaracteres(), stats, table);
			setAvancement(6);
			arbre.set(c.getTable().getCodages().size(), c.getTable().getRacine());
			setAvancement(7);
			arbre2.setModel(new DefaultTreeModel(c.getTable().getRacine()));

		} catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Erreur : " + e1.getMessage());
			e1.printStackTrace();
		}

		chargeur.setActif(true);
		validate();
		repaint();
	}

	@Override
	public void choix(File fichier) throws Exception {
		analyse(Fichiers.getFluxFichier(fichier));
	}
	
	public static ImageIcon icone(String nom) {
		return new ImageIcon(Images.getInstance().getImage(nom + ".png"));
	}

}
