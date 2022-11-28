package app;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author 7J
 */
public class Main {

    static SessionFactory sesionf = NewHibernateUtil.getSessionFactory();
    static Session sesion = sesionf.openSession();
    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

    public static void closeSesions() {
        sesion.close();
        sesionf.close();
    }

    public static int menu() {
        int option = 0;
        boolean repeat = true;

        System.out.println("1. Show all departments\n"
                + "2. Show department whose name matches a pattern\n"
                + "3. Get average salary of a department (by name)\n"
                + "4. Show average salary of each department\n"
                + "5. Show all teachers\n"
                + "6. Show most veteran teacher\n"
                + "7. Set salary\n"
                + "8. Rise salary of senior teachers\n"
                + "9. Delete teachers of a department\n"
                + "10. Quit");
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

    public static void main(String[] args) {

    }
}
