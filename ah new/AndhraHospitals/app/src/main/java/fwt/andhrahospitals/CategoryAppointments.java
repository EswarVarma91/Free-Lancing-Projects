package fwt.andhrahospitals;

import android.graphics.Bitmap;

class CategoryAppointments {
    String name;
    String cat_id;
    String images;





    public CategoryAppointments(String cat_id, String name) {
        this.cat_id=cat_id;
        this.name = name;
        this.images=images;

    }

    public String getCat_id() {
        return cat_id;
    }

    public String getName() {
        return name;
    }
    public String getImages() {
        return images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}