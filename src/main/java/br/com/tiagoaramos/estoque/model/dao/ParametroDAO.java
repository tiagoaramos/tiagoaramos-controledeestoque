package br.com.tiagoaramos.estoque.model.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.ParametroModel;

public class ParametroDAO  extends DAO<ParametroModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static ParametroDAO instacia = null;
	
	
	private ParametroDAO() {
		super(new ParametroModel());
	}
	
	public static ParametroDAO getInstance(){
		if(instacia == null){
			ParametroDAO.instacia = new ParametroDAO();
		}
		return instacia;
	}
	
	public static ParametroModel obter(String chave) {
		Map<String, Object> mapa = new LinkedHashMap<String, Object>();
		String sql = "SELECT a FROM br.com.tiagoaramos.estoque.model.ParametroModel a WHERE ";
		sql += " a.chave =:chave ";
		mapa.put("chave", chave );
		
		ParametroDAO dao = ParametroDAO.getInstance();
		ParametroModel param ;
		try{
			param = dao.executaQuerySingle(mapa, sql);
		}catch (Exception e) {
			param = new ParametroModel();
			param.setChave(chave);
			param.setValor("");
			try {
				dao.persiste(param);
			} catch (PersistenciaException e1) {
				e1.printStackTrace();
			}
		}
		return param;
	}
	
}
