package controller;

import domain.Reparacion;
import domain.Dispositivo;
import domain.Usuario;

import service.ReparacionService;
import service.DispositivoService;
import service.UsuarioService;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private UsuarioService usuarioService;

    
    @GetMapping("/servicios")
    public String servicios() {

        return "reparacion/servicios";
    }

    
    @GetMapping("/reparacion/nueva")
    public String nueva(
            HttpSession session,
            Model model) {

        Integer id = (Integer) session.getAttribute("usuarioId");

        if (id == null) {
            return "redirect:/login";
        }

        model.addAttribute("reparacion", new Reparacion());

        return "reparacion/solicitud";
    }

    
    @PostMapping("/reparacion/guardar")
    public String guardar(
            @RequestParam String tipoDispositivo,
            @RequestParam String marcaModelo,
            @RequestParam String problemaReportado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Integer id = (Integer) session.getAttribute("usuarioId");

        if (id == null) {
            return "redirect:/login";
        }

        Usuario cliente = usuarioService.buscarUsuario(id);

        if (cliente == null) {
            session.invalidate();
            return "redirect:/login";
        }

        if (tipoDispositivo.isBlank()
                || marcaModelo.isBlank()
                || problemaReportado.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "error", "Debes completar todos los campos."
            );

            return "redirect:/reparacion/nueva";
        }

        
        Dispositivo dispositivo = new Dispositivo();

        dispositivo.setTipoDispositivo(tipoDispositivo);
        dispositivo.setMarcaModelo(marcaModelo);
        dispositivo.setCliente(cliente);

        
        dispositivoService.guardarDispositivo(dispositivo);

        
        Reparacion reparacion = new Reparacion();

        reparacion.setProblemaReportado(problemaReportado);

        reparacion.setEstado("Recibida");

        reparacion.setFechaSolicitud(LocalDateTime.now());

        reparacion.setDispositivo(dispositivo);

        reparacion.setCliente(cliente);

        
        reparacionService.guardarReparacion(reparacion);

        redirectAttributes.addFlashAttribute(
                "mensaje", "Tu solicitud de reparación fue registrada."
        );

        return "redirect:/reparacion/mis-reparaciones";
    }

    
    @GetMapping("/reparacion/mis-reparaciones")
    public String misReparaciones(
            HttpSession session,
            Model model) {

        Integer id = (Integer) session.getAttribute("usuarioId");

        if (id == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "reparaciones",
                reparacionService.listarPorCliente(id)
        );

        return "reparacion/mis-reparaciones";
    }

    
    @GetMapping("/reparacion/seguimiento/{id}")
    public String seguimiento(
            @PathVariable Integer id,
            HttpSession session,
            Model model) {

        Integer clienteId = (Integer) session.getAttribute("usuarioId");

        if (clienteId == null) {
            return "redirect:/login";
        }

        Reparacion reparacion =
                reparacionService.buscarReparacion(id);

        if (reparacion == null
                || reparacion.getCliente() == null
                || !reparacion.getCliente().getId().equals(clienteId)) {

            return "redirect:/reparacion/mis-reparaciones";
        }

        model.addAttribute("reparacion", reparacion);

        return "reparacion/seguimiento";
    }
}
