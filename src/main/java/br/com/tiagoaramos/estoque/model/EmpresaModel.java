package br.com.tiagoaramos.estoque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:19
 */
@Entity
public class EmpresaModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4530163223173414817L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo")
	private Integer id;
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "nome")
	private String nome;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "mensagem")
	private String mensagem;
	@Column(name = "telefone")
	private String telefone;

	public EmpresaModel() {}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getMensagem() {
		return mensagem;
	}


	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	
	
}