package br.com.tiagoaramos.estoque.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.ProdutoModel;
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
	
	public ArrayList<SaidaModel> buscarPorProduto(ProdutoModel produto){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("produto", produto);
		return (ArrayList<SaidaModel>) executaQueryList(map, "SELECT c FROM SaidaModel c  where c.produto = :produto");
	}
	
}
