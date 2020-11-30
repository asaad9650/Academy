package com.example.academy;

public class VideoDetails
{
    public String video_title , video_description , course , date_time ;

    public VideoDetails(String video_title, String video_description, String course,  String date_time , String video_link) {
        this.video_title = video_title;
        this.video_description = video_description;
        this.course = course;
        this.date_time = date_time;
    }

    public VideoDetails()
    {
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
