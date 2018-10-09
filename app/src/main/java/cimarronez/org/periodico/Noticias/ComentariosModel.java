package cimarronez.org.periodico.Noticias;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ComentariosModel {

    public String id;
    public String idNoticia;
    public int estatus;
    public String idUsuario;
    public String comentario;
    public String fecha;

    public ComentariosModel(){

    }

    public ComentariosModel(String id, String idNoticia,int estatus, String idUsuario, String comentario, String fecha) {
        this.id = id;
        this.idNoticia = idNoticia;
        this.estatus = estatus;
        this.idUsuario = idUsuario;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(String idNoticia) {
        this.idNoticia = idNoticia;
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("estatus", estatus);
        result.put("idUsuario", idUsuario);
        result.put("comentario", comentario);
        result.put("fecha", fecha);

        return result;
    }
}
