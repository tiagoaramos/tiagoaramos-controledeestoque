/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroFornecedor.java
 *
 * Created on 08/10/2009, 21:27:57
 */

package br.com.tiagoaramos.estoque.view.utils;

import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 * 
 * @author tiago
 */
public class BagPesquisarProduto extends CadastroBagAb<ProdutoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;


	private JTextField jtfNomeProduto;
	
	private JTextField codigo;
	
	public BagPesquisarProduto(JTextField codigo) {
		this.codigo = codigo; 
		initComponents();
	}

	protected void initComponents() {
		super.initComponents(new ProdutoModel(), ProdutoDAO.getInstance());
		setName("CadastroProduto");

		jtfNomeProduto = new JTextField();
		jtfNomeProduto.setName("jtfNomeProduto");
		jbtSalvar.setText("Pesquisar");
		jbtEditar.setText("Selecionar");
		grid.add("Nome:",jtfNomeProduto,jbtSalvar,jbtEditar);

		/* fim do formulário */
		jpnTabela.setBorder(new TitledBorder("Produtos"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Código", "Nome", "Preço", "Preço venda", "Quantidade","Categoria","Fornecedor" }) {
			private static final long serialVersionUID = 5622980448697494420L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		jtbTabela.setModel(tableModel);
		jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbTabela.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtbTabela.getColumnModel().getColumn(0).setResizable(false);
		jtbTabela.getColumnModel().getColumn(1).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(1).setResizable(true);
		jtbTabela.getColumnModel().getColumn(2).setPreferredWidth(100);
		jtbTabela.getColumnModel().getColumn(2).setResizable(true);
		jtbTabela.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtbTabela.getColumnModel().getColumn(3).setResizable(true);
		jtbTabela.getColumnModel().getColumn(4).setPreferredWidth(50);
		jtbTabela.getColumnModel().getColumn(4).setResizable(true);
		jtbTabela.getColumnModel().getColumn(5).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(5).setResizable(true);
		jtbTabela.getColumnModel().getColumn(6).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(6).setResizable(true);
		
		grid.add(jspContainer);

	}

	private void adicionarTabela(ProdutoModel produto) {
		tableModel.addRow(new Object[] { produto.getId().toString(),
				produto.getNome(),produto.getPreco().toString(),produto.getPrecoVenda().toString(),produto.getEstoqueAtual().toString(),produto.getCategoria(),produto.getFornecedor() });
	}

	// selecionar um registro
	protected void editarModel() {

 		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			indice = l[0];
			model = lista.get(indice);
			codigo.setText(model.getIdentificador());
			codigo.requestFocus();
			codigo.getKeyListeners()[0].keyReleased(new KeyEvent(codigo,KeyEvent.KEY_RELEASED,new Date().getTime(),0,KeyEvent.VK_ENTER,KeyEvent.CHAR_UNDEFINED));
			this.getParent().getParent().getParent().getParent().setVisible(false);
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// pesquisar
	protected void salvarModel() {
		if(dao == null)
			dao = ProdutoDAO.getInstance();
		
		model.setNome(jtfNomeProduto.getText());
		lista = ((ProdutoDAO)dao).pesquisar(model);

		for(int i = tableModel.getRowCount() - 1 ; i >= 0 ; i--){
			tableModel.removeRow(i);
		}		
		for (ProdutoModel produto : lista) {
			adicionarTabela(produto);
		}
	}
}
