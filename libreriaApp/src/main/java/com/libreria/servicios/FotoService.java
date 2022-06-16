
package com.libreria.servicios;

import com.libreria.entidades.Foto;
import com.libreria.errores.errorServicio;
import com.libreria.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {
    
    @Autowired
    private FotoRepositorio fotoRep;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws errorServicio{
        if(archivo != null){
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
            
                return fotoRep.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }   
        }
        return null;
    }
    @Transactional
    public Foto actualizar(String idFoto,MultipartFile archivo) throws errorServicio{
        if(archivo != null){
            try {
                Foto foto = new Foto();
                if(idFoto != null){
                    Optional<Foto> rta = fotoRep.findById(idFoto);
                    if(rta.isPresent()){
                        foto = rta.get();
                    }
                }
                
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
            
                return fotoRep.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }   
        }
        return null;
    }
}
