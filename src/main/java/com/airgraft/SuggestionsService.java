package com.airgraft;

import com.airgraft.models.Suggestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuggestionsService {


    // заглугшка
    public List<Suggestion> getSuggestions(String cityName, Double latitude, Double longitude) {

        Suggestion suggestion1 = new Suggestion();
        suggestion1.setCityName("Mos");
        suggestion1.setLatitude(345.345);
        suggestion1.setLongitude(23.564);
        suggestion1.setScore(0.4);

        Suggestion suggestion2 = new Suggestion();
        suggestion2.setCityName("Spb");
        suggestion2.setLatitude(1.234);
        suggestion2.setLongitude(-56.435);
        suggestion2.setScore(1.0);

        ArrayList<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(suggestion1);
        suggestions.add(suggestion2);
        return suggestions;
    }


}
