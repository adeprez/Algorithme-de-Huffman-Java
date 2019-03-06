package ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Apercu extends Ecran {
	private static final long serialVersionUID = 1L;
	private List<Character> contenu;
	private final JTextPane texte;

	
	public Apercu() {
		setLayout(new BorderLayout());
		texte = new JTextPane();
		texte.setEditable(false);
		add(new JScrollPane(texte));
	}
	
	public void setTexte(List<Character> contenu) {
		this.contenu = contenu;
		String s = "";
		for(final Character c : contenu)
			s += c;
		texte.setText(s);
	}

	public List<Character> getContenu() {
		return contenu;
	}

	
}
