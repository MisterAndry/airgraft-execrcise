package com.airgraft.service;

import com.airgraft.cache.CitiesCache;
import com.airgraft.dto.Suggestion;
import com.airgraft.models.City;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.*;

@Component
public class SuggestionsService {

    private final CitiesCache citiesCache;

    public SuggestionsService(CitiesCache citiesCache) {
        this.citiesCache = citiesCache;
    }

    public List<Suggestion> getSuggestions(String cityName, Double latitude, Double longitude) {
        Map<Character, List<City>> map = citiesCache.getCache();
        List<City> cities = map.getOrDefault(Character.toUpperCase(cityName.charAt(0)), new ArrayList<>());
        return cities.stream()
                .filter(city -> city.getName().toLowerCase().startsWith(cityName.toLowerCase()))
                .map(city -> new Suggestion(city.getFullName(),
                        city.getLatitude(),
                        city.getLongitude(),
                        getScore(cityName, latitude, longitude, city)))
                .sorted(Comparator.comparing(Suggestion::getScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * @return suggestion score based on name and distance
     * name scare and distance score have weights 0.4 and 0.6, respectively
     */
    public double getScore(String cityName, Double latitude, Double longitude, City city) {
        int inputNameLength = cityName.length();
        int diffNameLengths = city.getName().length() - inputNameLength;
        double score = max(1 - diffNameLengths * 0.1, 0);
        if (latitude != null && longitude != null) {
            double distanceScore = distanceScore(latitude, longitude, city);
            score = 0.4 * score + 0.6 * distanceScore;
        }
        score = (double) Math.round(score * 10) / 10;
        return score;
    }

    /**
     * @return suggestion score based only on distance. If distance more than 1000 km => score = 0
     */
    public double distanceScore(Double latitude, Double longitude, City city) {
        double distance = distance(city, latitude, longitude);
        double score = 0;
        int distanceThreshold = 1000;
        if (distance < distanceThreshold) {
            score = 1 - distance / distanceThreshold;
        }
        return score;
    }

    /**
     * @return distance (km) between city and input latitude and longitude
     */
    public double distance(City city, Double latitude, Double longitude) {
        int earthRadius = 6371;
        double latRad1 = latitude * PI / 180;
        double latRad2 = city.getLatitude() * PI / 180;
        double longRad1 = longitude * PI / 180;
        double longRad2 = city.getLongitude() * PI / 180;
        double centralAngle = acos(sin(latRad1) * sin(latRad2) + cos(latRad1) * cos(latRad2) * cos(longRad1 - longRad2));
        return centralAngle * earthRadius;
    }
}
