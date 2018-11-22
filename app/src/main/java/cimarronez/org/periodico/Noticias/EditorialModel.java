package cimarronez.org.periodico.Noticias;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class EditorialModel {

    public String id;
    public int estatus;
    public String titulo;
    public String descripcion;
    public String subtitulo;
    public String autor;
    public String imagen;
    public String fecha;

    public EditorialModel(){

    }

    public EditorialModel(String id, int estatus, String titulo, String descripcion, String categoria, String autor, String imagen, String fecha) {
        this.id = id;
        this.estatus = estatus;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.subtitulo = categoria;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return subtitulo;
    }

    public void setCategoria(String categoria) {
        this.subtitulo = categoria;
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


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("estatus", estatus);
        result.put("titulo", titulo);
        result.put("descripcion", descripcion);
        result.put("subtitulo", subtitulo);
        result.put("autor", autor);
        result.put("imagen", imagen);
        result.put("fecha", fecha);

        return result;
    }
}
