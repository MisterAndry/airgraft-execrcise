package com.airgraft.dto;

import java.util.List;

public class SuggestionsResponse {

    private List<Suggestion> suggestions;

    public SuggestionsResponse(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
