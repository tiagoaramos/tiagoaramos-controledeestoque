/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroEmpresa.java
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
import br.com.tiagoaramos.estoque.model.EmpresaModel;
import br.com.tiagoaramos.estoque.model.dao.EmpresaDAO;
import br.com.tiagoaramos.estoque.utils.mascaras.MaskTelefoneFormatter;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 * 
 * @author tiago
 */
public class BagCadastroEmpresa extends CadastroBagAb<EmpresaModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JTextField jtfNomeEmpresa;
	private JTextField jtfCnpjEmpresa;
	private JTextField jtfMensagemEmpresa;
	private JTextField jtfEnderecoEmpresa;
	private JFormattedTextField jtfTelefoneEmpresa;
	private int flagMerge = 0;

	public BagCadastroEmpresa() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new EmpresaModel(), EmpresaDAO.getInstance());
		setName("CadastroEmpresa");

		
		jtfNomeEmpresa = new JTextField();
		grid.add("Nome",jtfNomeEmpresa);

		jtfCnpjEmpresa = new JTextField();
		grid.add("CNPJ:",jtfCnpjEmpresa);

		jtfMensagemEmpresa = new JTextField();
		grid.add("Mensagem:",jtfMensagemEmpresa);
		
		jtfEnderecoEmpresa = new JTextField();
		grid.add("Endereço:",jtfEnderecoEmpresa);

		jtfTelefoneEmpresa = new JFormattedTextField(new MaskTelefoneFormatter());
		grid.add("Telefone:",jtfTelefoneEmpresa);

		grid.add("Ações:", jbtSalvar, jbtEditar, jbtExcluir);
				
		jpnTabela.setBorder(new TitledBorder("Empresas"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Código", "Nome", "CNPJ","Edereço", "Mensagem", "Telefone" }) {
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
						model.setCnpj(valor);
						break;
					case 3:
						model.setEndereco(valor);
						break;
					case 4:
						model.setMensagem(valor);
						break;
					case 5:
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
			dao = EmpresaDAO.getInstance();
			lista = dao.buscarTodos();
		}

		for (EmpresaModel Empresa : lista) {
			adicionarTabela(Empresa);
		}
	}

	private void adicionarTabela(EmpresaModel Empresa) {
		tableModel.addRow(new Object[] { Empresa.getId().toString(),
				Empresa.getNome(),Empresa.getCnpj(),Empresa.getEndereco(),Empresa.getMensagem(),Empresa.getTelefone() });
	}

	protected void editarModel() {

		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			indice = l[0];
			model = lista.get(indice);
			jtfNomeEmpresa.setText(model.getNome());
			jtfMensagemEmpresa.setText(model.getMensagem());
			jtfCnpjEmpresa.setText(model.getCnpj());
			jtfEnderecoEmpresa.setText(model.getEndereco());
			jtfTelefoneEmpresa.setText(model.getTelefone());
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para editar!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void salvarModel() {
		
		model.setNome(jtfNomeEmpresa.getText());
		model.setCnpj(jtfCnpjEmpresa.getText());
		model.setMensagem(jtfMensagemEmpresa.getText());
		model.setTelefone(jtfTelefoneEmpresa.getText());
		model.setEndereco(jtfEnderecoEmpresa.getText());
		
		try {
			if (model.getId() != null && model.getId().intValue() > 0) {

				dao.merge(model);
				flagMerge = 1;
				tableModel.setValueAt(model.getNome(), indice, 1);
				tableModel.setValueAt(model.getCnpj(), indice, 2);
				tableModel.setValueAt(model.getEndereco(), indice, 3);
				tableModel.setValueAt(model.getMensagem(), indice, 4);
				tableModel.setValueAt(model.getTelefone(), indice, 5);
				flagMerge = 0;
				JOptionPane.showMessageDialog(this,
						"Empresa atualizada com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				dao.persiste(model);
				lista.add(model);
				adicionarTabela(model);
				JOptionPane.showMessageDialog(this,
						"Empresa salva com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			}
			limparCampos();
		} catch (PersistenciaException ex) {
			Logger.getLogger(BagCadastroEmpresa.class.getName()).log(
					Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,
					"Impossível gravar a Empresa", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparCampos() {
		indice = -1;
		jtfNomeEmpresa.setText("");
		jtfMensagemEmpresa.setText("");
		jtfCnpjEmpresa.setText("");
		jtfTelefoneEmpresa.setText("");
		model = new EmpresaModel();
	}
}
