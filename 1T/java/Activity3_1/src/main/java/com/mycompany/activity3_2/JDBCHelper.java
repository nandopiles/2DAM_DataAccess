package com.mycompany.act3_2;

//@author 7J
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

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
                aux += "-->";
                for (int i = 1; i <= colNum; i++) { //from 1 to <= columnNum
                    aux += metaData.getColumnName(i) + ": "
                            + res.getString(i) + "; ";
                }
                aux += "\n";
            }
            System.out.println(aux + "----------------------------------");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Method used for knowing if the Table exists
     *
     * @param con Connexion to the DB
     * @param tableName Name of the table
     * @param column Name of the column that has to be founded
     * @return If the table exists or not
     */
    public static boolean containsColumn(Connection con, String tableName, String column) {
        ResultSet col = null;
        DatabaseMetaData dbmd = null;

        try {
            dbmd = con.getMetaData();
            col = dbmd.getColumns(null, null, tableName, column);
            while (col.next()) {
                return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * Method used for knowing if a table exists into a DB
     *
     * @param con The connection to the DB
     * @param tableName Name of the table that has to be founded
     * @return If exists or not
     */
    public static boolean containsTable(Connection con, String tableName) {
        ResultSet rs = null;

        try {
            rs = con.getMetaData().getTables(null, null, tableName, null);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * Method used for Adding an Item into the selected Table founding their
     * Data_Type to know if the data added is correct or not
     *
     * @param con Connection to the DB
     * @param table Name of the table where we have to Add the Item
     */
    public static void addItemToTable(Connection con, String table) {
        ResultSet rs = null;
        DatabaseMetaData dbmd = null;
        Scanner eb = new Scanner(System.in);
        Scanner fr = new Scanner(System.in);
        ArrayList<String> data = new ArrayList<>();
        String var, sql = "";
        int affectedRows = 0;
        boolean emailCheck = true, dateCheck = true, intCheck = true;

        try {
            dbmd = con.getMetaData();
            rs = dbmd.getColumns(null, null, table, null);
            //introduce the data
            while (rs.next()) {
                String nombreCol = rs.getString("COLUMN_NAME");
                var type = rs.getString("TYPE_NAME");
                //->(chapuza)
                if(type.length()>7 && type.substring(0, 7).equalsIgnoreCase("VARCHAR"))
                    type="VARCHAR";
                System.out.print(nombreCol + ": ");
                switch (type) {
                    case "INT":
                        do {
                            var = fr.nextLine();
                            try {
                                Integer.valueOf(var);
                                intCheck = false;
                                data.add(var);
                            } catch (NumberFormatException e) {
                                System.out.printf("(-) Incorrect Format...\n%s: ", nombreCol);
                            }
                        } while (intCheck);
                        break;
                    case "VARCHAR":
                        if (nombreCol.equalsIgnoreCase("email")) {
                            do {
                                var = eb.nextLine();
                                emailCheck = Email.checkEmail(var);
                                if (emailCheck == true) {
                                    System.out.print(nombreCol + ": ");
                                } else {
                                    data.add(var);
                                }
                            } while (emailCheck);
                        } else {
                            var = eb.nextLine();
                            data.add(var);
                        }
                        break;
                    case "DATE":
                        do {
                            var = eb.nextLine();
                            try {
                                java.sql.Date.valueOf(var);
                                dateCheck = false;
                            } catch (Exception e) {
                                System.out.println("(-)Invalid Format\n-> (yyyy-MM-dd)");
                            }
                            if (dateCheck == true) {
                                System.out.print(nombreCol + ": ");
                            } else {
                                data.add(var);
                            }
                        } while (dateCheck);
                        break;
                }

            }

            Statement stt = con.createStatement();
            //do the respective Insert
            if (table.equalsIgnoreCase("teachers")) {
                if (JDBCHelper.containsColumn(con, "teachers", "salary")) {
                    sql = String.format("INSERT INTO teachers values(%d, '%s', '%s', '%s', '%s', %d, %d);", Integer.valueOf(data.get(0)), data.get(1),
                            data.get(2), data.get(3), data.get(4), Integer.parseInt(data.get(5)), Integer.valueOf(data.get(6)));
                } else {
                    sql = String.format("INSERT INTO teachers values(%d, '%s', '%s', '%s', '%s', %d);", Integer.valueOf(data.get(0)), data.get(1),
                            data.get(2), data.get(3), data.get(4), Integer.parseInt(data.get(5)));
                }
            } else if (table.equalsIgnoreCase("departments")) {
                sql = String.format("INSERT INTO departments values(%d, '%s', '%s');", Integer.valueOf(data.get(0)), data.get(1), data.get(2));
            }
            affectedRows = stt.executeUpdate(sql);
            System.out.println("(+)Affected Rows: " + affectedRows);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
