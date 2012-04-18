package br.com.tiagoaramos.estoque.excecao;


public class PersistenciaException extends Exception {

	private static final long serialVersionUID = 8896538272842944506L;
	
	public PersistenciaException() {
		super();
	}
	 
	public PersistenciaException(String msg){
		super(msg);
	}
	
	 
	public PersistenciaException(Throwable t){
		super(t);
	}
	
	public PersistenciaException(String msg, Throwable t){
		super(msg,t);
	}

	public PersistenciaException(String message, String string) {
		super(message + "\n" + string);
	}

}
