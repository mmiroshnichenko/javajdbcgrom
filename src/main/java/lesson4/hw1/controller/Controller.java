package lesson4.hw1.controller;

import lesson4.hw1.model.File;
import lesson4.hw1.model.Storage;
import lesson4.hw1.service.Service;

import java.util.List;

public class Controller {
    public static File put(Storage storage, File file) throws Exception {
        return Service.put(storage, file);
    }

    public static void putAll(Storage storage, List<File> files) throws Exception {
        Service.putAll(storage, files);
    }

    public static void delete(Storage storage, File file) throws Exception {
        Service.delete(storage, file);
    }

    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        Service.transferAll(storageFrom, storageTo);
    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception
    {
        Service.transferFile(storageFrom, storageTo, id);
    }

    public static File saveFile(File file) throws Exception {
        return Service.saveFile(file);
    }

    public static Storage saveStorage(Storage storage) throws Exception {
        return Service.saveStorage(storage);
    }

    public static List<File> getFilesByStorage(Storage storage) throws Exception {
        return Service.getFilesByStorage(storage);
    }
}
