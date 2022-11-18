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
public class Ej3CS {

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
            String sql = "DROP PROCEDURE deptId IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE nameVeteran(OUT veteran VARCHAR(20)) "
                    + "reads sql data "
                    + "BEGIN ATOMIC "
                    + "SET veteran = SELECT TOP 1 name from Teachers "
                    + "WHERE start_date IS NOT NULL "
                    + "ORDER BY start_date ASC;"
                    + "END;";
            stt.executeUpdate(sql);
            CallableStatement call = con.prepareCall("call nameVeteran(?)");
            call.registerOutParameter(1, Types.VARCHAR);
            call.execute();
            System.out.println("Nombre del más veteran@ -> "+ call.getString(1));
        } catch (SQLException e) {
            System.out.println("(-) SQL Error...");
        }
    }
}
