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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
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
import br.com.tiagoaramos.estoque.model.MovimetaProdutoIf;
import br.com.tiagoaramos.estoque.model.ProducaoModel;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.SaidaModel;
import br.com.tiagoaramos.estoque.model.SaidaProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.EntradaDAO;
import br.com.tiagoaramos.estoque.model.dao.ProducaoProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.SaidaDAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;
import br.com.tiagoaramos.estoque.utils.enums.TipoEntrada;
import br.com.tiagoaramos.estoque.utils.enums.TipoProducao;
import br.com.tiagoaramos.estoque.utils.enums.TipoSaida;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;
import br.com.tiagoaramos.estoque.view.utils.BagPesquisarProduto;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;
import br.com.tiagoaramos.estoque.view.utils.HibernateUtils;

/**
 * 
 * @author tiago
 */
public class BagProducaoProduto extends CadastroBagAb<MovimetaProdutoIf> {

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
	private BigDecimal custoTotal;
	private BigDecimal custoUnitario;
	private String codigoProduto;
	private Border bordaPadrao;
	
	private JLabel lbVlTotal;
	private JLabel lbVlUnit;
	private JLabel lbVlVenda;
	
	private JTextField jtfCodigoProduto;
	private JFormattedTextField jtfQuantidadeProduto;
	private JComboBox cmbTipoEntrada;
	private JButton jbtPesquisar;
	private JButton jbtFinalizar;
	private EntradaModel entrada;
	private SaidaModel saida;
	private ProdutoDAO produtoDAO;
	
	private EntradaDAO compraDAO;
	private SaidaDAO saidaDAO;

	private ProducaoProdutoDAO producaoDAO;

	private int qtdTotalProduzida;

	private JFormattedTextField jtfValorMargem;
	

	public BagProducaoProduto() {
		try {			
			initComponents();			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(null, null);

		custoTotal = new BigDecimal(0);
		custoUnitario = new BigDecimal(0);
		qtdTotalProduzida = 0;
		
		panelEsquerda = new JPanel();
		panelDireita = new JPanel();
		panelDireita.setBackground(Color.WHITE);
		panelDireita.setBorder(new LineBorder(Color.BLACK,3));
		gridPanelEsquerdo = new GridLayout(panelEsquerda);
		gridPanelDireita = new GridLayout(panelDireita);
		
			lista = new LinkedList<MovimetaProdutoIf>();
			entrada = new EntradaModel();
			entrada.setData(new Date());
			entrada.setComprasProdutos(new ArrayList<EntradaProdutoModel>());
			entrada.setTipoEntrada(TipoEntrada.PRODUCAO);
			
			saida = new SaidaModel();
			saida.setData(new Date());
			saida.setProdutos(new ArrayList<SaidaProdutoModel>());
			
			jbtFinalizar = new JButton();
			cmbTipoEntrada = new JComboBox();
			
			setName("Produção de Produtos");
	
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
	
			for (TipoProducao tipo : TipoProducao.values()) {
				cmbTipoEntrada.addItem(tipo);
			}
			cmbTipoEntrada.setSelectedItem(TipoEntrada.COMPRA);
			gridPanelEsquerdo.add("Tipo de produção:",cmbTipoEntrada);
	    	
			jtfQuantidadeProduto = new JFormattedTextField(NumberFormat.getNumberInstance());
			jtfQuantidadeProduto.setName("jtfQuantidadeProduto");
			jtfQuantidadeProduto.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							&& !jtfQuantidadeProduto.getText().trim().equals("")) {
						jbtSalvar.requestFocus();
					}
				}
			});
			
			gridPanelEsquerdo.add("Quantidade:",jtfQuantidadeProduto);
			
			jtfValorMargem = new JFormattedTextField(new DecimalFormat("#.00"));
			jtfValorMargem.setName("jtfValorCompra");
			jtfValorMargem.setText("2.00");
			jtfValorMargem.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							&& !jtfValorMargem.getText().trim().equals("")) {
						alteraUnit(custoUnitario);
					}
				}
			});
			jtfValorMargem.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					if(!jtfValorMargem.getText().trim().equals("")){
						alteraUnit(custoUnitario);
						
					}
				}
				
				@Override
				public void focusGained(FocusEvent e) {
				}
			});

			gridPanelEsquerdo.add("Margem de venda:", jtfValorMargem);
			
			
			jbtSalvar.setText("Adicionar");
			
			
			jpnTabela.setBorder(new TitledBorder("Produtos"));
			tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
					"Nome", "Quantidade", "Tipo" }) {
				private static final long serialVersionUID = 5622980448697494420L;
	
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			gridPanelEsquerdo.add("Ações:",jbtSalvar,jbtExcluir,jbtFinalizar);
			
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
	                finalizarProducao();
	            }
	        });
	}

	private void adicionarTabela(MovimetaProdutoIf movimentaProduto, TipoProducao tipo) {
		tableModel.addRow(new Object[] { movimentaProduto.getProduto().getNome(), movimentaProduto.getQuantidade().toString(), tipo.getDESCRICAO()});
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
		
		TipoProducao tipo = (TipoProducao) cmbTipoEntrada.getSelectedItem();
		produto = produtoDAO.buscarPorIdentificador(jtfCodigoProduto.getText());
		
		if(tipo.equals(TipoProducao.MATERIA_PRIMA)){
			model = new SaidaProdutoModel();
			((SaidaProdutoModel)model).setSaida(saida);
			saida.getProdutos().add((SaidaProdutoModel) model);
			
			custoTotal = custoTotal.add(new BigDecimal(produto.getPreco().doubleValue() * new Integer(jtfQuantidadeProduto.getText())));
			
			alteraTotal(custoTotal);
			
		}else if(tipo.equals(TipoProducao.PRODUTO_FINAL)){
			model = new EntradaProdutoModel();
			((EntradaProdutoModel)model).setEntrada(entrada);
			entrada.getComprasProdutos().add((EntradaProdutoModel) model);

			qtdTotalProduzida += new Integer(jtfQuantidadeProduto.getText()); 

			
		}
		custoUnitario = new BigDecimal(qtdTotalProduzida > 0 ? custoTotal.doubleValue() / (double) qtdTotalProduzida : 0);
		alteraUnit(custoUnitario);
		
		model.setProduto(produto);
		model.setQuantidade(new BigDecimal(jtfQuantidadeProduto.getText()));

		try {
			if (model.getId() != null && model.getId().intValue() > 0) {

				tableModel.setValueAt(model.getProduto().getNome(), indice, 1);
				tableModel.setValueAt(model.getQuantidade().toString(), indice, 2);

			} else {
				lista.add(model);
				adicionarTabela(model, tipo );
			}
			limparCampos();
			jtfCodigoProduto.requestFocus();
			
		} catch (Exception ex) {
			Logger.getLogger(BagProducaoProduto.class.getName()).log(
					Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this,
					"Impossível adicionar o produto", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void finalizarProducao(){
		EntityManager em = HibernateUtils.getEm();
		try {

		    em.getTransaction().begin();
			
			if(compraDAO == null)
				compraDAO = EntradaDAO.getInstance();
			
			if(saidaDAO == null)
				saidaDAO = SaidaDAO.getInstance();

			if(produtoDAO == null)
				produtoDAO = ProdutoDAO.getInstance();
			
			if(producaoDAO == null)
				producaoDAO = ProducaoProdutoDAO.getInstance();
			
			
			for (EntradaProdutoModel compraProdutoModel : entrada.getComprasProdutos()) {
				
				ProdutoModel produto = produtoDAO.buscarPorCodigo( compraProdutoModel.getProduto().getId());; 
				
				double custoMedio = produto.getPreco().doubleValue();
				BigDecimal qtdAtual = produto.getEstoqueAtual();
				double custoTotal =qtdAtual.doubleValue() * custoMedio;
				
				BigDecimal qtdProduzida = compraProdutoModel.getQuantidade();
				double custoProducao = custoUnitario.doubleValue() * qtdProduzida.doubleValue();
				
				custoTotal += custoProducao;
				qtdAtual.add(qtdProduzida);
				custoMedio = custoTotal / qtdAtual.doubleValue(); 
				
				produto.setEstoqueAtual(qtdAtual);
				produto.setPreco(new BigDecimal(custoMedio));
				produto.setPrecoVenda(new BigDecimal(custoMedio).multiply(new BigDecimal(jtfValorMargem.getText().replaceAll("[,]", "."))));
				
				produtoDAO.merge(produto);
				
				compraProdutoModel.setPrecoCompra(produto.getPreco());
				compraProdutoModel.setPrecoCompra(new BigDecimal(0));
				
			}
			saida.setTipo(TipoSaida.USO_CONSUMO);
			for (SaidaProdutoModel saidaProduto : saida.getProdutos()) {
				saidaProduto.setPrecoVenda(new BigDecimal(0));
			}

			compraDAO.persiste(entrada);
			saidaDAO.persiste(saida);
			
			ProducaoModel producao = new ProducaoModel();
			producao.setCusto(custoTotal);
			producao.setData(new Date());
			producao.setUsuario(ControleSessaoUtil.usuarioLogado);
			producao.setEntrada(entrada);
			producao.setSaida(saida);
			
			producaoDAO.persiste(producao);
			

			for(int i = tableModel.getRowCount() - 1 ; i >= 0 ; i--){
				tableModel.removeRow(i);
			}		
			limparCampos();
			lista = new LinkedList<MovimetaProdutoIf>();

			custoTotal = new BigDecimal(0);
			custoUnitario = new BigDecimal(0);

		    em.getTransaction().commit();
			
			JOptionPane.showMessageDialog(this,
					"Produção registrada com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}finally {
			em.close();
		}
	}
	

	private void limparCampos() {
		indice = -1;
		jtfCodigoProduto.setText("");
		jtfQuantidadeProduto.setText("");
		
		
		
		
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

		lbVlTotal = new JLabel("0,0");
		lbVlTotal.setFont(new Font("Arial", Font.PLAIN, 17));
		
		lbVlUnit = new JLabel("0,0");
		lbVlUnit.setFont(new Font("Arial", Font.PLAIN, 17));
		
		lbVlVenda = new JLabel("0,0");
		lbVlVenda.setFont(new Font("Arial", Font.PLAIN, 17));

		
		JPanel panelTotais = new JPanel();
		panelTotais.setBorder(new LineBorder(Color.BLACK,3));
		GridLayout gridTotais = new GridLayout(panelTotais);

		JLabel lbTotal = new JLabel("CUSTO UNITARIO:");
		lbTotal.setFont(new Font("Arial",Font.BOLD,17));
		JLabel lbPrcVenda = new JLabel("PREÇO VENDA:");
		lbPrcVenda.setFont(new Font("Arial",Font.BOLD,17));
		gridTotais.add(lbTotal, lbVlUnit,lbPrcVenda,lbVlVenda);

		lbTotal = new JLabel("CUSTO TOTAL:");
		lbTotal.setFont(new Font("Arial",Font.BOLD,17));
		gridTotais.add(lbTotal, lbVlTotal);

		gridPanelDireita.add(gridTotais);
	}


	private void alteraTotal(BigDecimal alt) {
		lbVlTotal.setText( NumberFormat.getCurrencyInstance().format(alt.doubleValue()) );
	}	

	private void alteraUnit(BigDecimal alt) {
		lbVlUnit.setText( NumberFormat.getCurrencyInstance().format(alt.doubleValue()) );
		lbVlVenda.setText( NumberFormat.getCurrencyInstance().format(alt.doubleValue() * new BigDecimal(jtfValorMargem.getText().replaceAll("[,]", ".")).doubleValue()));
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
	
	protected void excluirModel() {
		int[] l = jtbTabela.getSelectedRows();//captura as linhas selecionadas
		if(l.length > 0){
					
			model = lista.get(l[0]);
			if(model instanceof SaidaProdutoModel){
				// saida corrigi custo total
				
				custoTotal = custoTotal.subtract(new BigDecimal(model.getQuantidade().doubleValue() * model.getProduto().getPreco().doubleValue()));
				saida.getProdutos().remove(model);
			}else{
				qtdTotalProduzida -= model.getQuantidade().doubleValue();
				entrada.getComprasProdutos().remove(model);
			}
			custoUnitario = new BigDecimal(qtdTotalProduzida > 0 ? custoTotal.doubleValue() / (double)qtdTotalProduzida : 0);
			
			lista.remove(model);
			
			
			tableModel.removeRow(l[0]);
		
			alteraTotal(custoTotal);
			alteraUnit(custoUnitario);
			
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um registro para excluir!", "Erro", JOptionPane.ERROR_MESSAGE );
		}	
	}
}
