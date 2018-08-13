package cimarronez.org.periodico.Noticias;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CategoriasModel {

    public int id;
    public int estatus;
    public String titulo;
    public String descripcion;
    public String categoria;
    public String autor;
    public String imagen;
    public String fecha;

    public CategoriasModel(){

    }

    public CategoriasModel(int id, int estatus, String titulo, String descripcion, String categoria, String autor, String imagen, String fecha) {
        this.id = id;
        this.estatus = estatus;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.autor = autor;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
