package br.com.tiagoaramos.estoque.view.utils;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.WindowConstants;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
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
					"Usuário e senha de administrador",
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
								"Usuário não administrador!");
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
		frLogin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		grid = new GridLayout(mainPanel);

		frLogin.getContentPane().add(mainPanel, BorderLayout.CENTER);

		List<UsuarioModel> usuarios = UsuarioDAO.getInstance().buscarTodos();
		if (usuarios.size() == 0) {
			UsuarioModel usuario = new UsuarioModel();
			usuario.setEmail("");
			usuario.setLogin("admin");
			usuario.setSenha("admin");
			usuario.setNome("Administrador");
			usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

			try {
				UsuarioDAO.getInstance().persiste(usuario);
				usuarios.add(usuario);
				JOptionPane.showMessageDialog(frLogin,
						"Usuário padrão cadastrado.\n Login : admin + \n Senha: admin");
			} catch (PersistenciaException e1) {
				e1.printStackTrace();
				JOptionPane
						.showMessageDialog(
								frLogin,
								"Erro ao tentar criar um usuário.\n"
										+ "Favor entrar em contato com o administrador do sistema.\n"
										+ "\n" + "Erro:\n" + e1.getMessage());
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
				System.exit(0);
			}
		});

		grid.add("Login:", jtfLogin);
		grid.add("Senha:", jtfSenha);
		grid.add("", jtbOk, jtbCancel);

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
				return true;
			} else {
				JOptionPane.showMessageDialog(mainPanel,
						"Login e/ou senha inválidos!");
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mainPanel,
					"Login e/ou senha inválidos!");

			jtfLogin.setText("");
			jtfSenha.setText("");
		}
		return false;
	}

}
