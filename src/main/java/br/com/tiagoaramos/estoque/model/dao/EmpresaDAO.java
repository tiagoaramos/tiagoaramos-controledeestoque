package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.model.EmpresaModel;

public class EmpresaDAO  extends DAO<EmpresaModel>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8027452703617159603L;
	private static EmpresaDAO instacia = null;
	
	private EmpresaDAO() {
		super(new EmpresaModel());
	}
	public static EmpresaDAO getInstance(){
		if(instacia == null){
			EmpresaDAO.instacia = new EmpresaDAO();
		}
		return instacia;
	}
	
}
