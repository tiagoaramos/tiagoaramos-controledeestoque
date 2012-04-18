package br.com.tiagoaramos.estoque.utils.mascaras;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class MaskInteiros extends KeyAdapter {
	
	private static MaskInteiros maskInteiros;
	
	public static MaskInteiros getInstance(){
		if(maskInteiros == null)
			maskInteiros = new MaskInteiros();
		return maskInteiros;
	}
	
	public void keyReleased(KeyEvent e){  
         JTextComponent comp = (JTextComponent)e.getSource();
         comp.setText(comp.getText().replaceAll("[^0-9]", ""));  
     }
		 
}
