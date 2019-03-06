package io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Flux {
	private final List<Boolean> bits;


	public Flux() {
		bits = new ArrayList<Boolean>();
	}
	
	public Flux(Entree in) throws IOException {
		this();
		lire(in);
	}

	public List<Boolean> getBits() {
		return bits;
	}

	public void lire(Entree in) throws IOException {
		byte[] bt = new byte[Integer.SIZE/Byte.SIZE];
		if(in.read(bt) != bt.length)
			throw new IOException("Pas assez d'elements a lire");
		int taille = ByteBuffer.wrap(bt).getInt();
		bits.clear();
		int octet = 0, lus = Byte.SIZE;
		for(int i=0 ; i<taille ; i++, lus++) {
			if(lus == Byte.SIZE) {
				octet = in.read();
				lus = 0;
			}
			if(octet == -1)
				throw new IOException("Plus d'octet disponible");
			int decalage = Byte.SIZE - lus - 1;
			int a = taille - i - decalage - 1;
			if(a < 0)
				decalage += a;
			bits.add(((octet >> decalage) & 00000001) == 1);
		}
	}

	public void ecrire(Sortie out) throws IOException {
		out.write(ByteBuffer.allocate(Integer.SIZE/Byte.SIZE).putInt(bits.size()).array());
		int i = 0;
		while(i<bits.size()) {
			byte n = 0;
			for(int ib=0 ; ib<Byte.SIZE && i<bits.size() ; ib++, i++)
				n = (byte) ((n << 1) + (bits.get(i) ? 1 : 0));
			out.write(n);
		}
	}

	@Override
	public String toString() {
		String s = "";
		for(final Boolean b : bits)
			s += b ? "1" : "0";
		return s;
	}

}
