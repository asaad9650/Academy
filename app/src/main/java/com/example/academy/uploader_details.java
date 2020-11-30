package com.example.academy;

public class uploader_details
{
    String uploader_name, uploader_email , uploader_password;

    public uploader_details(String uploader_name, String uploader_email, String uploader_password) {
        this.uploader_name = uploader_name;
        this.uploader_email = uploader_email;
        this.uploader_password = uploader_password;
    }

    public uploader_details(){}

    public String getUploader_name() {
        return uploader_name;
    }

    public void setUploader_name(String uploader_name) {
        this.uploader_name = uploader_name;
    }

    public String getUploader_email() {
        return uploader_email;
    }

    public void setUploader_email(String uploader_email) {
        this.uploader_email = uploader_email;
    }

    public String getUploader_password() {
        return uploader_password;
    }

    public void setUploader_password(String uploader_password) {
        this.uploader_password = uploader_password;
    }
}
