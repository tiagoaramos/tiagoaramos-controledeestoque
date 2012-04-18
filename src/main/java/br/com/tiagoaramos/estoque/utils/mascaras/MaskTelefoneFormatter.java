package br.com.tiagoaramos.estoque.utils.mascaras;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class MaskTelefoneFormatter extends MaskFormatter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6199350660484800457L;

	public MaskTelefoneFormatter() throws ParseException {
		super("##-####-####");
	}

}
