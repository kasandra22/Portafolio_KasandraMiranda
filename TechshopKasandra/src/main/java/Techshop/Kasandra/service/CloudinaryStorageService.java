/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Techshop.Kasandra.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author HP
 */
@Service
public class CloudinaryStorageService {

    @Value("${cloudinary.folder}")
    private String baseFolder;

    private final Cloudinary cloudinary;

    public CloudinaryStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile localFile, String folder, Integer id) throws IOException {
        String originalName = localFile.getOriginalFilename();
        String fileExtension = "";
        if (originalName != null && originalName.contains(".")) {
            fileExtension = originalName.substring(originalName.lastIndexOf("."));
        }

        String fileName = "img" + getFormattedNumber(id) + fileExtension;

        File tempFile = convertToFile(localFile);

        try {
            return uploadToCloudinary(tempFile, folder, fileName);
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("upload-", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    @SuppressWarnings("unchecked")
    private String uploadToCloudinary(File file, String folder, String fileName) throws IOException {
        String publicId = baseFolder + "/" + folder + "/" + fileName.substring(0, fileName.lastIndexOf("."));

        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );

        Map result = cloudinary.uploader().upload(file, options);

        return (String) result.get("secure_url");
    }

    public void deleteImage(String folder, Integer id) throws IOException {
        String publicId = baseFolder + "/" + folder + "/img" + getFormattedNumber(id);
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    private String getFormattedNumber(long id) {
        return String.format("%014d", id);
    }
}