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
public class ProducaoModel implements Model  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617951037308642893L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="prdid")
	private Integer id;

	@Column(name="prddata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="prdusuid", nullable=false)
	private UsuarioModel usuario;
	
	@Column(name="prdcusto")
	private BigDecimal custo;
	
	@ManyToOne
	@JoinColumn(name="prdentid", nullable=false)
	private EntradaModel entrada;
	
	@ManyToOne
	@JoinColumn(name="prdsaiid", nullable=false)
	private VendaModel saida;

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

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public EntradaModel getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradaModel entrada) {
		this.entrada = entrada;
	}

	public VendaModel getSaida() {
		return saida;
	}

	public void setSaida(VendaModel saida) {
		this.saida = saida;
	}
		
}