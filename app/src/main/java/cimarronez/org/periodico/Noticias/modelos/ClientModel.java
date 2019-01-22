package cimarronez.org.periodico.Noticias.modelos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ClientModel {

    public String id;
    public int estatus;
    public String idUsuario;
    public String nombre;
    public String token;

    public ClientModel(){

    }

    public ClientModel(String id, int estatus, String idUsuario, String nombre,String token) {
        this.id = id;
        this.estatus = estatus;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.token = token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        result.put("token", token);

        return result;
    }
}
