package com.example.evente.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Eventi {
    public Eventi(Eventi primljeni)
    {
        EventId = primljeni.getEventId();
        Naziv = primljeni.getNaziv();
        Opis = primljeni.getOpis();
        ObjekatOdrzavanja = primljeni.getObjekatOdrzavanja();
        Kategorija = primljeni.getKategorija();
        Grad = primljeni.getGrad();
        Adresa = primljeni.getAdresa();
        Status = primljeni.isStatus();
        ImgUrl = primljeni.getImgUrl();
        DatumOdrzavanja = primljeni.getDatumOdrzavanja();
    }
    @SerializedName("EventId")
    private int EventId;
    @SerializedName("Naziv")
    private String Naziv;
    @SerializedName("Opis")
    private String Opis;
    @SerializedName("ObjekatOdrzavanja")
    private String ObjekatOdrzavanja;
    @SerializedName("Kategorija")
    private String Kategorija;
    @SerializedName("Grad")
    private String Grad;
    @SerializedName("Drzava")
    private String Drzava;
    @SerializedName("Adresa")
    private String Adresa;
    @SerializedName("DatumOdrzavanja")
    private Date DatumOdrzavanja;
    @SerializedName("Status")
    private boolean Status;
    @SerializedName("ImgUrl")
    private String ImgUrl;

    public int getEventId() {
        return EventId;
    }

    public void setEventId(int eventId) {
        EventId = eventId;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public String getObjekatOdrzavanja() {
        return ObjekatOdrzavanja;
    }

    public void setObjekatOdrzavanja(String objekatOdrzavanja) {
        ObjekatOdrzavanja = objekatOdrzavanja;
    }




    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public Date getDatumOdrzavanja() {
        return DatumOdrzavanja;
    }

    public void setDatumOdrzavanja(Date datumOdrzavanja) {
        DatumOdrzavanja = datumOdrzavanja;
    }




    public String getGrad() {
        return Grad;
    }

    public void setGrad(String grad) {
        Grad = grad;
    }

    public String getDrzava() {
        return Drzava;
    }

    public void setDrzava(String drzava) {
        Drzava = drzava;
    }

    public String getKategorija() {
        return Kategorija;
    }

    public void setKategorija(String kategorija) {
        Kategorija = kategorija;
    }
}
