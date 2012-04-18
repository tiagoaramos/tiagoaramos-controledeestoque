package br.com.tiagoaramos.estoque.model;

import java.math.BigDecimal;

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
public class EntradaProdutoModel implements Model  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6617951037308642893L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="enpid")
	private Integer id;

	@Column(name="enpquantidade")
	private Integer quantidade;	

	@Column(name="enppreco")
	private BigDecimal precoCompra;	

	@ManyToOne
	@JoinColumn(name="enpproid")
	private ProdutoModel produto;
	
	@ManyToOne
	@JoinColumn(name="enpentid")
	private EntradaModel entrada;

	public EntradaProdutoModel(){}
			
	
	public EntradaProdutoModel(Integer id, Integer quantidade,
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
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
	
	
	
	
}