package br.com.tiagoaramos.estoque.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:19
 */
@Entity
public class RetiradaModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115258308695705410L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="retid")
	private Integer id;
	
	@Column(name="retdata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@Column(name="retvalor")
	private BigDecimal valor;
	
	@Column(name="retdescricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name="retusuid", nullable=false)
	private UsuarioModel usuario;
	
	public RetiradaModel(){}
	
	public RetiradaModel(Integer id, Date data, BigDecimal valor,
			String descricao) {
		super();
		this.id = id;
		this.data = data;
		this.valor = valor;
		this.descricao = descricao;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}
	
}