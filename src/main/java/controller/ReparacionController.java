package controller;

import domain.Reparacion;
import service.ReparacionService;
import service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;

    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("/servicios")
    public String servicios() {
        return "reparacion/servicios";
    }

    @GetMapping("/reparacion/nueva")
    public String nueva(Reparacion reparacion, Model model) {

        model.addAttribute("dispositivos",
                dispositivoService.listarDispositivos());

        return "reparacion/solicitud";
    }

    @PostMapping("/reparacion/guardar")
    public String guardar(Reparacion reparacion) {

        reparacionService.guardarReparacion(reparacion);

        return "redirect:/reparacion/mis-reparaciones";
    }

    @GetMapping("/reparacion/mis-reparaciones")
    public String misReparaciones(Model model) {

        model.addAttribute("reparaciones",
                reparacionService.listarReparaciones());

        return "reparacion/mis-reparaciones";
    }

    @GetMapping("/reparacion/seguimiento/{id}")
    public String seguimiento(@PathVariable Integer id, Model model) {

        Reparacion reparacion =
                reparacionService.buscarReparacion(id);

        if (reparacion == null) {
            return "redirect:/reparacion/mis-reparaciones";
        }

        model.addAttribute("reparacion", reparacion);

        return "reparacion/seguimiento";
    }
}
