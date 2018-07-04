package br.com.tiagoaramos.estoque.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:20
 */
@Entity
public class ClienteModel implements Model  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2816645917245338599L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="nome")
	private String nome;

	private String telefone;
	
	private Date dataCadastro;
	
	private Date ultimaCompra;
	
	private String cpf;
	
	private String rg;
	
	private String referencias;
	
	private String comentarios;
	
	@OneToMany(mappedBy="cliente",cascade=CascadeType.ALL)
	private List<VendaModel> compras;

	public ClienteModel() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getUltimaCompra() {
		return ultimaCompra;
	}

	public void setUltimaCompra(Date ultimaCompra) {
		this.ultimaCompra = ultimaCompra;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getReferencias() {
		return referencias;
	}

	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public List<VendaModel> getCompras() {
		return compras;
	}

	public void setCompras(List<VendaModel> compras) {
		this.compras = compras;
	}
	
	

}