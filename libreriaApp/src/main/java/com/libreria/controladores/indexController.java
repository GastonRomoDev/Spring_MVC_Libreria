
package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Cliente;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.ClienteRepositorio;
import com.libreria.repositorios.EditorialRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class indexController {
    
    @Autowired
    private AutorRepositorio autorRep;
    @Autowired
    private EditorialRepositorio editRep;
    @Autowired
    private LibroRepositorio libroRep;
    @Autowired
    private ClienteRepositorio clienteRep;
    
    @GetMapping("/")
    public String index(ModelMap modelo){
        List<Autor> autores = autorRep.findAll();
        List<Editorial> editoriales = editRep.findAll();
        List<Libro> libros = libroRep.findAll();
        List<Cliente> clientes = clienteRep.findAll();
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        modelo.put("libros", libros);
        modelo.put("clientes", clientes);
        
        return "index.html";
    }
}
