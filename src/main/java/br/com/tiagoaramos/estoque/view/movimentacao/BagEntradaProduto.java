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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.EntradaModel;
import br.com.tiagoaramos.estoque.model.EntradaProdutoModel;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.EntradaDAO;
import br.com.tiagoaramos.estoque.model.dao.EntradaProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;
import br.com.tiagoaramos.estoque.utils.enums.TipoEntrada;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;
import br.com.tiagoaramos.estoque.view.utils.BagPesquisarProduto;

/**
 * 
 * @author tiago
 */
public class BagEntradaProduto extends CadastroBagAb<EntradaProdutoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	// Paineis
	private JPanel panelEsquerda;
	private GridLayout gridPanelEsquerdo;
	private JPanel panelDireita;
	private GridLayout gridPanelDireita;
	

	// dados do produto
	private JLabel lbNomeProduto;
	private JLabel lbVlNomeProduto;
	private JLabel lbEstoque;
	private JLabel lbVlEstoque;
	private JLabel lbPrecoCompra;
	private JLabel lbVlPrecoCompra;

	private ProdutoModel produto;
	private String codigoProduto;
	private Border bordaPadrao;

	
	private JTextField jtfCodigoProduto;
	private JFormattedTextField jtfQuantidadeProduto;
	private JFormattedTextField jtfValorCompra;
	private JComboBox cmbTipoEntrada;
	private JButton jbtPesquisar;
	private JButton jbtFinalizar;
	private EntradaModel entrada;
	private ProdutoDAO produtoDAO;
	
	private EntradaDAO compraDAO;

	public BagEntradaProduto() {
		try {			
			initComponents();			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new EntradaProdutoModel(), EntradaProdutoDAO.getInstance());

		panelEsquerda = new JPanel();
		panelDireita = new JPanel();
		panelDireita.setBackground(Color.WHITE);
		panelDireita.setBorder(new LineBorder(Color.BLACK,3));
		gridPanelEsquerdo = new GridLayout(panelEsquerda);
		gridPanelDireita = new GridLayout(panelDireita);
		
			lista = new LinkedList<EntradaProdutoModel>();
			entrada = new EntradaModel();
			entrada.setData(new Date());
			jbtFinalizar = new JButton();
			cmbTipoEntrada = new JComboBox();
			
			setName("Entrada de Produto");
	
			jtfCodigoProduto = new JTextField();
			jtfCodigoProduto.setName("jtfCodigoProduto");
			jtfCodigoProduto.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							&& !jtfCodigoProduto.getText().trim().equals("")) {
						jtfQuantidadeProduto.selectAll();
						jtfQuantidadeProduto.requestFocus();
						jtfQuantidadeProduto.selectAll();
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
			
			jbtPesquisar = new JButton();
			jbtPesquisar.setText("Pesquisar");
			jbtPesquisar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	JFrame janela = new JFrame();
	            	janela.getContentPane().add(new BagPesquisarProduto(jtfCodigoProduto),BorderLayout.NORTH);
	            	janela.setSize(640, 400);
	            	janela.setVisible(true);
	            }
	        });
			gridPanelEsquerdo.add("Código:",jtfCodigoProduto,jbtPesquisar);
	
			for (TipoEntrada tipo : TipoEntrada.values()) {
				cmbTipoEntrada.addItem(tipo);
			}
			cmbTipoEntrada.setSelectedItem(TipoEntrada.COMPRA);
			gridPanelEsquerdo.add("Tipo de entrada:",cmbTipoEntrada);
	    	
			jtfQuantidadeProduto = new JFormattedTextField(NumberFormat.getIntegerInstance());
			jtfQuantidadeProduto.setName("jtfQuantidadeProduto");
			jtfQuantidadeProduto.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							&& !jtfQuantidadeProduto.getText().trim().equals("")) {
						jtfValorCompra.requestFocus();
					}
				}
			});
			
			gridPanelEsquerdo.add("Quantidade:",jtfQuantidadeProduto);
			
			jtfValorCompra = new JFormattedTextField(new DecimalFormat("#.00"));
			jtfValorCompra.setName("jtfValorCompra");
			jtfValorCompra.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						jbtSalvar.requestFocus();
					}
				}
			});
			gridPanelEsquerdo.add("Valor:", jtfValorCompra);
			
			
			
			jbtSalvar.setText("Adicionar");
			
			
			jpnTabela.setBorder(new TitledBorder("Produtos"));
			tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
					"Nome", "Quantidade" }) {
				private static final long serialVersionUID = 5622980448697494420L;
	
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			gridPanelEsquerdo.add("Ações:",jbtSalvar,jbtEditar,jbtExcluir,jbtFinalizar);
			
			montaPanelDireito();
			
			grid.add(panelEsquerda,panelDireita,4);
			
			jtbTabela.setModel(tableModel);
			jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jtbTabela.getColumnModel().getColumn(0).setPreferredWidth(200);
			jtbTabela.getColumnModel().getColumn(0).setResizable(true);
			jtbTabela.getColumnModel().getColumn(1).setPreferredWidth(100);
			jtbTabela.getColumnModel().getColumn(1).setResizable(true);
			grid.add(jspContainer);
			
			jbtFinalizar.setText("Finalizar");
			jbtFinalizar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                finalizarCompra();
	            }
	        });
	}

	private void adicionarTabela(EntradaProdutoModel compraProduto) {
		tableModel.addRow(new Object[] { compraProduto.getProduto().getNome(),compraProduto.getQuantidade().toString()});
	}

	protected void editarModel() {

		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			
			indice = l[0];
			model = lista.get(indice);
			jtfCodigoProduto.setText(model.getProduto().getId().toString());
			jtfQuantidadeProduto.setText(model.getQuantidade().toString());
			
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para editar!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void salvarModel() {
		
		model.setEntrada(entrada);
		model.setProduto(produtoDAO.buscarPorIdentificador(jtfCodigoProduto.getText()));
		model.setQuantidade(new Integer(jtfQuantidadeProduto.getText()));
		model.setPrecoCompra(new BigDecimal(jtfValorCompra.getText().replaceAll(",", ".")));

		try {
			if (model.getId() != null && model.getId().intValue() > 0) {

				tableModel.setValueAt(model.getProduto().getNome(), indice, 1);
				tableModel.setValueAt(model.getQuantidade().toString(), indice, 2);

			} else {

				lista.add(model);
				adicionarTabela(model);
			}
			limparCampos();
			jtfCodigoProduto.requestFocus();
			
		} catch (Exception ex) {
			Logger.getLogger(BagEntradaProduto.class.getName()).log(
					Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,
					"Impossível adicionar o produto", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void finalizarCompra(){
		try {
			entrada.setComprasProdutos(lista);
			if(compraDAO == null)
				compraDAO = EntradaDAO.getInstance();
			
			compraDAO.persiste(entrada);
			
			if(produtoDAO == null)
				produtoDAO = ProdutoDAO.getInstance();
			
			for (EntradaProdutoModel compraProdutoModel : entrada.getComprasProdutos()) {
				ProdutoModel produto = compraProdutoModel.getProduto(); 
				produto.setPreco(compraProdutoModel.getPrecoCompra());
				produto.setEstoqueAtual(new Integer(produto.getEstoqueAtual().intValue() + compraProdutoModel.getQuantidade().intValue()));
				produtoDAO.merge(produto);
			}
			

			for(int i = tableModel.getRowCount() - 1 ; i >= 0 ; i--){
				tableModel.removeRow(i);
			}		
			limparCampos();
			lista = new LinkedList<EntradaProdutoModel>();

			JOptionPane.showMessageDialog(this,
					"Compra registrada com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}		
	}
	

	private void limparCampos() {
		indice = -1;
		jtfCodigoProduto.setText("");
		jtfQuantidadeProduto.setText("");
		model = new EntradaProdutoModel();
	}
	
	
	private void montaPanelDireito() {
		lbNomeProduto = new JLabel("Produto:");
		lbNomeProduto.setFont(new Font("Arial",Font.BOLD,12));
		lbVlNomeProduto = new JLabel("-");
		lbVlNomeProduto.setFont(new Font("Arial",Font.PLAIN,12));
		gridPanelDireita.add(lbNomeProduto, lbVlNomeProduto);
		
		lbEstoque = new JLabel("Estoque:");
		lbEstoque.setFont(new Font("Arial",Font.BOLD,12));
		lbVlEstoque = new JLabel("0");
		lbVlEstoque.setFont(new Font("Arial",Font.PLAIN,12));
		gridPanelDireita.add(lbEstoque, lbVlEstoque);
		
		lbPrecoCompra = new JLabel("Custo:");
		lbPrecoCompra.setFont(new Font("Arial",Font.BOLD,12));
		lbVlPrecoCompra = new JLabel("0,00");
		lbVlPrecoCompra.setFont(new Font("Arial",Font.PLAIN,12));
		gridPanelDireita.add(lbPrecoCompra, lbVlPrecoCompra);
				
	}
	
	
	private FocusListener codigoProdutoFocusListener = new FocusListener() {

		public void focusLost(FocusEvent e) {

			if (!jtfCodigoProduto.getText().trim().equals("")
					&& (codigoProduto == null || (codigoProduto != null && !codigoProduto
							.equals(jtfCodigoProduto.getText()
									.trim())))) {

				codigoProduto = jtfCodigoProduto.getText().trim();
				produto = getProdutoDAO().buscarPorIdentificador(codigoProduto);
				if (produto != null) {

					if (bordaPadrao != null)
						jtfCodigoProduto.setBorder(bordaPadrao);

					lbVlNomeProduto.setText(produto.getNome());
					lbVlPrecoCompra.setText(produto.getPreco().toPlainString().replaceAll("[.]", ","));
					lbVlEstoque.setText(produto.getEstoqueAtual().toString());

				} else {
					if (bordaPadrao == null)
						bordaPadrao = jtfCodigoProduto.getBorder();
					
					limparLabelsPreco();
					jtfCodigoProduto.setBorder(new LineBorder(Color.RED, 2));

					jtfCodigoProduto.requestFocus();
				}
			} else if (produto == null && !jtfCodigoProduto.getText().equals("") && !e.getOppositeComponent().equals(jbtPesquisar)) {
				jtfCodigoProduto.requestFocus(); 
				jtfCodigoProduto.selectAll();
				limparLabelsPreco();
			}
		}

		public void focusGained(FocusEvent e) {
		}
	};
	
	private ProdutoDAO getProdutoDAO() {
		if (produtoDAO == null) {
			produtoDAO = ProdutoDAO.getInstance();
		}
		return produtoDAO;
	}
	

	private void limparLabelsPreco() {
		lbVlNomeProduto.setText("-");
		lbVlPrecoCompra.setText("0,00");
		lbVlEstoque.setText("");
	}
}
