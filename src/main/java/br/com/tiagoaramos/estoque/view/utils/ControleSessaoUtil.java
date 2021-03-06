package br.com.tiagoaramos.estoque.view.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.mzsw.PesoLib;
import br.com.tiagoaramos.estoque.control.ControleEstoqueMain;
import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.CategoriaProdutoModel;
import br.com.tiagoaramos.estoque.model.EmpresaModel;
import br.com.tiagoaramos.estoque.model.FornecedorModel;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.CategoriaDAO;
import br.com.tiagoaramos.estoque.model.dao.EmpresaDAO;
import br.com.tiagoaramos.estoque.model.dao.FornecedorDAO;
import br.com.tiagoaramos.estoque.model.dao.UsuarioDAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;
import br.com.tiagoaramos.estoque.utils.enums.TipoUsuario;
import br.com.tiagoaramos.estoque.view.ControleEstoqueView;

public class ControleSessaoUtil {

	static JFrame frLogin;
	static JPanel mainPanel;
	static GridLayout grid;
	static JTextField jtfLogin;
	static JPasswordField jtfSenha;
	static JButton jtbOk;
	static JButton jtbCancel;
	public static UsuarioModel usuarioLogado;
	public static EmpresaModel empresaLogado;
	public static PesoLib balanca;

	static Thread threadAdmin;

	static {
		HibernateUtils.getEmOpen();
		UsuarioDAO.getInstance();
	}

	public static boolean solicitaSenhaAdmin(Component componete) {

		if (!usuarioLogado.getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR)) {

			jtfLogin = new JTextField();
			jtfLogin.setName("jtfLogin");
			jtfLogin.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						jtfSenha.requestFocus();
					}
				}

				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}
			});

			jtfSenha = new JPasswordField();
			jtfSenha.setName("jtfSenha");

			JPanel myPanel = new JPanel(new java.awt.GridLayout(2, 2));
			myPanel.add(new JLabel("Login:"));
			myPanel.add(jtfLogin);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Senha:"));
			myPanel.add(jtfSenha);

			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Usu�rio e senha de administrador",
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

				UsuarioModel curUser = usuarioLogado;
				if (verificaLogin()) {
					if (usuarioLogado.getTipoUsuario().equals(
							TipoUsuario.ADMINISTRADOR)) {
						usuarioLogado = curUser;
						return true;
					} else {
						JOptionPane.showMessageDialog(frLogin,
								"Usu�rio n�o administrador!");
					}
				}
			}

			return false;

		} else {
			return true;
		}
	}

	public static void solicitaSenhaAcesso() {

		frLogin = new JFrame("Login");
		frLogin.setSize(400, 300);
		frLogin.addWindowListener(new SystemExitListenner());

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		grid = new GridLayout(mainPanel);

		frLogin.getContentPane().add(mainPanel, BorderLayout.CENTER);

		List<UsuarioModel> usuarios = UsuarioDAO.getInstance().buscarTodos();
		if (usuarios.size() == 0) {

			try {
				
				UsuarioModel usuario = new UsuarioModel();
				usuario.setEmail("");
				usuario.setLogin("admin");
				usuario.setSenha("admin");
				usuario.setNome("Administrador");
				usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
				UsuarioDAO.getInstance().persiste(usuario);
				
				EmpresaModel empresa = new EmpresaModel();
				empresa.setCnpj("00.000.000/0001-00");
				empresa.setMensagem("Volte Sempre!");
				empresa.setNome("Empresa de Teste");
				empresa.setTelefone("99-9999-9999");
				EmpresaDAO.getInstance().persiste(empresa);
				
				CategoriaProdutoModel categoria = new CategoriaProdutoModel();
				categoria.setNome("Categoria base");	
				CategoriaDAO.getInstance().persiste(categoria);
				
				FornecedorModel fornecedor = new FornecedorModel();
				fornecedor.setNome("Forncedor Base");
				FornecedorDAO.getInstance().persiste(fornecedor);
				
				
				usuarios.add(usuario);
				empresaLogado = empresa;
				
				
				JOptionPane.showMessageDialog(frLogin,
						"Usu�rio padr�o cadastrado.\n Login : admin + \n Senha: admin");
			} catch (PersistenciaException e1) {
				e1.printStackTrace();
				JOptionPane
						.showMessageDialog(
								frLogin,
								"Erro ao tentar criar um usu�rio.\n"
										+ "Favor entrar em contato com o administrador do sistema.\n"
										+ "\n" + "Erro:\n" + e1.getMessage());
				ControleEstoqueMain.mysqldResource.shutdown();
				System.exit(-1);
			}
		}

		jtfLogin = new JTextField();
		jtfLogin.setName("jtfLogin");
		jtfLogin.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					jtfSenha.requestFocus();
				}
			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		jtfSenha = new JPasswordField();
		jtfSenha.setName("jtfSenha");

		jtfSenha.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (verificaLogin()) {
						frLogin.setVisible(false);
						new ControleEstoqueView();
					}
				}
			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		jtbOk = new JButton("Ok");
		jtbOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (verificaLogin()) {
					frLogin.setVisible(false);
					new ControleEstoqueView();
				}
			}
		});
		jtbCancel = new JButton("Cancelar");
		jtbCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ControleEstoqueMain.mysqldResource.shutdown();
				System.exit(0);
			}
		});

		grid.add("Login:", jtfLogin);
		grid.add("Senha:", jtfSenha);
		grid.add("", jtbOk, jtbCancel);


		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frLogin.setLocation(dim.width / 2 - frLogin.getSize().width / 2, dim.height
				/ 2 - frLogin.getSize().height / 2);
		
		frLogin.setVisible(true);
		jtfLogin.requestFocus();
	}

	private static boolean verificaLogin() {

		try {

			UsuarioModel usuario = (UsuarioModel) UsuarioDAO.getInstance()
					.buscarPorLogin(jtfLogin.getText());
			String senha = new String(jtfSenha.getPassword());

			if (usuario.getSenha().equals(senha)) {
				usuarioLogado = usuario;
				empresaLogado = EmpresaDAO.getInstance().buscarTodos().get(0);
				return true;
			} else {
				JOptionPane.showMessageDialog(mainPanel,
						"Login e/ou senha inv�lidos!");
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mainPanel,
					"Login e/ou senha inv�lidos!");

			jtfLogin.setText("");
			jtfSenha.setText("");
		}
		return false;
	}

}
