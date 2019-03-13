package jdbc.lesson4.hw1.dao;

import jdbc.lesson4.hw1.model.Storage;

import java.sql.*;

public class StorageDAO{
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    public Storage save(Storage storage) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGE VALUES (?, ?, ?, ?)"))
        {
            storage.setId(getNextId());
            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, String.join(",", storage.getFormatsSupported()));
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageMaxSize());

            preparedStatement.executeUpdate();

            return storage;
        } catch (SQLException e) {
            throw new SQLException("Error: new storage has not saved");
        }
    }

    public Storage update(Storage storage) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGE SET FORMATS_SUPPORTED = ?, STORAGE_COUNTRY = ?, STORAGE_MAX_SIZE = ? WHERE ID = ?"))
        {
            preparedStatement.setString(1, String.join(",", storage.getFormatsSupported()));
            preparedStatement.setString(2, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageMaxSize());
            preparedStatement.setLong(4, storage.getId());

            return storage;
        } catch (SQLException e) {
            throw new SQLException("Error: storage(id: " + storage.getId() + ") has not updated");
        }
    }

    public void delete(long id) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM STORAGE WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error: Storage(id: " + id + ") has not deleted");
        }
    }

    public Storage findById(long id) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STORAGE WHERE ID = ?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Storage(
                        resultSet.getLong(1),
                        resultSet.getString(2).split(","),
                        resultSet.getString(3),
                        resultSet.getLong(4));
            }
        } catch (SQLException e) {
            throw new SQLException("Error: Storage(id: " + id + ") has not found");
        }

        return null;
    }
    private long getNextId() throws SQLException {
        long id = 1;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(ID) FROM STORAGE");
            while (resultSet.next()) {
                id += resultSet.getLong(1);
            }

        } catch (SQLException e) {
            throw e;
        }

        return id;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
