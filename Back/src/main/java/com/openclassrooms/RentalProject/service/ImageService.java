package com.openclassrooms.RentalProject.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	
    // Save image in a local directory
    public String saveImage(MultipartFile imageFile) throws IOException {
    	// TODO
    	return "";
    }
}