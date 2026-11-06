package controller;

import domain.Producto;
import service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import service.CategoriaService;
import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/producto/listado")
    public String listado(
            @RequestParam(required = false) String busqueda,
            Model model) {
        var productos = (busqueda == null || busqueda.isBlank())
                ? productoService.listarProductos()
                : productoService.buscarPorNombre(busqueda);

        model.addAttribute("productos", productos);
        model.addAttribute("busqueda", busqueda);

        return "producto/listado";
    }

    @GetMapping("/producto/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model) {

        Producto producto = productoService.buscarProducto(id);

        if (producto == null) {
            return "redirect:/producto/listado";
        }

        model.addAttribute("producto", producto);

        return "producto/detalle";
    }

    @GetMapping("/producto/nuevo")
    public String nuevo(Producto producto, Model model) {
        model.addAttribute("categorias", categoriaService.listarCategorias());
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
        
        if (producto == null){
            return "redirect:/producto/listado";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarCategorias());
        
        return "producto/modifica";
    }

    @GetMapping("/producto/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return "redirect:/producto/listado";
    }
}
