package com.openclassrooms.RentalProject.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Service handling images with Amazon S3.
 */
@Service
public class ImageService {

	@Value("${aws.s3.bucket}")
	private String bucketName;

	private AmazonS3 s3client;

	public ImageService(AmazonS3 s3client) {
		this.s3client = s3client;
	}
	
	/**
	 * Upload a file on AmazonS3 server.
	 * 
	 * @param file file to upload.
	 * @return the URL where is stored the uploaded image.
	 * @throws IOException if the file can't be read.
	 */
	public String uploadFile(MultipartFile file) throws IOException {
		String key = UUID.randomUUID().toString() + "_" + file.getName();
		s3client.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), null)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		return s3client.getUrl(bucketName, key).toString();
	}
}