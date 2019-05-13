package com.example.hp.immob.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("id_img")
    @Expose
    private String idImg;

    public String getIdImg() {
        return idImg;
    }

    public String getImg() {
        return image;
    }

}