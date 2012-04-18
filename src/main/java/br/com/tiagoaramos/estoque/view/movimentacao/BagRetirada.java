/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroFornecedor.java
 *
 * Created on 08/10/2009, 21:27:57
 */

package br.com.tiagoaramos.estoque.view.movimentacao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.model.RetiradaModel;
import br.com.tiagoaramos.estoque.model.dao.RetiradaDAO;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 * 
 * @author tiago
 */
public class BagRetirada extends CadastroBagAb<RetiradaModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JFormattedTextField jtfValor;
	private JTextField jtfDescricao;

	public BagRetirada() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new RetiradaModel(), RetiradaDAO.getInstance());

		jtfValor = new JFormattedTextField(new DecimalFormat("#.00"));
		grid.add("Valor:",jtfValor);
		jtfDescricao = new JTextField();
		grid.add("Descrição:",jtfDescricao);
		
		/* fim do formulário */
		jpnTabela.setBorder(new TitledBorder("Produtos"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Valor", "Descrição"}) {
			private static final long serialVersionUID = 5622980448697494420L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		grid.add("Ação:",jbtSalvar,jbtExcluir);
		jtbTabela.setModel(tableModel);
		jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbTabela.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(0).setResizable(true);
		jtbTabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtbTabela.getColumnModel().getColumn(1).setResizable(true);
		grid.add(jspContainer);
		
		preencherTabela();
		

	}

	private void adicionarTabela(RetiradaModel compraProduto) {
		tableModel.addRow(new Object[] { compraProduto.getValor().toString(), compraProduto.getDescricao() });
	}
	
	private void preencherTabela() {
		if (lista == null) {
			dao = RetiradaDAO.getInstance();
			lista = dao.buscarTodos();
		}

		for (RetiradaModel retirada : lista) {
			adicionarTabela(retirada);
		}
	}
	
	protected void editarModel() {
	}

	protected void salvarModel() {

		try {
			
			model.setData(new Date());
			model.setDescricao(jtfDescricao.getText());
			model.setValor(new BigDecimal(jtfValor.getText().replaceAll("[,]", ".")));

			dao.persiste(model);
			lista.add(model);
			adicionarTabela(model);

			limparCampos();

		} catch (Exception ex) {
			Logger.getLogger(BagRetirada.class.getName()).log(Level.SEVERE,
					null, ex);
			JOptionPane.showMessageDialog(this, "Impossível fazer retirada",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparCampos() {
		indice = -1;
		model = new RetiradaModel();
		jtfDescricao.setText("");
		jtfValor.setText("0,00");
	}

}
