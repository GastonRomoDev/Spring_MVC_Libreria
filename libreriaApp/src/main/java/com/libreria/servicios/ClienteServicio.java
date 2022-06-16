
package com.libreria.servicios;

import com.libreria.entidades.Cliente;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.ClienteRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRep;
    
    @Transactional
    public void registrar(long documento,String nombre,String apellido,String telefono) throws errorServicio{
        validar(documento, nombre, apellido, telefono);
        Cliente c = new Cliente();
        c.setDocumento(documento);
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setTelefono(telefono);
        c.setAlta(true);
        
        clienteRep.save(c);
    }
    @Transactional
    public void modificar(String id,long documento,String nombre,String apellido,String telefono) throws errorServicio{
        validar(documento, nombre, apellido, telefono);
        Optional <Cliente> respuesta = clienteRep.findById(id);
        if(respuesta.isPresent()){
            Cliente c = respuesta.get();
            c.setDocumento(documento);
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setTelefono(telefono);
            
            clienteRep.save(c);
        }
    }
    @Transactional
    public void desabilitar(String id) throws errorServicio{
        Optional<Cliente> respuesta = clienteRep.findById(id);
        if(respuesta.isPresent()){
            Cliente c = respuesta.get();
            c.setAlta(false);
        }else{
            throw new errorServicio("No se pudo desasbilitar el cliente");
        }
    }
    @Transactional
    public void habilitar(String id) throws errorServicio{
        Optional<Cliente> respuesta = clienteRep.findById(id);
        if(respuesta.isPresent()){
            Cliente c = respuesta.get();
            c.setAlta(true);
        }else{
            throw new errorServicio("No se pudo habilitar el cliente");
        }
    }
    public void validar(long documento,String nombre,String apellido,String telefono) throws errorServicio{
        if(documento <= 1000000){
            throw new errorServicio("Error el cliente no puede tener un dni menor a 1000000");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new errorServicio("Error el cliente tiene que tener un nombre");
        }
        if(apellido == null || apellido.isEmpty()){
            throw new errorServicio("Error el cliente tiene que tener un apellido");
        }
        if(telefono == null || telefono.isEmpty()){
            throw new errorServicio("Error el cliente tiene que tener un celular");
        }
    }
    
}
