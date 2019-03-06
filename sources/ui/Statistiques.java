package ui;

import huffman.CodageHuffman;

import java.awt.GridLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public class Statistiques<E extends Comparable<? super E>> extends Ecran {
	private static final long serialVersionUID = 1L;
	private final JEditorPane texte;


	public Statistiques() {
		setLayout(new GridLayout());
		texte = new JEditorPane();
		texte.setContentType("text/html");
		add(new JScrollPane(texte));
	}

	public void set(CodageHuffman<E> c, int tailleCode, long tempsCode, long tempsDecode, long global) {
		float p = 100f/global;
		String html = "<html><body>";
		html += "<h1>Statistiques</h1>";
		html += "<li>Nombre de caractères du fichier d'origine : " + c.getContenu().size() + "</li>";
		html += "<li>Nombre de caractères du fichier codé : " + tailleCode + "</li><br/>";
		html += "<li>Taux de compression : " + (100 * (c.getContenu().size() - tailleCode))/Math.max(1, c.getContenu().size()) + "%</li><br/>";
		html += "<li>Temps d'exécution global : " + global + "ms</li>";
		html += "<li><u>[" + Math.round(p * c.getTempsAnalyse()) 
				+ "%]</u> Temps d'analyse des occurences du fichier : " + c.getTempsAnalyse() + "ms</li>";
		html += "<li><u>[" + Math.round(p * c.getTempsTableCodage() )
				+ "%]</u> Temps de creation de la table de codage : " + c.getTempsTableCodage() + "ms</li>";
		html += "<li><u>[" + Math.round(p * tempsCode)
				+ "%]</u> Temps de codage du fichier : " + tempsCode + "ms (" + (c.getContenu().size() * 1000)/Math.max(1, tempsCode) + " elements/s)</li>";
		html += "<li><u>[" + Math.round(p * tempsDecode)
				+ "%]</u> Temps de décodage du fichier : " + tempsDecode + "ms (" + (tailleCode * 1000)/Math.max(1, tempsDecode) + " elements/s)</li>";
		texte.setText(html + "</body></html>");
	}
	
	public String getTexte() {
		return texte.getText();
	}


}
