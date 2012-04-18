package br.com.tiagoaramos.estoque.model.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import br.com.tiagoaramos.estoque.model.AberturaCaixaModel;

public class AberturaCaixaDAO  extends DAO<AberturaCaixaModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static AberturaCaixaDAO instacia = null;
	
	
	private AberturaCaixaDAO() {
		super(new AberturaCaixaModel());
	}

	

	public AberturaCaixaModel buscarPorData(Date data) {
		Map<String, Object> mapa = new LinkedHashMap<String, Object>();
		
		String sql = "SELECT a FROM br.com.tiagoaramos.estoque.model.AberturaCaixaModel a WHERE ";
		sql += " a.data =:data ";
		mapa.put("data", data );
		try{
			return executaQuerySingle(mapa, sql);
    	}catch (NoResultException e) {
			return null;
		}
	}

	public static AberturaCaixaDAO getInstance(){
		if(instacia == null){
			AberturaCaixaDAO.instacia = new AberturaCaixaDAO();
		}
		return instacia;
	}
	
}
