package com.mycompany.activity3_1;

import java.sql.*;
import java.util.Scanner;

/**
 * @author 7J
 */
public class Activity3_1 {

    static Scanner eb = new Scanner(System.in);
    static Scanner fr = new Scanner(System.in);

    public static int menu() {
        int opc;

        System.out.println("Choose the DataBase:");
        System.out.println("1. SQLite Database\n"
                + "2. Derby Database\n"
                + "3. HSQLDB Database\n"
                + "4. Exit");
        do {
            System.out.print("Opc --> ");
            opc = fr.nextInt();
            if (opc < 1 || opc > 4) {
                System.out.println("(-) Error...");
            }
        } while (opc < 1 || opc > 4);
        return opc;
    }

    public static void connectDb(int opc) {
        Connection con = null;
        String name = "", route = "";
        ResultSet resul = null, columnas = null;

        try {
            if (opc == 1) {
                name = "SQLite";
                route = "jdbc:sqlite:src\\main\\db_pr\\sqlite\\act2.1.db";
            } else if (opc == 2) {
                name = "Derby";
                route = "jdbc:derby:src\\main\\db_pr\\derby\\act2.2";
            } else {
                name = "HSQLDB";
                route = "jdbc:hsqldb:src\\main\\db_pr\\hsqldb\\ej2.3";
            }
            System.out.println("----------------------------------\n"
                    + "DATABASE INFORMATION (" + name + ")\n"
                    + "----------------------------------");
            con = DriverManager.getConnection(route); //realative route

            DatabaseMetaData md = con.getMetaData();
            System.out.println("DB Product Name: " + md.getDatabaseProductName());
            System.out.println("Driver Name: " + md.getDriverName());
            System.out.println("Driver Version: " + md.getDriverVersion());
            System.out.println("URL: " + md.getURL());
            System.out.println("User Name: " + md.getUserName());

            System.out.println("----------------------------------\n"
                    + "TABLES INFORMATION (" + name + ")\n"
                    + "----------------------------------");

            if (opc == 1) {
                resul = md.getTables(null, null, null, null); //sqlite
            } else if (opc == 2) {
                resul = md.getTables(null, "APP", null, null); //derby
            } else {
                resul = md.getTables(null, "PUBLIC", null, null); //hsqldb
            }
            while (resul.next()) {
                String tabla = resul.getString("TABLE_NAME");
                String catalogo = resul.getString("TABLE_CAT");
                String esquema = resul.getString("TABLE_SCHEM");
                String tipo = resul.getString("TABLE_TYPE");
                System.out.println("TABLENAME: " + tabla + "; Catalog: " + catalogo + "; Schema: "
                        + esquema + "; Type: " + tipo);

                System.out.println("*** COLUMNS of TABLE " + tabla + " ***");
                columnas = md.getColumns(null, null, tabla, null);
                while (columnas.next()) {
                    String nombreCol = columnas.getString("COLUMN_NAME");
                    String tipoCol = columnas.getString("TYPE_NAME");
                    String tamCol = columnas.getString("COLUMN_SIZE");
                    String nula = columnas.getString("IS_NULLABLE");

                    System.out.println("\tColumna: " + nombreCol + "; tipo: "
                            + tipoCol + "; tamaño: " + tamCol + "; Admite null: " + nula);
                }
                System.out.println("----------------------------------");
            }
            con.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int opc;

        do {
            opc = menu();
            switch (opc) {
                case 1:
                    connectDb(1);
                    break;
                case 2:
                    connectDb(2);
                    break;
                case 3:
                    connectDb(3);
                    break;
                case 4:
                    System.out.println("(+) SEE U!");
                    break;
            }
        } while (opc != 4);
    }
}
