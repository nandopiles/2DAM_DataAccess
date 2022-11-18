package com.mycompany.act3_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 *
 * @author Nando
 */
public class Act_3_3 {

    static Scanner eb = new Scanner(System.in);

    /**
     * Recursive method used for deleting the folder of the DB (I tried to do it
     * with a TRUNCATE but --> when I couldn't do a truncate on "departments"
     * becouse I had a FOREIGN KEY on "teachers")
     *
     * @param carpeta Where the DB is saved
     */
    public static void deleteDB(File carpeta) {
        File[] files = carpeta.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDB(f);
                } else {
                    f.delete();
                }
            }
        }
        carpeta.delete();
    }

    /**
     * Method used for creating a Derby DB on the path selected
     *
     * @param path
     * @return the connection to the DB
     */
    public static Connection createDB(String path) {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:derby:" + path + ";create=true");
            System.out.println("(+) DB CREATED\n======================================");
        } catch (SQLException e1) {
            System.out.println("(-) Error when CREATING DB");
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
     * Method used for creating the table "departments"
     *
     * @param con
     */
    public static void createTableDepartments(Connection con) {
        String sql;

        try {
            Statement stt = con.createStatement();
            sql = "CREATE TABLE departments(dept_num int not null primary key,"
                    + "name varchar(50),"
                    + "office varchar(20))";
            stt.executeUpdate(sql);
            System.out.println("(+) Table \"departments\" Created\n======================================");
        } catch (SQLException e) {
            System.out.println("(-) Error when CREATING Table");
        }
    }

    /**
     * Method used for filling the information in the table "departments"
     *
     * @param con
     */
    public static void fillTableDepartments(Connection con) {
        File f;
        BufferedReader br = null;
        String text;
        String[] arrayTxt;
        int generalaffectedRows = 0;

        f = new File(".\\files\\departments.txt");
        if (f.exists()) {
            System.out.println("Inserting values...");
            try {
                br = new BufferedReader(new FileReader(f));
                text = br.readLine();
                while (text != null) {
                    //System.out.println(text);
                    arrayTxt = text.split(",");
                    String sql = "INSERT INTO departments VALUES (?,?,?)";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setInt(1, Integer.parseInt(arrayTxt[0]));
                    statement.setString(2, arrayTxt[1]);
                    statement.setString(3, arrayTxt[2]);
                    int affectedRows = statement.executeUpdate();
                    generalaffectedRows += affectedRows;
                    text = br.readLine();
                }
                System.out.println("(+)Affected Rows: " + generalaffectedRows);
            } catch (FileNotFoundException e1) {
                System.out.println("(-) File Not Found");
            } catch (IOException e2) {
                System.out.println("(-) Something related to IO went wrong");
            } catch (SQLException e3) {
                System.out.println("(-) SQL Error");
            }
            System.out.println("======================================");
        }
    }

    /**
     * Method used for doing a Query. The method will continue running until u
     * do a Select Query
     *
     * @param con
     */
    public static void customQuery(Connection con) {
        String sql;
        boolean continu = true;

        do {
            try {
                System.out.print("Introduce ur Query:\n>");
                sql = eb.nextLine().toLowerCase();
                if (sql.contains("select")) {
                    Statement stt = con.createStatement();
                    ResultSet re = stt.executeQuery(sql);
                    JDBCHelper.showResultSet(re);
                    continu = false;
                } else {
                    System.out.println("(-) U can only do Selects");
                }
            } catch (SQLException e1) {
                System.out.println("(-) Something related to the Select went wrong");
            }
        } while (continu);
    }

    /**
     * Method used for creating the table "teachers"
     *
     * @param con
     */
    public static void createTableTeachers(Connection con) {
        String sql;

        try {
            Statement stt = con.createStatement();
            sql = "CREATE TABLE teachers(id int not null primary key,"
                    + "name varchar(15),"
                    + "surname varchar(40),"
                    + "email varchar(50),"
                    + "start_date date,"
                    + "dept_num int,"
                    + "FOREIGN KEY(dept_num) REFERENCES departments(dept_num))";
            stt.executeUpdate(sql);
            System.out.println("(+) Table \"teachers\" Created\n======================================");
        } catch (SQLException e) {
            System.out.println("(-) Error when CREATING Table");
        }
    }

    /**
     * Method used for converting String to sql.Date
     *
     * @param strDate String
     * @return sql.Date
     */
    public static java.sql.Date convertDate(String strDate) {
        java.sql.Date sqlDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

        try {
            java.util.Date date = sdf.parse(strDate);
            sqlDate = new Date(date.getTime());
        } catch (ParseException e) {
            System.out.println("(-) Parse Error");
        }
        return sqlDate;
    }

    /**
     * Method used for filling the information in the table "teachers"
     *
     * @param con
     */
    public static void fillTableTeachers(Connection con) {
        File f;
        BufferedReader br = null;
        String text;
        String[] arrayTxt;
        int generalaffectedRows = 0;
        java.sql.Date sqlDate = null;

        f = new File(".\\files\\teachers.txt");
        if (f.exists()) {
            System.out.println("Inserting values...");
            try {
                br = new BufferedReader(new FileReader(f));
                text = br.readLine();
                while (text != null) {
                    //System.out.println(text);
                    arrayTxt = text.split(",");
                    String sql = "INSERT INTO teachers VALUES (?,?,?,?,?,?)";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setInt(1, Integer.parseInt(arrayTxt[0])); //id
                    statement.setString(2, arrayTxt[1]); //name
                    statement.setString(3, arrayTxt[2]); //surname
                    statement.setString(4, arrayTxt[3]); //email
                    if (arrayTxt[4].equals("")) { //date
                        statement.setNull(5, Types.DATE);
                    } else {
                        sqlDate = convertDate(arrayTxt[4]);
                        statement.setDate(5, sqlDate);
                    }
                    statement.setInt(6, Integer.parseInt(arrayTxt[5])); //dept_num
                    int affectedRows = statement.executeUpdate();
                    generalaffectedRows += affectedRows;
                    text = br.readLine();
                }
                System.out.println("(+)Affected Rows: " + generalaffectedRows);
            } catch (FileNotFoundException e1) {
                System.out.println("(-) File Not Found");
            } catch (IOException e2) {
                System.out.println("(-) Something related to IO went wrong");
            } catch (SQLException e3) {
                System.out.println("(-) SQL Error");
            }
            System.out.println("======================================");
        }
    }

    /**
     * Method used for waiting 500 miliseconds
     */
    public static void waitS() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("(-) Error while waiting");
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        String path = ".\\database\\act3_3.db";
        File f = new File(path);

        deleteDB(f);
        System.out.println("(+) DB DELETED");
        con = createDB(path);
        waitS();
        //table departments
        createTableDepartments(con);
        waitS();
        fillTableDepartments(con);
        //print the table departments
        customQuery(con);
        waitS();
        //table teachers
        createTableTeachers(con);
        waitS();
        fillTableTeachers(con);
        waitS();
        //print the table teachers
        customQuery(con);
        closeDb(con);
    }
}
