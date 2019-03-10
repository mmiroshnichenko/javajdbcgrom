package jdbc.lesson2.hw3;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public static void main(String[] args) {
        increasePrice();
        changeDescription();
    }

    public static void increasePrice() {
        executeSql("UPDATE PRODUCT SET PRICE = PRICE + 100 WHERE PRICE < 970");
    }

    public static void changeDescription() {
        ArrayList<Product> products = getListProductsFromDB("SELECT * FROM PRODUCT WHERE LENGTH(DESCRIPTION) > 100");

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement("UPDATE PRODUCT SET DESCRIPTION = ? WHERE ID = ?")) {
            for (Product product : products) {
                statement.clearParameters();
                statement.setString(1, cutLastSentence(product.getDescription()));
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static String cutLastSentence(String text) {
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(text.lastIndexOf('.', text.length() - 2));
        indexes.add(text.lastIndexOf('?', text.length() - 2));
        indexes.add(text.lastIndexOf('!', text.length() - 2));
        int indexLastSentence = Collections.max(indexes);

        return indexLastSentence == -1 ? "" : text.substring(0, indexLastSentence + 1);
    }

    private static void executeSql(String sql) {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static ArrayList<Product> getListProductsFromDB(String sql) {
        ArrayList<Product> products = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            statement.execute(sql);

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String productDescription = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    products.add(new Product(id, productName, productDescription, price));
                }
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return products;
    }

}
