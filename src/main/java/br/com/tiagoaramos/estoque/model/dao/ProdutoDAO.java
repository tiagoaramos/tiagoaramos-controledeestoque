package br.com.tiagoaramos.estoque.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagoaramos.estoque.model.ProdutoModel;


public class ProdutoDAO  extends DAO<ProdutoModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static ProdutoDAO instacia = null;
	
	private ProdutoDAO() {
		super(new ProdutoModel());
	}
	
	public static ProdutoDAO getInstance(){
		if(instacia == null){
			ProdutoDAO.instacia = new ProdutoDAO();
		}
		return instacia;
	}
	
	public List<ProdutoModel> pesquisar(ProdutoModel model){
		String sql = "SELECT c FROM ProdutoModel c WHERE 1 = 1 ";
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		if(model != null){
			if(model.getNome() != null && !model.getNome().equals("")){
				sql += " AND c.nome like :nome ";
				mapa.put("nome", "%"+model.getNome()+"%");
			}
			if(model.getIdentificador() != null && !model.getIdentificador().equals("")){
				sql += " AND c.identificador = :identificador ";
				mapa.put("identificador", model.getIdentificador());
			}
		}
		sql += " ORDER BY c.nome ";
		return executaQueryList(mapa, sql);
	}

	public ProdutoModel buscarPorIdentificador(String identificador) {
		ProdutoModel model = new ProdutoModel();
		model.setIdentificador(identificador);
		List<ProdutoModel> lista = pesquisar(model);
		if(lista != null && lista.size() == 1)
			return lista.get(0);
		return null;
	}
	
}
