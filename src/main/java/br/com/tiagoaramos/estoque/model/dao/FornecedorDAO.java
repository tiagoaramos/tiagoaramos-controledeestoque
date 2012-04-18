package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.model.FornecedorModel;


public class FornecedorDAO  extends DAO<FornecedorModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static FornecedorDAO instacia = null;
	
	private FornecedorDAO() {
		super(new FornecedorModel());
	}

	public static FornecedorDAO getInstance(){
		if(instacia == null){
			FornecedorDAO.instacia = new FornecedorDAO();
		}
		return instacia;
	}
}
