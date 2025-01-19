package navigator.domain;

import struct.impl.ArrayList;

public class Route {
    private String id;
    private double distance;
    private int popularity;
    private boolean isFavorite;
    private ArrayList<String> locationPoints;

    public Route(String id, double distance, int popularity, boolean isFavorite, ArrayList<String> locationPoints) {
        this.id = id;
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
    }

    public Route(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPopularity() {
        return popularity;
    }
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public ArrayList<String> getLocationPoints() {
        return locationPoints;
    }
    public void setLocationPoints(ArrayList<String> locationPoints) {
        this.locationPoints = locationPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return distance == route.distance && locationPoints.getFirst().equals(route.locationPoints.getFirst()) && locationPoints.getLast().equals(route.locationPoints.getLast());
    }

    @Override
    public int hashCode() {
        int hash = 11;
        int prime = 37;

        for (int i = 0; i < id.length(); i++) {
            hash = hash * prime + id.charAt(i);
        }

        return Math.abs(hash);
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", Расстояние: " + distance +
                ", Популярность: " + popularity +
                ", Избранное: " + (isFavorite ? "да" : "нет") +
                ", Пункты: [" + String.join(", ", locationPoints) +
                ']';
    }
}
