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

/**
 * @version 1.0
 * @created 17-set-2009 20:56:19
 */
@Entity
public class EntradaProdutoModel implements MovimetaProdutoIf  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617951037308642893L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="enpid")
	private Integer id;

	@Column(name="enpquantidade")
	private BigDecimal quantidade;	

	@Column(name="enppreco")
	private BigDecimal precoCompra;	

	@ManyToOne
	@JoinColumn(name="enpproid")
	private ProdutoModel produto;
	
	@ManyToOne
	@JoinColumn(name="enpentid")
	private EntradaModel entrada;

	public EntradaProdutoModel(){}
			
	
	public EntradaProdutoModel(Integer id, BigDecimal quantidade,
			ProdutoModel produto, EntradaModel entrada) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.produto = produto;
		this.entrada = entrada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	public EntradaModel getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradaModel entrada) {
		this.entrada = entrada;
	}


	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}


	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}
	
	
	@Override
	public Date getData() {
		return getEntrada().getData();
	}	
	
}