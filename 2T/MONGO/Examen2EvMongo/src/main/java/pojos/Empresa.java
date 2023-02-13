package pojos;

import java.util.List;

/**
 * @author Nando💦
 */
public class Empresa {
    private String nombre;
    private String cif;
    private String web;
    private List<String> servicios;
    private Contabilidad contabilidad;

    public Empresa() {
    }

    public Empresa(String nombre, String cif, String web, List<String> servicios, Contabilidad contabilidad) {
        this.nombre = nombre;
        this.cif = cif;
        this.web = web;
        this.servicios = servicios;
        this.contabilidad = contabilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    public Contabilidad getContabilidad() {
        return contabilidad;
    }

    public void setContabilidad(Contabilidad contabilidad) {
        this.contabilidad = contabilidad;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", cif='" + cif + '\'' +
                ", web='" + web + '\'' +
                ", servicios=" + servicios +
                ", contabilidad=" + contabilidad +
                '}';
    }
}
