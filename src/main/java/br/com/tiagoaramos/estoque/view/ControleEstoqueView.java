/*
 * ControleEstoqueView.java
 */

package br.com.tiagoaramos.estoque.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuCadastroCategoria;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuCadastroFornecedor;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuCadastroProduto;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuCadastroUsuario;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuKardexProduto;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuMovimentacaoEntrada;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuMovimentacaoProducao;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuMovimentacaoRetirada;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuMovimentacaoSaida;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuMovimentacaoVenda;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuRelatorio;
import br.com.tiagoaramos.estoque.control.actionlistener.ActMenuSairAplicacao;
import br.com.tiagoaramos.estoque.model.AberturaCaixaModel;
import br.com.tiagoaramos.estoque.model.UsuarioModel;
import br.com.tiagoaramos.estoque.model.dao.AberturaCaixaDAO;
import br.com.tiagoaramos.estoque.view.cadastro.BagCadastroSenha;
import br.com.tiagoaramos.estoque.view.movimentacao.BagVendaProduto;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;
import br.com.tiagoaramos.estoque.view.utils.MysqlSystemExitListenner;

/**
 * The application's main frame.
 */
public class ControleEstoqueView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5857862349648366446L;

    public static JPanel mainPanel;
    
    public static UsuarioModel usuarioCorrente;
    
    
    
    public ControleEstoqueView() {
    	super();
    	AberturaCaixaDAO dao = AberturaCaixaDAO.getInstance();
    	AberturaCaixaModel model = dao.buscarPorData(new Date());
    	String valor = null;
    	
    	addWindowListener(new MysqlSystemExitListenner());
    	
    	if(model != null)
    		valor = model.getValorInicial().toString();
    		
    	while(valor == null || valor.trim().equals("")){
    		valor = JOptionPane.showInputDialog("Digite o caixa incial!");
    		if(valor == null) 
    			continue;
    		try{
    			if(!valor.trim().equals("")){
    				model = new AberturaCaixaModel();
	    			model.setData(new Date());
	    			model.setValorInicial(Double.parseDouble(valor));
	    			dao.persiste(model);
    			}else{
    				valor = null;
    			}
    		}catch (Exception e) {
				valor = null;
			}
    	}
    	
    	
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

    	try {
    		this.setSize(800, 600);
    		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        	
        	
        	JMenuBar mbMenu = montaMenuAplicacao();
        	mainPanel = new JPanel();
            mainPanel.setLayout(null);

            getContentPane().add(mbMenu, BorderLayout.BEFORE_FIRST_LINE);
            getContentPane().add(mainPanel,BorderLayout.CENTER);
		
            alteraMainPanel(new BagVendaProduto());
            
    	} catch (Exception e) {
			
    		JOptionPane.showMessageDialog(this,
					"Ocorreu um erro ao inicializar a aplicação!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			
    		System.exit(0);
		}
    
    	
    }
    
    
    
    private JMenuBar montaMenuAplicacao() {
    	
    	JMenuBar mbMenu = new JMenuBar();
    	
    	mbMenu = new JMenuBar();
        mbMenu.setName("menuBar");

        JMenu jmArquivo = new JMenu();
        jmArquivo.setText("Arquivo");

        JMenuItem miSair = new JMenuItem();
        miSair.setText("Sair");  
        miSair.addActionListener(new ActMenuSairAplicacao(this));
        
        JMenuItem miSenha = new JMenuItem();
        miSenha.setText("Alterar senha");  
        miSenha.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(ControleSessaoUtil.solicitaSenhaAdmin(mainPanel))				
					alteraMainPanel(new BagCadastroSenha());
				
			}
		});

        jmArquivo.add(miSair);
        jmArquivo.add(miSenha);
        mbMenu.add(jmArquivo);
    	
        JMenu jmMovimentacao = new JMenu();
        jmMovimentacao.setText("Movimentação");

        JMenuItem miCadSaidaProduto = new JMenuItem();
        miCadSaidaProduto.setText("Saida");  
        miCadSaidaProduto.addActionListener(new ActMenuMovimentacaoSaida(this));   
        
        JMenuItem miCadVendaProduto = new JMenuItem();
        miCadVendaProduto.setText("Venda");  
        miCadVendaProduto.addActionListener(new ActMenuMovimentacaoVenda(this));   
        
        JMenuItem miCadEntradaProduto = new JMenuItem();
        miCadEntradaProduto.setText("Entrada");  
        miCadEntradaProduto.addActionListener(new ActMenuMovimentacaoEntrada(this));   
        
        JMenuItem miCadRetirada = new JMenuItem();
        miCadRetirada.setText("Retirada");  
        miCadRetirada.addActionListener(new ActMenuMovimentacaoRetirada(this));   
        
        JMenuItem miCadProducao = new JMenuItem();
        miCadProducao.setText("Produção");  
        miCadProducao.addActionListener(new ActMenuMovimentacaoProducao(this));   
        
        jmMovimentacao.add(miCadVendaProduto);
        jmMovimentacao.add(miCadSaidaProduto);
        jmMovimentacao.add(miCadEntradaProduto);
        jmMovimentacao.add(miCadRetirada);
        jmMovimentacao.add(miCadProducao);
        mbMenu.add(jmMovimentacao);
        
        JMenuItem jmCadastro = new JMenu();
        jmCadastro.setText("Cadastro");
        
        JMenuItem miCadCategoria = new JMenuItem();
        miCadCategoria.setText("Categoria");  
        miCadCategoria.addActionListener(new ActMenuCadastroCategoria(this));   

        JMenuItem miCadFornecedor = new JMenuItem();
        miCadFornecedor.setText("Fornecedor");  
        miCadFornecedor.addActionListener(new ActMenuCadastroFornecedor(this));   

        JMenuItem miCadProduto = new JMenuItem();
        miCadProduto.setText("Produto");  
        miCadProduto.addActionListener(new ActMenuCadastroProduto(this));   
        
        JMenuItem miCadUsuario = new JMenuItem();
        miCadUsuario.setText("Usuário");  
        miCadUsuario.addActionListener(new ActMenuCadastroUsuario(this));   

        jmCadastro.add(miCadCategoria);
        jmCadastro.add(miCadFornecedor);
        jmCadastro.add(miCadProduto);
        jmCadastro.add(miCadUsuario);
        mbMenu.add(jmCadastro);
        
        jmCadastro = new JMenu();
        jmCadastro.setText("Relatórios");
        
        miCadCategoria = new JMenuItem();
        miCadCategoria.setText("Relatórios diários");  
        miCadCategoria.addActionListener(new ActMenuRelatorio(this));   
        jmCadastro.add(miCadCategoria);
        

        miCadUsuario = new JMenuItem();
        miCadUsuario.setText("kardex Produto");  
        miCadUsuario.addActionListener(new ActMenuKardexProduto(this));   
        jmCadastro.add(miCadUsuario);
        
        
        mbMenu.add(jmCadastro);
        
        
                
        return mbMenu; 
        
	}

	public void alteraMainPanel(JPanel panel) {
        getContentPane().remove(mainPanel);
        mainPanel = panel;
        getContentPane().add(mainPanel);
        setTitle(mainPanel.getName() + " - Loja: " + ControleSessaoUtil.usuarioLogado.getNome() );
        validate();
    }
    
	public void mudaLookAndFeel(LookAndFeel look) {
		try {
			UIManager.setLookAndFeel(look);
			this.repaint();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}
}
