package com.airgraft.cache;

import com.airgraft.models.City;
import com.airgraft.utils.FileReader;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CitiesCacheTest {

    @Test
    public void warmUpTest() {
        List<City> cities = Arrays.asList(
                new City("Montreal", "CA", 45.0, -73.0),
                new City("Boston", "US", 31.0, -86.0)
        );
        FileReader fileReader = mock(FileReader.class);
        when(fileReader.readAllCities()).thenReturn(cities);
        CitiesCache citiesCache = new CitiesCache(fileReader);
        citiesCache.warmCache();

        HashMap<Character, List<City>> expectedCache = new HashMap<>();
        expectedCache.put('M', Collections.singletonList(new City("Montreal", "CA", 45.0, -73.0)));
        expectedCache.put('B', Collections.singletonList(new City("Boston", "US", 31.0, -86.0)));

        assertEquals(expectedCache, citiesCache.getCache());
    }

}