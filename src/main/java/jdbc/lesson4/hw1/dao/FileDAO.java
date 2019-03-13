package jdbc.lesson4.hw1.dao;

import jdbc.lesson4.hw1.model.File;
import jdbc.lesson4.hw1.model.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ckzanzkzkznq.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "W1DdNDwypsUyohttCttN";

    StorageDAO storageDAO = new StorageDAO();

    public File save(File file) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)"))
        {
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

            return file;
        } catch (SQLException e) {
            throw new SQLException("Error: file: " + file.getName() + "." + file.getFormat() + " has not saved");
        }
    }

    public File update(File file) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET NAME = ?, FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? WHERE ID = ?"))
        {
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

            return file;
        } catch (SQLException e) {
            throw new SQLException("Error: file: " + file.getName() + "." + file.getFormat() + " has not updated");
        }
    }

    public void delete(long id) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FILES WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error: File(id: " + id + ") has not deleted");
        }
    }

    public File findById(long id) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE ID = ?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new File(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getString(5) != null ? storageDAO.findById(resultSet.getLong(5)) : null);
            }
        } catch (SQLException e) {
            throw new SQLException("Error: File(id: " + id + ") has not found");
        }

        return null;
    }

    public List<File>getFilesByStorage(Storage storage) throws SQLException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?"))
        {
            preparedStatement.setLong(1, storage.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<File> files = new ArrayList<>();
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
            throw new SQLException("Error: Something went wrong. Can't get files by storage(id:" + storage.getId());
        }
    }

    public void updateFilesList(List<File> files) throws SQLException {
        try (Connection connection = getConnection()) {
            updateList(connection, files);
        } catch (SQLException e) {
            throw e;
        }
    }

    private void updateList(Connection connection, List<File> files) throws SQLException {
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

    private long getNextId() throws SQLException {
        long id = 1;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(ID) FROM FILES");
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
