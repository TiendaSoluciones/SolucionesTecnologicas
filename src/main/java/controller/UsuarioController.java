package controller;

import domain.Usuario;
import service.UsuarioService;
import service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping("/registro")
    public String registro(Usuario usuario, Model model) {

        model.addAttribute("roles", rolService.listarRoles());

        return "usuario/registro";
    }

    @PostMapping("/usuario/guardar")
    public String guardar(Usuario usuario) {

        usuarioService.guardarUsuario(usuario);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {

        return "usuario/login";
    }

    @GetMapping("/usuario/perfil")
    public String perfil() {

        return "usuario/perfil";
    }
}
