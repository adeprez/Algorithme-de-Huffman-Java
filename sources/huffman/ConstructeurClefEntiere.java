package huffman;

public class ConstructeurClefEntiere implements ConstructeurClef<Integer> {

	
	@Override
	public Integer getClef(Integer k1, Integer k2) {
		return k1 + k2;
	}

}
