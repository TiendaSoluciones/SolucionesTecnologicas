package controller;

import domain.Pedido;
import domain.DetallePedido;
import domain.Usuario;
import service.PedidoService;
import service.DetallePedidoService;
import service.UsuarioService;
import service.CarritoService;
import service.ItemCarrito;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarritoService carritoService;

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

        List<DetallePedido> todosLosDetalles
                = detallePedidoService.listarDetalles();

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

    @PostMapping("/pedido/finalizar")
    @Transactional
    public String finalizar(
            @RequestParam String correo,
            RedirectAttributes redirectAttributes) {

        // Verificar que el carrito tenga productos
        List<ItemCarrito> items = carritoService.obtenerItems();

        if (items.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error", "El carrito está vacío.");
            return "redirect:/carrito/listado";
        }
        System.out.println("PRODUCTOS EN CARRITO: " + items.size());

        // Buscar al cliente por su correo
        Usuario cliente = usuarioService.buscarPorCorreo(correo);

        if (cliente == null || cliente.getRol() == null
                || cliente.getRol().getId() != 2) {

            redirectAttributes.addFlashAttribute(
                    "error", "No se encontró un cliente con ese correo.");
            return "redirect:/carrito/listado";
        }
        System.out.println("CLIENTE ENCONTRADO: " + (cliente != null));

        if (cliente != null) {
            System.out.println("ROL DEL CLIENTE: " + cliente.getRol().getId());
        }

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotal(carritoService.calcularTotal());
        pedido.setEstado("Pendiente");

        System.out.println("INTENTANDO GUARDAR PEDIDO...");

        pedidoService.guardarPedido(pedido);

        // Guardar los productos del pedido
        for (ItemCarrito item : items) {

            DetallePedido detalle = new DetallePedido();

            detalle.setPedido(pedido);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());

            detallePedidoService.guardarDetalle(detalle);
        }

        // Vaciar el carrito después de guardar el pedido
        carritoService.vaciarCarrito();

        redirectAttributes.addFlashAttribute(
                "mensaje", "¡Tu pedido se registró correctamente!");

        return "redirect:/pedido/listado";
    }
}
