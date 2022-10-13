package com.example.tourin.Model;

public class Editor {
    String name, imageUrl, PlaceId;

    public Editor(String name, String imageUrl, String placeId) {
        this.name = name;
        this.imageUrl = imageUrl;
        PlaceId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }
}
