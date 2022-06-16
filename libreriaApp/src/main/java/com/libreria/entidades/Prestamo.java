
package com.libreria.entidades;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy ="uuid2")
    private String id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPrestamo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDevolucion;
    private boolean alta;
    @OneToOne
    private Libro libro;
    @ManyToOne
    private Cliente cliente;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.fechaPrestamo);
        hash = 79 * hash + Objects.hashCode(this.fechaDevolucion);
        hash = 79 * hash + (this.alta ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.libro);
        hash = 79 * hash + Objects.hashCode(this.cliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prestamo other = (Prestamo) obj;
        if (this.alta != other.alta) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaPrestamo, other.fechaPrestamo)) {
            return false;
        }
        if (!Objects.equals(this.fechaDevolucion, other.fechaDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.libro, other.libro)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion + ", alta=" + alta + ", libro=" + libro + ", cliente=" + cliente + '}';
    }
    
      
    
}
