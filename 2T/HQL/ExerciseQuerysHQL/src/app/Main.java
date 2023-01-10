package app;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Empleados;

/**
 *
 * @author 7J
 */
public class Main {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
    static Session sesion = sesionf.openSession();
    
    public static void closeSesions() {
        sesion.close();
        sesionf.close();
    }

    public static void infoVentas() {
        Query q = sesion.createQuery("FROM Empleados e"
                + " WHERE e.departamentos.dnombre = 'Ventas'");
        List<Empleados> lista = q.list();
        System.out.println("Hay " + lista.size() + " empleados en Ventas");
        for (Empleados empleado : lista) {
            System.out.println("Apellido: " + empleado.getApellido());
        }
    }

    public static void main(String[] args) {
        infoVentas();
        closeSesions();
    }
}
