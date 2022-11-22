/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import ejemplohibernate.Departamentos;
import ejemplohibernate.Empleados;
import java.util.Date;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author 7J
 */
public class Ejemplo {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
    static Session sesion = sesionf.openSession();
    static Transaction tx = null;

    public static void close() {
        sesion.close();
        sesionf.close();
    }

    public static void mostrarEmple(Set<Empleados> listemple) {

        for (Empleados empleados : listemple) {
            System.out.println("------------------\nApellido: " + empleados.getApellido() + "; Salario: " + empleados.getSalario());
        }
        System.out.println("\nFin empleados");
    }

    public static void main(String[] args) {
        tx = sesion.beginTransaction();
        System.out.println("==================\nDatos del Dept1");
        Departamentos dep;
        dep = (Departamentos) sesion.get(Departamentos.class, 1);
        System.out.println("Nombre: " + dep.getDnombre());
        System.out.println("Localidad: " + dep.getLoc());
        System.out.println("Añadir Empleado:");

        Empleados empleado = new Empleados(101, dep, "Vicktor el Hugo", 1);
        Set<Empleados> listaEmpleados = dep.getEmpleadoses();
        listaEmpleados.add(empleado);
        dep.setEmpleadoses(listaEmpleados);
        sesion.save(empleado);
        tx.commit();
        mostrarEmple(listaEmpleados);
        close();
    }
}
