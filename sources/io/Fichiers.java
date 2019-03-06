package io;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

/**
 * Singleton permettant de charger des fichiers et d'acceder a leur contenu
 */
public class Fichiers {
	private static Fichiers instance;
	private File dossier;


	/**
	 * Design-pattern singleton. Cree l'unique instance de cette classe, si elle n'existe pas encore
	 * @return l'unique instance de cette classe
	 */
	public static Fichiers getInstance() {
		synchronized(Fichiers.class) {
			if(instance == null)
				instance = new Fichiers();
			return instance;
		}
	}

	/**
	 * Constructeur prive, selon le design-pattern singleton
	 */
	private Fichiers() {
		dossier = new File(".");
	}

	public InputStream getFluxFichier(String nom) {
		return getClass().getResourceAsStream(nom);
	}

	public File getFichier(boolean ouvre) throws FileNotFoundException {
		JFileChooser jfc = new JFileChooser(dossier);
		jfc.setMultiSelectionEnabled(false);
		int i = ouvre ? jfc.showOpenDialog(null) : jfc.showSaveDialog(null);
		if(i == JFileChooser.CANCEL_OPTION || jfc.getSelectedFile().isDirectory())
			throw new FileNotFoundException();
		dossier = jfc.getSelectedFile().getParentFile();
		return jfc.getSelectedFile();
	}

	public InputStream getFluxFichier() throws FileNotFoundException {
		return new FileInputStream(getFichier(true));
	}

	public List<Character> lireLignesFichier(String nom) {
		return lireFichier(getFluxFichier("/" + nom));
	}

	public List<Character> lireLignesFichier() throws FileNotFoundException {
		return lireFichier(getFluxFichier());
	}

	public static InputStream getFluxFichier(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	public static FileOutputStream getFluxEcriture(String nom) throws IOException {
		File f = new File(nom);
		f.createNewFile();
		return new FileOutputStream(f);
	}

	public static List<Character> lireFichier(InputStream flux) {
		ArrayList<Character> caracteres = new ArrayList<Character>();
		BufferedReader read = null;
		try {
			read = new BufferedReader(new InputStreamReader(flux));
			int caract;
			while((caract = read.read()) != -1)
				caracteres.add((char) caract);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(read != null)
					read.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return caracteres;
	}

	public static List<Integer> lireIntFichier(InputStream flux) {
		ArrayList<Integer> entiers = new ArrayList<Integer>();
		BufferedReader read = null;
		try {
			boolean lire = true;
			while(lire) {
				read = new BufferedReader(new InputStreamReader(flux));
				int entier = 0;
				for(int i=0 ; i<Integer.SIZE/Byte.SIZE && lire ; i++) {
					int b = read.read();
					if(b == -1)
						lire = false;
					else entier = (entier << 8) + b;
				}
				entiers.add(entier);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(read != null)
					read.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return entiers;
	}

}
