package app;

import util.NewHibernateUtil;
import util.Email;
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
import java.util.Calendar;
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

    public static void separator(int length) {
        for (int i = 0; i < (length * 2); i++) {
            System.out.print("=");
        }
        System.out.println("");
    }

    public static void showDepartment(int id) throws DepartmentNotFoundException {
        Departments dep = null;
        String head;

        dep = (Departments) sesion.get(Departments.class, id);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            head = "\tINFO DEPARTMENT(" + id + ")";
            System.out.println(head);
            separator(head.length());
            System.out.printf("Name: %s\n", dep.getName());
            System.out.printf("Office: %s\n", dep.getOffice());
            System.out.printf("Teachers: %d\n", dep.getTeacherses().size());
            separator(head.length());
        }
    }

    public static void infoTeacher(Teachers teacher, String head) {
        System.out.println(head);
        separator(head.length());
        System.out.printf("Name: %s %s\n", teacher.getName(), teacher.getSurname());
        System.out.printf("Department: %s\n", teacher.getDepartments().getName());
        System.out.printf("Email: %s\n", teacher.getEmail());
        System.out.printf("Salary: %d\n", teacher.getSalary());
        System.out.printf("Start Date: %s\n", String.valueOf(teacher.getStartDate()));
        separator(head.length());
    }

    public static void showTeacher(int id) throws TeacherNotFoundException {
        Teachers teacher = null;
        String head;

        teacher = (Teachers) sesion.get(Teachers.class, id);
        if (teacher == null) {
            throw new TeacherNotFoundException();
        } else {
            head = "\tINFO TEACHER(" + id + ")";
            infoTeacher(teacher, head);
        }
    }

    public static void showTeachersDept(int id) throws DepartmentNotFoundException {
        Departments dep = null;
        String head;

        dep = (Departments) sesion.get(Departments.class, id);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            Set<Teachers> listT = dep.getTeacherses();
            head = "\tTEACHERS OF DEPT(" + id + ")";
            System.out.println(head);
            separator(head.length());
            listT.forEach(teacher -> {
                infoTeacher(teacher, head);
            });
        }
    }

    public static Departments createNewDepartment() {
        int deptNum = 0;
        String name, office, head;
        boolean repeat = true;
        Set<Teachers> teachers = new HashSet();
        Departments dep = null;

        head = "\tCREATE DEPARTMENT";
        System.out.println(head);
        separator(head.length());
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
        System.out.print("Office: ");
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

        return dep;
    }

    public static Teachers createNewTeacherWithoutDept() {
        int id = 0, salary = 0;
        String name, surname, email, stringDate = "", head;
        Date date = null;
        Teachers teacher = null;
        boolean repeat = true;
        DateFormat dateFormat = null;

        head = "\tCREATE TEACHER";
        System.out.println(head);
        separator(head.length());
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
        teacher = new Teachers(id, name, surname, email, date, salary);

        return teacher;
    }

    public static void createNewTeacherWithDept() {
        Departments dep = null;
        Teachers teacher = null;

        dep = createNewDepartment();
        teacher = createNewTeacherWithoutDept();
        teacher.setDepartments(dep);
        sesion.save(teacher);
        tx = sesion.beginTransaction();
        tx.commit();

        try {
            showTeacher(teacher.getId());
        } catch (TeacherNotFoundException ex) {
            System.out.println("\t(-) Teacher Not Found...");
        }
    }

    public static void createNewTeacherInExistingDept() {
        Departments dep = null;
        Teachers teacher = null;
        int deptNum = 0;
        boolean repeat = true, deptFound = false;

        teacher = createNewTeacherWithoutDept();
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

        try {
            dep = (Departments) sesion.get(Departments.class, deptNum);
            deptFound = true;
        } catch (Exception e) {
            System.out.println("\t(-) Department Not Found...");
        }
        if (deptFound) {
            teacher.setDepartments(dep);
            sesion.save(teacher);
            tx = sesion.beginTransaction();
            tx.commit();
            try {
                showTeacher(teacher.getId());
            } catch (TeacherNotFoundException ex) {
                System.out.println("\t(-) Teacher Not Found...");
            }
        }
    }

    public static void deleteTeacher(int id) throws TeacherNotFoundException {
        String head;
        boolean repeat = true;
        Teachers teacher = null;

        teacher = (Teachers) sesion.get(Teachers.class, id);
        if (teacher == null) {
            throw new TeacherNotFoundException();
        } else {
            head = "\tINFO TEACHER(" + id + ")";
            infoTeacher(teacher, head);
            sesion.delete(teacher);
            tx = sesion.beginTransaction();
            tx.commit();
            System.out.printf("\t(+) Teacher (%d) has been deleted\n", teacher.getId());
        }
    }

    public static void deleteDepartment(int deptNum) throws DepartmentNotFoundException {
        String head, option;
        boolean repeat = true, deleteConfirmed = false;
        Departments dep = null;
        Set<Teachers> listT = null;

        dep = (Departments) sesion.get(Departments.class, deptNum);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            head = "\tINFO TEACHER(" + deptNum + ")";
            showDepartment(deptNum);
            if (!dep.getTeacherses().isEmpty()) {
                System.out.printf("\t(-) This Dept has %d Teachers, u have to delete them to delete this Dept\n", dep.getTeacherses().size());
                System.out.printf("(*) Do u want to delete all Teachers of %s's Dept?(S/N): ", dep.getName());
                do {
                    option = eb.nextLine().toUpperCase();
                    if (option.equals("S") || option.equals("N")) {
                        repeat = false;
                        if (option.equals("S")) {
                            deleteConfirmed = true;
                        } else {
                            System.out.println("\t(*) Action Aborted");
                        }
                    } else {
                        System.out.println("\t(-) Invalid option...");
                    }
                } while (repeat);
            }
            if (deleteConfirmed || dep.getTeacherses().isEmpty()) {
                listT = dep.getTeacherses();
                for (Teachers teacher : listT) {
                    sesion.delete(teacher);
                    tx = sesion.beginTransaction();
                    tx.commit();
                    System.out.printf("(+) Teacher \"%s %s\" deleted\n", teacher.getName(), teacher.getSurname());
                }
                sesion.delete(dep);
                tx = sesion.beginTransaction();
                tx.commit();
                System.out.printf("\t\t(+) Department (%d) has been deleted\n", dep.getDeptNum());
            }
        }
    }

    public static void setSalaryOfDept(int deptNum) throws DepartmentNotFoundException {
        Departments dep = null;
        Set<Teachers> listT = null;
        boolean repeat = true;
        int salary = 0;

        dep = (Departments) sesion.get(Departments.class, deptNum);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            do {
                System.out.print("New Salary: ");
                try {
                    salary = fr.nextInt();
                    repeat = false;
                } catch (InputMismatchException ex) {
                    System.out.println("(-) Input must be a number");
                    fr.nextLine();
                }
            } while (repeat);
            listT = dep.getTeacherses();
            for (Teachers teacher : listT) {
                teacher.setSalary(salary);
                sesion.save(teacher);
                tx = sesion.beginTransaction();
                tx.commit();
                System.out.printf("\t(+) \"%s %s's\"(%d) salary changed to %d\n", teacher.getName(), teacher.getSurname(), teacher.getId(), teacher.getSalary());
            }
        }
    }

    public static void riseSalaryOfDeptSeniors(int deptNum) throws DepartmentNotFoundException {
        Departments dep = null;
        Calendar cal = Calendar.getInstance();
        String stringDate = "";
        int yearSenior = 0, currrentYear, oldSalary = 0, newSalary = 0;
        float percentage = 0;
        boolean repeat = true;
        Set<Teachers> listT = null;

        dep = (Departments) sesion.get(Departments.class, deptNum);
        if (dep == null) {
            throw new DepartmentNotFoundException();
        } else {
            do {
                System.out.print("Years to be Senior: ");
                try {
                    yearSenior = fr.nextInt();
                    repeat = false;
                } catch (InputMismatchException ex) {
                    System.out.println("(-) Input must be a number");
                    fr.nextLine();
                }
            } while (repeat);
            repeat = true;
            do {
                System.out.print("Percentage to rise: ");
                try {
                    percentage = fr.nextFloat();
                    repeat = false;
                } catch (InputMismatchException ex) {
                    System.out.println("(-) Input must be a number");
                    fr.nextLine();
                }
            } while (repeat);

            listT = dep.getTeacherses();
            currrentYear = cal.get(Calendar.YEAR);

            for (Teachers teacher : listT) {
                //I dont know how to use Calendar instead of util.Date, that's Deprecated
                if ((currrentYear - (teacher.getStartDate().getYear() + 1900)) >= yearSenior) { //u have to add 1900 to the year, is how it works💩
                    oldSalary = teacher.getSalary();
                    teacher.setSalary((Integer) Math.round(oldSalary + ((percentage / 100) * oldSalary)));
                    sesion.save(teacher);
                    tx = sesion.beginTransaction();
                    tx.commit();
                    System.out.printf("\t(+) \"%s %s's\" new salary -> %d (old: %d(+%2.2f%%))\n", teacher.getName(), teacher.getSurname(), teacher.getSalary(), oldSalary, percentage);
                }
            }
        }
    }

    public static void main(String[] args) {
        int option, idDept, idTeacher;

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
                    idTeacher = inputId();
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
                    createNewTeacherWithDept();
                    break;
                case 6:
                    createNewTeacherInExistingDept();
                    break;
                case 7: {
                    idTeacher = inputId();
                    try {
                        deleteTeacher(idTeacher);
                    } catch (TeacherNotFoundException ex) {
                        System.out.println("\t(-) Teacher Not Found...\n");
                    }
                }
                break;
                case 8:
                    idDept = inputId();
                    try {
                        deleteDepartment(idDept);
                    } catch (DepartmentNotFoundException e) {
                        System.out.println("\t(-) Department Not Found...\n");
                    }
                    break;
                case 9:
                    idDept = inputId();
                    try {
                        setSalaryOfDept(idDept);
                    } catch (DepartmentNotFoundException e) {
                        System.out.println("\t(-) Department Not Found...\n");
                    }
                    break;
                case 10:
                    idDept = inputId();
                    try {
                        riseSalaryOfDeptSeniors(idDept);
                    } catch (DepartmentNotFoundException e) {
                        System.out.println("\t(-) Department Not Found...\n");
                    }
                    break;
                case 11:
                    break;
            }
        } while (option != 11);
        closeSesions();
        System.out.println("(+) SEE U!");
    }
}
