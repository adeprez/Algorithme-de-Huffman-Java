package huffman;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class AnalyseOccurrences<E extends Comparable<? super E>> {
	private final Map<E, Integer> occurences;
	private final int nombre;
	private Map<E, Float> ratios;

	
	public AnalyseOccurrences(E... elements) {
		this(Arrays.asList(elements));
	}

	public AnalyseOccurrences(List<E> elements) {
		occurences = new HashMap<E, Integer>();
		nombre = elements.size();
		for(int i=0 ; i<nombre ; i++) {
			E e = elements.get(i);
			if(!occurences.containsKey(e))
				occurences.put(e, 1);
			else occurences.put(e, occurences.get(e) + 1);
		}
	}

	public int getNombreOccurences(E e) {
		return occurences.get(e);
	}

	public float getRatio(E e) {
		return getNombreOccurences(e) / ((float) nombre);
	}

	public Map<E, Integer> getOccurences() {
		return occurences;
	}

	public List<Noeud<Integer,E>> creerNoeuds() {
		List<Noeud<Integer,E>> noeuds = new ArrayList<Noeud<Integer, E>>();
		for(final Entry<E, Integer> e : occurences.entrySet())
			noeuds.add(new Noeud<Integer, E>(e.getValue(), e.getKey()));
		Collections.sort(noeuds);
		return noeuds;
	}

	public Map<E, Float> getRatios() {
		if(ratios == null) {
			ratios = new HashMap<E, Float>();
			for(final E e : occurences.keySet())
				ratios.put(e, getRatio(e));
		}
		return ratios;
	}
	
	public int getNombreTotal() {
		return nombre;
	}

}
