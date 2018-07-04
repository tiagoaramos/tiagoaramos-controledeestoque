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

import br.com.tiagoaramos.estoque.utils.enums.StatusRecebimento;

/**
 * @version 1.0
 * @created 17-set-2009 20:56:20
 */
@Entity
public class RecebimentoModel implements Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6521762226701019215L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="valor")
	private BigDecimal valor;

	@Column(name="saldo")
	private BigDecimal saldo;

	@Column(name="vencimento")
	private Date vencimento;
	
	@Column(name="data_baixa")
	private Date dataBaixa;

	@ManyToOne
	@JoinColumn(name="cliente")
	private ClienteModel cliente;
	
	@ManyToOne
	@JoinColumn(name="venda")
	private VendaModel venda;
		
	private StatusRecebimento status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public VendaModel getVenda() {
		return venda;
	}

	public void setVenda(VendaModel venda) {
		this.venda = venda;
	}

	public StatusRecebimento getStatus() {
		return status;
	}

	public void setStatus(StatusRecebimento status) {
		this.status = status;
	}

		
}