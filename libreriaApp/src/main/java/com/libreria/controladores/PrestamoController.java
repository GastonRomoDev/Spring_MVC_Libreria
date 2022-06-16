package com.libreria.controladores;

import com.libreria.entidades.Cliente;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Prestamo;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.ClienteRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import com.libreria.repositorios.PrestamoRepositorio;
import com.libreria.servicios.PrestamoServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private PrestamoServicio prestamoServ;
    
    @GetMapping("/administrar")
    public String registro(ModelMap modelo){
        List <Prestamo> prestamos = prestamoRepositorio.findAll();
        List <Libro> libros = libroRepositorio.findAll();
        List <Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("prestamos", prestamos);
        modelo.put("libros", libros);
        modelo.put("clientes", clientes);
        return "prestamo.html";
    }
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo,@RequestParam String idLibro,@RequestParam String fechaPrestamo,@RequestParam String fechaDevolucion,@RequestParam String idCliente) throws errorServicio, ParseException{
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaPrest = formato.parse(fechaPrestamo);
            Date fechaDev = formato.parse(fechaDevolucion);
            prestamoServ.registrar(fechaPrest, fechaDev, idLibro, idCliente);
        } catch (errorServicio e) {
            modelo.put("error", e.getMessage());
            return "index.html"; 
        }
        modelo.put("exito", "El prestamo fue registrado de manera exitosa");
        return "index.html";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo,@PathVariable String id){
        Prestamo p = prestamoRepositorio.findById(id).get();
        List <Libro> libros = libroRepositorio.findAll();
        List <Cliente> clientes = clienteRepositorio.findAll();
        modelo.put("libros", libros);
        modelo.put("clientes", clientes);
        modelo.put("prestamoModificar", p);
        return "prestamo.html";
    }
    @PostMapping("/modificar")
    public String modificarPrestamo(ModelMap modelo,@RequestParam String id,@RequestParam String idLibro,@RequestParam String fechaPrestamo,@RequestParam String fechaDevolucion,@RequestParam String idCliente) throws ParseException{
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaPrest = formato.parse(fechaPrestamo);
            Date fechaDev = formato.parse(fechaDevolucion);
            prestamoServ.modificar(id,fechaPrest, fechaDev, idLibro, idCliente);
        } catch (errorServicio e) {
            modelo.put("error", e.getMessage());
            return "index.html"; 
        }
        return "redirect:/prestamo/administrar";
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws errorServicio{
        try{
            prestamoServ.desabilitar(id);
            return "redirect:/prestamo/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws errorServicio{
        try{
            prestamoServ.habilitar(id);
            return "redirect:/prestamo/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
}
