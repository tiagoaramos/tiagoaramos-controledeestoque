package br.com.tiagoaramos.estoque.control.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.relatorio.BagFechamentoDiario;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

public class ActMenuRelatorio implements ActionListener {

	private ControleEstoqueView controleEstoqueView;

	public ActMenuRelatorio(ControleEstoqueView controleEstoqueView) {
		this.controleEstoqueView = controleEstoqueView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ControleSessaoUtil.solicitaSenhaAdmin(controleEstoqueView))
			controleEstoqueView.alteraMainPanel(new BagFechamentoDiario());
	}

}
