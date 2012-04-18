package br.com.tiagoaramos.estoque.control.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.movimentacao.BagVendaProduto;

public class ActMenuMovimentacaoVenda implements ActionListener {
	
	private ControleEstoqueView controleEstoqueView;
	
	public ActMenuMovimentacaoVenda(ControleEstoqueView controleEstoqueView) {
		this.controleEstoqueView = controleEstoqueView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controleEstoqueView.alteraMainPanel(new BagVendaProduto());
	}

}
