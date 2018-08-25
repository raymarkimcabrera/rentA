package com.skuld.user.rent_a.views;

import com.skuld.user.rent_a.model.autocomplete.SuggestionsItem;

import java.util.List;

public interface SuggestionsView {

    void onSuggestionSuccess(List<SuggestionsItem> suggestionsItems);
}
