package com.example.listycity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class CityListTest {
    // creating a mock city list and a mock city
    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }
    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    @Test
    void testAdd() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.getCities().size());
        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);
        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains(city));
    }

    @Test
    void testAddException() {
        CityList cityList = mockCityList();
        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);
        assertThrows( IllegalArgumentException.class, () -> {
            cityList.add(city); });
    }

    @Test
    void testGetCities() {
        CityList cityList = mockCityList();
        assertEquals(0,
                mockCity().compareTo(cityList.getCities().get(0)));
        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);
        assertEquals(0, city.compareTo(cityList.getCities().get(0)));
        assertEquals(0,
                mockCity().compareTo(cityList.getCities().get(1)));
    }

    @Test
    void testHasCities() {
        CityList cityList = mockCityList();
        assertTrue(cityList.hasCity((City) cityList.getCities().get(0)));
    }

    @Test
    void testDelete() throws Exception {
        CityList cityList = mockCityList();
        City city = (City) cityList.getCities().get(0);
        cityList.delete(city);
        assertEquals(0, cityList.getCities().size());
        assertThrows(NoSuchElementException.class, () -> {cityList.delete(city);});
    }

    @Test
    void testCountCities() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.countCities());
    }
}