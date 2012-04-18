/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroCategoria.java
 *
 * Created on 08/10/2009, 21:27:57
 */

package br.com.tiagoaramos.estoque.view.cadastro;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.UsuarioDAO;
import br.com.tiagoaramos.estoque.utils.enums.TipoUsuario;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 *
 * @author tiago
 */
public class BagCadastroUsuario extends CadastroBagAb<UsuarioModel> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JTextField jtfIdentificadorUsuario;
	private JTextField jtfEmailUsuario;
	private JTextField jtfLoginUsuario;
	private JTextField jtfNomeUsuario;
	private JPasswordField jtfSenhaUsuario;
	private JComboBox cmbTipoUsuario;
	
	
	public BagCadastroUsuario() {
        initComponents();
    }

    protected void initComponents() {

		super.initComponents(new UsuarioModel(), UsuarioDAO.getInstance());
    	
    	setName("CadastroUsuario");
    	
    	jtfIdentificadorUsuario = new JTextField();
    	jtfIdentificadorUsuario.setEditable(false);
    	jtfIdentificadorUsuario.setName("jtfIdentificadorUsuario");
		grid.add("Código:",jtfIdentificadorUsuario);

		jtfLoginUsuario = new JTextField();
		jtfLoginUsuario.setName("jtfLoginUsuario");
		grid.add("Login:",jtfLoginUsuario);
		
		jtfEmailUsuario = new JTextField();
		jtfEmailUsuario.setName("jtfEmailUsuario");
		grid.add("Email:",jtfEmailUsuario);
    	    	
		jtfNomeUsuario = new JTextField();
		jtfNomeUsuario.setName("jtfNomeUsuario");
		grid.add("Nome:",jtfNomeUsuario);
    	
		jtfSenhaUsuario = new JPasswordField();
		jtfSenhaUsuario.setName("jtfSenhaUsuario");
		grid.add("Senha:",jtfSenhaUsuario);
    	
		cmbTipoUsuario = new JComboBox();
		cmbTipoUsuario.setName("cmbTipoUsuario");
		cmbTipoUsuario.addItem("selecione");
		for (TipoUsuario tipo : TipoUsuario.values()) {
			cmbTipoUsuario.addItem(tipo);	
		}
		grid.add("Tipo de usuário:",cmbTipoUsuario);

		grid.add("Ação:",jbtSalvar,jbtEditar,jbtExcluir);
		
		
		/** Fim do formulário **/
		
		jpnTabela.setBorder(new TitledBorder("Usuários"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Código", "Login", "Email", "Nome", "Tipo de usuário" }) {
			private static final long serialVersionUID = 5622980448697494420L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		jtbTabela.setModel(tableModel);
		jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbTabela.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtbTabela.getColumnModel().getColumn(0).setResizable(false);
		jtbTabela.getColumnModel().getColumn(1).setPreferredWidth(50);
		jtbTabela.getColumnModel().getColumn(1).setResizable(true);
		jtbTabela.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(2).setResizable(true);
		jtbTabela.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(3).setResizable(true);
		jtbTabela.getColumnModel().getColumn(4).setPreferredWidth(50);
		jtbTabela.getColumnModel().getColumn(4).setResizable(true);
		preencherTabela();
		
		grid.add(jspContainer);
    	
    }

	private void preencherTabela() {
		if (lista == null) {
			dao = UsuarioDAO.getInstance();
			lista = dao.buscarTodos();
		}

		for (UsuarioModel usuario : lista) {
			adicionarTabela(usuario);
		}
	}
	
	private void adicionarTabela(UsuarioModel usuario) {
		tableModel.addRow(new Object[] { usuario.getId().toString(),
				usuario.getLogin(), usuario.getEmail(), usuario.getNome(), usuario.getTipoUsuario()
		});
	}
	
    
	@Override
	protected void editarModel() {
		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			indice = l[0];
			model = lista.get(indice);
			
			jtfIdentificadorUsuario.setText(model.getId().toString());
			jtfEmailUsuario.setText(model.getEmail());
			jtfLoginUsuario.setText(model.getLogin());
			jtfNomeUsuario.setText(model.getNome());
			jtfSenhaUsuario.setText(model.getSenha());
			
			for(int i = 0 ; i < cmbTipoUsuario.getModel().getSize(); i++){
				if(cmbTipoUsuario.getModel().getElementAt(i).equals(model.getTipoUsuario())){
					cmbTipoUsuario.setSelectedIndex(i);
					break;
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para editar!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	@Override
	protected void salvarModel() {
		model.setNome(jtfNomeUsuario.getText());
		
		if(!jtfIdentificadorUsuario.getText().equals(""))
			model.setId(new Integer(jtfIdentificadorUsuario.getText()));
		else
			model.setId(null);
		
		model.setEmail(jtfEmailUsuario.getText());
		model.setLogin(jtfLoginUsuario.getText());
		model.setSenha(new String(jtfSenhaUsuario.getPassword()));
		model.setTipoUsuario((TipoUsuario) cmbTipoUsuario.getSelectedItem());
		
		try {
			if (model.getId() != null && model.getId().intValue() > 0) {

				dao.merge(model);
				int i = 1;
				tableModel.setValueAt(model.getLogin(), indice, i++);
				tableModel.setValueAt(model.getEmail(),  indice, i++);
				tableModel.setValueAt(model.getNome(),  indice, i++);
				tableModel.setValueAt(model.getTipoUsuario(), indice, i++);
				JOptionPane.showMessageDialog(this,
						"Usuário atualizado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				dao.persiste(model);
				lista.add(model);
				adicionarTabela(model);
				JOptionPane.showMessageDialog(this,
						"Usuário salvo com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			}
			limparCampos();
		} catch (PersistenciaException ex) {
			Logger.getLogger(BagCadastroUsuario.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,"Impossível gravar o usuário", "Erro",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void limparCampos() {
		indice = -1;
		jtfIdentificadorUsuario.setText("");
		jtfEmailUsuario.setText("");
		jtfLoginUsuario.setText("");
		jtfNomeUsuario.setText("");
		jtfSenhaUsuario.setText("");
		model = new UsuarioModel();
	}
	
}
