package app;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojos.Teachers;

/**
 *
 * @author Nando
 */
public class QueryTeach {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();

    public QueryTeach() {
    }

    public static void showTeacher(Teachers teacher) {
        Session sesion = sesionf.openSession();

        sesion.update(teacher);
        System.out.println(teacher.toString());
        sesion.close();
    }

    public static Teachers[] getAllTeachers() {
        Session sesion = sesionf.openSession();

        Query q = sesion.createQuery("FROM Teachers");
        List<Teachers> listTeachers = q.list();
        Teachers[] arrayTeachers = new Teachers[listTeachers.size()];
        listTeachers.toArray(arrayTeachers);
        sesion.close();

        return arrayTeachers;
    }

    public static Teachers getMostVeteranTeacher() {
        Teachers teach = null;
        Session sesion = sesionf.openSession();

        Query q = sesion.createQuery("FROM Teachers t "
                + "WHERE t.startDate=(SELECT min(tc.startDate) FROM Teachers tc)");
        teach = (Teachers) q.uniqueResult();
        sesion.close();

        return teach;
    }

    public static int setSalary(int newSalary) {
        Session sesion = sesionf.openSession();
        int affectedRows;
        Transaction tx = sesion.beginTransaction();

        Query q = sesion.createQuery("UPDATE Teachers t SET t.salary = :newSalary");
        q.setInteger("newSalary", newSalary);
        affectedRows = q.executeUpdate();
        tx.commit();
        sesion.close();

        return affectedRows;
    }

    public static int riseSalaryOfSeniers(int numOfYearsToBeSenior, int prctRise) {
        int affectedRows = 0;
        Session sesion = sesionf.openSession();
        Transaction tx = sesion.beginTransaction();

        Query q = sesion.createQuery("UPDATE Teachers t SET t.salary = t.salary + ((:prct / 100) * t.salary) "
                + "WHERE year(current_date()) - year(t.startDate) > :yearsSenior)");
        q.setInteger("yearsSenior", numOfYearsToBeSenior);
        q.setInteger("prct", prctRise);
        affectedRows = q.executeUpdate();
        tx.commit();
        sesion.close();

        return affectedRows;
    }

    public static int deleteTeachersOfDepartment(String depName) {
        int affectedRows = 0;

        return affectedRows;
    }

    public static void closeSF() {
        sesionf.close();
    }

    @Override
    public String toString() {
        return "";
    }
}
