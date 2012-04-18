package br.com.tiagoaramos.estoque.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tiagoaramos.estoque.utils.enums.TipoSaida;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:19
 */
@Entity
public class SaidaModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115258308695705410L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="saiid")
	private Integer id;
	
	@Column(name="saidata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="saitipo")
	private TipoSaida tipo;
	
	@OneToMany(mappedBy="saida",cascade=CascadeType.ALL)
	private List<SaidaProdutoModel> produtos;
	
	@ManyToOne
	@JoinColumn(name="saiusuid", nullable=false)
	private UsuarioModel usuario;
	
	public SaidaModel(Integer id, Date data, List<SaidaProdutoModel> produtos) {
		super();
		this.id = id;
		this.data = data;
		this.produtos = produtos;
	}


	public SaidaModel() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public List<SaidaProdutoModel> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<SaidaProdutoModel> produtos) {
		this.produtos = produtos;
	}


	public TipoSaida getTipo() {
		return tipo;
	}


	public void setTipo(TipoSaida tipo) {
		this.tipo = tipo;
	}


	public UsuarioModel getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	
}