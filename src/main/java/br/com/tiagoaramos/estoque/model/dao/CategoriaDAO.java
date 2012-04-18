package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.model.CategoriaProdutoModel;


public class CategoriaDAO  extends DAO<CategoriaProdutoModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static CategoriaDAO instacia = null;
	
	private CategoriaDAO() {
		super(new CategoriaProdutoModel());
	}
	
	
	public static CategoriaDAO getInstance(){
		if(instacia == null){
			CategoriaDAO.instacia = new CategoriaDAO();
		}
		return instacia;
	}

	
}
