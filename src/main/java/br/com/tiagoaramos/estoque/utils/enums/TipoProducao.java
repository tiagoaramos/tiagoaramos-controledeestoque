package br.com.tiagoaramos.estoque.utils.enums;

public enum TipoProducao {
	
	MATERIA_PRIMA( (Integer) 1, "Materia Prima" ),
	PRODUTO_FINAL( (Integer) 2, "Produto Final" );
	
	private Integer CODIGO;
	private String DESCRICAO;

	TipoProducao(Integer codigo, String DESCRICAO) {
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
