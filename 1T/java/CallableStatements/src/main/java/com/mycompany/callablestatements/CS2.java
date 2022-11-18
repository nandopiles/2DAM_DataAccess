package com.mycompany.callablestatament;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author 7J
 */
public class CS2 {
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
            String sql = "DROP PROCEDURE changeOffice1 IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE changeOffice1(office_name varchar(20)) "
                    + "modifies sql data "
                    + "UPDATE departaments SET office = office_name;";
            stt.executeUpdate(sql);
            sql = "SELECT * FROM departaments";
            JDBCHelper.showResultSet(stt.executeQuery(sql));
            
            CallableStatement call = con.prepareCall("call changeOffice1(?)");
            call.setString(1, "SASA");
            call.execute();
            JDBCHelper.showResultSet(stt.executeQuery(sql));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
