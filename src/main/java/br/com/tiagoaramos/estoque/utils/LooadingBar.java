package br.com.tiagoaramos.estoque.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class LooadingBar {
	private boolean flag;
	private static final int MY_MINIMUM = 0;
	private static final int MY_MAXIMUM = 100;
	private static LooadingBar lLooadingBar;

	private JProgressBar barra;
	private JFrame frame;
	
	public static LooadingBar getInstance(){
		if(lLooadingBar == null){
			lLooadingBar = new LooadingBar();
		}
		return lLooadingBar;
	}
	
	private LooadingBar() {
	}
	
	private void initComponents(String title){
		
		JPanel jPanel = new JPanel();
		flag = false;

		jPanel.add(new JLabel("Processando ..."));

		barra = new JProgressBar();
		barra.setMinimum(MY_MINIMUM);
		barra.setMaximum(MY_MAXIMUM);
		barra.setToolTipText("Carregando Recursos!");
		barra.setStringPainted(false);

		jPanel.add(barra);

		frame = new JFrame("Carregando");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(jPanel);

		frame.pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height
				/ 2 - frame.getSize().height / 2);
		frame.setVisible(true);
	}
	
	public void begingLoading(final String title){
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				initComponents(title);
				
				while (!flag) {
					for (int i = MY_MINIMUM; i <= MY_MAXIMUM && !flag; i++) {
						final int percent = i;
						try {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									barra.setValue(percent);
									barra.getParent().repaint();
								}
							});
							Thread.sleep(100);
						} catch (InterruptedException e) {

						}
					}
					barra.setValue(MY_MAXIMUM);
					barra.getParent().repaint();
				}
				frame.setVisible(false);
			}
		}).start();
	}
	
	public void stopLoading(){
		flag = true;
	}

}
