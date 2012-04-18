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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.ParametroModel;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.ParametroDAO;
import br.com.tiagoaramos.estoque.model.dao.UsuarioDAO;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;
import br.com.tiagoaramos.estoque.view.utils.ControleConstantes;

/**
 *
 * @author tiago
 */
public class BagCadastroSenha extends CadastroBagAb<UsuarioModel> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JTextField jtfSenha;

	
	public BagCadastroSenha() {
        initComponents();
    }

    protected void initComponents() {
    	super.initComponents(new UsuarioModel(), UsuarioDAO.getInstance());
    	setName("CadastroUsuario");
    	jtfSenha = new JTextField();
    	jtfSenha.setText(ParametroDAO.obter(ControleConstantes.SENHA_ADMIN).getValor());
    	grid.add("Senha:",jtfSenha,jbtSalvar);
    	grid.add(new JPanel());
    }

	@Override
	protected void editarModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void salvarModel() {
		ParametroModel model = ParametroDAO.obter(ControleConstantes.SENHA_ADMIN);
		model.setValor(jtfSenha.getText());
		try {
			ParametroDAO.getInstance().merge(model);
			JOptionPane.showMessageDialog(this,
					"Senha alterada com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
	}
}
