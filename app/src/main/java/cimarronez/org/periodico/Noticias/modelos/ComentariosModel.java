package cimarronez.org.periodico.Noticias.modelos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ComentariosModel {

    public String id;
    public int estatus;
    public String idUsuario;
    public String nombre;
    public String comentario;
    public String fecha;

    public ComentariosModel(){

    }

    public ComentariosModel(String id,int estatus, String idUsuario,String nombre, String comentario, String fecha) {
        this.id = id;
        this.estatus = estatus;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("estatus", estatus);
        result.put("idUsuario", idUsuario);
        result.put("nombre", nombre);
        result.put("comentario", comentario);
        result.put("fecha", fecha);

        return result;
    }
}
