package br.com.tiagoaramos.estoque.utils.enums;

public enum TipoRelatorio {

	FECHAMENTO_CAIXA( (Integer) 1, "fechamentocaixa", "Fechamento de caixa" ),
	LANCAMENTOS( (Integer) 2, "fechamentocaixa", "Lançamentos do dia" );
	
	private Integer CODIGO;
	private String DESCRICAO;
	private String jasper;

	TipoRelatorio(Integer codigo,String jasper, String DESCRICAO) {
		this.CODIGO = codigo;
		this.jasper = jasper;
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

	public String getJasper() {
		return jasper;
	}
	
}
