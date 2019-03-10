package jdbc.lesson3.lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public Product save(Product product) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getPrice());
            int result = preparedStatement.executeUpdate();

            System.out.println("Save was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return product;
    }

    public Product update(Product product) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, PRICE = ? WHERE ID = ?")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setLong(4, product.getId());
            int result = preparedStatement.executeUpdate();
            System.out.println("Update was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> getProducts() {
        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
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

    public void delete(long id) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCT WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            System.out.println("Delete was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
