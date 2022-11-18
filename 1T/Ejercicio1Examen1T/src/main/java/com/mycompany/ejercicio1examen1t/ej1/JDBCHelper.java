package com.mycompany.ejercicio1examen1t;

//@author 7J
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
            System.out.println("==================================");
            String aux = "";
            while (res.next()) {
                aux += "--> ";
                for (int i = 1; i <= colNum; i++) { //from 1 to <= columnNum
                    aux += metaData.getColumnName(i) + ": "
                            + res.getString(i) + "; ";
                }
                aux += "\n";
            }
            System.out.println(aux + "==================================");
        } catch (SQLException e1) {
            System.out.println("(-) Error trying to show the Result");
        }
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
            showResultSet(re);
        } catch (SQLException e1) {
            System.out.println("(-) Error doing a SELECT");
        }
    }
}
