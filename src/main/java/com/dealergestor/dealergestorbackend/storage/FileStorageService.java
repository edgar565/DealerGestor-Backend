package com.dealergestor.dealergestorbackend.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    private final String STORAGE_PATH = "/dealergestor/company-logos/";

    public String storeFile(MultipartFile file, String subfolder) {
        try {
            String folderPath = STORAGE_PATH + subfolder;
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filename = file.getOriginalFilename();
            String fullPath = folderPath + "/" + filename;

            File destinationFile = new File(fullPath);
            file.transferTo(destinationFile);

            return "/company-logos/" + subfolder + "/" + filename; // ruta relativa para usarla como URL
        } catch (IOException e) {
            throw new RuntimeException("Error storing file", e);
        }
    }
}