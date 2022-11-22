package code;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
                        System.out.println("(-) Option Out of Range");
                        repeat = true;
                    }
                } while (option < 1 || option > 11);
            } catch (InputMismatchException e) {
                System.out.println("(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);

        return option;
    }

    public static void showDepartment() {
        int id;
        boolean repeat = true;

        do {
            try {
                System.out.print("Id Department -> ");
                id = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("");
                fr.nextLine();
            }
        } while (repeat);
        
    }

    public static void main(String[] args) {
        int option;

        option = menu();
        switch (option) {
            case 1:
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
    }
}
