package pojos;
// Generated 13 feb. 2023 8:50:08 by Hibernate Tools 4.3.1

/**
 * Productos generated by hbm2java
 */
public class Productos implements java.io.Serializable {

    private int ref;
    private Fabricantes fabricantes;
    private String nombre;
    private Integer precio;

    public Productos() {
    }

    public Productos(int ref) {
        this.ref = ref;
    }

    public Productos(int ref, Fabricantes fabricantes, String nombre, Integer precio) {
        this.ref = ref;
        this.fabricantes = fabricantes;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getRef() {
        return this.ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public Fabricantes getFabricantes() {
        return this.fabricantes;
    }

    public void setFabricantes(Fabricantes fabricantes) {
        this.fabricantes = fabricantes;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return this.precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Productos{" + "ref=" + ref + ", fabricantes=" + fabricantes + ", nombre=" + nombre + ", precio=" + precio + '}';
    }
}
