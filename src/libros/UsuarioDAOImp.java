package libros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImp implements UsuarioDAO{
	//obtenemos la conexion como un atributo de la clase
	private Connection conexion = Conexion.getConexion("prueba");
	
	
	
	@Override
	public List<UsuarioDTO> getUsuarios() {
		String sentencia = "select * from usuario";
		List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
		try {
			Statement statement = conexion.createStatement();
			ResultSet r = statement.executeQuery(sentencia);
			String nombre ="";
			String dni = "";
			UsuarioDTO u = null;
			
			while(r.next()){
				nombre= r.getString("nombre");
				dni = r.getString("dni");
				u = new UsuarioDTO(nombre, dni);
				lista.add(u);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void addUsuario(UsuarioDTO u) {
		String sql = "insert into usuario (nombre,dni) values (?,?)";
		try {
			PreparedStatement s = conexion.prepareStatement(sql);
			s.setString(1, u.getNombre());
			s.setString(2, u.getDni());
			s.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarUsuario(UsuarioDTO u) {
		
		String sql = "delete from usuario where dni =?";
		try {
			PreparedStatement prep = conexion.prepareStatement(sql);
			prep.setString(1, u.getDni());
			prep.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void actualizarUsuario(UsuarioDTO u) {
		String sql = "UPDATE USUARIO set nombre =? where dni=?";
		try {
			PreparedStatement prep = conexion.prepareStatement(sql);
			prep.setString(1, u.getNombre());
			prep.setString(2, u.getDni());
			prep.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
