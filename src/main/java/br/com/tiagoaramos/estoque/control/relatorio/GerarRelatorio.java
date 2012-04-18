package br.com.tiagoaramos.estoque.control.relatorio;

import java.io.File;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class GerarRelatorio {
	public String gerarRelatorio(JRDataSource dataSource, HashMap<String, Object> parameters, String file, String dest){
		try{
			File destino = new File( dest + ".pdf");
			if(destino.isFile())
				destino.delete();
			
			JasperReport report = JasperCompileManager.compileReport(GerarRelatorio.class.getResourceAsStream("/jaspper/" + file + ".jrxml"));
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
			JasperExportManager.exportReportToPdfFile(print, dest + ".pdf");
			
			destino = new File( dest + ".pdf");
			
			return destino.getAbsolutePath();
			
		}catch(JRException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
