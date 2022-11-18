package com.mycompany.callablestatements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author 7J
 */
public class Ej1CS {

    public static void infoDB(Connection con) {
        try {
            DatabaseMetaData md = con.getMetaData();
            System.out.println("DB Product Name: " + md.getDatabaseProductName());
            System.out.println("Driver Name: " + md.getDriverName());
            System.out.println("Driver Version: " + md.getDriverVersion());
            System.out.println("URL: " + md.getURL());
            System.out.println("User Name: " + md.getUserName());
            System.out.println("------------------------------------");
        } catch (SQLException e) {
            System.out.println("(-) SQL Error...");
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:hsqldb:.\\db\\ej2.3.db");
            infoDB(con);
            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE contDept IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE contDept(OUT cont INT) "
                    + "reads sql data "
                    + "BEGIN ATOMIC "
                    + "SET cont = SELECT count(*) from Departaments;"
                    + "END;";
            stt.executeUpdate(sql);
            CallableStatement call = con.prepareCall("call contDept(?)");
            call.registerOutParameter(1, Types.INTEGER);
            call.execute();
            System.out.println("Numumero Total Depts: " + call.getInt(1));
        } catch (SQLException e) {
            System.out.println("(-) SQL Error...");
        }
    }
}
