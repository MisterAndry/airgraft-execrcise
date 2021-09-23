package com.airgraft.cache;

import com.airgraft.models.City;
import com.airgraft.utils.FileReader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CitiesCache {

    private final FileReader fileReader;
    private final Map<Character, List<City>> cache = new HashMap<>();

    public CitiesCache(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @PostConstruct
    public void warmCache() {
        List<City> cities = fileReader.readAllCities();
        for (City city : cities) {
            char firstChar = Character.toUpperCase(city.getName().charAt(0));
            if (cache.containsKey(firstChar)) {
                cache.get(firstChar).add(city);
            } else {
                List<City> list = new ArrayList<>();
                list.add(city);
                cache.put(firstChar, list);
            }
        }
    }

    public Map<Character, List<City>> getCache() {
        return cache;
    }

}
