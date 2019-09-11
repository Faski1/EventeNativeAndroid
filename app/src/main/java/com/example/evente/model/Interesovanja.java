package com.example.evente.model;

public class Interesovanja {
private int EventId;
private int KorisnikId;
private boolean Idem;
private boolean Zainteresovan;

    public int getEventId() {
        return EventId;
    }

    public void setEventId(int eventId) {
        EventId = eventId;
    }

    public int getKorisnikId() {
        return KorisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        KorisnikId = korisnikId;
    }

    public boolean isIdem() {
        return Idem;
    }

    public void setIdem(boolean idem) {
        Idem = idem;
    }

    public boolean isZainteresovan() {
        return Zainteresovan;
    }

    public void setZainteresovan(boolean zainteresovan) {
        Zainteresovan = zainteresovan;
    }
}
