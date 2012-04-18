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

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.FornecedorModel;
import br.com.tiagoaramos.estoque.model.dao.FornecedorDAO;
import br.com.tiagoaramos.estoque.utils.mascaras.MaskTelefoneFormatter;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 * 
 * @author tiago
 */
public class BagCadastroFornecedor extends CadastroBagAb<FornecedorModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JTextField jtfNomeFornecedor;
	private JTextField jtfEmailFornecedor;
	private JTextField jtfSiteFornecedor;
	private JFormattedTextField jtfTelefoneFornecedor;
	private int flagMerge = 0;

	public BagCadastroFornecedor() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new FornecedorModel(), FornecedorDAO.getInstance());
		setName("CadastroFornecedor");

		
		jtfNomeFornecedor = new JTextField();
		grid.add("Nome",jtfNomeFornecedor);

		jtfEmailFornecedor = new JTextField();
		grid.add("Email:",jtfEmailFornecedor);

		jtfSiteFornecedor = new JTextField();
		grid.add("Site:",jtfSiteFornecedor);

		jtfTelefoneFornecedor = new JFormattedTextField(new MaskTelefoneFormatter());
		grid.add("Telefone:",jtfTelefoneFornecedor);

		grid.add("Ações:", jbtSalvar, jbtEditar, jbtExcluir);
				
		jpnTabela.setBorder(new TitledBorder("Fornecedors"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Código", "Nome", "Email", "Site", "Telefone" }) {
			private static final long serialVersionUID = 5622980448697494420L;

			public boolean isCellEditable(int row, int col) {
				if (col == 0)
					return false;
				else
					return true;
			}
		};
		tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE && flagMerge == 0) {

					String valor = (String) tableModel.getValueAt(e
							.getFirstRow(), e.getColumn());
					model = lista.get(e.getFirstRow());

					switch (e.getColumn()) {
					case 1:
						model.setNome(valor);
						break;
					case 2:
						model.setEmail(valor);
						break;
					case 3:
						model.setSite(valor);
						break;
					case 4:
						model.setTelefone(valor);
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
		jtbTabela.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(2).setResizable(true);
		jtbTabela.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(3).setResizable(true);
		preencherTabela();

		grid.add(jspContainer);
	}

	private void preencherTabela() {
		if (lista == null) {
			dao = FornecedorDAO.getInstance();
			lista = dao.buscarTodos();
		}

		for (FornecedorModel fornecedor : lista) {
			adicionarTabela(fornecedor);
		}
	}

	private void adicionarTabela(FornecedorModel fornecedor) {
		tableModel.addRow(new Object[] { fornecedor.getId().toString(),
				fornecedor.getNome(),fornecedor.getEmail(),fornecedor.getSite(),fornecedor.getTelefone() });
	}

	protected void editarModel() {

		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			indice = l[0];
			model = lista.get(indice);
			jtfNomeFornecedor.setText(model.getNome());
			jtfSiteFornecedor.setText(model.getSite());
			jtfEmailFornecedor.setText(model.getEmail());
			jtfTelefoneFornecedor.setText(model.getTelefone());			
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para editar!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void salvarModel() {
		
		model.setNome(jtfNomeFornecedor.getText());
		model.setEmail(jtfEmailFornecedor.getText());
		model.setSite(jtfSiteFornecedor.getText());
		model.setTelefone(jtfTelefoneFornecedor.getText());
		
		try {
			if (model.getId() != null && model.getId().intValue() > 0) {

				dao.merge(model);
				flagMerge = 1;
				tableModel.setValueAt(model.getNome(), indice, 1);
				tableModel.setValueAt(model.getEmail(), indice, 2);
				tableModel.setValueAt(model.getSite(), indice, 3);
				tableModel.setValueAt(model.getTelefone(), indice, 4);
				flagMerge = 0;
				JOptionPane.showMessageDialog(this,
						"Fornecedor atualizada com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				dao.persiste(model);
				lista.add(model);
				adicionarTabela(model);
				JOptionPane.showMessageDialog(this,
						"Fornecedor salva com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			}
			limparCampos();
		} catch (PersistenciaException ex) {
			Logger.getLogger(BagCadastroFornecedor.class.getName()).log(
					Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,
					"Impossível gravar a fornecedor", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparCampos() {
		indice = -1;
		jtfNomeFornecedor.setText("");
		jtfSiteFornecedor.setText("");
		jtfEmailFornecedor.setText("");
		jtfTelefoneFornecedor.setText("");
		model = new FornecedorModel();
	}
}
