
package com.libreria.controladores;

import com.libreria.entidades.Libro;
import com.libreria.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoController {
    
    @Autowired
    private LibroRepositorio libroRep;
    
    @GetMapping("/libro")
    public ResponseEntity<byte[]> fotoLibro(@RequestParam String id){
        
        Libro l = libroRep.findById(id).get();
        
        byte[] foto =l.getFoto().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(foto,headers,HttpStatus.OK);
    }
}
