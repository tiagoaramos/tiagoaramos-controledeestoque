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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import br.com.tiagoaramos.estoque.control.relatorio.GerarRelatorio;
import br.com.tiagoaramos.estoque.model.AberturaCaixaModel;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.AberturaCaixaDAO;
import br.com.tiagoaramos.estoque.model.dao.ProdutoDAO;
import br.com.tiagoaramos.estoque.model.dao.RetiradaDAO;
import br.com.tiagoaramos.estoque.model.dao.SaidaProdutoDAO;
import br.com.tiagoaramos.estoque.utils.enums.TipoRelatorio;
import br.com.tiagoaramos.estoque.utils.enums.TipoSaida;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

import comp.CalendarComboBox;

/**
 * 
 * @author tiago
 */
public class BagFechamentoDiario extends CadastroBagAb<ProdutoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JComboBox cmbTipoDiario;
	private JButton jbtGerar;
	private CalendarComboBox dataCalendarComboBox;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public BagFechamentoDiario() {
		try {
			initComponents();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws ParseException {
		super.initComponents(new ProdutoModel(), ProdutoDAO.getInstance());

		lista = new LinkedList<ProdutoModel>();
		cmbTipoDiario = new JComboBox();
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Data", "Abertura Caixa", "Venda Cartão", "Venda Dinheiro", "Valor Retirada", "Valor Total" }) {
			private static final long serialVersionUID = 5622980448697494420L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		jtbTabela.setModel(tableModel);
		dataCalendarComboBox = new CalendarComboBox(true);
		
			setName("Fechamento de caixa");
	
			jbtGerar = new JButton();
			jbtGerar.setText("Gerar");
			jbtGerar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	try {
						
		            	TipoRelatorio tipo = (TipoRelatorio)cmbTipoDiario.getSelectedItem();
		            	if(tipo.getCodigo().intValue() == TipoRelatorio.FECHAMENTO_CAIXA.getCodigo().intValue()){
		            		Date dataSolicitacao;
		            		if(dataCalendarComboBox.getSelectedItem() != null)
		            			dataSolicitacao = sdf.parse((String) dataCalendarComboBox.getSelectedItem());
		            		else
		            			dataSolicitacao = new Date();
		            		
		            		AberturaCaixaDAO lAberturaCaixaDAO = AberturaCaixaDAO.getInstance();
		            		RetiradaDAO lRetiradaDAO = RetiradaDAO.getInstance();

		            		DecimalFormat df = new DecimalFormat("###,###,##0.00");
		            		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		            		
		            		AberturaCaixaModel lAberturaCaixaModel = lAberturaCaixaDAO.buscarPorData(dataSolicitacao);
		            		Double valorAbertura;
		            		if(lAberturaCaixaModel != null)
		            			valorAbertura = lAberturaCaixaModel.getValorInicial();
		            		else
		            			valorAbertura = new Double("0");
		            		
		            		Double valorCartao = SaidaProdutoDAO.getInstance().movimentoDoDia(TipoSaida.CARTAO,dataSolicitacao);
		            		Double valorDinheiro = SaidaProdutoDAO.getInstance().movimentoDoDia(TipoSaida.DINHEIRO,dataSolicitacao);
		            		Double valorRetirada = lRetiradaDAO.buscarTotalPorData(dataSolicitacao);
		            		Double valorTotal = valorAbertura + valorCartao + valorDinheiro - valorRetirada;
		            		
		            		tableModel.addRow(new Object[] {
		            				sdf.format(dataSolicitacao),
		            				"R$ " + df.format(valorAbertura) ,
		            				"R$ " + df.format(valorCartao) ,
		            				"R$ " + df.format(valorDinheiro) ,
		            				"R$ " + df.format(valorRetirada) ,
		            				"R$ " + df.format(valorTotal)  });
		            		
		            	}else{
		            		gerarRelatorio(tipo);
		            	}
					
	            	} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
	            }

	        });
			
			for (TipoRelatorio tipo : TipoRelatorio.values()) {
				cmbTipoDiario.addItem(tipo);
			}
			cmbTipoDiario.setSelectedItem(TipoRelatorio.FECHAMENTO_CAIXA);
			
			grid.add("Tipo:",cmbTipoDiario);
			grid.add("Data:",dataCalendarComboBox,jbtGerar,new JPanel(),new JPanel());
			grid.add(jspContainer);
	}

	
	protected void editarModel() {
	}

	protected void salvarModel() {
	}

	private void gerarRelatorio(TipoRelatorio tipo) throws ParseException, IOException {
		Date dataSolicitacao;
		if(dataCalendarComboBox.getSelectedItem() != null)
			dataSolicitacao = sdf.parse((String) dataCalendarComboBox.getSelectedItem());
		else
			dataSolicitacao = new Date();
		GerarRelatorio gerar = new GerarRelatorio();
		SaidaProdutoDAO saidaProdutoDAO = SaidaProdutoDAO.getInstance();
		ResultSet rs = saidaProdutoDAO.fechamento(dataSolicitacao);
		
		HashMap<String, Object> parametros = new LinkedHashMap<String, Object>();
		parametros.put("dataSolicitacao", dataSolicitacao);
		parametros.put("loja", ControleSessaoUtil.usuarioLogado.getNome());
		String resultado = gerar.gerarRelatorio(new JRResultSetDataSource(rs), parametros , tipo.getJasper(), tipo.getJasper() + ((String) sdf.format(dataSolicitacao)).replaceAll("[^0-9a-zA-Z]", ""));
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();  
		desktop.open(new File(resultado));
	}
	
	public static void main(String[] args) {
		try {
			BagFechamentoDiario lBagFechamentoDiario = new BagFechamentoDiario();
			lBagFechamentoDiario.dataCalendarComboBox.setSelectedItem(lBagFechamentoDiario.sdf.format(new Date()));
			ControleSessaoUtil.usuarioLogado = new UsuarioModel();
			ControleSessaoUtil.usuarioLogado.setId(1);
			ControleSessaoUtil.usuarioLogado.setNome("teste");
			lBagFechamentoDiario.gerarRelatorio(TipoRelatorio.LANCAMENTOS);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
