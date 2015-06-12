package libros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class Conexion {
	private static Connection conexion = null;
	
	private Conexion(){}
	/**
	 * 
	 * @param nombBD nombre de la base de datos sqlite hayada en el raiz del proyecto
	 * @return una conexion con la base de datos
	 */
	public static Connection getConexion(String nombBD){
		final String DB_URL ="jdbc:sqlite:"+nombBD;
		final String DRIVER = "org.sqlite.JDBC";
		SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);
		try {
			Class.forName(DRIVER);
			conexion= DriverManager.getConnection(DB_URL, config.toProperties());
			System.out.println("Establecida conexion con la base de datos");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conexion;
	}
	
	/**
	 * Cierra la conexion
	 */
	public static void desconectar(){
		if (conexion != null ){
			try {
				conexion.close();
				System.out.println("Conexion con la base de datos cerrada");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
