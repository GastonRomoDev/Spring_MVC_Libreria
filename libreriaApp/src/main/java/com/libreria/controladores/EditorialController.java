package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import com.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServ;
    @Autowired 
    private EditorialRepositorio editorialRep;

    @GetMapping("/administrar")
    public String formularioEditorial(ModelMap modelo){
        List <Editorial> editoriales = editorialRep.findAll();
        modelo.put("editoriales", editoriales);
        return "editorial.html";
    }
    @PostMapping("/registrar")
    public String registrarEditorial(ModelMap modelo,@RequestParam String nombre) throws errorServicio{
        try{
            editorialServ.registrar(nombre);
        }catch(errorServicio e){
            modelo.put("error", e.getMessage());
            modelo.put("nombre",nombre);
            List <Editorial> editoriales = editorialRep.findAll();
            modelo.put("editoriales", editoriales);
            return "index.html";
        }
        modelo.put("exito", "La editorial fue registrada de manera satisfactoria!");
        return "index.html";
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws errorServicio{
        try{
            editorialServ.desabilitar(id);
            return "redirect:/editorial/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo,@PathVariable String id){
        Editorial e = editorialRep.findById(id).get();
        modelo.put("editorialModificar", e);
        return "editorial.html";
    }
    @PostMapping("/modificar")
    public String modificarEditorial(ModelMap modelo,@RequestParam String id,@RequestParam String nombre){
        try {
            editorialServ.modificar(id, nombre);
            
        } catch (errorServicio e) {
            modelo.put("error", e.getMessage());
            return "index.html";    
        }
        return "redirect:/editorial/administrar";
        
    }
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws errorServicio{
        try{
            editorialServ.habilitar(id);
            return "redirect:/editorial/administrar";
        }catch(errorServicio e){
            return "redirect:/";
        }
    }
}