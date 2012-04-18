package br.com.tiagoaramos.estoque.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.Model;
import br.com.tiagoaramos.estoque.model.dao.DAO;
import br.com.tiagoaramos.estoque.utils.GridLayout;

public abstract class CadastroBagAb<T> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7070676072480334549L;


	protected JPanel jpnTabela;
	protected JScrollPane jspContainer;
	protected JTable jtbTabela;
	protected JButton jbtSalvar;
	protected JButton jbtExcluir;
	protected JButton jbtEditar;
	protected T model;
	protected List<T> lista;
	protected DAO<T> dao;
	protected int indice = -1;
	protected DefaultTableModel tableModel;
	protected GridLayout grid;
	
	protected void initComponents(T model, DAO<T> dao) {
		
		jpnTabela = new JPanel();
    	jspContainer = new JScrollPane();    	
    	jtbTabela = new JTable();
    	jbtExcluir = new JButton();
    	jbtEditar = new JButton();
    	jbtSalvar = new JButton();
    	
		this.model = model;
		this.dao = dao;

        jbtSalvar.setText("Salvar");
    	jbtSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	salvarModel();
            }
        });
    	jbtSalvar.addKeyListener(new KeyListener() {
		
			@Override
			public void keyTyped(KeyEvent e) {}
		
			@Override
			public void keyReleased(KeyEvent e) {}
		
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					salvarModel();
				}
			}
		});
    	
        jpnTabela.setLayout(new java.awt.GridLayout(1, 0));
        jspContainer.setViewportView(jtbTabela);
    	jpnTabela.add(jspContainer);
    	
    	jbtExcluir.setText("Exlcuir");
    	jbtExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	excluirModel();
            }
        });
    	
    	jbtEditar.setText("Editar");
    	jbtEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editarModel();
            }
        });
    	
		grid = new GridLayout( this );
	}

	protected abstract void editarModel() ;

	protected void excluirModel() {
		int[] l = jtbTabela.getSelectedRows();//captura as linhas selecionadas
		if(l.length > 0){
					
			model = lista.get(l[0]);
			try {
				dao.removerPorCodigo( ((Model)model).getId());
				lista.remove(l[0]);
			} catch (PersistenciaException e) {
				e.printStackTrace();
			}
			tableModel.removeRow(l[0]);//remove todas as linhas selecionadas
		
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um registro para excluir!", "Erro", JOptionPane.ERROR_MESSAGE );
		}	
	}

	protected abstract void salvarModel() ;
	
}
