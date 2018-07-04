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
 * @created 17-set-2009 20:56:20
 */
@Entity
public class VendaProdutoModel implements MovimetaProdutoIf  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7236449969475240270L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="valor")
	private BigDecimal precoVenda;
	
	@Column(name="quantidade")
	private BigDecimal quantidade;

	@ManyToOne
	@JoinColumn(name="produto")
	private ProdutoModel produto;
	
	@ManyToOne
	@JoinColumn(name="venda")
	private VendaModel venda;

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

	public Date getData() {
		return getVenda().getData();
	}

	public VendaModel getVenda() {
		return venda;
	}

	public void setVenda(VendaModel venda) {
		this.venda = venda;
	}
	
}