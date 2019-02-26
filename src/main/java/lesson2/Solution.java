package lesson2;

import java.sql.*;
import java.text.BreakIterator;
import java.util.ArrayList;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public static void main(String[] args) {
        /*saveProduct();
        deleteProducts();
        deleteProductsByPrice();*/

        /*System.out.println(getAllProducts());
        System.out.println(getProductsByPrice());
        System.out.println(getProductsByDescription());*/

        increasePrice();
        changeDescription();
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

    public static ArrayList<Product> getAllProducts() {
        return getListProductsFromDB("SELECT * FROM PRODUCT");
    }

    public static ArrayList<Product> getProductsByPrice() {
        return getListProductsFromDB("SELECT * FROM PRODUCT WHERE PRICE <= 100");
    }

    public static ArrayList<Product> getProductsByDescription() {
        return getListProductsFromDB("SELECT * FROM PRODUCT WHERE LENGTH(DESCRIPTION) > 50");
    }

    public static void increasePrice() {
        executeSql("UPDATE PRODUCT SET PRICE = PRICE + 100 WHERE PRICE < 970");
    }

    public static void changeDescription() {
        ArrayList<Product> products = getListProductsFromDB("SELECT * FROM PRODUCT WHERE LENGTH(DESCRIPTION) > 100");

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement("UPDATE PRODUCT SET DESCRIPTION = ? WHERE ID = ?")) {
            for (Product product : products) {
                ArrayList<String> sentences = splitTextIntoSentences(product.getDescription());
                sentences.remove(sentences.size() - 1);
                String newText = String.join("", sentences);

                statement.clearParameters();
                statement.setString(1, newText);
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static ArrayList<String> splitTextIntoSentences(String text) {
        ArrayList<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentences.add(text.substring(start,end));
        }

        return sentences;
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
