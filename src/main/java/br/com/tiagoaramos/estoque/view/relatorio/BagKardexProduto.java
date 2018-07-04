/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroFornecedor.java
 *
 * Created on 08/10/2009, 21:27:57
 */

package br.com.tiagoaramos.estoque.view.relatorio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.model.EntradaModel;
import br.com.tiagoaramos.estoque.model.EntradaProdutoModel;
import br.com.tiagoaramos.estoque.model.MovimetaProdutoIf;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.VendaModel;
import br.com.tiagoaramos.estoque.model.VendaProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.EntradaProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.SaidaProdutoDAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;
import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.utils.BagPesquisarProduto;

/**
 * 
 * @author tiago
 */
public class BagKardexProduto extends CadastroBagAb<MovimetaProdutoIf> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	// Paineis
	JPanel panelEsquerda;
	GridLayout gridPanelEsquerdo;
	JPanel panelDireita;
	GridLayout gridPanelDireita;

	private JTextField jtfCodigoProduto;
	private JButton jbtPesquisar;
	private JButton jbtGerar;
	private Border bordaPadrao;

	// dados do produto
	private JLabel lbNomeProduto;
	private JLabel lbVlNomeProduto;
	private JLabel lbEstoque;
	private JLabel lbVlEstoque;
	private JLabel lbPrecoCompra;
	private JLabel lbVlPrecoCompra;

	private ProdutoDAO produtoDAO;

	private VendaModel saida;
	private SaidaProdutoDAO saidaDAO;

	private EntradaModel entrada;
	private EntradaProdutoDAO entradaDAO;

	private ProdutoModel produto;
	private String codigoProduto;

	public BagKardexProduto() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(null,null);

		setName("Kardex de Produto");

		panelEsquerda = new JPanel();
		panelDireita = new JPanel();
		panelDireita.setBackground(Color.WHITE);
		panelDireita.setBorder(new LineBorder(Color.BLACK, 3));
		gridPanelEsquerdo = new GridLayout(panelEsquerda);
		gridPanelDireita = new GridLayout(panelDireita);

		// Tipo saida
		linha0();

		// Produto
		linha1();

		// Quantidade // Preço // Informações
		linha2();

		// Ações
		linha3();

		montaPanelDireito();

		grid.add(panelEsquerda, panelDireita, 3);

		// Grid
		linha4();

	}

	private void linha0() {
	}

	private void linha1() {

		// código do produto
		jtfCodigoProduto = new JTextField();
		jtfCodigoProduto.setName("jtfCodigoProduto");
		jtfCodigoProduto.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != 74 && e.getKeyCode() != 17
						&& e.getKeyCode() != KeyEvent.VK_ENTER
						&& bordaPadrao != null) {
					jtfCodigoProduto.setText("");
					jtfCodigoProduto.setBorder(bordaPadrao);
					bordaPadrao = null;
					codigoProduto = "";
				}
			}

			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER
						&& !jtfCodigoProduto.getText().trim().equals("")) {
					if(buscaProduto()){
						jbtGerar.requestFocus();
					}
				}
			}
		});
		addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			public void ancestorAdded(AncestorEvent event) {
				jtfCodigoProduto.requestFocus();
			}
		});

		jtfCodigoProduto.addFocusListener(codigoProdutoFocusListener);

		// botão pesquisar
		jbtPesquisar = new JButton();
		jbtPesquisar.setText("Pesquisar");
		jbtPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFrame janela = new JFrame();
				janela.getContentPane().add(
						new BagPesquisarProduto(jtfCodigoProduto),
						BorderLayout.NORTH);
				janela.setSize(640, 400);
				janela.setVisible(true);
			}
		});
		gridPanelEsquerdo.add("Código:", jtfCodigoProduto, jbtPesquisar,
				new JPanel());
	}

	private void linha2() {

	}

	private void montaPanelDireito() {
		lbNomeProduto = new JLabel("Produto:");
		lbNomeProduto.setFont(new Font("Arial", Font.BOLD, 12));
		lbVlNomeProduto = new JLabel("-");
		lbVlNomeProduto.setFont(new Font("Arial", Font.PLAIN, 12));
		gridPanelDireita.add(lbNomeProduto, lbVlNomeProduto);

		lbEstoque = new JLabel("Estoque:");
		lbEstoque.setFont(new Font("Arial", Font.BOLD, 12));
		lbVlEstoque = new JLabel("0");
		lbVlEstoque.setFont(new Font("Arial", Font.PLAIN, 12));
		gridPanelDireita.add(lbEstoque, lbVlEstoque);

		lbPrecoCompra = new JLabel("Custo:");
		lbPrecoCompra.setFont(new Font("Arial", Font.BOLD, 12));
		lbVlPrecoCompra = new JLabel("0,00");
		lbVlPrecoCompra.setFont(new Font("Arial", Font.PLAIN, 12));
		gridPanelDireita.add(lbPrecoCompra, lbVlPrecoCompra);

		JPanel panelTotal = new JPanel();
		JLabel lbTotal = new JLabel("TOTAL:");
		lbTotal.setFont(new Font("Arial", Font.BOLD, 36));

		gridPanelDireita.add(panelTotal);
	}

	private void linha3() {
		// Botões
		jbtGerar = new JButton();
		jbtGerar.setText("Gerar");
		jbtGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				finalizarSaida();
			}
		});

		jbtGerar.addKeyListener(new KeyListener() {
		
			@Override
			public void keyTyped(KeyEvent e) {}
		
			@Override
			public void keyReleased(KeyEvent e) {}
		
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					finalizarSaida();
				}
			}
		});

		JButton jbtCancelar = new JButton();
		jbtCancelar.setText("Cancelar");
		jbtCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				Component comp = (Component) evt.getSource();
				while (true) {
					comp = comp.getParent();
					if (comp instanceof ControleEstoqueView)
						break;
				}
				((ControleEstoqueView) comp)
						.alteraMainPanel(new BagKardexProduto());
			}
		});

		gridPanelEsquerdo.add("Ações:", jbtCancelar, jbtGerar, jbtCancelar);
	}

	private void linha4() {
		// Grid parcial
		jpnTabela.setBorder(new TitledBorder("Produtos"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Data", "Quantidade", "Tipo", "Saldo" }) {
			private static final long serialVersionUID = 5622980448697494420L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		jtbTabela.setModel(tableModel);
		jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbTabela.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(0).setResizable(true);
		jtbTabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtbTabela.getColumnModel().getColumn(1).setResizable(true);
		jtbTabela.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtbTabela.getColumnModel().getColumn(2).setResizable(true);
		grid.add(jspContainer);
	}


	protected void editarModel() {

	}

	protected void salvarModel() {

		lista = new LinkedList<MovimetaProdutoIf>();

		for(int i = tableModel.getRowCount() - 1 ; i >= 0 ; i--){
			tableModel.removeRow(i);
		}		
		
		if(entradaDAO == null)
			entradaDAO = EntradaProdutoDAO.getInstance();
		if(saidaDAO == null)
			saidaDAO = SaidaProdutoDAO.getInstance();
		
		ArrayList<EntradaProdutoModel> entradas = entradaDAO.buscarPorProduto(produto);
		ArrayList<VendaProdutoModel> saidas = saidaDAO.buscarPorProduto(produto);
		lista.addAll(entradas);
		lista.addAll(saidas);
		MovimetaProdutoIf[] ordenado = lista.toArray(new MovimetaProdutoIf[0]);
		Arrays.sort(ordenado, new Comparator<MovimetaProdutoIf>() {

			@Override
			public int compare(MovimetaProdutoIf o1, MovimetaProdutoIf o2) {
				if(o1 != null && o1.getData() != null && o2.getData() != null) {
					return o1.getData().compareTo(o2.getData());
				}
				return -1;
			}
			
		});
		lista.clear();
		lista = Arrays.asList(ordenado);
		BigDecimal saldo = produto.getSaldoInicial();
		for (MovimetaProdutoIf movimetaProdutoIf : ordenado) {
			
			if(movimetaProdutoIf instanceof EntradaProdutoModel){
				saldo.add(movimetaProdutoIf.getQuantidade());
			}else{
				saldo.subtract(movimetaProdutoIf.getQuantidade());
			}
			
			tableModel.addRow(new Object[] {
					(movimetaProdutoIf.getData() != null ? SimpleDateFormat.getDateInstance().format(movimetaProdutoIf.getData()) : ""),
					movimetaProdutoIf.getQuantidade(),
					movimetaProdutoIf.getClass().getSimpleName().replace("ProdutoModel", ""),
					saldo
			});
		}
	}

	private void finalizarSaida() {
		salvarModel();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				jtfCodigoProduto.requestFocus();
				jtfCodigoProduto.selectAll();
			}
		}).start();
	}

	private ProdutoDAO getProdutoDAO() {
		if (produtoDAO == null) {
			produtoDAO = ProdutoDAO.getInstance();
		}
		return produtoDAO;
	}

	protected void excluirModel() {
	}

	private FocusListener codigoProdutoFocusListener = new FocusListener() {

		public void focusLost(FocusEvent e) {

		}

		public void focusGained(FocusEvent e) {
		}
	};

	public boolean buscaProduto() {
		if (!jtfCodigoProduto.getText().trim().equals("")
				&& (codigoProduto == null || (codigoProduto != null && !codigoProduto
						.equals(jtfCodigoProduto.getText().trim())))) {

			codigoProduto = jtfCodigoProduto.getText().trim();
			produto = getProdutoDAO().buscarPorIdentificador(codigoProduto);
			if (produto != null) {

				if (bordaPadrao != null) {
					jtfCodigoProduto.setBorder(bordaPadrao);
					bordaPadrao = null;
				}

				lbVlNomeProduto.setText(produto.getNome());
				lbVlPrecoCompra.setText(produto.getPreco().toPlainString()
						.replaceAll("[.]", ","));
				lbVlEstoque.setText(produto.getEstoqueAtual().toString());

				return true;

			} else {
				if (bordaPadrao == null)
					bordaPadrao = jtfCodigoProduto.getBorder();
				jtfCodigoProduto.setBorder(new LineBorder(Color.RED, 2));
				jtfCodigoProduto.requestFocus();
			}
		} else if (codigoProduto.equals(jtfCodigoProduto.getText().trim())) {
			return true;
		}
		return false;
	}

}
