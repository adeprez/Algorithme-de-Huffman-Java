package ui.interfaces;

import java.io.File;
import java.util.EventListener;

public interface ChoixFichierListener extends EventListener {
	public void choix(File fichier) throws Exception;
}
