/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import domain.Producto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
 * @author ssanc
 */
@Service
public class CarritoService {

    private final List<ItemCarrito> items = new ArrayList<>();

    public void agregarProducto(Producto producto) {

        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + 1);
                return;
            }
        }

        items.add(new ItemCarrito(producto, 1));
    }

    public void eliminarProducto(Integer idProducto) {
        items.removeIf(item
                -> item.getProducto().getId().equals(idProducto));
    }

    public void actualizarCantidad(Integer idProducto, int cantidad) {

        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(idProducto)) {

                if (cantidad <= 0) {
                    eliminarProducto(idProducto);
                } else {
                    item.setCantidad(cantidad);
                }

                return;
            }
        }
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    public int calcularCantidadProductos() {
        int cantidad = 0;

        for (ItemCarrito item : items) {
            cantidad += item.getCantidad();
        }

        return cantidad;
    }

    public void vaciarCarrito() {
        items.clear();
    }
}