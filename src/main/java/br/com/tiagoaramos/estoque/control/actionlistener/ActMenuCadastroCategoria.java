package br.com.tiagoaramos.estoque.control.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.cadastro.BagCadastroCategoria;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

public class ActMenuCadastroCategoria implements ActionListener {

	private ControleEstoqueView controleEstoqueView;

	public ActMenuCadastroCategoria(ControleEstoqueView controleEstoqueView) {
		this.controleEstoqueView = controleEstoqueView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ControleSessaoUtil.solicitaSenha(controleEstoqueView))
			controleEstoqueView.alteraMainPanel(new BagCadastroCategoria());
	}

}
