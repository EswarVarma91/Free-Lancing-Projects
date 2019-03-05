package iaf.breakupstories;

import android.content.Context;

public class ModelClass {
    String name;
    String id;
    String email;
    String mobile;
    String title;
    String story;
    String date;

    public ModelClass(String id,String name, String email, String mobile, String title, String story, String date) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.title = title;
        this.story = story;
        this.date = date;
    }

    public ModelClass(String id, String name, String title, String story) {

        this.name = name;
        this.id = id;
        this.title = title;
        this.story = story;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }


}
