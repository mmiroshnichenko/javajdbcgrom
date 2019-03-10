package jdbc.lesson2.hw1;

import java.sql.*;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public static void main(String[] args) {
        saveProduct();
        deleteProducts();
        deleteProductsByPrice();
    }

    public static void saveProduct() {
        executeSql("INSERT INTO PRODUCT VALUES(999, 'toy', 'for children', 60)");
    }

    public static void deleteProducts() {
        executeSql("DELETE FROM PRODUCT WHERE NAME != 'toy'");
    }

    public static void deleteProductsByPrice() {
        executeSql("DELETE FROM PRODUCT WHERE PRICE < 100");
    }

    private static void executeSql(String sql) {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

}
