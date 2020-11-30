package com.example.academy;

public class Subscribe
{
   String uploader_name, user_name;

    public Subscribe()
    {
    }

    public Subscribe(String uploader_name, String user_name) {
        this.uploader_name = uploader_name;
        this.user_name = user_name;
    }



    public String getUploader_name() {
        return uploader_name;
    }

    public void setUploader_name(String uploader_name) {
        this.uploader_name = uploader_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    @Override
    public String toString() {
        return "Subscribe{" +
                "uploader_name='" + uploader_name + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
