package br.com.tiagoaramos.estoque.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.impl.SessionImpl;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.RetiradaModel;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;


public class RetiradaDAO  extends DAO<RetiradaModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7022855005267602428L;
	private static RetiradaDAO instacia = null;
	
	private RetiradaDAO() {
		super(new RetiradaModel());
	}
	public static RetiradaDAO getInstance(){
		if(instacia == null){
			RetiradaDAO.instacia = new RetiradaDAO();
		}
		return instacia;
	}
	public Double buscarTotalPorData(Date data) {
		String sql = "SELECT SUM(A.retvalor) TOTAL " +
					 "  FROM retiradamodel A " +
					 " WHERE DAY(A.retdata) = DAY(?) " +
					 "       AND a.retusuid = ? ";
		       
		try {
			SessionImpl impl = (SessionImpl) getEm().getDelegate();
			Connection conn = impl.connection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDate(1,new java.sql.Date(data.getTime()));
			st.setInt(2, ControleSessaoUtil.usuarioLogado.getId().intValue());
			ResultSet rs = st.executeQuery();
			if(rs.next()){
				return new Double(rs.getDouble("TOTAL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Double("0.00");
	}

	@Override
	public void persiste(RetiradaModel retirada) throws PersistenciaException {
		retirada.setUsuario(ControleSessaoUtil.usuarioLogado);
		super.persiste(retirada);
	}
	
	@Override
    public List<RetiradaModel> buscarTodos() {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("usuid",ControleSessaoUtil.usuarioLogado.getId());
        return executaQueryList(mapa, "SELECT c FROM RetiradaModel c where c.usuario.id = :usuid");
	}

}
