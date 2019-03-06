package ui;

import huffman.AnalyseOccurrences;
import huffman.CodageHuffman;
import huffman.Noeud;
import huffman.TableCodage;
import io.Flux;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TableCodageGraphique<E extends Comparable<? super E>> extends Ecran implements TableModel {
	private static final String[] COLONNES = new String[] 
			{"Element", "Nombre d'occurrences", "Fréquence d'apparition", "Taille du codage", "Codage binaire"};
	private static final long serialVersionUID = 1L;
	private List<Entry<E, Noeud<Integer, E>>> elements;
	private AnalyseOccurrences<E> analyse;
	
	
	public TableCodageGraphique() {
		setLayout(new GridLayout());
		JTable t = new JTable(this);
		t.setAutoCreateRowSorter(true);
		add(new JScrollPane(t));
	}
	
	public void set(TableCodage<E> table, AnalyseOccurrences<E> analyse) {
		this.analyse = analyse;
		elements = new ArrayList<Entry<E, Noeud<Integer, E>>>();
		for(final Entry<E, Noeud<Integer, E>> e : table.getCodages().entrySet())
			elements.add(e);
		TableModelEvent e = new TableModelEvent(this);
		for(final TableModelListener l : listenerList.getListeners(TableModelListener.class))
			l.tableChanged(e);
	}

	public void set(CodageHuffman<E> c) {
		set(c.getTable(), c.getAnalyse());
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		listenerList.add(TableModelListener.class, l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case 1:
		case 3:
			return Integer.class;
		}
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return COLONNES.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLONNES[columnIndex];
	}

	@Override
	public int getRowCount() {
		if(elements == null)
			return 0;
		return elements.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Entry<E, Noeud<Integer, E>> e = elements.get(rowIndex);
		switch(columnIndex) {
		case 0: 
			return e.getKey() + "";
		case 1: 
			return analyse.getNombreOccurences(e.getKey());
		case 2:
			return analyse.getRatio(e.getKey()) * 100 + "%";
		case 3:
			Flux ff = new Flux();
			e.getValue().ecrire(ff);
			return ff.getBits().size();
		case 4:
			Flux f = new Flux();
			e.getValue().ecrire(f);
			return f.toString();
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listenerList.remove(TableModelListener.class, l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

}
