package lesson4.hw1.dao;

import lesson4.hw1.model.BaseModel;

import java.sql.*;
import java.util.List;

public abstract class BaseDAO<T extends BaseModel> {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    protected abstract String getTableName();
    protected abstract String getObjectName();
    protected abstract T addObjectInDb(Connection connection, T object) throws SQLException;
    protected abstract T updateObjectInDb(Connection connection, T object) throws SQLException;
    protected abstract T mapObjectFromDb(ResultSet resultSet) throws SQLException;

    public T save(T object) throws SQLException {
        T savedObject;
        try (Connection connection = getConnection()) {
            savedObject = addObjectInDb(connection, object);
        } catch (SQLException e) {
            throw e;
        }

        return savedObject;
    }

    public T update(T object) throws SQLException {
        try (Connection connection = getConnection()) {
            updateObjectInDb(connection, object);
        } catch (SQLException e) {
            throw e;
        }

        return object;
    }

    public void delete(long id) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ? WHERE ID = ?")) {
            preparedStatement.setString(1, getTableName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error: " + getObjectName() + "(id: " + id + ") has not deleted");
        }
    }

    public T findById(long id) throws SQLException {
        T object = null;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE ID = ?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                object = mapObjectFromDb(resultSet);
                break;
            }
        } catch (SQLException e) {
            throw new SQLException("Error: " + getObjectName() + "(id: " + id + ") has not found");
        }

        return object;
    }

    protected long getNextId() throws SQLException {
        long id = 1;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(ID) FROM " + getTableName());
            while (resultSet.next()) {
                id += resultSet.getLong(1);
            }

        } catch (SQLException e) {
            throw e;
        }

        return id;
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
