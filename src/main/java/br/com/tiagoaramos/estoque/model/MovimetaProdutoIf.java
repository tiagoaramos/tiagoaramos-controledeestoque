package br.com.tiagoaramos.estoque.model;

import java.math.BigDecimal;
import java.util.Date;

public interface MovimetaProdutoIf extends Model {
	
	public BigDecimal getQuantidade() ;

	public void setQuantidade(BigDecimal quantidade) ;

	public ProdutoModel getProduto() ;

	public void setProduto(ProdutoModel produto) ;
	
	public Date getData();
	
}
