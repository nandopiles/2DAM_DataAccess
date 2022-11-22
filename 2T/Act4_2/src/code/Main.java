package code;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Departments;
import exceptions.DepartmentNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nando
 */
public class Main {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
    static Session sesion = sesionf.openSession();
    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

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
                    if (option < 1 || option > 8) {
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
            System.out.println("\tINFO DEPARTMENT(" + id + ")\n=============================");
            System.out.printf("Name: %s\n", dep.getName());
            System.out.printf("Office: %s\n", dep.getOffice());
            System.out.printf("Teachers: %d\n=============================\n", dep.getTeacherses().size());
        }
    }

    public static void main(String[] args) {
        int option, idDept;

        do {
            option = menu();
            switch (option) {
                case 1:
                    idDept = inputId();
                    try {
                        showDepartment(idDept);
                    } catch (DepartmentNotFoundException ex) {
                        System.out.println("\t(-) Department Not Found...\n");
                    }
                    break;
                case 2:
                    
                    break;
                case 3:
                    break;
                case 4:
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
                    System.out.println("(+) SEE U!");
                    break;
            }
        } while (option != 11);
    }
}
