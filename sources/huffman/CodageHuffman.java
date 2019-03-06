package huffman;

import io.Flux;

import java.util.Collection;
import java.util.List;

public class CodageHuffman<E extends Comparable<? super E>> {
	private final long tempsAnalyse, tempsTableCodage;
	private final AnalyseOccurrences<E> analyse;
	private final TableCodage<E> table;
	private final Collection<E> contenu;
	private Flux flux;


	public CodageHuffman(List<E> contenu) {
		this.contenu = contenu;
		long t = System.currentTimeMillis();
		analyse = new AnalyseOccurrences<E>(contenu);
		tempsAnalyse = System.currentTimeMillis() - t;
		t = System.currentTimeMillis();
		table = new TableCodage<E>(analyse);
		tempsTableCodage = System.currentTimeMillis() - t;
	}

	public Flux getFlux() {
		if(flux == null) {
			flux = new Flux();
			for(final E e : contenu)
				table.getCodages().get(e).ecrire(flux);
		}
		return flux;
	}

	public AnalyseOccurrences<E> getAnalyse() {
		return analyse;
	}

	public TableCodage<E> getTable() {
		return table;
	}

	public Collection<E> getContenu() {
		return contenu;
	}

	public long getTempsAnalyse() {
		return tempsAnalyse;
	}

	public long getTempsTableCodage() {
		return tempsTableCodage;
	}
	

}