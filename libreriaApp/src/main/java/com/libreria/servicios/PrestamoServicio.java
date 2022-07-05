
package com.libreria.servicios;

import com.libreria.entidades.Cliente;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Prestamo;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.ClienteRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import com.libreria.repositorios.PrestamoRepositorio;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {
    @Autowired
    private PrestamoRepositorio prestamoRep;
    @Autowired
    private ClienteRepositorio clienteRep;
    @Autowired
    private LibroRepositorio libroRep;
    
    @Transactional
    public void registrar(Date fechaPrestamo,Date fechaDevolucion,String idLibro,String idCliente) throws errorServicio{
        Cliente c = clienteRep.findById(idCliente).get();
        Libro l = libroRep.findById(idLibro).get();
        if(l.getEjemRestantes() == 0 ){
            throw new errorServicio("No quedan mas ejemplares del libro "+l.getTitulo());
        }
        validar(c,l,fechaPrestamo, fechaDevolucion);
        
        Prestamo p = new Prestamo();
        p.setFechaPrestamo(fechaPrestamo);
        p.setFechaDevolucion(fechaDevolucion);
        
        l.setEjemPrestados(l.getEjemPrestados() + 1 );
        l.setEjemRestantes();
        libroRep.save(l);
        
        p.setCliente(c);
        p.setLibro(l);
        p.setAlta(true);
        
        prestamoRep.save(p);
    }
    @Transactional
    public void modificar(String id,Date fechaPrestamo,Date fechaDevolucion,String idLibro,String idCliente) throws errorServicio{
        Cliente c = clienteRep.findById(idCliente).get();
        Libro l = libroRep.findById(idLibro).get();
        validar(c,l,fechaPrestamo, fechaDevolucion);
        
        Optional <Prestamo> respuesta = prestamoRep.findById(id);
        if(respuesta.isPresent()){
            Prestamo p = respuesta.get();
            p.setFechaPrestamo(fechaPrestamo);
            p.setFechaDevolucion(fechaDevolucion);
            p.setCliente(clienteRep.findById(idCliente).get());
            p.setLibro(libroRep.findById(idLibro).get());
        
            prestamoRep.save(p);
        }
    }
    @Transactional
    public void desabilitar(String id) throws errorServicio{
        Optional <Prestamo> respuesta = prestamoRep.findById(id);
        if(respuesta.isPresent()){
            Prestamo p = respuesta.get();
            p.setAlta(false);
        }else{
            throw new errorServicio("No se pudo desabilitar el prestamo");
        }
    }
    @Transactional
    public void habilitar(String id) throws errorServicio{
        Optional <Prestamo> respuesta = prestamoRep.findById(id);
        if(respuesta.isPresent()){
            Prestamo p = respuesta.get();
            p.setAlta(true);
        }else{
            throw new errorServicio("No se pudo desabilitar el prestamo");
        }
    }
    public void validar(Cliente c,Libro l,Date fechaPrestamo,Date fechaDevolucion) throws errorServicio{
        Calendar calendarPrest = Calendar.getInstance();
        Calendar calendarDev = Calendar.getInstance();
        calendarPrest.setTime(fechaPrestamo);
        calendarDev.setTime(fechaDevolucion);
        LocalDate fechaPres = LocalDate.of(calendarPrest.get(Calendar.YEAR), calendarPrest.get(Calendar.MONTH), calendarPrest.get(Calendar.DAY_OF_MONTH));
        LocalDate fechaDev = LocalDate.of(calendarDev.get(Calendar.YEAR), calendarDev.get(Calendar.MONTH), calendarDev.get(Calendar.DAY_OF_MONTH));
        
        if(fechaDev.compareTo(fechaPres) < 0 ){
            throw new errorServicio("Error la fecha de devolucion no puede ser menor a la fecha del prestamo");
        }
        
        List<Prestamo> prestamos = prestamoRep.buscarPorDocumentoCliente(c.getDocumento());
        for (Prestamo prestamo : prestamos) {
            //si hay un prestamo de los prestamos que realizo el Cliente c que coincida con el libro que quiere adquirir
            if(prestamo.getLibro().getId().equals(l.getId())){
                throw new errorServicio("El cliente "+c.getNombre()+" "+c.getApellido()+" ya tiene un prestamo del libro "+l.getTitulo());
            }
        }
        
    }
            
}
