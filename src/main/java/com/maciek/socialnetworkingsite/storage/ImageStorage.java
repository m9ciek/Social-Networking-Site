package com.maciek.socialnetworkingsite.storage;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class ImageStorage implements StorageService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        if (file == null) {
            return null;
        }

        String filename = file.getOriginalFilename();
        String fileType = file.getContentType();

        if (file.isEmpty() || filename.contains("..")) {
            throw new RuntimeException("Failed to store empty or incorrect file " + filename);
        }

        while (checkIfFileExists(filename)) {
            filename = rename(filename);
        }

        if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
            try {
                Path fullPath = Paths.get(rootLocation.toString(), filename);
                Files.write(fullPath, file.getBytes());
                return fullPath.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to store an image ", e);
            }
        }
        return null;
    }

    @Override
    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfFileExists(String filename) {
        Path path = Paths.get(rootLocation.toString(), filename);
        return Files.exists(path);
    }

    @Override
    public String rename(String filename) {
        String[] splittedString = filename.split("\\.");
        Random r = new Random();
        int randomNumber = r.nextInt(10);
        return splittedString[0] + randomNumber + "." + splittedString[1];
    }
}
