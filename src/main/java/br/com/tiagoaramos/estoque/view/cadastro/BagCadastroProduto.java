/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroFornecedor.java
 *
 * Created on 08/10/2009, 21:27:57
 */

package br.com.tiagoaramos.estoque.view.cadastro;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.CategoriaProdutoModel;
import br.com.tiagoaramos.estoque.model.FornecedorModel;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.CategoriaDAO;
import br.com.tiagoaramos.estoque.model.dao.FornecedorDAO;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.utils.mascaras.MaskDecimal;
import br.com.tiagoaramos.estoque.utils.mascaras.MaskInteiros;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 * 
 * @author tiago
 */
public class BagCadastroProduto extends CadastroBagAb<ProdutoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JTextField jtfNomeProduto;
	private JTextField jtfIdentificadorProduto;
	private JFormattedTextField jtfPrecoProduto;
	private JFormattedTextField jtfPrecoVendaProduto;
	private JFormattedTextField jtfEstoqueProduto;
	private JComboBox cmbCategoria;
	private JComboBox cmbFornecedor;
	
	private int flagMerge = 0;

	public BagCadastroProduto() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new ProdutoModel(), ProdutoDAO.getInstance());
		
		setName("CadastroProduto");

		jtfIdentificadorProduto = new JTextField();
		jtfIdentificadorProduto.setName("jtfIdentificadorProduto");
		grid.add("Código:",jtfIdentificadorProduto);
		
		jtfNomeProduto = new JTextField();
		jtfNomeProduto.setName("jtfNomeProduto");
		grid.add("Nome:",jtfNomeProduto);

		jtfPrecoProduto = new JFormattedTextField(new DecimalFormat("#.00"));
		jtfPrecoProduto.addKeyListener(MaskDecimal.getInstance());  
		grid.add("Preço:",jtfPrecoProduto);
		
		jtfPrecoVendaProduto = new JFormattedTextField(new DecimalFormat("#.00"));
		jtfPrecoVendaProduto.addKeyListener(MaskDecimal.getInstance()); 
		grid.add("Preço Venda",jtfPrecoVendaProduto);

		jtfEstoqueProduto = new JFormattedTextField(NumberFormat.getIntegerInstance());
		jtfEstoqueProduto.addKeyListener(MaskInteiros.getInstance()); 
		grid.add("Estoque:",jtfEstoqueProduto);
		
		cmbCategoria = new JComboBox();
		cmbCategoria.addItem("selecione");
		for (CategoriaProdutoModel categoria : CategoriaDAO.getInstance().buscarTodos()) {
			cmbCategoria.addItem(categoria);	
		}
		grid.add("Categoria:",cmbCategoria);

		cmbFornecedor = new JComboBox();
		cmbFornecedor.addItem("selecione");
		for (FornecedorModel fornecedor : FornecedorDAO.getInstance().buscarTodos()) {
			cmbFornecedor.addItem(fornecedor);	
		}
		grid.add("Fornecedor:",cmbFornecedor);	
		
		
		grid.add("Ação:",jbtSalvar,jbtEditar,jbtExcluir);
		
		/* fim do formulário */
		jpnTabela.setBorder(new TitledBorder("Produtos"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Código", "Nome", "Preço", "Preço venda", "Quantidade","Categoria","Fornecedor" }) {
			private static final long serialVersionUID = 5622980448697494420L;
			public boolean isCellEditable(int row, int col) {
				if (col == 0 || col == 4 || col == 5 || col == 6)
					return false;
				else
					return true;
			}
		};
		tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE && flagMerge == 0) {
					String valor = (String) tableModel.getValueAt(e.getFirstRow(), e.getColumn());
					model = lista.get(e.getFirstRow());
					BigDecimal big;
					switch (e.getColumn()) {
						case 1:
							model.setNome(valor);
							break;
						case 2:
							try{
								big = new BigDecimal(valor);
								model.setPreco(big);
							}catch (Exception e2) {
								tableModel.setValueAt(model.getPreco().toString(),e.getFirstRow(), e.getColumn());
								JOptionPane.showMessageDialog((Component) e.getSource(),
										"Digite apenas números!", "Erro",
										JOptionPane.ERROR_MESSAGE);
							}
							break;
						case 3:
							try{
								big = new BigDecimal(valor);
								model.setPrecoVenda(new BigDecimal(valor));
							}catch (Exception e2) {
								tableModel.setValueAt(model.getPrecoVenda().toString(),e.getFirstRow(), e.getColumn());
								JOptionPane.showMessageDialog((Component) e.getSource(),
										"Digite apenas números!", "Erro",
										JOptionPane.ERROR_MESSAGE);
							}
							break;
					}

					try {
						dao.merge(model);
						limparCampos();
					} catch (PersistenciaException e1) {
						e1.printStackTrace();
					}

				}
			}

		});
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
		preencherTabela();
		
		grid.add(jspContainer);
	
	}

	private void preencherTabela() {
		if (lista == null) {
			dao = ProdutoDAO.getInstance();
			lista = dao.buscarTodos();
		}

		for (ProdutoModel produto : lista) {
			adicionarTabela(produto);
		}
	}

	private void adicionarTabela(ProdutoModel produto) {
		tableModel.addRow(new Object[] { produto.getId().toString(),
				produto.getNome(),produto.getPreco().toString(),produto.getPrecoVenda().toString(),produto.getEstoqueAtual().toString(),produto.getCategoria(),produto.getFornecedor() });
	}

	protected void editarModel() {

		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			indice = l[0];
			model = lista.get(indice);
			jtfIdentificadorProduto.setText(model.getIdentificador());
			jtfNomeProduto.setText(model.getNome());
			jtfPrecoVendaProduto.setText(model.getPrecoVenda().toString());
			jtfPrecoProduto.setText(model.getPreco().toString());
			jtfEstoqueProduto.setText(model.getEstoqueAtual().toString());
			jtfEstoqueProduto.setEnabled(false);
			
			for(int i = 0 ; i < cmbCategoria.getModel().getSize(); i++){
				if(cmbCategoria.getModel().getElementAt(i).equals(model.getCategoria())){
					cmbCategoria.setSelectedIndex(i);
					break;
				}
			}
			
			for(int i = 0 ; i < cmbFornecedor.getModel().getSize(); i++){
				if(cmbFornecedor.getModel().getElementAt(i).equals(model.getFornecedor())){
					cmbFornecedor.setSelectedIndex(i);
					break;
				}
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para editar!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void salvarModel() {
		
		model.setNome(jtfNomeProduto.getText());
		model.setIdentificador(jtfIdentificadorProduto.getText());
		model.setPreco(new BigDecimal(jtfPrecoProduto.getText().replaceAll(",", ".")));
		model.setPrecoVenda(new BigDecimal(jtfPrecoVendaProduto.getText().replaceAll(",", ".")));
		model.setEstoqueAtual(new Integer(jtfEstoqueProduto.getText()));
		model.setCategoria((CategoriaProdutoModel) cmbCategoria.getSelectedItem());
		model.setFornecedor((FornecedorModel) cmbFornecedor.getSelectedItem());
		
		try {
			if (model.getId() != null && model.getId().intValue() > 0) {

				dao.merge(model);
				flagMerge = 1;
				tableModel.setValueAt(model.getNome(), indice, 1);
				tableModel.setValueAt(model.getPreco().toString(), indice, 2);
				tableModel.setValueAt(model.getPrecoVenda().toString(), indice, 3);
				tableModel.setValueAt(model.getEstoqueAtual().toString(), indice, 4);
				tableModel.setValueAt(model.getCategoria().toString(), indice, 5);
				tableModel.setValueAt(model.getFornecedor().toString(), indice, 6);
				flagMerge = 0;
				JOptionPane.showMessageDialog(this,
						"Produto atualizado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				dao.persiste(model);
				lista.add(model);
				adicionarTabela(model);
				JOptionPane.showMessageDialog(this,
						"Produto salvo com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			}
			limparCampos();
		} catch (PersistenciaException ex) {
			Logger.getLogger(BagCadastroProduto.class.getName()).log(
					Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,
					"Impossível gravar o produto", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparCampos() {
		indice = -1;
		jtfNomeProduto.setText("");
		jtfPrecoVendaProduto.setText("");
		jtfIdentificadorProduto.setText("");
		jtfPrecoProduto.setText("");
		jtfEstoqueProduto.setText("");
		cmbCategoria.setSelectedIndex(0);
		cmbFornecedor.setSelectedIndex(0);
		jtfEstoqueProduto.setEnabled(true);
		model = new ProdutoModel();
	}
}
