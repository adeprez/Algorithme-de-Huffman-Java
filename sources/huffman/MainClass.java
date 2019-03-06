package huffman;

import javax.swing.UIManager;

import ui.EcranPrincipal;
import ui.Fenetre;



public class MainClass {
	public static void main(String... args) throws Exception {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {}
		new Fenetre(new EcranPrincipal());
	}
}
