package libros;

import java.util.List;

public interface UsuarioDAO {
	List<UsuarioDTO> getUsuarios();
	void addUsuario (UsuarioDTO u);
	void eliminarUsuario (UsuarioDTO u);
	void actualizarUsuario (UsuarioDTO u);
}
