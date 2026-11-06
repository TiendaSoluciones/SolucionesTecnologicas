package controller;

import domain.Producto;
import service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto/listado")
    public String listado(Model model) {
        var productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    @GetMapping("/producto/nuevo")
    public String nuevo(Producto producto) {
        return "producto/modifica";
    }

    @PostMapping("/producto/guardar")
    public String guardar(Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/producto/modificar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Producto producto = productoService.buscarProducto(id);
        model.addAttribute("producto", producto);
        return "producto/modifica";
    }

    @GetMapping("/producto/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return "redirect:/producto/listado";
    }
}
