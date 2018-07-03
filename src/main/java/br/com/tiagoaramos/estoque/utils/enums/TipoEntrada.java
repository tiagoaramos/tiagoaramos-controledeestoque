package br.com.tiagoaramos.estoque.utils.enums;

public enum TipoEntrada {
	
	COMPRA( (Integer) 1, "Compra" ),
	DEVOLUCAO( (Integer) 2, "Devolução" ),
	TROCA ((Integer) 3, "Troca"),
	PRODUCAO ((Integer) 4, "Produção"),
	SALDO0 ((Integer) 5, "Venda Saldo 0");
	
	private Integer CODIGO;
	private String DESCRICAO;

	TipoEntrada(Integer codigo, String DESCRICAO) {
		this.CODIGO = codigo;
		this.DESCRICAO = DESCRICAO;
	}
	
	public Integer getCodigo() {
		return this.CODIGO;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}

	public String toString() {
		return getDESCRICAO();
	}
	
}
