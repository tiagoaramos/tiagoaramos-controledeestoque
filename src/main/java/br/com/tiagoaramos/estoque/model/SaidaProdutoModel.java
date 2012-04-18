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
 * @created 17-set-2009 20:56:20
 */
@Entity
public class SaidaProdutoModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7236449969475240270L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sapid")
	private Integer id;
	
	@Column(name="sappreco")
	private BigDecimal precoVenda;
	
	@Column(name="sapquantidade")
	private Integer quantidade;

	@ManyToOne
	@JoinColumn(name="sapproid")
	private ProdutoModel produto;
	
	@ManyToOne
	@JoinColumn(name="sapsaiid")
	private SaidaModel saida;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
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

	public SaidaModel getSaida() {
		return saida;
	}

	public void setSaida(SaidaModel saida) {
		this.saida = saida;
	}
	
}