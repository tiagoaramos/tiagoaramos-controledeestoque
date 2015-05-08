package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.model.ProducaoModel;


public class ProducaoProdutoDAO  extends DAO<ProducaoModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static ProducaoProdutoDAO instacia = null;
	
	private ProducaoProdutoDAO() {
		super(new ProducaoModel());
	}

	
	public static ProducaoProdutoDAO getInstance(){
		if(instacia == null){
			ProducaoProdutoDAO.instacia = new ProducaoProdutoDAO();
		}
		return instacia;
	}
	
}
