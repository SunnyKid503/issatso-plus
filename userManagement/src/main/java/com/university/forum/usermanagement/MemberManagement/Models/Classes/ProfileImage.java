package com.university.forum.usermanagement.MemberManagement.Models.Classes;

public class ProfileImage {
    private String imageUrl;
    private String publicId;

    public ProfileImage(String imageUrl, String publicId) {
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public ProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
