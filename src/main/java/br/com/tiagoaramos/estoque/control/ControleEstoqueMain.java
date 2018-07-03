package br.com.tiagoaramos.estoque.control;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.management.MysqldResource;
import com.mysql.management.MysqldResourceI;

import br.com.mzsw.PesoLib;
import br.com.tiagoaramos.estoque.utils.LooadingBar;
import br.com.tiagoaramos.estoque.view.utils.ControleSessaoUtil;

public class ControleEstoqueMain {

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	public static MysqldResource mysqldResource;
	public static PesoLib balanca;
	
	public static void main(String args[]) throws ClassNotFoundException,
			SQLException {

		LooadingBar.getInstance().begingLoading("Iniciando Recursos");
		System.load(ControleEstoqueMain.class.getResource("/").getFile() + "PesoLib.dll");
		balanca = new PesoLib(); 
		
		File ourAppDir = new File(System.getProperty(JAVA_IO_TMPDIR));
		File databaseDir = new File(ourAppDir, "mysql-mxj");
		int portNumber = Integer.parseInt(System.getProperty("c-mxj_test_port",
				"3386"));
		String userName = "olyanren";
		String password = "1987";
		ControleEstoqueMain.mysqldResource = startDatabase(databaseDir,
				portNumber, userName, password);

		LooadingBar.getInstance().stopLoading();
		
		ControleSessaoUtil.solicitaSenhaAcesso();
		
	}

	public static MysqldResource startDatabase(File databaseDir, int port,
			String userName, String password) {
		MysqldResource mysqldResource = new MysqldResource(databaseDir);
		HashMap<String, String> database_options = new HashMap<String, String>();
		database_options.put(MysqldResourceI.PORT, Integer.toString(port));
		database_options.put(MysqldResourceI.INITIALIZE_USER, "true");
		database_options.put(MysqldResourceI.INITIALIZE_USER_NAME, userName);
		database_options.put(MysqldResourceI.INITIALIZE_PASSWORD, password);

		mysqldResource.start("test-mysqld-thread", database_options);
		if (!mysqldResource.isRunning()) {
			throw new RuntimeException("MySQL did not start.");
		}
		System.out.println("MySQL is running.");
		return mysqldResource;
	}

}
