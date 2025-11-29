package com.university.forum.usermanagement.Shared.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.university.forum.usermanagement.MemberManagement.Models.Classes.ProfileImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CloudinaryService {


    private final String cloudinaryMainPath="ISSATSo/";

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public ProfileImage uploadFileToFolder(MultipartFile file, String folderPath) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new IOException("File has no extension");
        }

        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS").format(new Date());

        // Set public_id without folder path
        String public_id = timestamp; // Use timestamp only as unique identifier

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", cloudinaryMainPath + folderPath, // Define folder separately
                "public_id", public_id,                   // Avoid duplicating folder path here
                "resource_type", isVideoFile(extension) ? "video" : "image"
        );

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        String returnedPublicId = uploadResult.get("public_id").toString();
        String secureUrl = uploadResult.get("secure_url").toString();
        return new ProfileImage(secureUrl,returnedPublicId);
    }

    private boolean isVideoFile(String extension) {
        String[] videoExtensions = {"mp4", "avi", "mov", "flv", "mkv", "wmv"};
        for (String ext : videoExtensions) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    public String deleteFile(String publicId) throws IOException {
        Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return deleteResult.get("result").toString(); // "ok" indicates a successful deletion
    }


}
