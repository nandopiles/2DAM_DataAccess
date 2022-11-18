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
public class CS4 {
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
            String sql = "DROP PROCEDURE nextDeptNum IF EXISTS";
            stt.executeUpdate(sql);
            sql = "CREATE PROCEDURE nextDeptNum(OUT n INT) "
                    + "reads sql data "
                    + "BEGIN ATOMIC "
                    + "SET n = 1+(SELECT max(id) from departaments);"
                    + "END;";
            stt.executeUpdate(sql);        
            CallableStatement call = con.prepareCall("call nextDeptNum(?)");
            call.registerOutParameter(1, Types.INTEGER);
            call.execute();
            System.out.println("Next Dept Num: " + call.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
