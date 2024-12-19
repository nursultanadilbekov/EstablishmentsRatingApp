package com.example.rating.model;

public class Favourite {
    private int userId;
    private int establishmentId;
    private boolean favourite;

    public Favourite(int userId, int establishmentId) {
        this.userId = userId;
        this.establishmentId = establishmentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getEstablishmentId() {
        return establishmentId;
    }
    public boolean isFavourite() {
        return favourite;

    }

    public void setFavourite(boolean b) {
    }
}
