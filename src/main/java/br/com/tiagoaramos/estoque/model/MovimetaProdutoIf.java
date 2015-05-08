package br.com.tiagoaramos.estoque.model;

import java.util.Date;

public interface MovimetaProdutoIf extends Model {
	
	public Integer getQuantidade() ;

	public void setQuantidade(Integer quantidade) ;

	public ProdutoModel getProduto() ;

	public void setProduto(ProdutoModel produto) ;
	
	public Date getData();
	
}
