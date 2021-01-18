package ru.sapteh;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input name: ");
        String path = reader.readLine();
        Path sourcePath = Paths.get(path );

        //Проход по дереву файлов
        MyVisitClass myVisitClass = new MyVisitClass();
        Files.walkFileTree(sourcePath, myVisitClass);

        //Создание архива
        FileOutputStream zipArchive = new FileOutputStream(sourcePath.toString() + ".zip");
        ZipOutputStream zip = new ZipOutputStream(zipArchive);
        ZipEntry ze;

        for(File filePath : myVisitClass.getFileList()) {
            if(filePath.isDirectory()){
                ze = new ZipEntry(filePath + "/");
                zip.putNextEntry(ze);
                zip.closeEntry();
            } else if(filePath.isFile()){
                ze = new ZipEntry(filePath.toString());
                zip.putNextEntry(ze);
                Files.copy(filePath.toPath(),zip);
                zip.closeEntry();
                System.out.printf("%-35s %-5d (%-4d) %.0f%%\n", filePath, ze.getSize(), ze.getCompressedSize(),
                        (100 - ((double)ze.getCompressedSize()/ ze.getSize()*100)));
            }
        }


        zip.close();




    }
}
