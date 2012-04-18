package br.com.tiagoaramos.estoque.control.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

public class ActMenuSairAplicacao implements ActionListener {
	
	private ControleEstoqueView controleEstoqueView;
	
	public ActMenuSairAplicacao(ControleEstoqueView controleEstoqueView) {
		this.controleEstoqueView = controleEstoqueView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controleEstoqueView.setVisible(false);
		ControleSessaoUtil.solicitaSenhaAcesso();
	}

}
