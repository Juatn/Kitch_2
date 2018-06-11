package moreno.juan.kitch.modelo;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class Ingrediente {
    @Id
    private Long id;
    @NameInDb("ingrediente")
    private String ingrediente;

    public Ingrediente(Long id, String ingrediente) {
        this.id = id;
        this.ingrediente = ingrediente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }
}
