package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.SaidaModel;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;


public class SaidaDAO  extends DAO<SaidaModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static SaidaDAO instacia = null;
	
	
	private SaidaDAO() {
		super(new SaidaModel());
	}
	public static SaidaDAO getInstance(){
		if(instacia == null){
			SaidaDAO.instacia = new SaidaDAO();
		}
		return instacia;
	}
	
	@Override
	public void persiste(SaidaModel saida) throws PersistenciaException {
		saida.setUsuario(ControleSessaoUtil.usuarioLogado);
		super.persiste(saida);
	}
	
}
