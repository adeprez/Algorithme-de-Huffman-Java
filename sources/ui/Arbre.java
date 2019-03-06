package ui;

import huffman.Noeud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class Arbre extends Ecran {
	private static final long serialVersionUID = 1L;
	private Noeud<Integer, Character> racine;
	private int taille, nbr;

	
	public void set(int nbr, Noeud<Integer, Character> racine) {
		this.nbr = nbr;
		this.racine = racine;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(POLICE);
		String s = "*Certains noeuds peuvent se superposer";
		g.drawString(s, getWidth() - g.getFontMetrics().stringWidth(s) - 10, getHeight() - 10);
		g.setFont(POLICE.deriveFont((float) taille/2));
		if(racine != null) {
			taille = (int) Math.min(getWidth()/nbr * 2, getHeight()/(racine.getProfondeur() + 1)/1.5);
			int x = (getWidth() - taille)/2;
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			racine.dessinerLiens((Graphics2D) g, taille, x);
			racine.dessiner((Graphics2D) g, taille, x);
		}
	}

}
