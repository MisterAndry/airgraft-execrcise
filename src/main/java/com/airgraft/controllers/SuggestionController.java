package com.airgraft.controllers;

import com.airgraft.dto.Suggestion;
import com.airgraft.dto.SuggestionsResponse;
import com.airgraft.service.SuggestionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestionController {

    private final SuggestionsService suggestionsService;

    public SuggestionController(SuggestionsService suggestionsService) {
        this.suggestionsService = suggestionsService;
    }

    @GetMapping(value = "/suggestions")
    public ResponseEntity<SuggestionsResponse> suggestion(@RequestParam(value = "cityName") String cityName,
                                                          @RequestParam(value = "latitude", required = false) Double latitude,
                                                          @RequestParam(value = "longitude", required = false) Double longitude) {

        List<Suggestion> cities = suggestionsService.getSuggestions(cityName, latitude, longitude);
        SuggestionsResponse suggestionsResponse = new SuggestionsResponse(cities);

        return ResponseEntity.ok(suggestionsResponse);
    }
}
