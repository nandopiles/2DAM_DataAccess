package com.mycompany.callablestatament;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Nando
 */
public class JDBCHelper {

    /**
     * Method used for printing a RS
     *
     * @param res RS that has to be printed
     */
    public static void showResultSet(ResultSet res) {
        try {
            ResultSetMetaData metaData = res.getMetaData();
            int colNum = metaData.getColumnCount();
            System.out.println("======================================");
            String aux = "";
            while (res.next()) {
                aux += "-->";
                for (int i = 1; i <= colNum; i++) { //from 1 to <= columnNum
                    aux += metaData.getColumnName(i) + ": "
                            + res.getString(i) + "; ";
                }
                aux += "\n";
            }
            System.out.println(aux + "======================================");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Method used for knowing if a table exists into a DB
     *
     * @param con The connection to the DB
     * @param tableName Name of the table that has to be founded
     * @return If exists
     */
    public static boolean containsTable(Connection con, String tableName) {
        ResultSet rs = null;

        try {
            rs = con.getMetaData().getTables(null, "APP", tableName.toUpperCase(), null);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}
