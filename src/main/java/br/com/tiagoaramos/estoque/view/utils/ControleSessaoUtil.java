package br.com.tiagoaramos.estoque.view.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.ParametroDAO;
import br.com.tiagoaramos.estoque.model.dao.UsuarioDAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;
import br.com.tiagoaramos.estoque.utils.enums.TipoUsuario;
import br.com.tiagoaramos.estoque.view.ControleEstoqueView;

public class ControleSessaoUtil {

	static JFrame frLogin;
	static JPanel mainPanel;
	static GridLayout grid;
	static JComboBox jcbLogin;
	static JPasswordField jtfSenha;
	static JButton jtbOk;
	static JButton jtbCancel;
	public static UsuarioModel usuarioLogado;
	
	static {
		HibernateUtils.getEmOpen();
		UsuarioDAO.getInstance();
	}

	public static boolean solicitaSenha(Component componete) {

		while (true) {
			String senhaCerta = ParametroDAO.obter(ControleConstantes.SENHA_ADMIN).getValor();
			if(senhaCerta != null && senhaCerta.isEmpty()){
				String senha = JOptionPane.showInputDialog("Digite a senha do administrador!");
	
				if (senha.equals(senhaCerta))
					return true;
	
				int res = JOptionPane.showConfirmDialog(componete,"Senha incorreta! \n Deseja tentar novamente?");
				if ( res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION )
					return false;
			}else{
				return true;
			}

		}
	}

	public static void solicitaSenhaAcesso() {
		
		
		
			frLogin = new JFrame("Login");
			frLogin.setSize(400, 300);
			frLogin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
			mainPanel = new JPanel();
			mainPanel.setLayout(null);
			grid = new GridLayout(mainPanel);
	
			frLogin.getContentPane().add(mainPanel, BorderLayout.CENTER);
	
			List<UsuarioModel> usuarios = UsuarioDAO.getInstance().buscarTodos();
			if(usuarios.size() == 0){
				UsuarioModel usuario = new UsuarioModel();
				usuario.setEmail("");
				usuario.setLogin("loja1");
				usuario.setSenha("loja1");
				usuario.setNome("Loja 1");
				usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
				
				try {
					UsuarioDAO.getInstance().persiste(usuario);
					usuarios.add(usuario);
					JOptionPane.showMessageDialog(frLogin, "Usuário padrão cadastrado.\n " +
															"Senha: loja1");
				} catch (PersistenciaException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frLogin, "Erro ao tentar criar um usuário.\n" +
														   "Favor entrar em contato com o administrador do sistema.\n" +
														   "\n" +
														   "Erro:\n" +
														   e1.getMessage());
					System.exit(-1);
				}
			}
			
			
			jcbLogin = new JComboBox();
			jcbLogin.setName("jtfLogin");
			jcbLogin.removeAll();
			for (UsuarioModel usuarioModel : usuarios) {
				jcbLogin.addItem(usuarioModel);
			}
			
	
			jtfSenha = new JPasswordField();
			jtfSenha.setName("jtfSenha");
			
			jtfSenha.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() ==  KeyEvent.VK_ENTER ){
						if(verificaLogin()){
							frLogin.setVisible(false);
							new ControleEstoqueView();
						}
					}
				}
				public void keyTyped(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {}
			});
	
			jtbOk = new JButton("Ok");
			jtbOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(verificaLogin()){
						frLogin.setVisible(false);
						new ControleEstoqueView();
					}
				}
			});
			jtbCancel = new JButton("Cancelar");
			jtbCancel.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
	
			grid.add("Login:", jcbLogin);
			grid.add("Senha:", jtfSenha);
			grid.add("", jtbOk, jtbCancel);
			
			frLogin.setVisible(true);
			jtfSenha.requestFocus();
			
		
	}
	
	private static boolean verificaLogin(){
		
		UsuarioModel usuario = (UsuarioModel) jcbLogin.getSelectedItem(); 
		String senha = new String(jtfSenha.getPassword());

		try {
			if (usuario.getSenha().equals(senha)) {
				usuarioLogado = usuario;
				return true;
			} else {
				JOptionPane.showMessageDialog(mainPanel,
						"Login e/ou senha inválidos!");
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mainPanel,
					"Login e/ou senha inválidos!");
		}
		return false;
	}

}
