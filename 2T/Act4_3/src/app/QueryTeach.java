package app;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        int salary = 0;

        return salary;
    }

    public static int riseSalaryOfSeniers(int numOfYearsToBeSenior, int prctRise) {
        int rise = 0;

        return rise;
    }

    public static int deleteTeachersOfDepartment(String depName) {
        int a = 0;

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
