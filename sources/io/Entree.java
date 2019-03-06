package io;

import java.io.IOException;

public interface Entree {
	public int read(byte[] cible) throws IOException ;
	public int read() throws IOException ;
}
