package com.mycompany.ejercicio1examen1t;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Nando
 */
public class Ejercicio1Examen1T {

    static Scanner fr = new Scanner(System.in);
    static Scanner eb = new Scanner(System.in);

    /**
     * Menu used for choosing the SGBD
     *
     * @return the option choosed
     */
    public static int menu() {
        int opc;

        System.out.println("Elige SGBD:");
        System.out.println("1. SQLite Database\n"
                + "2. Derby Database\n"
                + "3. HSQLDB Database\n"
                + "4. Exit");
        do {
            System.out.print("Opc -> ");
            opc = fr.nextInt();
            if (opc < 1 || opc > 4) {
                System.out.println("(-) Error trying to choose the SGBD");
            }
        } while (opc < 1 || opc > 4);

        return opc;
    }

    /**
     * Method used for connecting to the DB choosed
     *
     * @param opc
     * @return the Connection
     */
    public static Connection connectDb(int opc) {
        Connection con = null;
        String name = "", route = "";

        try {
            if (opc == 1) {
                name = "SQLite";
                route = "jdbc:sqlite:.\\db_examen\\sqlite\\database";
            } else if (opc == 2) {
                name = "Derby";
                route = "jdbc:derby:.\\db_examen\\derby\\database";
            } else {
                name = "HSQLDB";
                route = "jdbc:hsqldb:.\\db_examen\\hsqldb\\database";
            }
            System.out.println("----------------------------------\n"
                    + "DATABASE INFORMATION (" + name + ")\n"
                    + "----------------------------------");
            con = DriverManager.getConnection(route); //realative route

            DatabaseMetaData md = con.getMetaData();
            System.out.println("Driver Name: " + md.getDriverName());
            System.out.println("Driver Version: " + md.getDriverVersion());
        } catch (SQLException e) {
            System.out.println("(-) Error trying to connect to the DB");
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
     * Menu used for choosing the ACTION relatet to the DB
     *
     * @return
     */
    public static int menu2() {
        int opc;

        System.out.println("\n\n¿Qué quieres hacer?");
        System.out.println("1. Mostrar alumnos\n"
                + "2. Mostrar municipios\n"
                + "3. Introducir nuevo Alumno\n"
                + "4. Introducir nuevo municipio");
        do {
            System.out.print("Opc -> ");
            opc = fr.nextInt();
            if (opc < 1 || opc > 4) {
                System.out.println("(-) Error trying to choose the ACTION");
            }
        } while (opc < 1 || opc > 4);

        return opc;
    }

    /**
     * Method used for showing the table "alumnos"
     *
     * @param con
     */
    public static void mostrar_alumnos(Connection con) {
        String table = "alumnos";

        JDBCHelper.showTable(con, table);
    }

    /**
     * Method used for showing the table "municipios"
     *
     * @param con
     */
    public static void mostrar_municipios(Connection con) {
        String table = "municipios";

        JDBCHelper.showTable(con, table);
    }

    /**
     * Method used for adding a student
     *
     * @param con
     */
    public static void introduce_alumno(Connection con) {
        int nia, id_municipio;
        String nombre, apellidos;

        System.out.print("nia -> ");
        nia = fr.nextInt();
        System.out.print("nombre -> ");
        nombre = eb.nextLine();
        System.out.print("Apellidos -> ");
        apellidos = eb.nextLine();
        System.out.print("Id Municipio -> ");
        id_municipio = fr.nextInt();

        String sql = "INSERT INTO alumnos VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, nia);
            statement.setString(2, nombre);
            statement.setString(3, apellidos);
            statement.setInt(4, id_municipio);
            statement.executeUpdate();
            System.out.printf("(+) Student \"%s\"(%d) has been added\n", nombre, nia);
        } catch (SQLException e) {
            System.out.println("(-) Error trying to INSERT a student");
        }
    }

    /**
     * Method used for checking if the DB used is a HSQLDB
     *
     * @param con
     * @return
     */
    public static boolean comprobarHSQLDB(Connection con) {
        String driverName;

        try {
            DatabaseMetaData md = con.getMetaData();
            driverName = md.getDriverName();
            if (driverName.contains("HSQL")) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("(-) Error trying to check DB");
        }

        return false;
    }

    /**
     * Method used for adding a "municipio" only if u are using a HSQLDB 
     *
     * @param con
     */
    public static void introduce_municipio(Connection con) {
        boolean checkDB = false;
        int n_habitantes;
        String nombre, cod;

        checkDB = comprobarHSQLDB(con);
        if (checkDB) {
            System.out.print("Código -> ");
            cod = eb.nextLine();
            System.out.print("Nombre -> ");
            nombre = eb.nextLine();
            System.out.print("Nº Habitantes -> ");
            n_habitantes = fr.nextInt();
            try {
                CallableStatement call = con.prepareCall("call nuevo_municipio(?, ?, ?)");
                call.setString(1, cod);
                call.setString(2, nombre);
                call.setInt(3, n_habitantes);
                call.execute();
                System.out.println("(+) Municipio " + nombre + " has been addded");
            } catch (SQLException e) {
                System.out.println("(-) Error trying to INSERT a \"municipio\"");
            }
        } else {
            System.out.println("(-) This function only works on HSQL");
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        int opc, opc2;

        do {
            opc = menu();
            if (opc != 4) {
                con = connectDb(opc);
                opc2 = menu2();
                switch (opc2) {
                    case 1:
                        mostrar_alumnos(con);
                        break;
                    case 2:
                        mostrar_municipios(con);
                        break;
                    case 3:
                        introduce_alumno(con);
                        break;
                    case 4:
                        introduce_municipio(con);
                        break;
                }
            }
        } while (opc != 4);
        closeDb(con);
        System.out.println("(+) SEE U!");
    }
}
