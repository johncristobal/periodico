package cimarronez.org.periodico.Noticias.modelos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MapasModel {

    public String id;
    public int estatus;
    public String titulo;
    public String descripcion;
    public String poblacion;
    public String imagen;

    public MapasModel(){

    }

    public MapasModel(String id, int estatus, String titulo, String descripcion, String poblacion, String imagen) {
        this.id = id;
        this.estatus = estatus;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.poblacion = poblacion;
        this.imagen = imagen;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
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


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("estatus", estatus);
        result.put("titulo", titulo);
        result.put("descripcion", descripcion);
        result.put("poblacion", poblacion);
        result.put("imagen", imagen);

        return result;
    }
}
