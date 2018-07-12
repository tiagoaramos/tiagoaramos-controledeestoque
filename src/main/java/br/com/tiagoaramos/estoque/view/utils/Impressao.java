package br.com.tiagoaramos.estoque.view.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import org.apache.commons.lang.StringUtils;

import br.com.tiagoaramos.estoque.model.EmpresaModel;
import br.com.tiagoaramos.estoque.model.VendaModel;
import br.com.tiagoaramos.estoque.model.VendaProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.EmpresaDAO;

/**
 *
 * @author Sergio
 */
public class Impressao {
	
	private static int COLUNAS = 48;

	/**
	 * @param args
	 *            the command line arguments
	 * @throws javax.print.PrintException
	 */
	public static void imprimir(VendaModel saida) throws PrintException {
		try {
			// Localiza todas as impressoras com suporte a arquivos txt
			PrintService[] servicosImpressao = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE,
					null);

			System.out.println("Impressoras com suporte: " + servicosImpressao.length);

			// Localiza a impressora padrão
			PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();

			// System.out.println("Impressora: " + impressora.getName());

			// System.out.println("Imprimindo arquivo-texto");

			// Definição de atributos do conteúdo a ser impresso:
			DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

			// Atributos de impressão do documento
			HashDocAttributeSet attributes = new HashDocAttributeSet();

			// InputStream apontando para o conteúdo a ser impresso
			FileOutputStream fil = new FileOutputStream("c.txt");

			PrintStream p = new PrintStream(fil);

			EmpresaModel empresa = EmpresaDAO.getInstance().buscarPorCodigo(ControleSessaoUtil.empresaLogado.getId());

			p.print(centerCols (empresa.getNome()) + (char) 27);
			p.print(centerCols (empresa.getCnpj()) + (char) 27);
			p.print(centerCols (empresa.getTelefone()) + (char) 27);
			p.print(centerCols (empresa.getEndereco()) + (char) 27);
			p.print(centerCols (SimpleDateFormat.getDateTimeInstance().format(new Date())) + (char) 27);
			

			p.print(StringUtils.rightPad("", COLUNAS,"=") + (char) 27);
			

			// Cabeçalho
			
			
			// codigo 4 colunas
			String linha = "COD.";
			
			linha += " ";
			
			// Descriçao 20
			linha += StringUtils.rightPad("DESCRICAO",18," ");
			linha += " ";
			
			// Quantidade 6
			linha += StringUtils.rightPad("QTD",6," ");
			linha += " ";
			
			// VALOR UNI 8
			linha += StringUtils.rightPad("UNIT",8," ");
			linha += " ";
			
			// VALOR TOTAL 9
			linha += "TOTAL   "+ (char) 27;
			
			p.print( linha );
			
			int i = 1;
			BigDecimal total = new BigDecimal(0.0d);
			BigDecimal totalItem = new BigDecimal(0.0d);
			BigDecimal totalDesconto = new BigDecimal(0.0d);
			
			for (VendaProdutoModel item : saida.getProdutos()) {
				String linhaItem =  "    ";
				
				// codigo 4 colunas
				linhaItem = StringUtils.leftPad((item.getProduto().getIdentificador()),4,"0");
				
				linhaItem+= " ";
				// Descriçao 28
				linhaItem+= StringUtils.rightPad((item.getProduto().getNome().substring(0, (item.getProduto().getNome().length() < 18 ? item.getProduto().getNome().length() : 18 ) )),18," ");
				
				linhaItem+= " ";
				// Quantidade 6
				linhaItem+= StringUtils.rightPad((NumberFormat.getNumberInstance().format(item.getQuantidade().doubleValue())),6," ");

				linhaItem+= " ";
				// VALOR UNI 8
				linhaItem+= StringUtils.rightPad(NumberFormat.getCurrencyInstance().format(item.getProduto().getPrecoVenda().doubleValue()),8," ");

				linhaItem+= " ";
				// VALOR TOTAL 9
				linhaItem+= StringUtils.rightPad(NumberFormat.getCurrencyInstance().format(item.getProduto().getPrecoVenda().doubleValue() * item.getQuantidade().doubleValue()),8," ");
				totalItem = totalItem.add(new BigDecimal(item.getProduto().getPrecoVenda().doubleValue() * item.getQuantidade().doubleValue()));
				totalDesconto = totalDesconto.add(new BigDecimal((item.getProduto().getPrecoVenda().doubleValue() * item.getQuantidade().doubleValue()) - item.getPrecoVenda().doubleValue()));
				
				p.print( linhaItem + (char) 27);
				total = total.add(item.getPrecoVenda());
			}
			p.print(StringUtils.rightPad("", COLUNAS," ") + (char) 27);
			p.print(StringUtils.rightPad("[QTD. Mercadorias:  "+saida.getProdutos().size()+"]",COLUNAS)+ (char) 27);
			
			
			p.print(StringUtils.rightPad("", COLUNAS,"=") + (char) 27);
			p.print(StringUtils.rightPad("", COLUNAS," ") + (char) 27);
			
			total.setScale(2, RoundingMode.CEILING);
			p.print("TOTAL ITENS   " + StringUtils.leftPad(NumberFormat.getCurrencyInstance().format(totalItem.doubleValue()),COLUNAS - 14," ") + (char) 27);
			p.print("DESCONTO      " + StringUtils.leftPad(NumberFormat.getCurrencyInstance().format(totalDesconto.doubleValue()),COLUNAS - 14," ") + (char) 27);
			p.print("TOTAL GERAL   " + StringUtils.leftPad(NumberFormat.getCurrencyInstance().format(total.doubleValue()),COLUNAS - 14," ") + (char) 27);

			p.print(StringUtils.rightPad("", COLUNAS," ") + (char) 27);
			p.print(StringUtils.rightPad("", COLUNAS," ") + (char) 27);
			p.print(centerCols(empresa.getMensagem()) + (char) 27);
			p.print(StringUtils.rightPad("", COLUNAS," ") + (char) 27);
			p.print(" " + (char) 27 + 'w');

			FileInputStream fi = new FileInputStream("c.txt");

			// Cria um Doc para impressão a partir do arquivo exemplo.txt
			Doc documentoTexto = new SimpleDoc(fi, docFlavor, attributes);

			// Configura o conjunto de parametros para a impressora
			PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();

			boolean mostrarDialogo = false;
			System.out.println(mostrarDialogo);

			if (mostrarDialogo) {
				// exibe um dialogo de configuracoes de impressao
				PrintService servico = ServiceUI.printDialog(null, 320, 240, servicosImpressao, impressora, docFlavor,
						printerAttributes);

				if (servico != null) {
					DocPrintJob printJob = servico.createPrintJob();
					printJob.print(documentoTexto, printerAttributes);
				}
			} else {
				// Cria uma tarefa de impressão
				DocPrintJob printJob = impressora.createPrintJob();

				// Adiciona propriedade de impressão: imprimir duas cópias
				printerAttributes.add(new Copies(1));

				// Imprime o documento sem exibir uma tela de dialogo
				printJob.print(documentoTexto, printerAttributes);

			}
		} catch (IOException e) {
			// System.out.println("ERRO IO"+e.getMessage());
		} catch (PrintException ex2) {
			ex2.getMessage();
		}
	}
	
	
	public static String centerCols(String conteudo) {
		String retorno ="";
		if(conteudo != null) {
			int preencher = (COLUNAS - conteudo.length()) / 2;
			retorno = StringUtils.leftPad("", preencher," ");
			retorno += conteudo;
			retorno = StringUtils.rightPad(retorno, COLUNAS," ");
		}else {
			retorno = StringUtils.rightPad(retorno, COLUNAS," ");
		}
		return retorno;
	}
	
}