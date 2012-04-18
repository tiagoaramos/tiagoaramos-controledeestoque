package br.com.tiagoaramos.estoque.model.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.DAO;

public class UsuarioDAO  extends DAO<UsuarioModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static UsuarioDAO instacia = null;
	
	private UsuarioDAO() {
		super(new UsuarioModel());
	}
	public static UsuarioDAO getInstance(){
		if(instacia == null){
			UsuarioDAO.instacia = new UsuarioDAO();
		}
		return instacia;
	}
	public List<UsuarioModel> pesquisar(UsuarioModel usuario) {
		Map<String, Object> mapa = new LinkedHashMap<String, Object>();
		String sql = "SELECT a FROM UsuarioModel a WHERE 1=1 ";
		 
		if(usuario.getId() != null && usuario.getId().intValue() != 0){
			sql += " AND a.id = :id ";
			mapa.put("id", usuario.getId());
		}
		if(usuario.getLogin() != null && !usuario.getLogin().equals("")){
			sql += " AND a.login like :login ";
			mapa.put("login", "%"+usuario.getLogin()+"%" );
		}
		if(usuario.getSenha() != null && !usuario.getSenha().equals("")){
			sql += " AND a.senha like :senha ";
			mapa.put("senha", "%"+usuario.getSenha()+"%" );
		}
		if(usuario.getTipoUsuario() != null){
			sql += " AND a.tipoUsuario =:tipoUsuario ";
			mapa.put("tipoUsuario", usuario.getTipoUsuario() );
		}

		return executaQueryList(mapa, sql);
	}

	public UsuarioModel buscarPorLogin(String login) {
		Map<String, Object> mapa = new LinkedHashMap<String, Object>();
		
		String sql = "SELECT a FROM UsuarioModel a WHERE ";
		sql += " a.login =:login ";
		mapa.put("login", login );
		
		return executaQuerySingle(mapa, sql);
	}
	
}
