package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.model.EntradaProdutoModel;


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
	
}
