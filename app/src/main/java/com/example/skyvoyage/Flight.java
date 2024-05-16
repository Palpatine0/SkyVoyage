package com.example.skyvoyage;

public class Flight {
    private String time;
    private String duration;
    private String route;
    private String price;
    private String airlineName;
    private int airlineLogo;
    private int count;

    public Flight(String time, String duration, String route, String price, String airlineName, int airlineLogo, int count) {
        this.time = time;
        this.duration = duration;
        this.route = route;
        this.price = price;
        this.airlineName = airlineName;
        this.airlineLogo = airlineLogo;
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public int getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(int airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
