package jdbc.lesson4.hw1.dao;

import jdbc.lesson4.hw1.model.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageDAO extends BaseDAO<Storage> {
    @Override
    protected String getTableName() {
        return "STORAGE";
    }

    @Override
    protected String getObjectName() {
        return "Storage";
    }

    @Override
    protected Storage addObjectInDb(Connection connection, Storage storage) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGE VALUES (?, ?, ?, ?)")) {

            storage.setId(getNextId());
            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, String.join(",", storage.getFormatsSupported()));
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageMaxSize());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error: new storage has not saved");
        }

        return storage;
    }

    @Override
    protected Storage updateObjectInDb(Connection connection, Storage storage) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGE SET FORMATS_SUPPORTED = ?, STORAGE_COUNTRY = ?, STORAGE_MAX_SIZE = ? WHERE ID = ?")) {
            preparedStatement.setString(1, String.join(",", storage.getFormatsSupported()));
            preparedStatement.setString(2, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageMaxSize());
            preparedStatement.setLong(4, storage.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error: storage(id: " + storage.getId() + ") has not updated");
        }

        return storage;
    }

    protected Storage mapObjectFromDb(ResultSet resultSet) throws SQLException {
        return new Storage(
                resultSet.getLong(1),
                resultSet.getString(2).split(","),
                resultSet.getString(3),
                resultSet.getLong(4));
    }
}
