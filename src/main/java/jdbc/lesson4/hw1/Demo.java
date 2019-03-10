package jdbc.lesson4.hw1;

import jdbc.lesson4.hw1.controller.Controller;
import jdbc.lesson4.hw1.model.File;
import jdbc.lesson4.hw1.model.Storage;
import jdbc.lesson4.hw1.service.Service;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        String[] formatsSupported1 = {"txt", "pdf", "doc", "jpg", "png"};
        String[] formatsSupported2 = {"jpg", "png", "psd", "txt", "pdf"};

        Storage storage1 = new Storage(formatsSupported1, "USA", 100000);
        Storage storage2 = new Storage(formatsSupported2, "UK", 120000);

        try {
            Controller.saveStorage(storage1);
            Controller.saveStorage(storage2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        File fileTxt1 = new File("txt1", "txt", 12000, null);
        File fileTxt2 = new File("txt2", "txt", 12000, null);
        File fileTxt3 = new File("txt3", "txt", 1200, null);
        File fileTxt4 = new File("txt4", "txt", 12000000, null);

        File filePdf1 = new File("pdf1", "pdf", 12000, null);
        File filePdf2 = new File("pdf2", "pdf", 1200, null);
        File filePdf3 = new File("pdf3", "pdf", 1200, null);
        File filePdf4 = new File("pdf4", "pdf", 1200, null);

        File fileDoc1 = new File("doc1", "doc", 12000, null);
        File fileDoc2 = new File("doc2", "doc", 1200, null);
        File fileDoc3 = new File("doc3", "doc", 1200, null);
        File fileDoc4 = new File("doc4", "doc", 1200, null);

        File fileJpg1 = new File("jpg1", "jpg", 1200, null);
        File fileJpg2 = new File("jpg2", "jpg", 1200, null);
        File fileJpg3 = new File("jpg3", "jpg", 1200, null);
        File fileJpg4 = new File("jpg4", "jpg", 1200, null);

        File filePng1 = new File("png1", "png", 1200, null);
        File filePng2 = new File("png2", "png", 1200, null);
        File filePng3 = new File("png3", "png", 1200, null);
        File filePng4 = new File("png4", "png", 70000, null);

        File filePsd1 = new File("psd1", "psd", 1200, null);
        File filePsd2 = new File("psd2", "psd", 1200, null);
        File filePsd3 = new File("psd3", "psd", 1200, null);
        File filePsd4 = new File("psd4", "psd", 60000, null);

        try {
            Service.saveFile(fileTxt1);
            Service.saveFile(fileTxt2);
            Service.saveFile(fileTxt3);
            Service.saveFile(fileTxt4);

            Service.saveFile(filePdf1);
            Service.saveFile(filePdf2);
            Service.saveFile(filePdf3);
            Service.saveFile(filePdf4);

            Service.saveFile(fileDoc1);
            Service.saveFile(fileDoc2);
            Service.saveFile(fileDoc3);
            Service.saveFile(fileDoc4);

            Service.saveFile(fileJpg1);
            Service.saveFile(fileJpg2);
            Service.saveFile(fileJpg3);
            Service.saveFile(fileJpg4);

            Service.saveFile(filePng1);
            Service.saveFile(filePng2);
            Service.saveFile(filePng3);
            Service.saveFile(filePng4);

            Service.saveFile(filePsd1);
            Service.saveFile(filePsd2);
            Service.saveFile(filePsd3);
            Service.saveFile(filePsd4);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("put correct file in storage");
        try {
            Controller.put(storage1, fileTxt1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);

        System.out.println("put file with incorrect format");
        try {
            Controller.put(storage1, filePsd1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);

        System.out.println("put too big file");
        try {
            Controller.put(storage1, fileTxt4);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);

        System.out.println("put all correct files");
        try {
            List<File> files1 = new ArrayList<>();
            files1.add(fileTxt2);
            files1.add(fileTxt3);
            files1.add(filePdf1);
            files1.add(filePdf2);
            files1.add(fileDoc1);
            files1.add(fileDoc2);

            Controller.putAll(storage1, files1);

            List<File> files2 = new ArrayList<>();
            files2.add(fileJpg1);
            files2.add(fileJpg2);
            files2.add(filePng1);
            files2.add(filePng2);
            files2.add(filePsd1);
            files2.add(filePsd2);

            Controller.putAll(storage2, files2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);
        showFilesInStorage(storage2);

        System.out.println("put list of files with incorrect format");
        try {
            List<File> files3 = new ArrayList<>();
            files3.add(filePdf3);
            files3.add(filePsd3);

            Controller.putAll(storage1, files3);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);

        System.out.println("put list of files with too big size");
        try {
            List<File> files4 = new ArrayList<>();
            files4.add(filePng4);
            files4.add(filePsd4);

            Controller.putAll(storage2, files4);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage2);

        System.out.println("transfer correct file");
        try {
            Controller.transferFile(storage1, storage2, fileTxt1.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);
        showFilesInStorage(storage2);

        System.out.println("transfer file is not in storage");
        try {
            Controller.transferFile(storage1, storage2, filePng1.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);
        showFilesInStorage(storage2);

        System.out.println("transfer file incorrect format");
        try {
            Controller.transferFile(storage1, storage2, fileDoc1.getId());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);
        showFilesInStorage(storage2);

        System.out.println("transfer all files between storages. Incorrect format");
        try {
            Controller.transferAll(storage1, storage2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);
        showFilesInStorage(storage2);

        System.out.println("transfer all files between storages. Correct.");
        try {
            Controller.delete(storage1, fileDoc1);
            Controller.delete(storage1, fileDoc2);
            Controller.transferAll(storage1, storage2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage1);
        showFilesInStorage(storage2);

        System.out.println("delete file from storage");
        try {
            Controller.delete(storage2, filePsd1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage2);

        System.out.println("delete file is not exist in storage");
        try {
            Controller.delete(storage2, fileTxt4);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        showFilesInStorage(storage2);


    }

    private static void showFilesInStorage(Storage storage) {
        try {
            System.out.println(Controller.getFilesByStorage(storage));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
