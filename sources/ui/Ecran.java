package ui;

import java.awt.Font;

import ui.interfaces.ChangeEcranListener;
import ui.interfaces.EcranChangeable;

public class Ecran extends PanelImage implements EcranChangeable {
	protected final Font POLICE = new Font(Font.DIALOG, Font.BOLD, 14);
	private static final long serialVersionUID = 1L;
	
	
	public void addChangeEcranListener(ChangeEcranListener l) {
		listenerList.add(ChangeEcranListener.class, l);
	}

	public void removeChangeEcranListener(ChangeEcranListener l) {
		listenerList.remove(ChangeEcranListener.class, l);
	}
	
	public void fermer() {}
	
	@Override
	public Ecran changer(Ecran nouveau) {
		for(final ChangeEcranListener l : listenerList.getListeners(ChangeEcranListener.class))
			l.changeEcran(nouveau);
		return nouveau;
	}
	
}
