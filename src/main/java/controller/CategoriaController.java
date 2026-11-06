package controller;

import domain.Categoria;
import service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categoria/listado")
    public String listado(Model model) {
        var categorias = categoriaService.listarCategorias();
        model.addAttribute("categorias", categorias);
        return "categoria/listado";
    }

    @GetMapping("/categoria/nuevo")
    public String nuevo(Categoria categoria) {
        return "categoria/modifica";
    }

    @PostMapping("/categoria/guardar")
    public String guardar(Categoria categoria) {
        categoriaService.guardarCategoria(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/categoria/modificar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaService.buscarCategoria(id);
        model.addAttribute("categoria", categoria);
        return "categoria/modifica";
    }

    @GetMapping("/categoria/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categoria/listado";
    }
}
