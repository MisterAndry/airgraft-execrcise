package com.airgraft.controllers;

import com.airgraft.models.Suggestion;
import com.airgraft.SuggestionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SuggestionController {

    private final SuggestionsService suggestionsService;

    public SuggestionController(SuggestionsService suggestionsService) {
        this.suggestionsService = suggestionsService;
    }

    @GetMapping(value = "/suggestions")
    public ResponseEntity<List<Suggestion>> suggestion(@RequestParam(value = "cityName") String cityName,
                                                       @RequestParam(value = "latitude", required = false) Double latitude,
                                                       @RequestParam(value = "longitude", required = false) Double longitude) {

        System.out.println("cityName = " + cityName + ", latitude = " + latitude + ", longitude = " + longitude);
        List<Suggestion> suggestions = suggestionsService.getSuggestions(cityName, latitude, longitude);

        return ResponseEntity.ok(suggestions);
    }
}
