package com.mycompany.act3_4;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Nando
 */
public class Act3_4 {

    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

    /**
     * Method used for connecting with the DB
     *
     * @param path where is the DB
     * @return The Connection of the DB
     */
    public static Connection connectDB(String path) {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:hsqldb:" + path);
        } catch (SQLException e1) {
            System.out.println("(-) Error CONNECTING with the DB");
        }
        return con;
    }

    /**
     * Method used for closing the DB
     *
     * @param con The Connection of the DB
     */
    public static void closeDb(Connection con) {
        try {
            con.close();
        } catch (SQLException e1) {
            System.out.println("(-) Error when CLOSING DB");
        }
    }

    /**
     * A simple menu
     *
     * @return the option choosen
     */
    public static int menu() {
        int option = 0;
        boolean repeat = true;

        System.out.println("_________________\n"
                + "Choose an option:\n"
                + "1. Set new department\n"
                + "2. Set new teacher\n"
                + "3. Set salary to...\n"
                + "4. Rise salary a %\n"
                + "5. Risa salary a % to a department like...\n"
                + "6. Get newest teacher surname\n"
                + "7. Count teachers of department...\n"
                + "8. Quit");
        do {
            try {
                do {
                    System.out.print("Option -> ");
                    option = fr.nextInt();
                    repeat = false;
                    if (option < 1 || option > 8) {
                        System.out.println("(-) Option Out of Range");
                        repeat = true;//F.E -> if the 1ast time the user inputs 9, var "repeat" will be changed to
                    }                 //false and if the 2nd time I write "a", the exception will be thrown but its not ok 
                } while (option < 1 || option > 8);
            } catch (InputMismatchException e) {
                System.out.println("(-) Input must be a number");
                fr.nextLine();
            }
        } while (repeat);

        return option;
    }

    /**
     * Method used for doing a Procedure that will insert a new Department
     *
     * @param con
     */
    public static void setNewDepartmentProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE set_new_department IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE set_new_department(dept_num INT, name VARCHAR(15), office VARCHAR(20)) "
                    + "modifies sql data "
                    + "BEGIN ATOMIC "
                    + "INSERT INTO departments VALUES(dept_num, name, office);"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: set_new_department");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> set_new_department");
        }
    }

    /**
     * Method used for doing a Procedure that will insert a new Teacher
     *
     * @param con
     */
    public static void setNewTeacherProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE set_new_teacher IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE set_new_teacher(id INT, name VARCHAR(15), surname VARCHAR(40), email VARCHAR(50), start_date DATE, dept_num INT, salary INT) "
                    + "modifies sql data "
                    + "BEGIN ATOMIC "
                    + "INSERT INTO teachers VALUES(id, name, surname, email, start_date, dept_num, salary);"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: set_new_teacher");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> set_new_teacher");
        }
    }

    /**
     * Method used for doing a Procedure that will change the salary of all
     * Teachers
     *
     * @param con
     */
    public static void setSalaryProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE set_salary IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE set_salary(k INT) "
                    + "modifies sql data "
                    + "BEGIN ATOMIC "
                    + "UPDATE teachers "
                    + "SET salary = k;"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: set_salary");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> set_salary");
        }
    }

    /**
     * Method used for doing a Procedure that will rise a % the salary
     *
     * @param con
     */
    public static void riseSalaryProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE set_salary_prct IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE set_salary_prct(p FLOAT) "
                    + "modifies sql data "
                    + "BEGIN ATOMIC "
                    + "UPDATE teachers "
                    + "SET salary = round(salary * (p / 100) + salary, 2);"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: set_salary_prct");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> set_salary_prct");
        }
    }

    /**
     * Method used for doing a Procedure that will rise a % the salary of the
     * Teachers of the Department given
     *
     * (Goes wrong)
     * 
     * @param con
     */
    public static void riseSalaryDeptProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE set_salary_per_dept IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE set_salary_per_dept(p FLOAT, dept_name VARCHAR(15)) "
                    + "modifies sql data "
                    + "BEGIN ATOMIC "
                    + "UPDATE teachers "
                    + "SET salary = round(salary * (p / 100) + salary, 2) "
                    + "WHERE dept_num = "
                    + "(SELECT d.dept_num "
                    + "FROM departments d "
                    + "WHERE dept_num = d.dept_num "
                    + "AND d.name = dept_name);"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: set_salary_per_dept");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> set_salary_per_dept");
        }
    }

    /**
     * Method used for doing a Procedure that will return the surname of the
     * newest teacher
     *
     * @param con
     */
    public static void newestTeacherProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE get_newest_teacher IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE get_newest_teacher(OUT newest_surname VARCHAR(40)) "
                    + "reads sql data "
                    + "BEGIN ATOMIC "
                    + "SET newest_surname = SELECT TOP 1 surname from teachers "
                    + "WHERE start_date IS NOT NULL "
                    + "ORDER BY start_date DESC;"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: get_newest_teacher");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> get_newest_teacher");
        }
    }

    /**
     * Method used for doing a Procedure that will return the number of teachers
     * in that department
     *
     * @param con
     */
    public static void countTeachersProcedure(Connection con) {
        try {
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE count_teachers IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE count_teachers(IN dept_name VARCHAR(15), OUT number_teachers INT) "
                    + "reads sql data "
                    + "BEGIN ATOMIC "
                    + "SET number_teachers = "
                    + "SELECT count(t.id) "
                    + "FROM teachers t "
                    + "JOIN departments d "
                    + "ON t.dept_num = d.dept_num "
                    + "AND d.name = dept_name;"
                    + "END;";
            stt.executeUpdate(sql);
            System.out.println("\t...Created procedure: count_teachers");
        } catch (SQLException e) {
            System.out.println("(-) Error creating Procedure -> count_teachers");
        }
    }

    /**
     * Method used for creating all the Procedures
     *
     * @param con
     */
    public static void createProcedures(Connection con) {
        setNewDepartmentProcedure(con);
        setNewTeacherProcedure(con);
        setSalaryProcedure(con);
        riseSalaryProcedure(con);
        riseSalaryDeptProcedure(con);
        newestTeacherProcedure(con);
        countTeachersProcedure(con);
    }

    /**
     * Method used for doing a Select of the Table that has been passed
     *
     * @param con The Connection of the DB
     * @param table The table that has to be printed
     */
    public static void showTable(Connection con, String table) {
        try {
            Statement stt = con.createStatement();
            ResultSet re = stt.executeQuery("select * from " + table);
            JDBCHelper.showResultSet(re);
        } catch (SQLException e1) {
            System.out.println("(-) Error doing a SELECT");
        }
    }

    /**
     * Method used for writing the data of a new Department, then it will be
     * added
     *
     * @param con
     */
    public static void inputNewDepartment(Connection con) {
        int deptNum = 0;
        String name = null, office = null;
        boolean repeat = true;

        do {
            try {
                System.out.print("Dept num -> ");
                deptNum = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("(-) Dept num must be a number");
                fr.nextLine();
            }
        } while (repeat);

        System.out.print("Name -> ");
        name = eb.nextLine();
        System.out.print("Office -> ");
        office = eb.nextLine();

        try {
            CallableStatement call = con.prepareCall("call set_new_department(?, ?, ?)");
            call.setInt(1, deptNum);
            call.setString(2, name);
            call.setString(3, office);
            call.execute();
            System.out.printf("(+) Dept \"%s\" has been added\n", name);
            showTable(con, "departments");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> set_new_department");
        }
    }

    /**
     * Method used for converting String to sql.Date
     *
     * @return sql.Date
     */
    public static java.sql.Date convertDate() {
        String strDate = "";
        java.sql.Date sqlDate = null;
        boolean repeat = true;

        do {
            System.out.print("Start date(yyyy-mm-dd) -> ");
            strDate = eb.nextLine();
            try {
                sqlDate = java.sql.Date.valueOf(strDate);
                repeat = false;
            } catch (IllegalArgumentException e) {
                System.out.println("(-) Incorrect Date");
            }
        } while (repeat);

        return sqlDate;
    }

    /**
     * Method used for writing the data of a new Teacher, then it will be added
     *
     * @param con
     */
    public static void inputNewTeacher(Connection con) {
        int id = 0, deptNum = 0, salary = 0;
        String name = "", surname = "", email;
        boolean repeat = true;
        java.sql.Date date = null;

        do {
            try {
                System.out.print("Id -> ");
                id = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("(-) Id must be a number");
                fr.nextLine();
            }
        } while (repeat);
        repeat = true;
        System.out.print("Name -> ");
        name = eb.nextLine();
        System.out.print("Surname -> ");
        surname = eb.nextLine();
        do {
            System.out.print("Email -> ");
            email = eb.nextLine();
        } while (Email.checkEmail(email));
        date = convertDate();
        do {
            try {
                System.out.print("Dept num -> ");
                deptNum = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("(-) Dept num must be a number");
                fr.nextLine();
            }
        } while (repeat);
        repeat = true;
        do {
            try {
                System.out.print("Salary -> ");
                salary = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("(-) Salary must be a number");
                fr.nextLine();
            }
        } while (repeat);

        try {
            CallableStatement call = con.prepareCall("call set_new_teacher(?, ?, ?, ?, ?, ?, ?)");
            call.setInt(1, id);
            call.setString(2, name);
            call.setString(3, surname);
            call.setString(4, email);
            call.setDate(5, date);
            call.setInt(6, deptNum);
            call.setInt(7, salary);
            call.execute();
            System.out.printf("(+) Teacher \"%s\" has been added\n", name);
            showTable(con, "teachers");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> set_new_teacher");
        }
    }

    /**
     * Method used for change the Salary of all teachers
     *
     * @param con
     */
    public static void setSalary(Connection con) {
        int salary = 0;
        boolean repeat = true;

        do {
            System.out.print("Introduce the new Salary -> ");
            try {
                salary = fr.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("(-) Salary must be a number");
                fr.nextLine();
            }
        } while (repeat);
        try {
            CallableStatement call = con.prepareCall("call set_salary(?)");
            call.setInt(1, salary);
            call.execute();
            System.out.printf("(+) Salary has been changed to \"%d\"€\n", salary);
            showTable(con, "teachers");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> set_salary");
        }
    }

    /**
     * Method used for entering a percentage
     *
     * @return
     */
    public static float enterPrct() {
        float prct = 0;
        boolean repeat = true;

        do {
            System.out.print("Rise Salary a % -> ");
            try {
                prct = fr.nextFloat();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("(-) % must be a number");
                fr.nextLine();
            }
        } while (repeat);

        return prct;
    }

    /**
     * Method used for rising the salary of all teachers
     *
     * @param con
     */
    public static void riseSalaryPrct(Connection con) {
        float prct;

        prct = enterPrct();
        try {
            CallableStatement call = con.prepareCall("call set_salary_prct(?)");
            call.setFloat(1, prct);
            call.execute();
            System.out.printf("(+) Salary has been rise \"%.2f%s\"\n", prct, "%");
            showTable(con, "teachers");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> set_salary_prct");
        }
    }

    /**
     * Method used for rising the teacher's salary of a specific department
     *
     * @param con
     */
    public static void riseSalaryDept(Connection con) {
        String deptName = "";
        float prct;

        prct = enterPrct();
        System.out.print("Dept Name -> ");
        deptName = eb.nextLine();
        try {
            CallableStatement call = con.prepareCall("call set_salary_per_dept(?, ?)");
            call.setFloat(1, prct);
            call.setString(2, deptName.toUpperCase());
            call.execute();
            System.out.printf("(+) The salary of the \"%s's department\" has been rised \"%.2f%s\"\n", deptName, prct, "%");
            showTable(con, "teachers");
            showTable(con, "departments");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> set_salary_per_dept");
        }
    }

    /**
     * Method used for knowing the newest teacher who has be hired
     *
     * @param con
     */
    public static void newestTeacher(Connection con) {
        try {
            CallableStatement call = con.prepareCall("call get_newest_teacher(?)");
            call.registerOutParameter(1, Types.VARCHAR);
            call.execute();
            System.out.printf("(+) The newest teacher's surname is \"%s\"\n", call.getString(1).toUpperCase());
            showTable(con, "teachers");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> get_newest_teacher");
        }
    }

    /**
     * Method used for counting the quantity of teachers of a specific
     * department
     *
     * @param con
     */
    public static void countTeachers(Connection con) {
        String department = "";

        System.out.print("Department -> ");
        department = eb.nextLine();
        try {
            CallableStatement call = con.prepareCall("call count_teachers(?, ?)");
            call.setString(1, department);
            call.registerOutParameter(2, Types.INTEGER);
            call.execute();
            System.out.printf("(+) In the \"%s's\" department there're %d teachers\n", department, call.getInt(2));
            showTable(con, "teachers");
            showTable(con, "departments");
        } catch (SQLException e) {
            System.out.println("(-) Error trying to call -> count_teachers");
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        String path = ".\\database\\db";
        int option;

        con = connectDB(path);
        createProcedures(con);
        do {
            option = menu();
            switch (option) {
                case 1:
                    inputNewDepartment(con);
                    break;
                case 2:
                    inputNewTeacher(con);
                    break;
                case 3:
                    setSalary(con);
                    break;
                case 4:
                    riseSalaryPrct(con);
                    break;
                case 5:
                    riseSalaryDept(con);
                    break;
                case 6:
                    newestTeacher(con);
                    break;
                case 7:
                    countTeachers(con);
                    break;
                case 8:
                    System.out.println("(+) SEE U!");
                    break;
            }
        } while (option != 8);
        closeDb(con);
    }
}