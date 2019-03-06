package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FluxFichier implements Entree, Sortie {
	private final File fichier;
	private OutputStream out;
	private InputStream in;
	
	
	public FluxFichier(File fichier) {
		this.fichier = fichier;
	}
	
	public OutputStream getOut() throws FileNotFoundException {
		if(out == null)
			out = new FileOutputStream(fichier);
		return out;
	}
	
	public InputStream getIn() throws FileNotFoundException {
		if(in == null)
			in = new FileInputStream(fichier);
		return in;
	}

	@Override
	public void ecrire(int i) throws IOException {
		getOut().write(i);
	}

	@Override
	public void write(byte... bytes) throws IOException {
		getOut().write(bytes);
	}

	@Override
	public int read(byte[] cible) throws IOException {
		return getIn().read(cible);
	}

	@Override
	public int read() throws IOException {
		return getIn().read();
	}
	
}
