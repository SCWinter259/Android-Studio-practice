package com.example.listycity;

/**
 * This class creates an object of city with two variables: city {@link String} and province {@link String}
 *
 * @author SCWinter259
 * @version 1.0.0
 * @see CityList
 * @see Comparable
 */
public class City implements Comparable{
    private String city;
    private String province;

    /**
     * This method is a constructor for the class City
     * @param city {@link String}
     * @param province {@link String}
     * @since 1.0.0
     */
    City(String city, String province){
        this.city = city;
        this.province = province;
    }

    /**
     * This method gets the city name from the class City
     * @return city name
     * @since 1.0.0
     */
    String getCityName(){
        return this.city;
    }

    /**
     * This method gets the province name from the class City
     * @return province name
     * @since 1.0.0
     */
    String getProvinceName(){
        return this.province;
    }

    /**
     * Given an Object o, returns the comparison result of the city name and city name of o Object
     * @param o {@link Object}
     * @return an int as the result of lexical comparison between 2 city names. 0 for equal, less than 0 for Object city name being larger,
     * more than 0 otherwise
     * @since 1.0.0
     */
    @Override
    public int compareTo(Object o) {
        City city = (City) o;
        return this.city.compareTo(city.getCityName());
    }
}