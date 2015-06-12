package diapositivas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class PruebaConexion {
	public static final String DB_URL ="jdbc:sqlite:C:\\Users\\mañana\\Desktop\\tema16\\sqlite\\libros\\prueba";
	public static final String DRIVER = "org.sqlite.JDBC";
	
	public static void main(String[] args) {
		try {
			Class.forName(DRIVER); //cargo la clase en memoria de la JDBC en memoria para poder trabajar con ella
			//vamos a permitir la integridad referencial en sqlite
			
			//debemos establecer PRAGAMA foreing_keys=ON;
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			
			Connection connection = DriverManager.getConnection(DB_URL, config.toProperties()); //abro una conexion con la BD
			
			System.out.println("Conectado a la base de datos");
			
//			Thread.sleep(3000); simulo operaciones con la BD (3s de espera)
			
			Statement statement = connection.createStatement(); //creo un objeto statement asignado a la conexion, este objeto nos permite realizar consultas
			//primero creamos la consulta como string
			String consultaSQL ="select * from libro";
			ResultSet resulset = statement.executeQuery(consultaSQL); //ejecuto la sentencia sql que nos devuelve un objeto resulset
			
			//recorremos el resultset
			String nombre="", autor ="";
			int idCategoria;
			while(resulset.next()){
				nombre = resulset.getString("nombre"); //devuelve un string con el campo que contenga la columna indicada
				autor = resulset.getString("autor");	//es posible pasar el numero de columna "empezando por 1"
				idCategoria = resulset.getInt("idCategoria"); //este caso devuelve un int porque el campo contiene un integer
				System.out.printf("%-35s %-15s %-15d %n",nombre ,autor,idCategoria);
			}
			//para actualizar datos
			String consulta2 ="update usuario set nombre = 'joaquin' where id=2";
			
			int filasAfectadas = statement.executeUpdate(consulta2); //para hacer un update se utiliza el metodo executeUpdate que devuelve un numero con la cantidad de filas afectadas
			System.out.println("Filas afectadas en update : "+filasAfectadas);
			
			//para borrar datos
			String consulta3 = "delete from usuario where id=6";
			filasAfectadas = statement.executeUpdate(consulta3);
			System.out.println("Filas afectadas en borrado : "+filasAfectadas);
			//usando preparedstatement
			String consulta4 = "select * from libro where id=?";
			PreparedStatement prepStatement = connection.prepareStatement(consulta4);
			for (int i = 0; i < 6; i++) {
				prepStatement.setInt(1, i);
				resulset= prepStatement.executeQuery();
				while (resulset.next()){
					nombre = resulset.getString("nombre"); 
					String editorial = resulset.getString("editorial");	
					System.out.printf("Libro: %-15s   Editorial : %-15s %n",nombre,editorial);
				}
				
			}
			//agrupar sentecncias sql usando Batch
			String consulta5 = "insert into usuario (nombre, dni) values (?,?)";
			PreparedStatement prepStatement2 = connection.prepareStatement(consulta5);
			prepStatement2.setString(1, "Luis");
			prepStatement2.setString(2, "2522377");
			prepStatement2.addBatch(); //añade la sentencia al Batch, pero aun no se ha ejecutado
			prepStatement2.setString(1, "Luis2");
			prepStatement2.setString(2, "25223217");
			prepStatement2.addBatch();
			
			
		/*	consulta5 = "update usuario set dni = ? where nombre=?";
		    prepStatement2 = connection.prepareStatement(consulta5);
			prepStatement2.setString(1, "40232503");
			prepStatement2.setString(2, "Luis");
			prepStatement2.addBatch(); //se añade otra sentencia al Batch
			prepStatement2.setString(1, "40232505");
			prepStatement2.setString(2, "Luis2");
			prepStatement2.addBatch();   batch se utiliza para sentencias similares como (insert ) */
			int [] registrosAfectados =  prepStatement2.executeBatch();
			for (int i = 0; i < registrosAfectados.length; i++) {
				System.out.println("Registros afectados "+registrosAfectados[i]);
			}
			try{
				//transacciones
				//preparamos la base de datos
				connection.setAutoCommit(false);
				String consulta6 = "insert into usuario (nombre,dni) values ('joaquin','dni11')";
				String consulta7 = "insert into usuario (nombre,dni) values ('martin','dni22')";
				Statement statement2 = connection.createStatement();
				statement2.execute(consulta6);
				statement2.execute(consulta7);
				
			
			} catch (Exception e){
				connection.rollback();
			}
			connection.close(); //cierro la conexion
			System.out.println("Desconectado a la base de datos");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
