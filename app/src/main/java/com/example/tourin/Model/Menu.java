package com.example.tourin.Model;

public class Menu {
    String region, name, imageUrl, PlaceId, description;

    public Menu(String region, String name, String imageUrl, String placeId, String description) {
        this.region = region;
        this.name = name;
        this.imageUrl = imageUrl;
        PlaceId = placeId;
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
