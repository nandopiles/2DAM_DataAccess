package com.mycompany.act3_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 7J
 */
public class Act3_2 {

    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

    /**
     * Method used for connecting to the DB
     *
     * @param path Is the path where is the DB
     * @return The Connection with de DB
     */
    public static Connection connectDb(String path) {
        Connection con = null;

        try {
            con = DriverManager.getConnection(path);
        } catch (SQLException e1) {
            e1.printStackTrace();
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
            e1.printStackTrace();
        }
    }

    /**
     * Method used for choosing an option
     *
     * @return The option that the user chose
     */
    public static int menu() {
        int opc = 0;

        System.out.println("1. Show all teachers\n"
                + "2. Show all departments\n"
                + "3. Add new teacher\n"
                + "4. Add new department\n"
                + "5. Add salary column to teachers\n"
                + "6. Evaluate custom query\n"
                + "7. Quit");
        do {
            System.out.print("Opc -> ");
            try {
                opc = fr.nextInt();
                if (opc < 1 || opc > 7) {
                    System.out.println("(-) Error...");
                } 
            } catch (InputMismatchException e) {
                System.out.println("(-) Incorrect Format");
                fr.nextLine();
            }           
        } while (opc < 1 || opc > 7);

        return opc;
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
            e1.printStackTrace();
        }
    }

    /**
     * Method used for creating a new Column on the Table "teachers"
     *
     * @param con The connection to the DB
     * @param column Name of the column that has to be added
     */
    public static void createColumn(Connection con, String column) {
        if (JDBCHelper.containsColumn(con, "teachers", column) == false) {
            try {
                Statement stt = con.createStatement();
                String sql = String.format("ALTER TABLE teachers "
                        + "ADD %s INT;", column);
                int affectedRows = stt.executeUpdate(sql);
                System.out.println("(+)Affected Rows: " + affectedRows);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void customQuery(Connection con) {
        String sql;

        try {
            System.out.print("Introduce ur Query:\n>");
            sql = eb.nextLine().toLowerCase();
            if (sql.contains("select")) {
                Statement stt = con.createStatement();
                ResultSet re = stt.executeQuery(sql);
                JDBCHelper.showResultSet(re);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int opc;
        String path = "jdbc:sqlite:src\\main\\DB"; //Relative Path of the DB
        Connection con;

        con = connectDb(path);
        do {
            opc = menu();
            switch (opc) {
                case 1:
                    showTable(con, "teachers");
                    break;
                case 2:
                    showTable(con, "departments");
                    break;
                case 3:
                    JDBCHelper.addItemToTable(con, "teachers");
                    break;
                case 4:
                    JDBCHelper.addItemToTable(con, "departments");
                    break;
                case 5:
                    createColumn(con, "salary");
                    break;
                case 6:
                    customQuery(con);
                    break;
                case 7:
                    System.out.println("(+) SEE U!");
                    closeDb(con);
                    break;

            }
        } while (opc != 7);
    }
}