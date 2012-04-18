package br.com.tiagoaramos.estoque.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.tiagoaramos.estoque.utils.enums.TipoUsuario;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:20
 */
@Entity
public class UsuarioModel implements Model  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4600384531091450080L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String email;
	private String login;
	private String nome;
	private String senha;
	private TipoUsuario tipoUsuario;
	

	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<SaidaModel> saidas;
	
	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<RetiradaModel> retiradas;
	
	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<EntradaModel> entradas;
	
	public UsuarioModel(){

	}

	public void finalize() throws Throwable {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<SaidaModel> getSaidas() {
		return saidas;
	}

	public void setSaidas(List<SaidaModel> saidas) {
		this.saidas = saidas;
	}

	public List<RetiradaModel> getRetiradas() {
		return retiradas;
	}

	public void setRetiradas(List<RetiradaModel> retiradas) {
		this.retiradas = retiradas;
	}

	public List<EntradaModel> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<EntradaModel> entradas) {
		this.entradas = entradas;
	}

	@Override
	public String toString() {
		return getNome();
	}
	
}