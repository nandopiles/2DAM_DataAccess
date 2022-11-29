package app;

import java.util.HashMap;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Departments;

/**
 *
 * @author Nando
 */
public class QueryDept {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();

    public QueryDept() {
    }

    public static void showDepartment(Departments dep) {
        Session sesion = sesionf.openSession();
        sesion.save(dep);
        System.out.println(dep.toString());
        sesion.close();
    }

    public static Departments[] getAllDepartments() {
        Session sesion = sesionf.openSession();
        Query q = sesion.createQuery("FROM Departments");
        List<Departments> listDept = q.list();
        Departments[] arrayDep = new Departments[listDept.size()];
        listDept.toArray(arrayDep);
        sesion.close();

        return arrayDep;
    }

    public static Departments getDepartmentByName(String patternName) {
        Session sesion = sesionf.openSession();
        Departments dep = null;

        Query q = sesion.createQuery("FROM Departments d WHERE d.name='" + patternName + "'");
        dep = (Departments) q.uniqueResult();
        sesion.close();

        return dep;
    }

    public static double getAverageSalaryofDepartment(String depName) {
        double a = 0;

        return a;
    }

    public static HashMap<String, Double> getAverageSalaryPerDept() {
        HashMap<String, Double> a = null;

        return a;
    }

    public static void closeSF() {
        sesionf.close();
    }

    @Override
    public String toString() {
        return "";
    }
}
