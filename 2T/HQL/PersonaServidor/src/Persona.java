import java.io.Serializable;

public class Persona implements Serializable {
    private String nom;
    private int edad;
    private static final long serialVersionUID = 2002L;

    public Persona(String nom, int edad) {
        super();
        this.nom = nom;
        this.edad = edad;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
