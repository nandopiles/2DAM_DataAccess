package app;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojos.Departments;
import pojos.Teachers;

/**
 *
 * @author Nando
 */
public class Main {

    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

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

    public static void showAllDepartments() {
        Departments[] listDep = null;

        listDep = QueryDept.getAllDepartments();
        for (Departments dep : listDep) {
            QueryDept.showDepartment(dep);
        }
    }

    public static void searchDeptByName() {
        Departments dep = null;
        String nameDept;

        System.out.print("DeptName: ");
        nameDept = eb.nextLine();
        dep = QueryDept.getDepartmentByName(nameDept);
        if (dep != null) {
            QueryDept.showDepartment(dep);
        } else {
            System.out.printf("(-) Dept \"%s\" not found...\n", nameDept);
        }
    }

    public static void main(String[] args) {
        int option = 0;

        do {
            option = menu();
            switch (option) {
                case 1:
                    showAllDepartments();
                    break;
                case 2:
                    searchDeptByName();
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
            }
        } while (option != 10);
        System.out.println("(+) SEE U!");
        QueryDept.closeSF();
    }
}
