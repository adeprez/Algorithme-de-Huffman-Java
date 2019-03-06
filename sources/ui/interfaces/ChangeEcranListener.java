package ui.interfaces;

import java.util.EventListener;

import ui.Ecran;

public interface ChangeEcranListener extends EventListener {
	public void changeEcran(Ecran nouveau);
}
