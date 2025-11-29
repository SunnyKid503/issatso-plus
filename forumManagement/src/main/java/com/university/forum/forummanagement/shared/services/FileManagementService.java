package com.university.forum.forummanagement.shared.services;

import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileManagementService {
    private final boolean throwOnFileUploadErrors = true;
    private final CloudinaryService cloudinaryService;
    private final String fileUploadPath = "posts/";

    public FileManagementService(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    public List<FileReference> uploadAll(MultipartFile[] files) throws FileUploadException {
        if(files == null)
            return null;

        try {
            List<FileReference> uploadedImages = new ArrayList<>();
            for(MultipartFile image : files){
                uploadedImages.add(
                        cloudinaryService.uploadFileToFolder(image,
                                fileUploadPath + UUID.randomUUID())
                );
            }
            return uploadedImages;
        } catch (IOException e) {
            if(throwOnFileUploadErrors)
                throw new FileUploadException("File upload error.", e);
            return null;
        }
    }

    public FileReference upload(MultipartFile file) throws FileUploadException {
        if(file == null)
            return null;

        try {
            return cloudinaryService.uploadFileToFolder(file,
                            fileUploadPath + UUID.randomUUID());
        } catch (IOException e) {
            if(throwOnFileUploadErrors)
                throw new FileUploadException("File upload error.", e);
            return null;
        }
    }

    public boolean delete(FileReference file) throws FileUploadException {
        if(file == null)
            return false;

        try {
            return cloudinaryService.deleteFile(file.getPublicId());
        } catch (IOException e) {
            if(throwOnFileUploadErrors)
                throw new FileUploadException("Could not delete file.", e);
            return false;
        }
    }

    public int deleteAll(List<FileReference> files) throws FileUploadException {
        if(files == null)
            return 0;
        int i = 0;

        try {
            for(i = 0; i < files.size(); i++) {
                cloudinaryService.deleteFile(files.get(i).getPublicId());
                i++;
            }
            return i;
        } catch (IOException e) {
            if(throwOnFileUploadErrors)
                throw new FileUploadException(
                        String.format("Could not delete file with index %d.", i),
                        e);
            return i;
        }
    }

}
