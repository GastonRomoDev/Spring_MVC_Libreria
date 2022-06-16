
package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Transactional
    public void registrar(String nombre) throws errorServicio{
        validar(nombre);
        Editorial e = new Editorial();
        e.setNombre(nombre);
        e.setAlta(true);
        
        editorialRepositorio.save(e);
        
    }
    @Transactional
    public void modificar(String id,String nombre) throws errorServicio{
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial e = respuesta.get();
            e.setNombre(nombre);
            
            editorialRepositorio.save(e);
        }
    }
    @Transactional
    public void desabilitar(String id) throws errorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial e = respuesta.get();
            e.setAlta(false);
            
            editorialRepositorio.save(e);
            
        }else{
            
            throw new errorServicio("No se encontro el autor solicitado");
        }
    }
    @Transactional
    public void habilitar(String id) throws errorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial e = respuesta.get();
            e.setAlta(true);
            
            editorialRepositorio.save(e);
        }else{
            throw new errorServicio("No se encontro el autor solicitado");
        }
    }
    
    public void validar(String nombre) throws errorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new errorServicio("Error la editorial no puede tener un nombre vacio.");
        }
    }
}
