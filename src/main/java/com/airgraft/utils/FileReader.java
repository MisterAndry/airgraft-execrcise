package com.airgraft.utils;

import com.airgraft.models.City;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileReader {

    private final static String CANADA_CITIES = "CA.txt";
    private final static String USA_CITIES = "US.txt";

    public List<City> readAllCities() {
        List<City> cities = readCities(CANADA_CITIES);
        cities.addAll(readCities(USA_CITIES));
        return cities;
    }

    public List<City> readCities(String fileName) {
        List<City> cities = new ArrayList<>();
        try {
            URL resource = getClass().getClassLoader().getResource(fileName);
            Path file = new File(resource.toURI()).toPath();
            cities = Files.lines(file)
                    .map(line -> {
                        String[] split = line.split("\t");
                        double longitude = Double.parseDouble(split[5]);
                        double latitude = Double.parseDouble(split[4]);
                        return new City(split[1], split[8], latitude, longitude);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }
}
