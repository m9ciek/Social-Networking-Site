package com.maciek.socialnetworkingsite.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String store(MultipartFile file); //returns url to file
    void deleteAll();
    boolean checkIfFileExists(String filename); //returns true if filename exists
    String rename(String filename); //adds random number at the end
}
