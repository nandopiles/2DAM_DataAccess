package code;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Departments;
import exceptions.DepartmentNotFoundException;
import exceptions.TeacherNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Transaction;
import pojos.Teachers;

/**
 *
 * @author Nando
 */
public class Main {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
    static Session sesion = sesionf.openSession();
    static Transaction tx = null;
    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

    public static void closeSesions() {
        sesion.close();
        sesionf.close();
    }

    public static int menu() {
        int option = 0;
        boolean repeat = true;

        System.out.println("1. Show a department by ID\n"
                + "2. Show a teacher by ID\n"
                + "3. Show the teachers in existing department\n"
                + "4. Create new department\n"
                + "5. Create new teacher with new department associated\n"
                + "6. Create new teacher with existing department associated\n"
                + "7. Delete a teacher\n"
                + "8. Delete a department\n"
                + "9. Set salary of whole department\n"
                + "10. Rise salary for seniors of department\n"
                + "11. Quit");
        do {
            try {
                do {
                    System.out.print("-> ");
                    option = fr.nextInt();
                    repeat = false;
                    if (option < 1 || option > 11) {
                        System.out.println("\t(-) Option Out of Range");
                        repeat = true;
                    }
                } while (option < 1 || option > 11);
            } catch (InputMismatchException e) {
                System.out.println("\t(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);

        return option;
    }

    public static int inputId() {
        int id = 0;
        boolean repeat = true;

        do {
            try {
                System.out.print("Id -> ");
                id = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("\t(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);

        return id;
    }

    public static void showDepartment(int id) throws DepartmentNotFoundException {
        Departments dep = null;
        dep = (Departments) sesion.get(Departments.class, id);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            System.out.println("\tINFO DEPARTMENT(" + id + ")\n===================================");
            System.out.printf("Name: %s\n", dep.getName());
            System.out.printf("Office: %s\n", dep.getOffice());
            System.out.printf("Teachers: %d\n===================================\n", dep.getTeacherses().size());
        }
    }

    public static void infoTeacher(Teachers teacher) {
        System.out.printf("Name: %s %s\n", teacher.getName(), teacher.getSurname());
        System.out.printf("Department: %s\n", teacher.getDepartments().getName());
        System.out.printf("Email: %s\n", teacher.getEmail());
        System.out.printf("Salary: %d\n", teacher.getSalary());
        System.out.printf("Start Date: %s\n===================================\n", String.valueOf(teacher.getStartDate()));
    }

    public static void showTeacher(int id) throws TeacherNotFoundException {
        Teachers teacher = null;
        teacher = (Teachers) sesion.get(Teachers.class, id);
        if (teacher == null) {
            throw new TeacherNotFoundException();
        } else {
            System.out.println("\tINFO TEACHER(" + id + ")\n===================================");
            infoTeacher(teacher);
        }
    }

    public static void showTeachersDept(int id) throws DepartmentNotFoundException {
        Departments dep = null;
        dep = (Departments) sesion.get(Departments.class, id);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            Set<Teachers> listT = dep.getTeacherses();
            System.out.println("\tTEACHERS OF DEPT(" + id + ")\n===================================");
            for (Teachers teacher : listT) {
                infoTeacher(teacher);
            }
        }
    }

    public static void createNewDepartment() {
        int deptNum = 0;
        String name, office;
        boolean repeat = true;
        Set<Teachers> teachers = new HashSet();
        Departments dep = null;

        System.out.println("\tCREATE DEPARTMENT\n===================================");
        do {
            System.out.print("DeptNum: ");
            try {
                deptNum = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException ex) {
                System.out.println("(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);
        System.out.print("Name: ");
        name = eb.nextLine();
        office = eb.nextLine();
        dep = new Departments(deptNum, name, office, teachers);
        sesion.save(dep);
        tx = sesion.beginTransaction();
        tx.commit();

        try {
            showDepartment(deptNum);
        } catch (DepartmentNotFoundException ex) {
            System.out.println("\t(-) Department Not Found...\n");
        }
    }

    public static void createNewTeacher() {
        int id, salary;
        String name, surname, email, stringDate = "";
        Date date;
        Departments dep = null;
        boolean repeat = true;
        DateFormat dateFormat = null;

        System.out.println("\tCREATE TEACHER\n===================================");

        do {
            System.out.print("Id: ");
            try {
                id = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException ex) {
                System.out.println("(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);
        System.out.print("Name: ");
        name = eb.nextLine();
        System.out.print("Surname: ");
        surname = eb.nextLine();
        do {
            System.out.print("Email: ");
            email = eb.nextLine();
        } while (Email.checkEmail(email));

        //fecha dudas, probar en clase
        repeat = true;
        dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        do {
            System.out.print("Start Date: ");
            stringDate = eb.nextLine();
            try {
                date = dateFormat.parse(stringDate);
                repeat = false;
            } catch (ParseException ex) {
                System.out.println("\t(-) Invalid Date...");
            }
        } while (repeat);

        repeat = true;
        do {
            System.out.print("Salary: ");
            try {
                salary = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException ex) {
                System.out.println("\t(-) Input must be a number...");
            }
        } while (repeat);
    }

    public static void main(String[] args) {
        int option;

        do {
            option = menu();
            switch (option) {
                case 1:
                    int idDept = inputId();
                    try {
                        showDepartment(idDept);
                    } catch (DepartmentNotFoundException ex) {
                        System.out.println("\t(-) Department Not Found...\n");
                    }
                    break;
                case 2:
                    int idTeacher = inputId();
                    try {
                        showTeacher(idTeacher);
                    } catch (TeacherNotFoundException ex) {
                        System.out.println("\t(-) Teacher Not Found...\n");
                    }
                    break;
                case 3:
                    idTeacher = inputId();
                    try {
                        showTeachersDept(idTeacher);
                    } catch (DepartmentNotFoundException ex) {
                        System.out.println("\t(-) Department Not Found...\n");
                    }
                    break;
                case 4:
                    createNewDepartment();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
            }
        } while (option != 11);
        closeSesions();
        System.out.println("(+) SEE U!");
    }
}
