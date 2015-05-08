package br.com.tiagoaramos.estoque.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.tiagoaramos.estoque.model.EntradaProdutoModel;
import br.com.tiagoaramos.estoque.model.ProdutoModel;


public class EntradaProdutoDAO  extends DAO<EntradaProdutoModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static EntradaProdutoDAO instacia = null;
	
	private EntradaProdutoDAO() {
		super(new EntradaProdutoModel());
	}

	
	public static EntradaProdutoDAO getInstance(){
		if(instacia == null){
			EntradaProdutoDAO.instacia = new EntradaProdutoDAO();
		}
		return instacia;
	}
	

	public ArrayList<EntradaProdutoModel> buscarPorProduto(ProdutoModel produto){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("produto", produto);
		return (ArrayList<EntradaProdutoModel>) executaQueryList(map, "SELECT c FROM EntradaProdutoModel c  where c.produto = :produto");
	}
	
}
