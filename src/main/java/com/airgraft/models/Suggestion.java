package com.airgraft.models;

public class Suggestion {

    private String cityName;
    private Double latitude;
    private Double longitude;
    private Double score;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Response{" +
                "cityName='" + cityName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", score=" + score +
                '}';
    }
}
