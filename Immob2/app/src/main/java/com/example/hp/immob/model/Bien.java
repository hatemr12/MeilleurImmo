package com.example.hp.immob.model;

/**
 * Created by hp on 03/08/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bien{

    @SerializedName("id_bien")
    @Expose
    private String idBien;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("prix")
    @Expose
    private String prix;
    @SerializedName("titre")
    @Expose
    private String titre;
    @SerializedName("date_ajout")
    @Expose
    private String dateAjout;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("ville")
    @Expose
    private String ville;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
   @SerializedName("nom")
   @Expose
   private String nom;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("tel")
    @Expose
    private String tel;

    public String getIdBien() {
        return idBien;
    }

    public String getUsername() {
        return username;
    }

    public String getPrix() {
        return prix;
    }

    public String getTitre() {
        return titre;
    }

    public String getDateAjout() {
        return dateAjout;
    }

    public String getRegion() {
        return region;
    }

    public String getVille() {
        return ville;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}