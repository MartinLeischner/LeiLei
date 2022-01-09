package de.htwberlin.webtech.LeiLei.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
 *
 */
public class LeiLeiFileUtils {

    private LeiLeiFileUtils() {}

    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static File getFile(String downloadDir, String fileName) {
        Path downloadPath = Paths.get(downloadDir);

        if (!Files.exists(downloadPath)) {
            return null;
        }

        Path filePath = downloadPath.resolve(fileName);
        return filePath.toFile();
    }

    public static void removeFileIfExists(String dir, String imageName) {
        File file = getFile(dir, imageName);
        if (file != null) {
            file.delete();
        }
    }

    public static String getFileExtension(String originalFilename) {
        var index = originalFilename.lastIndexOf('.');
        if (index == -1) {
            return "jpg";
        }
        else {
            return originalFilename.substring(index + 1);
        }
    }
}