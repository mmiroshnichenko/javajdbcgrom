package jdbc.lesson4.lesson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDemo {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public static void save(List<Product> products) {
        try(Connection connection = getConnection()) {
            saveList(products, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void saveList(List<Product> products, Connection connection) throws SQLException {
        long productId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            connection.setAutoCommit(false);
            for (Product product : products) {
                productId = product.getId();
                preparedStatement.setLong(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getDescription());
                preparedStatement.setInt(4, product.getPrice());
                int result = preparedStatement.executeUpdate();

                System.out.println("Save was finished with result " + result);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Error: product(id: " + productId + ") has not saved");
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
