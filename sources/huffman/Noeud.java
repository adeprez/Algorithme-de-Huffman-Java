package huffman;

import io.Flux;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;


public class Noeud<K extends Comparable<K>, V> implements Comparable<Noeud<K, V>>, TreeNode, Enumeration<Noeud<K, V>> {
	private final K clef;
	private Noeud<K, V> parent, voisin, enfantG, enfantD;
	private List<Boolean> codage;
	private boolean droite;
	private V valeur;


	public Noeud(K clef) {
		this.clef = clef;
	}

	public Noeud(K clef, Noeud<K, V> enfantG, Noeud<K, V> enfantD) {
		this(clef);
		this.enfantG = enfantG;
		this.enfantD = enfantD;
	}

	public Noeud(K clef, V valeur) {
		this(clef);
		this.valeur = valeur;
	}

	/**
	 * Permet d'acceder au noeud parent de ce noeud. Construit le parent de ce noeud s'il possede un voisin.
	 * @param constructeur un objet permettant de construire la clef du parent, s'il n'existe pas
	 * @return le noeud parent
	 * @throws IllegalAccessError si le noeud n'a pas de parent, ni de voisin pour generer un parent
	 */
	public Noeud<K, V> getParent(ConstructeurClef<K> constructeur) {
		if(parent == null) {
			if(voisin == null)
				throw new IllegalAccessError();
			parent = new Noeud<K, V>(constructeur.getClef(clef, voisin.clef), voisin, this);
			voisin.parent = parent;
		}
		return parent;
	}

	public void setVoisin(Noeud<K, V> voisin) {
		this.voisin = voisin;
	}

	public Noeud<K, V> getVoisin() {
		return voisin;
	}

	public V getValeur() {
		return valeur;
	}

	public K getClef() {
		return clef;
	}

	public Noeud<K, V> getEnfantG() {
		return enfantG;
	}

	public Noeud<K, V> getEnfantD() {
		return enfantD;
	}

	public boolean estFeuille() {
		return enfantG == null && enfantD == null;
	}

	public boolean estRacine() {
		return parent == null;
	}

	public boolean estDroite() {
		return !estRacine() && parent.getEnfantD() == this;
	}

	public void ecrire(Flux flux) {
		if(codage == null) {
			codage = new ArrayList<Boolean>();
			Noeud<K, V> p = parent;
			codage.add(estDroite());
			while(p != null && !p.estRacine()) {
				codage.add(p.estDroite());
				p = p.getParent(null);
			}
		}
		for(int i=codage.size() - 1 ; i>=0 ; i--)
			flux.getBits().add(codage.get(i));
	}

	public String getArbre() {
		return getArbre(0);
	}

	public String getArbre(int d) {
		String ds = "";
		for(int i=0 ; i<d ; i++)
			ds += "|";
		String s = clef + (valeur == null ? "" : "("+ valeur + ")") + "\n";
		if(enfantD != null)
			s += ds + "\\_" + enfantD.getArbre(d + 1);
		if(enfantG != null)
			s += ds + "|_" + enfantG.getArbre(d + 1);
		return s;
	}

	public void dessinerLiens(Graphics2D g, int taille, int x) {
		dessinerLiens(g, taille, x, 0, false);
	}

	public void dessiner(Graphics2D g, int taille, int x) {
		dessiner(g, taille, x, 0, false);
	}

	public int getProfondeur() {
		return getProfondeur(0);
	}

	public int getProfondeur(int profondeur) {
		if(estFeuille())
			return profondeur;
		return Math.max(enfantG == null ? 0 : enfantG.getProfondeur(profondeur + 1), enfantD == null ? 0 : enfantD.getProfondeur(profondeur + 1));
	}

	public void dessinerLiens(Graphics2D g, int taille, int x, int y, boolean droite) {
		if(!estRacine() && getParent(null).estRacine())
			droite = estDroite();
		if(enfantD != null) {
			g.drawLine(x + taille/2, y + taille/2, x + (estRacine() || droite ? taille : 0) + taille/2, (int) (y + taille * 1.5 + taille/2));
			enfantD.dessinerLiens(g, taille, x + (estRacine() || droite ? taille : 0), (int) (y + taille * 1.5), droite);
		}
		if(enfantG != null) {
			g.drawLine(x + taille/2, y + taille/2, x - (droite ? 0 : taille) + taille/2, (int) (y + taille * 1.5 + taille/2));
			enfantG.dessinerLiens(g, taille, x - (droite ? 0 : taille), (int) (y + taille * 1.5), droite);
		}
	}

	public void dessiner(Graphics2D g, int taille, int x, int y, boolean droite) {
		if(!estRacine() && getParent(null).estRacine())
			droite = estDroite();
		String s = clef + (getValeur() == null ? "" : ":" + getValeur());
		g.setPaint(new GradientPaint(x + taille/2, y, Color.WHITE, x + taille, y + taille, Color.LIGHT_GRAY, true));
		g.fillOval(x, y, taille, taille);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, taille, taille);
		g.drawString(s, x + (taille - g.getFontMetrics().stringWidth(s))/2, y + (taille + g.getFontMetrics().getHeight()/2)/2);
		if(enfantD != null)
			enfantD.dessiner(g, taille, x + (estRacine() || droite ? taille : 0), (int) (y + taille * 1.5), droite);
		if(enfantG != null)
			enfantG.dessiner(g, taille, x - (droite ? 0 : taille), (int) (y + taille * 1.5), droite);
	}

	@Override
	public String toString() {
		if(estRacine())
			return "Racine : " + clef;
		if(estFeuille())
			return "Feuille " + (estDroite() ? "droite" : "gauche") + " : " + clef + "=\"" + valeur + "\"";
		return (estDroite() ? "Droite" : "Gauche") + " : " + clef;
	}

	@Override
	public int compareTo(Noeud<K, V> o) {
		return clef.compareTo(o.clef);
	}

	@Override
	public Enumeration<Noeud<K, V>> children() {
		return this;
	}

	@Override
	public boolean getAllowsChildren() {
		return !estFeuille();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return childIndex == 0 ? enfantG : enfantD;
	}

	@Override
	public int getChildCount() {
		return (enfantD == null ? 0 : 1) + (enfantG == null ? 0 : 1);
	}

	@Override
	public int getIndex(TreeNode node) {
		return node == enfantG ? 0 : 1;
	}

	@Override
	public TreeNode getParent() {
		return getParent(null);
	}

	@Override
	public boolean isLeaf() {
		return estFeuille();
	}

	@Override
	public boolean hasMoreElements() {
		return !droite;
	}

	@Override
	public Noeud<K, V> nextElement() {
		Noeud<K, V> n = droite ? enfantD : enfantG;
		droite = true;
		return n;
	}

}
