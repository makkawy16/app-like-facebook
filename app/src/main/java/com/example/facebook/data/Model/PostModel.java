package com.example.facebook.data.Model;

public class PostModel {

    String userId , userName , userProfilePic , postTitle , uploadedPhotoURl;

    public PostModel(String userId, String userName, String userProfilePic, String postTitle, String uploadedPhotoURl) {
        this.userId = userId;
        this.userName = userName;
        this.userProfilePic = userProfilePic;
        this.postTitle = postTitle;
        this.uploadedPhotoURl = uploadedPhotoURl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getUploadedPhotoURl() {
        return uploadedPhotoURl;
    }

    public void setUploadedPhotoURl(String uploadedPhotoURl) {
        this.uploadedPhotoURl = uploadedPhotoURl;
    }
}
