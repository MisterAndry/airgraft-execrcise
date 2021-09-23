package com.airgraft.utils;

import com.airgraft.models.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FileReaderTest {

    FileReader fileReader = new FileReader();

    @Test
    public void readCitiesTest() {
        List<City> actualList = fileReader.readAllCities();
        List<City> expectedList = Arrays.asList(
                new City("Montreal", "CA", 45.0, -73.0),
                new City("Boston", "US", 31.0, -86.0)
        );
        Assertions.assertArrayEquals(expectedList.toArray(), actualList.toArray());
    }
}