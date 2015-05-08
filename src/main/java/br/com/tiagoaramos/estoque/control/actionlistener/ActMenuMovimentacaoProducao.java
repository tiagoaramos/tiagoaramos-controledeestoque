package br.com.tiagoaramos.estoque.control.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.movimentacao.BagEntradaProduto;
import br.com.tiagoaramos.estoque.view.movimentacao.BagProducaoProduto;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

public class ActMenuMovimentacaoProducao implements ActionListener {
	
	private ControleEstoqueView controleEstoqueView;

	public ActMenuMovimentacaoProducao(ControleEstoqueView controleEstoqueView) {
		this.controleEstoqueView = controleEstoqueView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(ControleSessaoUtil.solicitaSenhaAdmin(controleEstoqueView))				
			controleEstoqueView.alteraMainPanel(new BagProducaoProduto());
	}

}
