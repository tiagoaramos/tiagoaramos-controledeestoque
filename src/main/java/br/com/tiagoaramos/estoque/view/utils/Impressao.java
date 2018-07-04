package br.com.tiagoaramos.estoque.view.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

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

			p.print(center80Cols (empresa.getNome()) + (char) 27);
			p.print(center80Cols (empresa.getCnpj()) + (char) 27);
			p.print(center80Cols (empresa.getEndereco()) + (char) 27);
			p.print(center80Cols (empresa.getTelefone()) + (char) 27);

			p.print(StringUtils.rightPad("", 80,"=") + (char) 27);

			int i = 1;
			BigDecimal total = new BigDecimal(0.0d);
			for (VendaProdutoModel item : saida.getProdutos()) {
				String linha = (item.getQuantidade().toPlainString()) + " x " + item.getProduto().getNome()+ "   ";
				String valorLinha = ": R$ "+NumberFormat.getCurrencyInstance().format(item.getPrecoVenda().doubleValue());
				linha = StringUtils.rightPad(linha, 80 - valorLinha.length(),"_") + valorLinha;
				p.print( linha + (char) 27);
				total.add(item.getPrecoVenda());
			}
			p.print("" + (char) 27);
			p.print(StringUtils.rightPad("", 80,"=") + (char) 27);
			p.print("" + (char) 27);
			total.setScale(2, RoundingMode.CEILING);
			p.print(StringUtils.leftPad("TOTAL  R$ " + total.toPlainString(),80," ") + (char) 27);
			p.print("" + (char) 27);
			p.print("" + (char) 27);
			p.print("" + empresa.getMensagem() + (char) 27);
			p.print("" + (char) 27 + 'w');

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
	
	
	public static String center80Cols(String conteudo) {
		String retorno ="";
		if(conteudo != null) {
			int preencher = (80 - conteudo.length()) / 2;
			retorno = StringUtils.leftPad("", preencher," ");
			retorno += conteudo;
			retorno = StringUtils.rightPad(retorno, 80," ");
		}else {
			retorno = StringUtils.rightPad(retorno, 80," ");
		}
		return retorno;
	}
	
}