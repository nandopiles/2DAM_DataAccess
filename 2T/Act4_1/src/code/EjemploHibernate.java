package code;

import Act4_1.Departments;
import Act4_1.Teachers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 7J
 */
public class EjemploHibernate {

    public static Teachers createTeacher(Departments dep) {
        Teachers teacher = new Teachers(133);
        teacher.setName("Perez");
        teacher.setEmail("pepitogarcia@gmail.com");
        teacher.setSalary(3);
        teacher.setDepartments(dep);

        return teacher;
    }

    public static void createDept() {
        SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
        Session sesion = sesionf.openSession();
        Transaction tx = null;
        try {
            tx = sesion.beginTransaction();
            Departments dep = new Departments(123);
            dep.setName("AAA");
            dep.setOffice("OFI_NAS");

            Teachers teacher = createTeacher(dep);
            System.out.printf("Guardando Teacher... ->%d\n", sesion.save(teacher));
            System.out.printf("Guardando Departamento... ->%d\n", sesion.save(dep));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        sesion.close();
        sesionf.close();
    }

    public static void main(String[] args) {
        createDept();
    }
}
