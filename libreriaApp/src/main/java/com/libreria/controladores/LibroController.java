package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.EditorialRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import com.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServicio libroServ;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired 
    private LibroRepositorio libroRep;


    @GetMapping("/administrar")
    public String formulario(ModelMap modelo){
        List <Autor> autores = autorRepositorio.findAll();
        List <Editorial> editoriales = editorialRepositorio.findAll();
        List <Libro> libros = libroRep.findAll();

        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        modelo.put("libros", libros);
        return "libro.html";
    }
    //metodo que recibe el formulario
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo,@RequestParam MultipartFile archivo,@RequestParam long isbn,@RequestParam String titulo,@RequestParam Integer anio,@RequestParam Integer ejemplares,@RequestParam Integer ejemPrestados,@RequestParam String idAutor,@RequestParam String idEditorial) throws errorServicio{
        try{
            libroServ.registrar(archivo,isbn, titulo, anio, ejemplares, ejemPrestados, idAutor, idEditorial);

        }catch(errorServicio e){
            modelo.put("error", e.getMessage());
            return "index.html";
        }
        modelo.put("exito", "El libro fue registrado de manera satisfactoria!");
        return "index.html";
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws errorServicio{
        try{
            libroServ.desabilitar(id);
            return "redirect:/libro/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo,@PathVariable String id){
        Libro l = libroRep.findById(id).get();
        List <Autor> autores = autorRepositorio.findAll();
        List <Editorial> editoriales = editorialRepositorio.findAll();

        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        modelo.put("libroModificar", l);
        return "libro.html";
    }
    @PostMapping("/modificar")
    public String modificarLibro(ModelMap modelo,@RequestParam String id,@RequestParam MultipartFile archivo,@RequestParam long isbn,@RequestParam String titulo,@RequestParam Integer anio,@RequestParam Integer ejemplares,@RequestParam Integer ejemPrestados,@RequestParam String idAutor,@RequestParam String idEditorial){
        try{
            libroServ.modificar(archivo,id,isbn, titulo, anio, ejemplares, ejemPrestados, idAutor, idEditorial);

        }catch(errorServicio e){
            modelo.put("error", e.getMessage());
            return "index.html";
        }
        return "redirect:/libro/administrar";
    }
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws errorServicio{
        try{
            libroServ.habilitar(id);
            return "redirect:/libro/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
}