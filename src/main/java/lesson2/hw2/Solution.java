package lesson2.hw2;

import lesson2.hw3.Product;

import java.sql.*;
import java.util.ArrayList;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public static void main(String[] args) {
        System.out.println(getAllProducts());
        System.out.println(getProductsByPrice());
        System.out.println(getProductsByDescription());
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
