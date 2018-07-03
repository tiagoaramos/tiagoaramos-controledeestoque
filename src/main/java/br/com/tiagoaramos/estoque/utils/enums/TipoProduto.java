package br.com.tiagoaramos.estoque.utils.enums;

public enum TipoProduto {
	
	UNIDADE( (byte) 0 ,"UNIDADE" ),
	PESO( (byte) 2,"PESO" );

	public byte CODIGO;
	public String DESCRICAO;
	
	TipoProduto(byte codigo,String descricao) {
		this.CODIGO = codigo;
		this.DESCRICAO = descricao;
	}
	
	public byte getCodigo() {
		return this.CODIGO;
	}
	
	public String getDescricao() {
		return this.DESCRICAO;
	}
	
	@Override
	public String toString() {
		return this.DESCRICAO;
	}

}
