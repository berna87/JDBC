package libros;

import java.util.List;

public class TestBD {

	public static void main(String[] args) {
		UsuarioDAOImp uImp = new UsuarioDAOImp();
		
		List<UsuarioDTO> lista = uImp.getUsuarios(); //llamo al metodo que debuelve una lista con todos los usuarios de la BD
		System.out.println(lista);
		
		UsuarioDTO usu = new UsuarioDTO("felipe", "253254"); 
		uImp.addUsuario(usu); //añado un usuario a la BD
		
		lista = uImp.getUsuarios();
		System.out.println(lista); //vuelvo a comprobar lo que hay en la lista 
		
	//	uImp.eliminarUsuario(usu); //metodo para borrar el usuario de la BD
		
		UsuarioDTO usuNuevo = new UsuarioDTO("felipa", "253254"); 
		
		uImp.actualizarUsuario(usuNuevo); //metodo que modifica el nombre del usuario con el dni indicado
		
		lista = uImp.getUsuarios();
		System.out.println(lista); //vuelvo a comprobar lo que hay en la lista 
		
		Conexion.desconectar();
	}

}
