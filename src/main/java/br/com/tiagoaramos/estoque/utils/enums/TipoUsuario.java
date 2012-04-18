package br.com.tiagoaramos.estoque.utils.enums;

public enum TipoUsuario {
	
	ADMINISTRADOR( (byte) 0 ,"Loja" );
//	, USUARIO( (byte) 2,"Vendendor" );

	public byte CODIGO;
	public String DESCRICAO;
	
	TipoUsuario(byte codigo,String descricao) {
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
