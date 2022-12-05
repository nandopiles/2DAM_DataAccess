package app;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import pojos.Departments;
import pojos.Teachers;
import utils.Separator;

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

    public static void closeSessionsFactorys() {
        QueryDept.closeSF();
        QueryTeach.closeSF();
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
        String deptName;

        System.out.print("DeptName: ");
        deptName = eb.nextLine();
        dep = QueryDept.getDepartmentByName(deptName);
        if (dep != null) {
            QueryDept.showDepartment(dep);
        } else {
            System.out.printf("\t(-) Dept \"%s\" not found...\n\n", deptName.toUpperCase());
        }
    }

    //podríamos utilizar el método searchDeptByName() para buscar el dept y luego pasarle al método de QueryDept un objeto Departments
    public static void getAverageSalaryByDeptName() {
        String deptName;
        double averageSalary = -0;

        System.out.print("DeptName: ");
        deptName = eb.nextLine();
        averageSalary = QueryDept.getAverageSalaryofDepartment(deptName);
        if (averageSalary != -0) {
            System.out.printf("(+) Average Salary of Dept \"%s\": %2.2f€\n\n", deptName.toUpperCase(), averageSalary);
        } else {
            System.out.printf("\t(-) Dept \"%s\" not found...\n\n", deptName.toUpperCase());
        }
    }

    public static void getAverageSalaryOfEachDept() {
        HashMap<String, Double> salaryHash = QueryDept.getAverageSalaryPerDept();
        String header;

        for (String nameDept : salaryHash.keySet()) {
            double salary = salaryHash.get(nameDept);
            header = "\tAverage Salary of " + nameDept + "\n";
            System.out.printf(header);
            Separator.separator(header.length());
            System.out.printf("Avg Salary: %2.2f€\n\n", salary);
        }
    }

    public static void showAllTeachers() {
        Teachers[] listTeach = QueryTeach.getAllTeachers();

        for (Teachers teacher : listTeach) {
            QueryTeach.showTeacher(teacher);
        }
    }

    public static void getMostVeteranTeacher() {
        Teachers veteranTeacher = QueryTeach.getMostVeteranTeacher();
        QueryTeach.showTeacher(veteranTeacher);
    }

    public static void setNewSalaryToEveryone() {
        int newSalary = 0, affectedRows;
        boolean repeat = true;

        do {
            System.out.print("New Salary: ");
            try {
                newSalary = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("\t(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);
        affectedRows = QueryTeach.setSalary(newSalary);
        System.out.printf("\t(+) %d Affected Rows\n\n", affectedRows);
    }

    public static void riseSeniorsSalary() {
        int affectedRows, prct = 0, yearsSenior = 0;
        boolean repeat = true;

        do {
            System.out.print("Years to be Senior: ");
            try {
                yearsSenior = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("\t(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);
        repeat = true;
        do {
            System.out.print("Prct to Rise: ");
            try {
                prct = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("\t(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);

        affectedRows = QueryTeach.riseSalaryOfSeniers(yearsSenior, prct);
        System.out.printf("\t(+) %d Affected Rows\n\n", affectedRows);
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
                    getAverageSalaryByDeptName();
                    break;
                case 4:
                    getAverageSalaryOfEachDept();
                    break;
                case 5:
                    showAllTeachers();
                    break;
                case 6:
                    getMostVeteranTeacher();
                    break;
                case 7:
                    setNewSalaryToEveryone();
                    break;
                case 8:
                    riseSeniorsSalary();
                    break;
                case 9:
                    break;
                case 10:
                    break;
            }
        } while (option != 10);
        System.out.println("(+) SEE U!");
        closeSessionsFactorys();
    }
}
