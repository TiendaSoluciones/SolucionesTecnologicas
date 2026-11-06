/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.CarritoService;
import service.ProductoService;
/**
 *
 * @author ssanc
 */

@Controller
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/carrito/listado")
    public String listar(Model model) {

        model.addAttribute("detalles", carritoService.obtenerItems());
        model.addAttribute("total", carritoService.calcularTotal());
        model.addAttribute("cantidadProductos",
                carritoService.calcularCantidadProductos());

        return "carrito/listado";
    }

    @PostMapping("/carrito/agregar")
    public String agregar(@RequestParam Integer idProducto) {

        Producto producto = productoService.buscarProducto(idProducto);

        if (producto != null && producto.isActivo()
                && producto.getExistencia() > 0) {
            carritoService.agregarProducto(producto);
        }

        return "redirect:/carrito/listado";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminar(@RequestParam Integer idProducto) {

        carritoService.eliminarProducto(idProducto);

        return "redirect:/carrito/listado";
    }

    @PostMapping("/carrito/actualizar")
    public String actualizar(@RequestParam Integer idProducto,
                             @RequestParam int cantidad) {

        carritoService.actualizarCantidad(idProducto, cantidad);

        return "redirect:/carrito/listado";
    }
}
