package pojos;

/**
 * @author Nando💦
 */
public class Contabilidad {
    private int n_empleados;
    private int facturacion;
    private int gastos;

    public Contabilidad() {
    }

    public Contabilidad(int n_empleados, int facturacion, int gastos) {
        this.n_empleados = n_empleados;
        this.facturacion = facturacion;
        this.gastos = gastos;
    }

    public int getN_empleados() {
        return n_empleados;
    }

    public void setN_empleados(int n_empleados) {
        this.n_empleados = n_empleados;
    }

    public int getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(int facturacion) {
        this.facturacion = facturacion;
    }

    public int getGastos() {
        return gastos;
    }

    public void setGastos(int gastos) {
        this.gastos = gastos;
    }

    @Override
    public String toString() {
        return "Contabilidad{" +
                "n_empleados=" + n_empleados +
                ", facturacion=" + facturacion +
                ", gastos=" + gastos +
                '}';
    }
}
