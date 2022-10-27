package com.example.listycity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is a class that keeps track of a list of city objects using the list "cities" {@link ArrayList}
 *
 * @author SCWinter259
 * @version 1.0.0
 * @see City
 */
public class CityList {
    /**
     * The list "cities" keeps City objects {@link ArrayList}
     * @since 1.0.0
     */
    private List<City> cities = new ArrayList<>();

    /**
     * This adds a city to the list if the city does not exist
     * @param city {@link City}
     * @since 1.0.0
     */
    public void add(City city) {
        if (cities.contains(city)) {
            throw new IllegalArgumentException();
        }
        cities.add(city);
    }

    /**
     * This returns a sorted list of cities
     * @return sorted list of cities
     * @since 1.0.0
     */
    public List getCities() {
        List list = cities;
        Collections.sort(list);
        return list;
    }

    /**
     * Given a city, return whether or not it belongs to the list
     * @param city {@link City}
     * @return true if city in in cities
     * @since 1.0.0
     */
    public boolean hasCity(City city) {
        return this.cities.contains(city);
    }

    /**
     * Checks if the city is present in the list. If it does, remove it from the list. Otherwise throw an exception.
     * @param city {@link City}
     * @throws NoSuchElementException if city is not in the list
     * @since 1.0.0
     */
    public void delete(City city) throws Exception {
        if(this.hasCity(city)) {
            this.cities.remove(city);
        }
        else {
            throw new NoSuchElementException("city is not in the city list");
        }
    }

    /**
     * Get the number of cities in the city list
     * @return the number of cities in the city list
     */
    public int countCities() {
        return this.cities.size();
    }
}