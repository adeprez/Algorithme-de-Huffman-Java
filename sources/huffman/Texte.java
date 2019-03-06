package huffman;

import io.Sortie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Texte implements Sortie {
	private final List<Character> caracteres;
	
	
	public Texte() {
		caracteres = new ArrayList<Character>();
	}

	@Override
	public void ecrire(int i) throws IOException {
		caracteres.add((char) i);
	}

	@Override
	public void write(byte... bytes) throws IOException {
		for(final byte b : bytes)
			caracteres.add((char) b);
	}
	
	public List<Character> getCaracteres() {
		return caracteres;
	}

}
