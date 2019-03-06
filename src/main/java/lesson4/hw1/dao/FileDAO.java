package lesson4.hw1.dao;

import lesson4.hw1.model.File;
import lesson4.hw1.model.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAO extends BaseDAO<File> {
    StorageDAO storageDAO = new StorageDAO();

    public List<File>getFilesByStorage(Storage storage) throws Exception {
        List<File> files = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?")) {
            preparedStatement.setLong(1, storage.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        storage);
                files.add(file);
            }

            return files;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return files;
    }

    public void updateFilesList(List<File> files) throws SQLException {
        try (Connection connection = getConnection()) {
            updateObjectListInDb(connection, files);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    protected String getTableName() {
        return "FILES";
    }

    @Override
    protected String getObjectName() {
        return "File";
    }

    @Override
    protected File addObjectInDb(Connection connection, File file) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)")) {

            file.setId(getNextId());
            preparedStatement.setLong(1, file.getId());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getFormat());
            preparedStatement.setLong(4, file.getSize());
            if (file.getStorage() != null) {
                preparedStatement.setLong(5, file.getStorage().getId());
            } else {
                preparedStatement.setString(5, null);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error: file: " + file.getName() + "." + file.getFormat() + " has not saved");
        }

        return file;
    }

    @Override
    protected File updateObjectInDb(Connection connection, File file) throws SQLException {
        List<File> files = new ArrayList<>();
        files.add(file);
        updateObjectListInDb(connection, files);

        return file;
    }

    private void updateObjectListInDb(Connection connection, List<File> files) throws SQLException {
        long fileId = 0;
        String fileName = "";
        String fileFormat = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET NAME = ?, FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? WHERE ID = ?")) {
            connection.setAutoCommit(false);

            for (File file : files) {
                fileId = file.getId();
                fileName = file.getName();
                fileFormat = file.getFormat();
                preparedStatement.setString(1, file.getName());
                preparedStatement.setString(2, file.getFormat());
                preparedStatement.setLong(3, file.getSize());
                if (file.getStorage() != null) {
                    preparedStatement.setLong(4, file.getStorage().getId());
                } else {
                    preparedStatement.setString(4, null);
                }
                preparedStatement.setLong(5, file.getId());
                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Error: file(id: " + fileId + "): " + fileName + "." +fileFormat + " has not updated");
        }
    }

    @Override
    protected File mapObjectFromDb(ResultSet resultSet) throws SQLException {
        Storage storage = null;
        if (resultSet.getString(5) != null) {
            storage = storageDAO.findById(resultSet.getLong(5));
        }

        return new File(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getLong(4),
                storage);
    }
}
