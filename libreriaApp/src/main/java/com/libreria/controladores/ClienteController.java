package com.libreria.controladores;

import com.libreria.entidades.Cliente;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.ClienteRepositorio;
import com.libreria.servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteRepositorio clienteRepo;
    @Autowired
    private ClienteServicio clienteServ;
    
    @GetMapping("/administrar")
    public String cliente(ModelMap modelmap){
        List <Cliente> clientes = clienteRepo.findAll();
        modelmap.put("clientes", clientes);
        return "cliente.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo,@RequestParam long documento,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String telefono ){
        try{
            clienteServ.registrar(documento, nombre, apellido, telefono);
        }catch(errorServicio e){
            modelo.put("error", e.getMessage());
            List <Cliente> clientes = clienteRepo.findAll();
            modelo.put("clientes", clientes);
            return "index.html";
        }
        modelo.put("exito", "El cliente fue registrado de manera exitosa");
        return "index.html";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo,@PathVariable String id){
        Cliente c = clienteRepo.findById(id).get();
        modelo.put("clienteModificar", c);
        return "cliente.html";
    }
    @PostMapping("/modificar")
    public String modificarCliente(ModelMap modelo,@RequestParam String id,@RequestParam long documento,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String telefono){
        try {
            clienteServ.modificar(id, documento, nombre, apellido, telefono);
        } catch (errorServicio e) {
            modelo.put("error", e.getMessage());
            return "index.html";
        }
        return "redirect:/cliente/administrar";
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws errorServicio{
        try{
            clienteServ.desabilitar(id);
            return "redirect:/cliente/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws errorServicio{
        try{
            clienteServ.habilitar(id);
            return "redirect:/cliente/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
}
