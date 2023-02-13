
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.Fabricantes;
import util.NewHibernateUtil;

/**
 *
 * @author Nando
 */
public class Main {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
    static Transaction tx = null;
    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

    /**
     * Devuelve una lista con todos los Fabricantes mostrando solo Cif, nombre y
     * descripción
     */
    public static List<Object[]> getFabricantes() {
        Session sesion = sesionf.openSession();

        Query q = sesion.createQuery("SELECT cif, nombre, descrip FROM Fabricantes");
        List<Object[]> listFabricante = (List<Object[]>) q.list();

        sesion.close();

        return listFabricante;
    }

    public static Fabricantes getSpecificFabricante(String cif) {
        Session sesion = sesionf.openSession();
        Fabricantes fabricante = null;

        Query q = sesion.createQuery("FROM Fabricantes WHERE cif=:cif");
        q.setString("cif", cif);
        try {
            fabricante = (Fabricantes) q.uniqueResult();
        } catch (org.hibernate.NonUniqueResultException e) {
            System.out.println("\t[-] More than 1 Result returned. Taking the first one...\n");
            fabricante = (Fabricantes) q.setMaxResults(1).uniqueResult();
        }
        if (fabricante != null) {
            System.out.printf("######## Fabricante (%s) ########\n", cif);
            System.out.println(fabricante);
        }
        sesion.close();

        return fabricante;
    }

    public static void deleteFabricante(Fabricantes fabricante) {
        Session sesion = sesionf.openSession();
        int affectedRows;
        String opc;

        Query q = sesion.createQuery("DELETE Productos WHERE fabricantes like :fabricante");
        q.setParameter("fabricante", fabricante);
        affectedRows = q.executeUpdate();
        System.out.printf("\t[+] Affected Rows (%d) \n", affectedRows);
        System.out.println("Would u like to Save the Changes? (s/n)");
        opc = eb.nextLine();
        if (opc.equalsIgnoreCase("s")) {
            Transaction tx = sesion.beginTransaction();
            tx.commit();
            System.out.println("\t[+] Changes Comitted");
        } else {
            System.out.println("[+] Changes NOT Comitted");
        }
        sesion.close();
    }

    public static void main(String[] args) {
        //Exercise 1
        List<Object[]> listFabricante = getFabricantes();
        for (Object[] fabricante : listFabricante) {
            System.out.println("=====================");
            System.out.println("Cif => " + fabricante[0]);
            System.out.println("Name => " + fabricante[1]);
            System.out.println("Description => " + fabricante[2]);
        }
        //Exercise 2 /\
        Fabricantes fabricante = null;
        String cif;

        do {
            System.out.print("\nCif => ");
            cif = eb.nextLine();
            fabricante = getSpecificFabricante(cif);
            if (fabricante == null) {
                System.out.printf("\t[-] Fabricante(%s) doesn't exist\n", cif);
            }
        } while (fabricante == null);
        //Exercise 3
        deleteFabricante(fabricante);

        fabricante = getSpecificFabricante(cif);
    }
}
