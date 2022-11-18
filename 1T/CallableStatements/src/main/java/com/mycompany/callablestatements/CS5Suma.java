package com.mycompany.callablestatament;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author 7J
 */
public class CS5Suma {

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:hsqldb:.\\db\\ej2.3.db");

            DatabaseMetaData md = con.getMetaData();
            System.out.println("DB Product Name: " + md.getDatabaseProductName());
            System.out.println("Driver Name: " + md.getDriverName());
            System.out.println("Driver Version: " + md.getDriverVersion());
            System.out.println("URL: " + md.getURL());
            System.out.println("User Name: " + md.getUserName());
            System.out.println("------------------------------------");

            Statement stt = con.createStatement();
            String sql = "DROP PROCEDURE suma IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE suma(IN a INT, IN b INT, OUT res INT) "
                    + "BEGIN ATOMIC "
                    + "SET res = a+b;"
                    + "END;";
            stt.executeUpdate(sql);
            CallableStatement call = con.prepareCall("call suma(?,?,?)");
            call.setInt(1, 8);
            call.setInt(2, 3);
            call.registerOutParameter(3, Types.INTEGER);
            call.execute();
            System.out.println("proc: " + call.getInt(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
