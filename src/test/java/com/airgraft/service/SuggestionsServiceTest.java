package com.airgraft.service;

import com.airgraft.cache.CitiesCache;
import com.airgraft.dto.Suggestion;
import com.airgraft.models.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SuggestionsServiceTest {

    private static final CitiesCache citiesCache = mock(CitiesCache.class);
    private static final SuggestionsService suggestionsService = spy(new SuggestionsService(citiesCache));
    private final City boston = new City("Boston", "US", 31.0, -86.0);

    @Test
    @DisplayName("getSuggestions() if latitude and longitude are present")
    public void getSuggestionsTest() {
        HashMap<Character, List<City>> cache = initCache();
        when(citiesCache.getCache()).thenReturn(cache);
        List<Suggestion> suggestions = suggestionsService.getSuggestions("lOnDo", 43.70011, -79.4163);

        assertEquals(3, suggestions.size());
        assertEquals("London, CA", suggestions.get(0).getName());
        assertEquals(0.9, suggestions.get(0).getScore());
        assertEquals("London, US", suggestions.get(1).getName());
        assertEquals(0.6, suggestions.get(1).getScore());
        assertEquals("Londontowne, US", suggestions.get(2).getName());
        assertEquals(0.4, suggestions.get(2).getScore());

    }

    @Test
    @DisplayName("getSuggestions() if latitude and longitude are not present")
    public void getSuggestionsOnlyCityNameTest() {
        HashMap<Character, List<City>> cache = initCache();
        when(citiesCache.getCache()).thenReturn(cache);
        List<Suggestion> suggestions = suggestionsService.getSuggestions("Londo", null, null);

        assertEquals(3, suggestions.size());
        assertEquals("London, CA", suggestions.get(0).getName());
        assertEquals(0.9, suggestions.get(0).getScore());
        assertEquals("London, US", suggestions.get(1).getName());
        assertEquals(0.9, suggestions.get(1).getScore());
        assertEquals("Londontowne, US", suggestions.get(2).getName());
        assertEquals(0.4, suggestions.get(2).getScore());
    }

    @Test
    @DisplayName("getSuggestions() if the input city does not exist")
    public void getSuggestionsNotExistedCityTest() {
        HashMap<Character, List<City>> cache = initCache();
        when(citiesCache.getCache()).thenReturn(cache);
        List<Suggestion> suggestions = suggestionsService.getSuggestions("FictionalCity", 0.0, 0.0);
        assertEquals(0, suggestions.size());
    }

    @Test
    @DisplayName("getScoreTest() if latitude and longitude are not present")
    public void getScoreOnlyNameTest() {
        doReturn(0.5).when(suggestionsService).distanceScore(any(), any(), any());
        double score = suggestionsService.getScore("Bost", null, null, boston);
        assertEquals(0.8, score);
    }

    @Test
    @DisplayName("getScoreTest() if latitude and longitude are present")
    public void getScoreTest() {
        doReturn(0.5).when(suggestionsService).distanceScore(any(), any(), any());
        double score = suggestionsService.getScore("Bos", 1.0, 1.0, boston);
        assertEquals(0.6, score);
    }

    @Test
    public void distanceScoreTest() {
        doReturn(500.0).when(suggestionsService).distance(any(), any(), any());
        double distanceScore = suggestionsService.distanceScore(1.0, 1.0, boston);
        assertEquals(0.5, distanceScore);
    }

    @Test
    public void distanceTest() {
        double distance = suggestionsService.distance(boston, 33.0, -86.0);
        assertEquals(222.38985328911158, distance);
    }

    private HashMap<Character, List<City>> initCache() {
        HashMap<Character, List<City>> cache = new HashMap<>();
        List<City> lCities = new ArrayList<>();
        lCities.add(new City("London", "CA", 42.98339, -81.23304));
        lCities.add(new City("London", "US", 39.88645, -83.44825));
        lCities.add(new City("Londontowne", "US", 38.93345, -76.54941));

        cache.put('L', lCities);
        return cache;
    }
}