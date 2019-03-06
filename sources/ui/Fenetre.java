package ui;

import io.Images;

import java.awt.Dimension;

import javax.swing.JFrame;

import ui.interfaces.ChangeEcranListener;

public class Fenetre extends JFrame implements ChangeEcranListener {
	private static final long serialVersionUID = 1L;
	private Ecran ecran;


	public Fenetre() {
		super("Huffman");
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(getRootPane());
		setIconImage(Images.getInstance().getImage("logo.png"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public Fenetre(Ecran ecran) {
		this();
		changeEcran(ecran);
		setVisible(true);
	}
	
	@Override
	public void changeEcran(Ecran nouveau) {
		if(ecran != null) {
			ecran.removeChangeEcranListener(this);
			ecran.fermer();
		}
		if(nouveau == null) {
			dispose();
		} else {
			ecran = nouveau;
			nouveau.addChangeEcranListener(this);
			setTitle(nouveau.getName());
			setContentPane(nouveau);
			validate();
			repaint();
		}
	}

}
