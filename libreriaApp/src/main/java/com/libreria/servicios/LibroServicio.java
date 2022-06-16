package com.libreria.servicios;

import com.libreria.entidades.Foto;
import com.libreria.entidades.Libro;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.EditorialRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private FotoService fotoServ;
    
    @Transactional
    public void registrar(MultipartFile archivo,long isbn,String titulo,Integer anio,Integer ejemplares,Integer ejemPrestados,String idAutor,String idEditorial) throws errorServicio{
        validar(archivo,isbn, titulo, anio, ejemplares, ejemPrestados);
        Libro l = new Libro();
        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setAnio(anio);
        l.setEjemplares(ejemplares);
        l.setEjemPrestados(ejemPrestados);
        l.setEjemRestantes();
        l.setAlta(true);
        l.setAutor(autorRepositorio.findById(idAutor).get());
        l.setEditorial(editorialRepositorio.findById(idEditorial).get());
        Foto foto = fotoServ.guardar(archivo);
        l.setFoto(foto);
        
        
        libroRepositorio.save(l);
        
    }
    @Transactional
    public void modificar(MultipartFile archivo,String id,long isbn,String titulo,Integer anio,Integer ejemplares,Integer ejemPrestados,String idAutor, String idEditorial) throws errorServicio{
        validar(archivo,isbn, titulo, anio, ejemplares, ejemPrestados);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro l = respuesta.get();
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setAnio(anio);
            l.setEjemplares(ejemplares);
            l.setEjemPrestados(ejemPrestados);
            l.setEjemRestantes();
            l.setAlta(true);
            l.setAutor(autorRepositorio.findById(idAutor).get());
            l.setEditorial(editorialRepositorio.findById(idEditorial).get());
            String idFoto = null;
            if(l.getFoto() != null){
                idFoto = l.getFoto().getId();
            }
            Foto foto = fotoServ.actualizar(idFoto, archivo);
            l.setFoto(foto);
            
            
            libroRepositorio.save(l);
        
        }else{
            throw new errorServicio("Error el libro a modificar no existe");
        }
    }
    @Transactional
    public void desabilitar(String id) throws errorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro l = respuesta.get();
            l.setAlta(false);
            
            libroRepositorio.save(l);
        }else{
            throw new errorServicio("No se encontro el libro solicitado");
        }
    }
    @Transactional
    public void habilitar(String id) throws errorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            Libro l = respuesta.get();
            l.setAlta(true);
            
            libroRepositorio.save(l);
        }else{
            throw new errorServicio("No se encontro el libro solicitado");
        }
    }
    @Transactional
    public void validar(MultipartFile archivo,long isbn,String titulo,Integer anio,Integer ejemplares,Integer ejemPrestados) throws errorServicio{
        if(archivo == null){
            throw new errorServicio("El libro tiene que llevar una imagen");
        }
        
        if(isbn <= 0){
            throw new errorServicio("Error el isbn no puede ser menor o igual a 0");
        }
        if(titulo == null || titulo.isEmpty()){
            throw new errorServicio("Error no se especifico el titulo.");
        }
        if(anio <= 0){
            throw new errorServicio("Error el año es invalido");
        }
        if(ejemplares < 0){
            throw new errorServicio("Error numero invalido de ejemplares del libro");
        }
        if(ejemPrestados < 0){
            throw new errorServicio("Error el numero de ejemplares prestados no puede ser negativo");
        }
        if(ejemPrestados > ejemplares){
            throw new errorServicio("Error el numero de ejemplares prestados no puede ser mayor al numero de ejemplares");
        }
        
        
    }   
    
}
