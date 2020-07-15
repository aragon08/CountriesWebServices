package com.example.orion.countrieswebservice;

/**
 * Created by Orion on 25/03/2017.
 */

public class Country {
    String name;
    String region;
    String capital;
    String population;
    String url_flag;

    public Country(String name, String region, String capital, String population, String url_flag) {
        this.name = name;
        this.region = region;
        this.capital = capital;
        this.population = population;
        this.url_flag = url_flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getUrl_flag() {
        return url_flag;
    }

    public void setUrl_flag(String url_flag) {
        this.url_flag = url_flag;
    }
}
