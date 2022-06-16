
package com.libreria.repositorios;

import com.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public void buscarLibroPorId(@Param("id")String id);
    @Query("SELECT l FROM Libro l WHERE l.autor.id = :idAutor")
    public void buscarLibroPorAutor(@Param("idAutor") String idAutor);
    @Query("SELECT l FROM Libro l WHERE l.editorial.id = :idEditorial")
    public void buscarLibroPorEditorial(@Param("idEditorial") String idEditorial);
}
