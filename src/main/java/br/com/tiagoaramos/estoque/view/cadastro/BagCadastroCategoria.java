/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroCategoria.java
 *
 * Created on 08/10/2009, 21:27:57
 */

package br.com.tiagoaramos.estoque.view.cadastro;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import br.com.tiagoaramos.estoque.excecao.PersistenciaException;
import br.com.tiagoaramos.estoque.model.CategoriaProdutoModel;
import br.com.tiagoaramos.estoque.model.dao.CategoriaDAO;
import br.com.tiagoaramos.estoque.view.CadastroBagAb;

/**
 *
 * @author tiago
 */
public class BagCadastroCategoria extends CadastroBagAb<CategoriaProdutoModel> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 35096180898462032L;

	private JTextField jtfNomeCategoria;
	
	public BagCadastroCategoria() {
        initComponents();
    }

    protected void initComponents() {
    	super.initComponents(new CategoriaProdutoModel(), CategoriaDAO.getInstance());
    	setName("CadastroCategoria");
    	
    	jtfNomeCategoria = new JTextField();    	

    	grid.add("Nome:", jtfNomeCategoria);
    	
    	
    	tableModel = new DefaultTableModel(new Object [][] { },new String [] {"Código","Nome"}){
    		private static final long serialVersionUID = 5622980448697494420L;
			public boolean isCellEditable(int row, int col) {
				if(col == 0)
					return false;
				else
					return true;
    		}
    	};
    	tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE){
					model = lista.get(e.getFirstRow());
					model.setNome( (String)tableModel.getValueAt(e.getFirstRow(), 1) );
					try {
						dao.merge(model);
		            	indice = -1;
		                jtfNomeCategoria.setText("");
		                model = new CategoriaProdutoModel();
					} catch (PersistenciaException e1) {
						e1.printStackTrace();
					}				
					
				}
			}
		});
    	jtbTabela.setModel(tableModel);
    	jtbTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
    	jtbTabela.getColumnModel().getColumn(0).setPreferredWidth(50);
    	jtbTabela.getColumnModel().getColumn(0).setResizable(false);
    	jtbTabela.getColumnModel().getColumn(1).setPreferredWidth(350);
    	jtbTabela.getColumnModel().getColumn(1).setResizable(false);
    	preencherTabela();

    	grid.add("Acções",jbtSalvar,jbtExcluir,jbtEditar);
    	grid.add("Categorias",jspContainer);
    	
    }

	private void preencherTabela() {
		if(lista == null){
			dao = CategoriaDAO.getInstance();
			lista = dao.buscarTodos();
		}
		 
		for (CategoriaProdutoModel categoriaProduto : lista) {
			adicionarTabela(categoriaProduto);
		}
	}
	
	private void adicionarTabela(CategoriaProdutoModel categoriaProduto) {
		tableModel.addRow(new Object [] {categoriaProduto.getId().toString(),categoriaProduto.getNome()});
	}
	
	
	protected void editarModel(){
		
		int[] l = jtbTabela.getSelectedRows();
		if(l.length > 0){
			indice = l[0];
			model = lista.get(indice);
			jtfNomeCategoria.setText(model.getNome());
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um registro para editar!", "Erro", JOptionPane.ERROR_MESSAGE );
		}
	}

	protected void salvarModel() {
        model.setNome(jtfNomeCategoria.getText());
        try {
            if(model.getId() != null && model.getId().intValue() > 0){
            	tableModel.setValueAt(model.getNome(), indice , 1);
            	indice = -1;
            	JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
            	dao.persiste(model);
            	lista.add(model);
                adicionarTabela(model);            
                JOptionPane.showMessageDialog(this, "Categoria salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            jtfNomeCategoria.setText("");
            model = new CategoriaProdutoModel();
        } catch (PersistenciaException ex) {
            Logger.getLogger(BagCadastroCategoria.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Impossível gravar a categoria", "Erro", JOptionPane.ERROR_MESSAGE );
        }
	}
}
