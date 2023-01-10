package app;

import utils.NewHibernateUtil;
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

        sesion.update(dep);
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

        Query q = sesion.createQuery("FROM Departments d WHERE d.name=:deptName");
        q.setString("deptName", patternName);
        try {
            dep = (Departments) q.uniqueResult();
        } catch (org.hibernate.NonUniqueResultException e) {
            System.out.println("\t(-) More than 1 Result returned. Taking the first one...\n");
            dep = (Departments) q.setMaxResults(1).uniqueResult();
        }
        sesion.close();

        return dep;
    }

    public static double getAverageSalaryofDepartment(String deptName) {
        Session sesion = sesionf.openSession();
        double averageSalary;

        Query q = sesion.createQuery("SELECT avg(t.salary) "
                + "FROM Teachers t "
                + "WHERE departments.name=:deptName");
        q.setString("deptName", deptName);
        averageSalary = (double) q.uniqueResult();
        sesion.close();

        return averageSalary;
    }

    public static HashMap<String, Double> getAverageSalaryPerDept() {
        Session sesion = sesionf.openSession();
        HashMap<String, Double> hash = new HashMap<>();

        Query q = sesion.createQuery("SELECT departments.name, avg(salary) "
                + "FROM Teachers "
                + "GROUP BY departments.name");
        List<Object[]> listFinal = (List<Object[]>) q.list();
        for (Object[] obj : listFinal) {
            hash.put((String) obj[0], (Double) obj[1]);
        }
        sesion.close();

        return hash;
    }

    public static void closeSF() {
        sesionf.close();
    }

    @Override
    public String toString() {
        return "";
    }
}
