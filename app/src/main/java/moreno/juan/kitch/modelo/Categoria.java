package moreno.juan.kitch.modelo;

public class Categoria {

    private int foto_categoria;
    private String nombre_categoria;


    public Categoria(int foto_categoria, String nombre_categoria) {
        this.foto_categoria = foto_categoria;
        this.nombre_categoria = nombre_categoria;
    }

    public int getFoto_categoria() {
        return foto_categoria;
    }

    public void setFoto_categoria(int foto_categoria) {
        this.foto_categoria = foto_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }
}
