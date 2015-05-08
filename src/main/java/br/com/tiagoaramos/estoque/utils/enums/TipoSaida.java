package br.com.tiagoaramos.estoque.utils.enums;

public enum TipoSaida {

	CARTAO(0, "Venda - Cart�o "), 
	DINHEIRO(1, "Venda - Dinheiro "), 
	TROCA(2, "Troca"), 
	BAIXA(3, "Baixa"), 
	DOACAO(4, "Doa��o"),
	USO_CONSUMO(5, "Uso Consumo");

	private Integer CODIGO;
	private String DESCRICAO;

	TipoSaida(Integer codigo, String DESCRICAO) {
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
