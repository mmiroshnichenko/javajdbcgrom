package jdbc.lesson3.hw1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public List<Product> findProductsByPrice(int price, int delta) {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, price - delta);
            preparedStatement.setInt(2, price + delta);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> findProductsByName(String word) throws Exception {
        validateWord(word);

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE NAME LIKE ?")) {
            preparedStatement.setString(1, '%' + word + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> findProductsWithEmptyDescription() {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE DESCRIPTION IS NULL OR DESCRIPTION = ''")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    private void validateWord(String word) throws Exception {
        if (word.length() < 3) {
            throw new Exception("Error. Incorrect word '" + word + "'. Must be great that 2 symbols");
        }

        for (Character ch : word.toCharArray()) {
            if (!Character.isLetter(ch)) {
                throw new Exception("Error. Incorrect word '" + word + "'. The word must contain only letters");
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
