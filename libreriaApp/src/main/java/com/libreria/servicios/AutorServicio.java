
package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.AutorRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio AutorRepositorio;
    @Transactional
    public void registrar(String nombre) throws errorServicio{
        validar(nombre);
        Autor a = new Autor();
        a.setNombre(nombre);
        a.setAlta(true);
        
        AutorRepositorio.save(a);
    }
    @Transactional
    public void modificarNombre(String id,String nombre) throws errorServicio{
        validar(nombre);
        Optional<Autor> respuesta = AutorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor a = respuesta.get();
            a.setNombre(nombre);
            
            AutorRepositorio.save(a);
        }else{
            throw new errorServicio("No se encontro el autor solicitado");
        }
        
    }
    @Transactional
    public void desabilitar(String id) throws errorServicio{
        Optional<Autor> respuesta = AutorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor a = respuesta.get();
            a.setAlta(false);
            
            AutorRepositorio.save(a);
        }else{
            throw new errorServicio("No se encontro el autor solicitado");
        }
    }
    @Transactional
    public void habilitar(String id) throws errorServicio{
        Optional<Autor> respuesta = AutorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor a = respuesta.get();
            a.setAlta(true);
            
            AutorRepositorio.save(a);
        }else{
            throw new errorServicio("No se encontro el autor solicitado");
        }
    } 
    public void validar(String nombre) throws errorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new errorServicio("Error el nombre no puede ser nulo. ");
        }
    }
}
