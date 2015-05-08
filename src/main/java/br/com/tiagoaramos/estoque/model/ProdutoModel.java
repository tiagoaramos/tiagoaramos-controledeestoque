package br.com.tiagoaramos.estoque.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:20
 */
@Entity
public class ProdutoModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7529953379375618899L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="proid")
	private Integer id;
	@Column(name="proidentificador", unique=true)
	private String identificador;
	@Column(name="proestoque")
	private Integer estoqueAtual;
	@Column(name="prosaldoinicial")
	private Integer saldoInicial;
	@Column(name="pronome")
	private String nome;
	@Column(name="propreco")
	private BigDecimal preco;
	@Column(name="proprecovenda")
	private BigDecimal precoVenda;
	
	@ManyToOne
	@JoinColumn(name="catid")
	private CategoriaProdutoModel categoria;

	@ManyToOne
	@JoinColumn(name="forid")
	private FornecedorModel fornecedor;
	
	@OneToMany(mappedBy="produto")	
	private List<EntradaProdutoModel> comprasProdutos;
	
	@OneToMany
	@JoinColumn(referencedColumnName="proid")
	private List<SaidaProdutoModel> vendas;
	
	public ProdutoModel(){

	}

	public void finalize() throws Throwable {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(Integer estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public FornecedorModel getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorModel fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<SaidaProdutoModel> getVendas() {
		return vendas;
	}

	public void setVendas(List<SaidaProdutoModel> vendas) {
		this.vendas = vendas;
	}

	public CategoriaProdutoModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProdutoModel categoria) {
		this.categoria = categoria;
	}

	public List<EntradaProdutoModel> getComprasProdutos() {
		return comprasProdutos;
	}

	public void setComprasProdutos(List<EntradaProdutoModel> comprasProdutos) {
		this.comprasProdutos = comprasProdutos;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Integer getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Integer saldoInicial) {
		this.saldoInicial = saldoInicial;
	}


}