package br.com.tiagoaramos.estoque.model.dao;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.EntradaModel;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;


public class EntradaDAO  extends DAO<EntradaModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static EntradaDAO instacia = null;
	
	private EntradaDAO() {
		super(new EntradaModel());
	}

	public static EntradaDAO getInstance(){
		if(instacia == null){
			EntradaDAO.instacia = new EntradaDAO();
		}
		return instacia;
	}

	@Override
	public void persiste(EntradaModel entrada) throws PersistenciaException {
		entrada.setUsuario(ControleSessaoUtil.usuarioLogado);
		super.persiste(entrada);
	}


}
