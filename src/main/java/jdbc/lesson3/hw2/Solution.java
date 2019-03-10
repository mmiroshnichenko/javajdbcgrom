package jdbc.lesson3.hw2;

import java.sql.*;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    //Save performance: 141430
    public static Long testSavePerformance() {
        long start = System.currentTimeMillis();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)")) {

            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setLong(1, i);
                preparedStatement.setString(2, "test" + i);
                preparedStatement.setInt(3, 200 + i);

                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }
            long finish = System.currentTimeMillis();
            return finish - start;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    //Delete by Id performance: 139712
    public static Long testDeleteByIdPerformance() {
        long start = System.currentTimeMillis();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE ID = ?")) {

            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setLong(1, i);

                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }

            long finish = System.currentTimeMillis();
            return finish - start;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    //Delete performance: 1547
    public static Long testDeletePerformance() {
        long start = System.currentTimeMillis();

        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM TEST_SPEED");

            long finish = System.currentTimeMillis();
            return finish - start;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    //Select by Id performance: 141255
    public static Long testSelectByIdPerformance() {
        long start = System.currentTimeMillis();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TEST_SPEED WHERE ID = ?")) {

            ResultSet resultSet;
            for (int i = 1; i <= 1000; i++) {
                preparedStatement.setLong(1, i);

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultSet.getLong(1);
                    resultSet.getString(2);
                    resultSet.getInt(3);
                }
                preparedStatement.clearParameters();
            }

            long finish = System.currentTimeMillis();
            return finish - start;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    //Select performance: 16446
    public static Long testSelectPerformance() {
        long start = System.currentTimeMillis();

        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST_SPEED");

            while (resultSet.next()) {
                resultSet.getLong(1);
                resultSet.getString(2);
                resultSet.getInt(3);
            }

            long finish = System.currentTimeMillis();
            return finish - start;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }



    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
