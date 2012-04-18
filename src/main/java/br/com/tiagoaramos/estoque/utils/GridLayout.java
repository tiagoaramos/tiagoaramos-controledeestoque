package br.com.tiagoaramos.estoque.utils;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GridLayout  {
	
	private Container contentPane;

	public GridLayout(Container contentPane) {
		this.contentPane = contentPane;
		this.getContentPane().setLayout(new GridBagLayout());
	}

	public Container getContentPane() {
		return contentPane;
	}

	/**
	 * Adiciona um label e um componente horizontalmente
	 * 
	 * @param label
	 *            String que irá aparecer no label
	 * @param componente
	 *            Componente de edição
	 */
	public void add(String label, JComponent componente) {
		this.add(new JLabel(label), componente);
	}
	
	public void add(JLabel label, JComponent componente) {
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(label, cons);

		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente, cons);
	}

	private GridBagConstraints getConstrainsts() {
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(4, 10, 4, 10);
		return cons;
	}

	/**
	 * Adiciona um label e um componente horizontalmente. O componente ocupará
	 * todo o reto da tela
	 * 
	 * @param label
	 *            String que irá aparecer no label
	 * @param componente
	 *            Componente de edição
	 */
	public void add(String label, JScrollPane componente) {
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = 1;
		cons.gridheight = GridBagConstraints.REMAINDER;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente, cons);
	}

	/**
	 * Adiciona um label e um componente horizontalmente. O componente ocupará
	 * todo o reto da tela
	 * 
	 * @param label
	 *            String que irá aparecer no label
	 * @param componente
	 *            Componente de edição
	 */
	public void add(JScrollPane componente) {
		GridBagConstraints cons = getConstrainsts();
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = 1;
		cons.weightx = 1;
		cons.gridheight = GridBagConstraints.REMAINDER;
		cons.fill = GridBagConstraints.BOTH;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente, cons);
	}
	
	/**
	 * Adiciona um label e um componente horizontalmente. O componente ocupará
	 * todo o reto da tela
	 * 
	 * @param label
	 *            String que irá aparecer no label
	 * @param componente
	 *            Componente de edição
	 */
	public void add(JComponent componente) {
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridheight = GridBagConstraints.REMAINDER;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.weighty = GridBagConstraints.REMAINDER;
		cons.weightx = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente, cons);
	}
	
	/**
	 * Adiciona um label, um componente de edição, mais um label e outro
	 * componente de edição. Todos na mesma linha
	 * 
	 * @param label
	 *            Label 1
	 * @param componente
	 *            Componente de edição
	 * @param label2
	 *            Label 2
	 * @param componente2
	 *            Componente de edição 2
	 */
	public void add(String label, JComponent componente, String label2, JComponent componente2) {
		
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente, cons);

		cons.fill = GridBagConstraints.NONE;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label2), cons);

		cons.weightx = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente2, cons);
	}
	
	public void add(String label, JComponent componente, JComponent componente2) {
		
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente, cons);

		cons.weightx = 1;
		cons.fill = GridBagConstraints.BOTH;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente2, cons);
	}
	
public void add(JComponent componente, JComponent componente2, JComponent componente3) {
		
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(componente, cons);

		cons.weightx = 0;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente2, cons);

		cons.weightx = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente3, cons);
	}
		
	public void add(String label, JButton componente, JButton componente2) {
		
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		this.getContentPane().add(componente, cons);

		cons.weightx = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		this.getContentPane().add(componente2, cons);
	}
	
	/**
	 * Adiciona um label, um componente de edição, mais um label e outro
	 * componente de edição. Todos na mesma linha
	 * 
	 * @param label
	 *            Label 1
	 * @param componente
	 *            Componente de edição
	 * @param label2
	 *            Label 2
	 * @param componente2
	 *            Componente de edição 2
	 */
	public void add(String label, JComponent componente, JComponent componente2, JComponent componente3) {

		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente, cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente2, cons);

		cons.weightx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente3, cons);
	}

	public void add(String label, JComponent componente, JComponent componente2, JComponent componente3, JComponent componente4) {

		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente, cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente2, cons);
		
		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente3, cons);

		cons.weightx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente4, cons);
		
	}

	public void add(String label, JComponent componente, JComponent componente2, JComponent componente3, JComponent componente4, JComponent componente5) {

		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente, cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente2, cons);
		
		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente3, cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente4, cons);

		cons.weightx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(componente5, cons);
		
	}
	
	public void add(String label, JTextField jtfNomeCategoria, JPanel panel, int i) {
		
		GridBagConstraints cons = getConstrainsts();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 0;
		cons.gridwidth = 1;
		this.getContentPane().add(new JLabel(label), cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(jtfNomeCategoria, cons);

		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.gridheight = i;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.EAST;
		this.getContentPane().add(panel, cons);

		for (int j = 0; j < i; j++) {
			cons = getConstrainsts();
			cons.fill = GridBagConstraints.BOTH;
			cons.gridwidth = GridBagConstraints.REMAINDER;		
			this.getContentPane().add(new JLabel(), cons);
		}
	}
	
	public void add(JPanel panel1, JPanel panel2, int i) {
		GridBagConstraints cons = new GridBagConstraints();
		
		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weightx = 1;
		cons.gridwidth = 1;
		cons.gridheight = i;
		container.add(panel1, cons);
		cons.weightx = 1;
		container.add(panel2, cons);
				
		cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridheight = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.weighty = 0;
		cons.weightx = 0;
		this.getContentPane().add(container,cons);
	}
	
}