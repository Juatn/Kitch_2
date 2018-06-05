package moreno.juan.kitch.modelo;

/**
 * Created by juana on 19/04/2018.
 */

public class Comentario {

    private String nombre_usuario;
    private String s_mensaje;
    private float f_nota_receta;

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getS_mensaje() {
        return s_mensaje;
    }

    public void setS_mensaje(String s_mensaje) {
        this.s_mensaje = s_mensaje;
    }

    public float getF_nota_receta() {
        return f_nota_receta;
    }

    public void setF_nota_receta(float f_nota_receta) {
        this.f_nota_receta = f_nota_receta;
    }
}
