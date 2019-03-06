package io;

import java.io.IOException;

public interface Sortie {
	public void ecrire(int i) throws IOException ;
	public void write(byte... bytes) throws IOException ;
}
