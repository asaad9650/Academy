package com.example.academy;

public class Post
{
    String course_name, data_time, uploader_name, video_description, video_link, video_title;

    public Post(String course_name, String data_time, String uploader_name, String video_description, String video_link, String video_title) {
        this.course_name = course_name;
        this.data_time = data_time;
        this.uploader_name = uploader_name;
        this.video_description = video_description;
        this.video_link = video_link;
        this.video_title = video_title;
    }

    public Post()
    {
    }

    public String toString()
    {
        return "videos{" +
                ", course_name='" + course_name + '\'' +
                ", data_time='" + data_time +'\''+
                ", uploader_name='"+ uploader_name + '\''+
                ", video_description='"+ video_description+'\''+
                ", video_link='"+ video_link+'\''+
                ", video_title='"+ video_title+'\''+
                '}';
    }

    public String getCourse_name()
    {
        return course_name;
    }

    public void setCourse_name(String course_name)
    {
        this.course_name = course_name;
    }

    public String getData_time()
    {
        return data_time;
    }

    public void setData_time(String data_time)
    {
        this.data_time = data_time;
    }

    public String getUploader_name()
    {
        return uploader_name;
    }

    public void setUploader_name(String uploader_name)
    {
        this.uploader_name = uploader_name;
    }

    public String getVideo_description()
    {
        return video_description;
    }

    public void setVideo_description(String video_description)
    {
        this.video_description = video_description;
    }

    public String getVideo_link()
    {
        return video_link;
    }

    public void setVideo_link(String video_link)
    {
        this.video_link = video_link;
    }

    public String getVideo_title()
    {
        return video_title;
    }

    public void setVideo_title(String video_title)
    {
        this.video_title = video_title;
    }
}