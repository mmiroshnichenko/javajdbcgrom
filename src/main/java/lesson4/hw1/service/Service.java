package lesson4.hw1.service;

import lesson4.hw1.dao.FileDAO;
import lesson4.hw1.dao.StorageDAO;
import lesson4.hw1.model.File;
import lesson4.hw1.model.Storage;

import java.util.List;

public class Service {
    private static FileDAO fileDAO = new FileDAO();
    private static StorageDAO storageDAO = new StorageDAO();

    public static File put(Storage storage, File file) throws Exception {
        validateNewFile(storage, file);

        file.setStorage(storage);
        return fileDAO.update(file);
    }

    public static void putAll(Storage storage, List<File> files) throws Exception {
        validateFilesList(storage, files);

        for (File file : files) {
            file.setStorage(storage);
        }
        fileDAO.updateFilesList(files);
    }

    public static void delete(Storage storage, File file) throws Exception {
        if (!storage.equals(file.getStorage())) {
            throw new Exception("Error: Storage(id:" + storage.getId() + ") does not contain file(id:" + file.getId() + ")");
        }

        file.setStorage(null);
        fileDAO.update(file);
    }

    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        putAll(storageTo, getFilesByStorage(storageFrom));
    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        File file = fileDAO.findById(id);
        if (!storageFrom.equals(file.getStorage())) {
            throw new Exception("Error: Storage(id:" + storageFrom.getId() + ") does not contain file(id:" + file.getId() + ")");
        }

        put(storageTo, file);
    }

    public static List<File> getFilesByStorage(Storage storage) throws Exception {
        return fileDAO.getFilesByStorage(storage);
    }

    public static File saveFile(File file) throws Exception {
        return fileDAO.save(file);
    }

    public static Storage saveStorage(Storage storage) throws Exception {
        return storageDAO.save(storage);
    }

    private static void validateFilesList(Storage storage, List<File> files) throws Exception {
        long filesSize = 0;
        for (File file : files) {
            if (!storage.isSupportedFormat(file.getFormat())) {
                throw new Exception("Error: File(id: " + file.getId() + ") has not supported format '" + file.getFormat()
                        + " in Storage(id: " + storage.getId() + ")");
            }

            filesSize += file.getSize();
        }

        if (getFreeStorageSpace(storage) < filesSize) {
            throw new Exception("Error: not enough space in Storage(id: " + storage.getId() + ") for new files");
        }
    }

    private static void validateNewFile(Storage storage, File file) throws Exception {
        if (!storage.isSupportedFormat(file.getFormat())) {
            throw new Exception("Error: File(id: " + file.getId() + ") has not supported format '" + file.getFormat()
                    + " in Storage(id: " + storage.getId() + ")");
        }

        if (getFreeStorageSpace(storage) < file.getSize()) {
            throw new Exception("Error: not enough space in Storage(id: " + storage.getId() + ") for File(id: " + file.getId() + ")");
        }
    }

    private static long getFreeStorageSpace(Storage storage) throws Exception {
        long usedSpace = 0;
        for (File file : getFilesByStorage(storage)) {
            usedSpace += file.getSize();
        }

        return storage.getStorageMaxSize() - usedSpace;
    }
}
