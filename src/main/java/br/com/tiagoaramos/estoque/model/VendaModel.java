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
public class VendaModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115258308695705410L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="data")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="tipo")
	private TipoSaida tipo;
	
	@OneToMany(mappedBy="venda",cascade=CascadeType.ALL)
	private List<VendaProdutoModel> produtos;
	
	@ManyToOne
	@JoinColumn(name="usuario", nullable=false)
	private UsuarioModel usuario;

	@ManyToOne
	@JoinColumn(name="cliente", nullable=true)
	private ClienteModel cliente;

	@OneToMany(mappedBy="venda",cascade=CascadeType.ALL)
	private List<RecebimentoModel> recebimentos;
	
	
	public VendaModel(Integer id, Date data, List<VendaProdutoModel> produtos) {
		super();
		this.id = id;
		this.data = data;
		this.produtos = produtos;
	}


	public VendaModel() {
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


	public List<VendaProdutoModel> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<VendaProdutoModel> produtos) {
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


	public ClienteModel getCliente() {
		return cliente;
	}


	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}


	public List<RecebimentoModel> getRecebimentos() {
		return recebimentos;
	}


	public void setRecebimentos(List<RecebimentoModel> recebimentos) {
		this.recebimentos = recebimentos;
	}

	
}