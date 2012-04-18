package br.com.tiagoaramos.estoque.utils.mascaras;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class MaskDecimal extends KeyAdapter {
	
	private static MaskDecimal maskInteiros;
	
	public static MaskDecimal getInstance(){
		if(maskInteiros == null)
			maskInteiros = new MaskDecimal();
		return maskInteiros;
	}
	
	public void keyReleased(KeyEvent e){  
         JTextComponent comp = (JTextComponent)e.getSource();
         comp.setText(comp.getText().replaceAll("[^0-9,]", ""));  
     }
	 
}
