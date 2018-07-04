package br.com.tiagoaramos.estoque.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.impl.SessionImpl;

import br.com.tiagoaramos.estoque.control.relatorio.GerarRelatorio;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
import br.com.tiagoaramos.estoque.model.VendaProdutoModel;
import br.com.tiagoaramos.estoque.utils.enums.TipoSaida;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;
import net.sf.jasperreports.engine.JRResultSetDataSource;

public class SaidaProdutoDAO extends DAO<VendaProdutoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static SaidaProdutoDAO instacia = null;
	
	
	private SaidaProdutoDAO() {
		super(new VendaProdutoModel());
	}

	public static SaidaProdutoDAO getInstance(){
		if(instacia == null){
			SaidaProdutoDAO.instacia = new SaidaProdutoDAO();
		}
		return instacia;
	}

	public ResultSet fechamento(Date data) {

		String sql = "SELECT if(tipoT IS NULL, '0', tipoT) tipoT, "
				+ "       id, "
				+ "       data, "
				+ "       tipo, "
				+ "       codigo, "
				+ "       quantidade, "
				+ "       preco, "
				+ "       pronome, "
				+ "       usuid, "
				+ "       total "
				+ "  FROM (  SELECT data, "
				+ "                 tipoT, "
				+ "                 id, "
				+ "                 tipo, "
				+ "                 codigo, "
				+ "                 quantidade, "
				+ "                 preco, "
				+ "             pronome, "
				+ "             usuid, "
				+ "             sum(total) total "
				+ "        FROM ((SELECT A.saidata data, "
				+ "                      '0' tipoT, "
				+ "                      A.saiid id, "
				+ "                      A.saitipo tipo, "
				+ "                      B.sapid codigo, "
				+ "                      B.sapquantidade quantidade, "
				+ "                      B.sappreco preco, "
				+ "                      (B.sapquantidade * B.sappreco) total, "
				+ "                      C.pronome pronome, "
				+ "                      a.saiusuid usuid "
				+ "                 FROM saidamodel A, saidaprodutomodel B, produtomodel C "
				+ "                WHERE     A.saiid = B.sapsaiid "
				+ "                      AND B.sapproid = C.proid "
				+ "                      AND day(a.saidata) = day(?)) "
				+ "              UNION "
				+ "              (SELECT A.entdata data, "
				+ "                      '1' tipoT, "
				+ "                      A.entid id, "
				+ "                     ifnull(A.enttipo,0) tipo, "
				+ "                      B.enpid codigo, "
				+ "                      B.enpquantidade quantidade, "
				+ "                      B.enppreco preco, "
				+ "                      if(A.enttipo = 5 ,0,(B.enpquantidade * B.enppreco * -1)) total, "
				+ "                      C.pronome pronome, "
				+ "                      a.entusuid usuid "
				+ "                 FROM entradamodel A, entradaprodutomodel B, produtomodel C "
				+ "                WHERE     A.entid = B.enpentid "
				+ "                      AND B.enpproid = C.proid "
				+ "                      AND day(a.entdata) = day(?)) "
				+ "              UNION "
				+ "              (SELECT A.retdata data, "
				+ "                      '2' tipoT, "
				+ "                      A.retid id, "
				+ "                      '1' tipo, "
				+ "                      A.retid codigo, "
				+ "                      0 quantidade, "
				+ "                      (a.retvalor * -1) preco, "
				+ "                      (a.retvalor * -1) total, "
				+ "                      a.retdescricao pronome, "
				+ "                      a.retusuid usuid "
				+ "                 FROM retiradamodel A where  day(a.retdata) = day(?) )) view "
				+ "    WHERE usuid = ? "
				+ "    GROUP BY data, " + "             tipoT, "
				+ "             id, " + "             tipo, "
				+ "             codigo, " + "             quantidade, "
				+ "             preco, "
				+ "             usuid, "
				+ "             pronome WITH ROLLUP) view "
				+ " WHERE (    tipoT IS NOT NULL " + "    AND id IS NOT NULL "
				+ "    AND data IS NOT NULL " + "    AND tipo IS NOT NULL "
				+ "    AND codigo IS NOT NULL "
				+ "    AND quantidade IS NOT NULL "
				+ "    AND preco IS NOT NULL "
				+ "    AND pronome IS NOT NULL) " + "   OR(    tipoT IS NULL "
				+ "      AND id IS NULL " + "      AND data IS NULL "
				+ "      AND tipo IS NULL " + "      AND codigo IS NULL "
				+ "      AND quantidade IS NULL " + "      AND preco IS NULL "
				+ "      AND pronome IS NULL) ";

		try {
			SessionImpl impl = (SessionImpl) getEm().getDelegate();
			Connection conn = impl.connection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDate(1,new java.sql.Date(data.getTime()));
			st.setDate(2,new java.sql.Date(data.getTime()));
			st.setDate(3,new java.sql.Date(data.getTime()));
			st.setInt(4, ControleSessaoUtil.usuarioLogado.getId());
			ResultSet rs = st.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public Double movimentoDoDia(TipoSaida tipo, Date data){
		
		String sql = "SELECT sum(sappreco * sapquantidade) TOTAL ";
		sql += " FROM saidamodel A, saidaprodutomodel B ";
		sql += " WHERE     a.saiid = B.sapsaiid ";
		sql += "       AND saitipo = ? ";
		sql += "       AND saiusuid = ? ";
		sql += "       AND day(a.saidata) = day(?) ";
		       
		       
		try {
			SessionImpl impl = (SessionImpl) getEm().getDelegate();
			Connection conn = impl.connection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, tipo.getCodigo().intValue());
			st.setInt(2, ControleSessaoUtil.usuarioLogado.getId().intValue());
			st.setDate(3, new java.sql.Date(data.getTime()));
			ResultSet rs = st.executeQuery();
			if(rs.next()){
				return new Double(rs.getDouble("TOTAL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Double("0.00");
	}
	

	public static void main(String[] args){
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		ResultSet rs = SaidaProdutoDAO.getInstance().fechamento(cal.getTime());
		
		GerarRelatorio gerar = new GerarRelatorio();
		gerar.gerarRelatorio(new JRResultSetDataSource(rs), null, "fechamentocaixa", "fechamento_"+new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
		
	}

	public ArrayList<VendaProdutoModel> buscarPorProduto(ProdutoModel produto){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("produto", produto);
		return (ArrayList<VendaProdutoModel>) executaQueryList(map, "SELECT c FROM SaidaProdutoModel c  where c.produto = :produto");
	}

}
