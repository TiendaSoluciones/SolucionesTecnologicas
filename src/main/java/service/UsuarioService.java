package service;

import dao.UsuarioRepository;
import domain.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

}
