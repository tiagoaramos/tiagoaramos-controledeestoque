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
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
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
import br.com.tiagoaramos.estoque.model.SaidaModel;
import br.com.tiagoaramos.estoque.model.SaidaProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.EntradaDAO;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.SaidaDAO;
import br.com.tiagoaramos.estoque.model.dao.SaidaProdutoDAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;
import br.com.tiagoaramos.estoque.utils.enums.TipoSaida;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;
import br.com.tiagoaramos.estoque.view.ControleEstoqueView;
import br.com.tiagoaramos.estoque.view.utils.BagPesquisarProduto;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

/**
 * 
 * @author tiago
 */
public class BagVendaProduto extends CadastroBagAb<SaidaProdutoModel> {

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
	private JFormattedTextField jtfQuantidadeProduto;
	private JFormattedTextField jtfValorVenda;
	private JButton jbt05Perc;
	private JButton jbt10Perc;
	private JButton jbt15Perc;
	private JButton jbtFinalizar;
	private Border bordaPadrao;

	// dados do produto
	private JLabel lbNomeProduto;
	private JLabel lbVlNomeProduto;
	private JLabel lbEstoque;
	private JLabel lbVlEstoque;
	private JLabel lbPrecoCompra;
	private JLabel lbVlPrecoCompra;

	// janela de finalização
	private JFrame frameModal;
	private JPanel modalJpanel;
	private JLabel lblTotalFinalizar;
	private JButton jbtFinalizarDinheiro;
	private JButton jbtFinalizarCartao;
	private JButton jbtContinuar;
	private JFormattedTextField jtfDesconto;

	// frame aguarde
	private JFrame frameAguarde;
	private JPanel aguardeJpanel;

	private JLabel lbVlTotal;

	private ProdutoDAO produtoDAO;

	private SaidaModel saida;
	private SaidaDAO saidaDAO;

	private ProdutoModel produto;
	private String codigoProduto;

	public BagVendaProduto() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new SaidaProdutoModel(), SaidaProdutoDAO.getInstance());

		setName("Venda de Produtos");

		if (saida == null) {
			saida = new SaidaModel();
			saida.setData(new Date());
			lista = new ArrayList<SaidaProdutoModel>();
		}

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

		jtfQuantidadeProduto.setEnabled(false);
		jtfValorVenda.setEnabled(false);

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
					if (buscaProduto()) {
						jtfQuantidadeProduto.setEnabled(true);
						jtfValorVenda.setEnabled(true);

						jtfQuantidadeProduto.selectAll();
						jtfQuantidadeProduto.requestFocus();
						jtfQuantidadeProduto.selectAll();

					} else {
						jtfQuantidadeProduto.setEnabled(false);
						jtfValorVenda.setEnabled(false);
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
		// Quantidade de produtos
		jtfQuantidadeProduto = new JFormattedTextField(NumberFormat
				.getIntegerInstance());
		jtfQuantidadeProduto.setName("jtfQuantidadeProduto");
		jtfQuantidadeProduto.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					jtfValorVenda.requestFocus();
					jtfValorVenda.selectAll();
				}
			}
		});
		jtfQuantidadeProduto.setText("1");
		gridPanelEsquerdo.add("Quantidade:", jtfQuantidadeProduto);

		// Valor de venda
		jtfValorVenda = new JFormattedTextField(new DecimalFormat("#.00"));
		jtfValorVenda.setName("jtfValorVenda");
		jtfValorVenda.addKeyListener(new KeyListener() {
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
		gridPanelEsquerdo.add("Valor:", jtfValorVenda);

		// Sujestões de vendas
		jbt05Perc = new JButton();
		jbt05Perc.setText("5% - ___,__");
		jbt05Perc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (produto != null && produto.getPrecoVenda() != null) {
					BigDecimal valor = produto.getPrecoVenda().multiply(
							new BigDecimal("0.95"));
					setValor(valor);
				}
			}
		});
		jbt10Perc = new JButton();
		jbt10Perc.setText("10% - ___,__");
		jbt10Perc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (produto != null && produto.getPrecoVenda() != null) {
					BigDecimal valor = produto.getPrecoVenda().multiply(
							new BigDecimal("0.90"));
					setValor(valor);
				}
			}

		});
		jbt15Perc = new JButton();
		jbt15Perc.setText("15% - ___,__");
		jbt15Perc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (produto != null && produto.getPrecoVenda() != null) {
					BigDecimal valor = produto.getPrecoVenda().multiply(
							new BigDecimal("0.85"));
					setValor(valor);
				}
			}
		});
		jbt05Perc.setEnabled(false);
		jbt10Perc.setEnabled(false);
		jbt15Perc.setEnabled(false);
		gridPanelEsquerdo.add("Descontos:", jbt05Perc, jbt10Perc, jbt15Perc);

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

		lbVlTotal = new JLabel("0,0");
		lbVlTotal.setFont(new Font("Arial", Font.PLAIN, 34));

		new GridLayout(panelTotal).add(lbTotal, lbVlTotal);
		gridPanelDireita.add(panelTotal);
	}

	private void linha3() {
		// Botões
		jbtFinalizar = new JButton();
		jbtFinalizar.setText("Finalizar");
		jbtFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mostraJanelaFinalizar();
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
						.alteraMainPanel(new BagVendaProduto());
			}
		});

		gridPanelEsquerdo.add("Ações:", jbtSalvar, jbtEditar, jbtExcluir,
				jbtFinalizar, jbtCancelar);
	}

	private void linha4() {
		// Grid parcial
		jpnTabela.setBorder(new TitledBorder("Produtos"));
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Código", "Nome", "Quantidade", "Preço de venda" }) {
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
		grid.add(jspContainer);
	}

	private void adicionarTabela(SaidaProdutoModel saidaProduto) {
		tableModel.addRow(new Object[] {
				saidaProduto.getProduto().getIdentificador(),
				saidaProduto.getProduto().getNome(),
				saidaProduto.getQuantidade().toString(),
				saidaProduto.getPrecoVenda().multiply(
						new BigDecimal(saidaProduto.getQuantidade())) });

	}

	protected void editarModel() {

		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {

			indice = l[0];
			model = lista.get(indice);
			jtfCodigoProduto.setText(model.getProduto().getIdentificador());
			jtfCodigoProduto.setEditable(false);
			produto = model.getProduto();
			jtfQuantidadeProduto.setText(model.getQuantidade().toString());
			jtfQuantidadeProduto.requestFocus();
			jtfValorVenda.setText(model.getPrecoVenda().toString());

			BigDecimal valor = model.getPrecoVenda();

			jbt05Perc.setText("05% - "
					+ valor.toPlainString().replaceAll("[.]", ","));

			valor = produto.getPrecoVenda().multiply(new BigDecimal("0.90"));
			valor = valor.setScale(2, RoundingMode.CEILING);
			jbt10Perc.setText("10% - "
					+ valor.toPlainString().replaceAll("[.]", ","));

			valor = produto.getPrecoVenda().multiply(new BigDecimal("0.85"));
			valor = valor.setScale(2, RoundingMode.CEILING);
			jbt15Perc.setText("15% - "
					+ valor.toPlainString().replaceAll("[.]", ","));

			jbt05Perc.setEnabled(true);
			jbt10Perc.setEnabled(true);
			jbt15Perc.setEnabled(true);

		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para editar!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			limparCampos();
		}
	}

	protected void salvarModel() {

		BigDecimal vendaAtual = model.getPrecoVenda() == null ? new BigDecimal(
				"0.00") : model.getPrecoVenda();
		Integer quantidadeAtual = model.getQuantidade();
		if (quantidadeAtual != null && quantidadeAtual.intValue() > 1)
			vendaAtual = vendaAtual.multiply(new BigDecimal(quantidadeAtual));

		model.setSaida(saida);
		model.setProduto(produto);
		model.setQuantidade(new Integer(jtfQuantidadeProduto.getText()));
		model.setPrecoVenda(new BigDecimal(jtfValorVenda.getText().replaceAll(
				",", ".")));

		try {
			if (indice >= 0) {

				// dao.merge(model);
				tableModel.setValueAt(model.getProduto().getNome(), indice, 0);
				tableModel.setValueAt(model.getQuantidade().toString(), indice,
						1);
				tableModel.setValueAt(model.getPrecoVenda().multiply(
						new BigDecimal(model.getQuantidade())).toString(),
						indice, 2);

				BigDecimal vendaAntiga = model.getPrecoVenda();
				if (model.getQuantidade() != null
						&& model.getQuantidade().intValue() > 1)
					vendaAntiga = vendaAntiga.multiply(new BigDecimal(model
							.getQuantidade()));

				vendaAtual = vendaAntiga.add(vendaAtual.negate());

			} else {
				lista.add(model);
				adicionarTabela(model);
				vendaAtual = model.getPrecoVenda().multiply(
						new BigDecimal(model.getQuantidade()));
			}
			jtfQuantidadeProduto.setEnabled(false);
			jtfValorVenda.setEnabled(false);
			limparCampos();

			alteraTotal(vendaAtual);

			mostraJanelaFinalizar();

		} catch (Exception ex) {
			Logger.getLogger(BagVendaProduto.class.getName()).log(Level.SEVERE,
					null, ex);
			JOptionPane.showMessageDialog(this,
					"Impossível adicionar o produto", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mostraJanelaFinalizar() {
		inicializaJanelaFinalizar();
		frameModal.setVisible(true);
		jtfDesconto.requestFocus();
		jtfDesconto.selectAll();
	}

	private void inicializaJanelaFinalizar() {

		if (frameModal == null) {

			frameModal = new JFrame();

			frameModal.setSize(350, 200);
			frameModal.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

			modalJpanel = new JPanel();

			frameModal.getContentPane().add(modalJpanel);

			GridLayout grid = new GridLayout(modalJpanel);

			jbtFinalizarDinheiro = new JButton("F2 - dinheiro");
			jbtFinalizarDinheiro.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frameModal.setVisible(false);
					saida.setTipo(TipoSaida.DINHEIRO);
					janelaAguarde(true);
					finalizarSaida();
				}
			});
			jbtFinalizarCartao = new JButton("F1 - cartão");
			jbtFinalizarCartao.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frameModal.setVisible(false);
					saida.setTipo(TipoSaida.CARTAO);
					janelaAguarde(true);
					finalizarSaida();
				}
			});
			jbtContinuar = new JButton("F3 - continuar");
			jtfDesconto = new JFormattedTextField(new DecimalFormat("#.00"));

			jtfDesconto.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if(!jtfDesconto.getText().equals(lbVlTotal.getText())){
							saida.setTipo(TipoSaida.CARTAO);
							frameModal.setVisible(false);
							janelaAguarde(true);
							finalizarSaida();
						}else{
							frameModal.setVisible(false);
							jtfCodigoProduto.requestFocus();
						}
					} else if (e.getKeyCode() == KeyEvent.VK_F1) {
						saida.setTipo(TipoSaida.CARTAO);
						frameModal.setVisible(false);
						janelaAguarde(true);
						finalizarSaida();
					} else if (e.getKeyCode() == KeyEvent.VK_F2) {
						saida.setTipo(TipoSaida.DINHEIRO);
						janelaAguarde(true);
						finalizarSaida();
						frameModal.setVisible(false);
					}
				}
			});
			lblTotalFinalizar = new JLabel();

			grid.add(jbtFinalizarCartao, jbtFinalizarDinheiro, jbtContinuar);

			grid.add("Total: ", lblTotalFinalizar);
			grid.add("Desconto: ", jtfDesconto);
		}
		jtfDesconto.setValue(new BigDecimal(lbVlTotal.getText().replaceAll(",",
				".")));
		lblTotalFinalizar.setText(lbVlTotal.getText());

	}

	protected void janelaAguarde(boolean visibilidade) {
		if (frameAguarde == null) {
			frameAguarde = (JFrame) this.getParent().getParent().getParent().getParent();
			JOptionPane optionPane = new JOptionPane();
			optionPane.setMessage("Aguarde o processamento!");
			optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			JInternalFrame modal = optionPane.createInternalFrame(this, "Aguarde");
			aguardeJpanel = new JPanel();
			aguardeJpanel.setOpaque(false);
			aguardeJpanel.add(modal);
			frameAguarde.setGlassPane(aguardeJpanel);
			modal.setVisible(true);
		}
		aguardeJpanel.setVisible(visibilidade);

	}

	private void finalizarSaida() {
		
		Thread tr = new Thread(){
			@Override
			public void run() {
				try {
					saida.setProdutos(lista);

					if (saida.getTipo().getCodigo().intValue() == TipoSaida.BAIXA
							.getCodigo().intValue()
							|| saida.getTipo().getCodigo().intValue() == TipoSaida.DOACAO
									.getCodigo().intValue()
							|| saida.getTipo().getCodigo().intValue() == TipoSaida.TROCA
									.getCodigo().intValue())
						if (!ControleSessaoUtil.solicitaSenhaAdmin(grid.getContentPane())) {
							JOptionPane
									.showMessageDialog(
											grid.getContentPane(),
											"Compra não registrada somente administradores podem fazer compras deste tipo!",
											"Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}

					if (saidaDAO == null)
						saidaDAO = SaidaDAO.getInstance();

					// desconto
					if(!jtfDesconto.getText().equals(lbVlTotal.getText())){
						BigDecimal valor = new BigDecimal(lbVlTotal.getText().replaceAll("[,]","."));
						BigDecimal valorDesconto = new BigDecimal(jtfDesconto.getText().replaceAll("[,]","."));
						BigDecimal razao = valor.subtract(valorDesconto);
						razao = razao.divide(valor,2,BigDecimal.ROUND_CEILING);
						valor = new BigDecimal("0.00");
						for (SaidaProdutoModel saidaProduto : saida.getProdutos()) {
							saidaProduto.setPrecoVenda(saidaProduto.getPrecoVenda().subtract(saidaProduto.getPrecoVenda().multiply(razao)));
							valor = valor.add(saidaProduto.getPrecoVenda());
						}
						if(!valor.toPlainString().equals(valorDesconto.toPlainString())){
							razao = valorDesconto.subtract(valor);
							SaidaProdutoModel saidaProduto = saida.getProdutos().get(0);
							saidaProduto.setPrecoVenda(saidaProduto.getPrecoVenda().add(razao));
						}
					}
					
					
					saidaDAO.persiste(saida);

					if (produtoDAO == null)
						produtoDAO = ProdutoDAO.getInstance();

					for (SaidaProdutoModel saidaProdutoModel : saida.getProdutos()) {
						ProdutoModel produto = saidaProdutoModel.getProduto();
						produto.setEstoqueAtual(new Integer(produto.getEstoqueAtual()
								.intValue()
								- saidaProdutoModel.getQuantidade().intValue()));

						if (produto.getEstoqueAtual().intValue() < 0) {
							registraEntrada(produto);
							produto.setEstoqueAtual(0);
						}

						produtoDAO.merge(produto);
					}
					janelaAguarde(false);
					JOptionPane.showMessageDialog(grid.getContentPane(),
							"Compra registrada com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					try {
						((ControleEstoqueView) grid.getContentPane().getParent().getParent().getParent()
								.getParent()).alteraMainPanel(((BagVendaProduto)grid.getContentPane()).getClass().newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
		
			}
		};
		tr.start();
		
		
	}

	private void registraEntrada(ProdutoModel produto)
			throws PersistenciaException {
		EntradaModel entrada = new EntradaModel();
		produto = ProdutoDAO.getInstance().buscarPorCodigo(produto.getId());
		entrada.setComprasProdutos(new ArrayList<EntradaProdutoModel>());
		EntradaProdutoModel entradaProduto = new EntradaProdutoModel();
		entradaProduto.setEntrada(entrada);
		entradaProduto.setProduto(produto);
		entradaProduto.setQuantidade(produto.getEstoqueAtual() * -1);
		produto.setEstoqueAtual(0);
		entrada.getComprasProdutos().add(entradaProduto);
		EntradaDAO.getInstance().persiste(entrada);
	}

	private void limparCampos() {
		indice = -1;
		codigoProduto = null;
		model = new SaidaProdutoModel();
		produto = new ProdutoModel();
		jtfCodigoProduto.setText("");
		jtfQuantidadeProduto.setText("1");
		jbt05Perc.setText("5% - ___,__");
		jbt05Perc.setEnabled(false);
		jbt10Perc.setText("10% - ___,__");
		jbt10Perc.setEnabled(false);
		jbt15Perc.setText("15% - ___,__");
		jbt15Perc.setEnabled(false);
		jtfCodigoProduto.setEditable(true);

		limparLabelsPreco();

	}

	private void limparLabelsPreco() {
		lbVlNomeProduto.setText("-");
		lbVlPrecoCompra.setText("0,00");
		lbVlEstoque.setText("");
		jtfValorVenda.setText("");
	}

	private ProdutoDAO getProdutoDAO() {
		if (produtoDAO == null) {
			produtoDAO = ProdutoDAO.getInstance();
		}
		return produtoDAO;
	}

	protected void excluirModel() {
		int[] l = jtbTabela.getSelectedRows();
		if (l.length > 0) {
			model = lista.get(l[0]);
			alteraTotal(model.getPrecoVenda().negate());
			lista.remove(l[0]);
			tableModel.removeRow(l[0]);
		} else {
			JOptionPane.showMessageDialog(this,
					"Selecione um registro para excluir!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		limparCampos();
	}

	private void alteraTotal(BigDecimal alt) {
		BigDecimal valor = new BigDecimal(lbVlTotal.getText().replaceAll("[,]",
				"."));
		valor = valor.add(alt);
		lbVlTotal.setText(valor.toPlainString().replaceAll("[.]", ","));
	}

	private void setValor(BigDecimal valor) {
		valor = valor.setScale(2, RoundingMode.CEILING);
		jtfValorVenda.setText(valor.toPlainString().replaceAll("[.]", ","));
		jtfValorVenda.requestFocus();
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
				jtfValorVenda.setText(produto.getPrecoVenda().toPlainString()
						.replaceAll("[.]", ","));

				BigDecimal valor = produto.getPrecoVenda().multiply(
						new BigDecimal("0.95"));
				valor = valor.setScale(2, RoundingMode.CEILING);
				jbt05Perc.setText("05% - "
						+ valor.toPlainString().replaceAll("[.]", ","));

				valor = produto.getPrecoVenda()
						.multiply(new BigDecimal("0.90"));
				valor = valor.setScale(2, RoundingMode.CEILING);
				jbt10Perc.setText("10% - "
						+ valor.toPlainString().replaceAll("[.]", ","));

				valor = produto.getPrecoVenda()
						.multiply(new BigDecimal("0.85"));
				valor = valor.setScale(2, RoundingMode.CEILING);
				jbt15Perc.setText("15% - "
						+ valor.toPlainString().replaceAll("[.]", ","));

				jbt05Perc.setEnabled(true);
				jbt10Perc.setEnabled(true);
				jbt15Perc.setEnabled(true);

				return true;

			} else {
				if (bordaPadrao == null)
					bordaPadrao = jtfCodigoProduto.getBorder();
				limparLabelsPreco();
				jtfCodigoProduto.setBorder(new LineBorder(Color.RED, 2));
				jtfCodigoProduto.requestFocus();
			}
		} else if (codigoProduto.equals(jtfCodigoProduto.getText().trim())) {
			return true;
		}
		return false;
	}

}
