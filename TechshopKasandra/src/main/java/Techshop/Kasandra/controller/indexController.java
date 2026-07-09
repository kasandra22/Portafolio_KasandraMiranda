/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Techshop.Kasandra.controller;
import Techshop.Kasandra.service.CategoriaService;
import Techshop.Kasandra.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author HP
 */

@Controller
public class indexController {
     private final ProductoService productoService;
    private final CategoriaService categoriaService;
 
    public indexController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }
 
    @GetMapping("/")
    public String cargarPaginaInicio(Model model) {
        var productos = productoService.getProductos(true);
        model.addAttribute("productos", productos);
 
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
 
        return "/index";
    }
 
    @GetMapping("/consultas/{idCategoria}")
    public String listado(@PathVariable("idCategoria") Integer idCategoria, Model model) {
        model.addAttribute("idCategoriaActual", idCategoria);
 
        var categoriaOptional = categoriaService.getCategoria(idCategoria);
        if (categoriaOptional.isEmpty()) {
            // Puede ser que no exista la categoría buscada...
            model.addAttribute("productos", java.util.Collections.emptyList());
        } else {
            var categoria = categoriaOptional.get();
            var productos = categoria.getProductos();
            model.addAttribute("productos", productos);
        }
 
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
 
        return "/index";
    }
}
