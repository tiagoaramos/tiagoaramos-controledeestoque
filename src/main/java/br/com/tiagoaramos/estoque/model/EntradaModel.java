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

import br.com.tiagoaramos.estoque.utils.enums.TipoEntrada;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:19
 */
@Entity
public class EntradaModel implements Model  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617951037308642893L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="entid")
	private Integer id;

	@Column(name="entdata")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="enttipo")
	private TipoEntrada tipoEntrada;
	
	@OneToMany(mappedBy="entrada",cascade=CascadeType.ALL)	 
	private List<EntradaProdutoModel> comprasProdutos;

	@ManyToOne
	@JoinColumn(name="entusuid", nullable=false)
	private UsuarioModel usuario;
	
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

	public TipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(TipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public List<EntradaProdutoModel> getComprasProdutos() {
		return comprasProdutos;
	}

	public void setComprasProdutos(List<EntradaProdutoModel> comprasProdutos) {
		this.comprasProdutos = comprasProdutos;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

}