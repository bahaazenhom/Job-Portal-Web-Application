package com.project.jobportal.util;

import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    /**
     * Saves a file to the specified directory.
     *
     * @param uploadDir     The directory where the file will be saved.
     * @param fileName      The name of the file to be saved.
     * @param multipartFile The multipart file to be saved.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return;
        }
        // Convert the upload directory to a Path object
        Path uploadPath = Paths.get(uploadDir);

        // Create the directory if it does not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Get the input stream of the multipart file
        InputStream inputStream = multipartFile.getInputStream();

        // Resolve the file path
        Path filePath = uploadPath.resolve(fileName);

        // Copy the file to the target location, replacing any existing file
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
