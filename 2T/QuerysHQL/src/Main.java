
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Departments;
import pojos.Teachers;

/**
 *
 * @author 7J
 */
public class Main {

    public static void main(String[] args) {
        SessionFactory sessionf = NewHibernateUtil.getSessionFactory();
        Session session = sessionf.openSession();
        
        Query q = session.createQuery("select avg(salary) from Teachers");
        Double salarioMedio = (Double) q.uniqueResult();
        System.out.println("Salario Medio: " + salarioMedio);
        
//        Query q = session.createQuery("from Departments d, Teachers e"
//                + " where d.deptNum = e.departments.deptNum order by e.surname");
//        List<Object[]> lista = q.list();
//        System.out.println("Se han obtenido " + lista.size() + " resultados:");
//        
//        for (Object[] element : lista) {
//            Departments dep = (Departments) element[0];
//            Teachers teacher = (Teachers) element[1];
//            System.out.println("\t " + dep.getDeptNum() + " | " + dep.getName() + " | " + teacher.getName());
//        }
    }
}
