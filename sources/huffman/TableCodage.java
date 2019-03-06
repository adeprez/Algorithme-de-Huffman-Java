package huffman;

import io.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TableCodage<E extends Comparable<? super E>> {
	private final Map<E, Noeud<Integer, E>> codages;
	private final Noeud<Integer, E> racine;


	public TableCodage(AnalyseOccurrences<E> analyse) {
		final ConstructeurClefEntiere constructeur = new ConstructeurClefEntiere();
		List<Noeud<Integer, E>> noeuds = analyse.creerNoeuds();
		Noeud<Integer, E> n = null;
		int i = 0;
		while(i < noeuds.size()) {
			n = noeuds.get(i);
			i++;
			if(i < noeuds.size()) {
				n.setVoisin(noeuds.get(i));
				Noeud<Integer, E> nouveau = n.getParent(constructeur);
				boolean ajoute = false;
				for(int j=i ; j<noeuds.size() ; j++) {
					if(noeuds.get(j).compareTo(nouveau) > 0) {
						noeuds.add(j, nouveau);
						ajoute = true;
						break;
					}
				}
				if(!ajoute)
					noeuds.add(nouveau);
				i++;
			}
		}
		racine = n;
		codages = new HashMap<E, Noeud<Integer, E>>();
		for(final Noeud<Integer, E> nd : noeuds)
			if(nd.getValeur() != null)
				codages.put(nd.getValeur(), nd);
	}

	public Map<E, Noeud<Integer, E>> getCodages() {
		return codages;
	}

	public Noeud<Integer, E> getRacine() {
		return racine;
	}

	public List<E> decoder(Flux flux) {
		List<E> liste = new ArrayList<E>();
		int i = 0;
		while(i < flux.getBits().size()) {
			Noeud<Integer, E> noeud = racine;
			while(!noeud.estFeuille()) {
				noeud = flux.getBits().get(i) ? noeud.getEnfantD() : noeud.getEnfantG();
				i++;
			}
			if(noeud.estRacine())
				i++;
			liste.add(noeud.getValeur());
		}
		return liste;
	}

}
