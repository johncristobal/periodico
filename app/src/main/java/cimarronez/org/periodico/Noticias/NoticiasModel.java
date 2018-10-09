package cimarronez.org.periodico.Noticias;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class NoticiasModel {

    public String id;
    public int estatus;
    public String titulo;
    public String descripcion;
    public int categoria;
    public String autor;
    public String imagen;
    public String fecha;
    public int likes;

    public NoticiasModel(){

    }

    public NoticiasModel(String id, int estatus, String titulo, String descripcion, int categoria, String autor, String imagen, String fecha, int likes) {
        this.id = id;
        this.estatus = estatus;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.autor = autor;
        this.imagen = imagen;
        this.fecha = fecha;
        this.likes = likes;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
