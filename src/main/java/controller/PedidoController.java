package controller;

import domain.Pedido;
import domain.DetallePedido;
import service.PedidoService;
import service.DetallePedidoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping("/pedido/listado")
    public String listado(Model model) {

        List<Pedido> pedidos = pedidoService.listarPedidos();

        model.addAttribute("pedidos", pedidos);

        return "pedido/listado";
    }

    @GetMapping("/pedido/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model) {

        Pedido pedido = pedidoService.buscarPedido(id);

        if (pedido == null) {
            return "redirect:/pedido/listado";
        }

        List<DetallePedido> todosLosDetalles =
                detallePedidoService.listarDetalles();

        List<DetallePedido> detallesPedido = new ArrayList<>();

        for (DetallePedido detalle : todosLosDetalles) {

            if (detalle.getPedido().getId().equals(id)) {
                detallesPedido.add(detalle);
            }
        }

        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detallesPedido);

        return "pedido/detalle";
    }
}