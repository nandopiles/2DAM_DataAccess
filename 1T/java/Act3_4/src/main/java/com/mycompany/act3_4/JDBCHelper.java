package com.mycompany.act3_4;

//@author 7J
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
}
