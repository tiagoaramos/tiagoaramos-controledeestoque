package br.com.tiagoaramos.estoque.view.utils;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import br.com.tiagoaramos.estoque.control.ControleEstoqueMain;
import br.com.tiagoaramos.estoque.utils.LooadingBar;

public class BalancaSystemExitListenner implements WindowListener {

	
	@Override
	public void windowOpened(WindowEvent e) {
	}
	
	@Override
	public void windowIconified(WindowEvent e) {
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	@Override
	public void windowClosing(WindowEvent e) {

		ControleEstoqueMain.balanca.fecha();				
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
	}

}
