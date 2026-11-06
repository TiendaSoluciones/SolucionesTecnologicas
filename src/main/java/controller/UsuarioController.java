package controller;

import domain.Usuario;
import domain.Rol;
import service.UsuarioService;
import service.RolService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    
    @GetMapping("/registro")
    public String registro(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "usuario/registro";
    }

   
    @PostMapping("/usuario/guardar")
    public String guardar(
            @ModelAttribute Usuario usuario,
            RedirectAttributes redirectAttributes) {

        
        Usuario existe = usuarioService.buscarPorCorreo(
                usuario.getCorreo().trim().toLowerCase()
        );

        if (existe != null) {

            redirectAttributes.addFlashAttribute(
                    "error", "Este correo ya se encuentra registrado."
            );

            return "redirect:/registro";
        }

        
        Rol rolCliente = rolService.buscarPorNombre("Cliente");

        if (rolCliente == null) {

            redirectAttributes.addFlashAttribute(
                    "error", "No se encontró el rol Cliente en la base de datos."
            );

            return "redirect:/registro";
        }

        usuario.setCorreo(usuario.getCorreo().trim().toLowerCase());

        
        usuario.setRol(rolCliente);

        
        usuarioService.guardarUsuario(usuario);

        redirectAttributes.addFlashAttribute(
                "mensaje", "Tu cuenta fue creada correctamente."
        );

        return "redirect:/login";
    }

    
    @GetMapping({"/login", "/usuario/login"})
    public String login() {

        return "usuario/login";
    }

    
    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam String correo,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {

        Usuario usuario = usuarioService.buscarPorCorreo(
                correo.trim().toLowerCase()
        );

        if (usuario == null
                || usuario.getContrasena() == null
                || !usuario.getContrasena().equals(contrasena)) {

            model.addAttribute(
                    "error", "Correo o contraseña incorrectos."
            );

            return "usuario/login";
        }

        
        session.setAttribute("usuarioId", usuario.getId());

        session.setAttribute(
                "usuarioNombre", usuario.getNombreCompleto()
        );

        session.setAttribute(
                "usuarioCorreo", usuario.getCorreo()
        );

        return "redirect:/usuario/perfil";
    }

    
    @GetMapping("/usuario/perfil")
    public String perfil(
            HttpSession session,
            Model model) {

        Integer id = (Integer) session.getAttribute("usuarioId");

        if (id == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarUsuario(id);

        if (usuario == null) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        return "usuario/perfil";
    }

    
    @PostMapping("/usuario/perfil/guardar")
    public String actualizarPerfil(
            @ModelAttribute Usuario datos,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Integer id = (Integer) session.getAttribute("usuarioId");

        if (id == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarUsuario(id);

        if (usuario == null) {
            session.invalidate();
            return "redirect:/login";
        }

        Usuario usuarioCorreo = usuarioService.buscarPorCorreo(
                datos.getCorreo().trim().toLowerCase()
        );

        // Comprobar si el correo pertenece a otro usuario
        if (usuarioCorreo != null
                && !usuarioCorreo.getId().equals(usuario.getId())) {

            redirectAttributes.addFlashAttribute(
                    "error", "El correo ya está siendo utilizado."
            );

            return "redirect:/usuario/perfil";
        }

        usuario.setNombreCompleto(datos.getNombreCompleto());
        usuario.setCorreo(datos.getCorreo().trim().toLowerCase());
        usuario.setTelefono(datos.getTelefono());

        usuarioService.guardarUsuario(usuario);

        session.setAttribute(
                "usuarioNombre", usuario.getNombreCompleto()
        );

        session.setAttribute(
                "usuarioCorreo", usuario.getCorreo()
        );

        redirectAttributes.addFlashAttribute(
                "mensaje", "Perfil actualizado correctamente."
        );

        return "redirect:/usuario/perfil";
    }

    
    @PostMapping("/logout")
    public String cerrarSesion(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}