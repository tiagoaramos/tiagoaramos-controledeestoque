package br.com.tiagoaramos.estoque.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:19
 */
@Entity
public class CategoriaProdutoModel implements Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4170766695064630373L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="catid")
	private Integer id;
	@Column(name="catnome")
	private String nome;
	
	@OneToMany
	@JoinColumn(referencedColumnName="catid")
	private List<ProdutoModel> produtos;

	public CategoriaProdutoModel(){

	}

	public void finalize() throws Throwable {

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

	public List<ProdutoModel> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoModel> produtos) {
		this.produtos = produtos;
	}

	public String toString() {
		return getNome();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((produtos == null) ? 0 : produtos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaProdutoModel other = (CategoriaProdutoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		
		return true;
	}
	

	
}