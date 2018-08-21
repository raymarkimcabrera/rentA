package com.skuld.user.rent_a.model.autocomplete;

import java.util.List;

public class Location{
	private List<SuggestionsItem> suggestions;

	public void setSuggestions(List<SuggestionsItem> suggestions){
		this.suggestions = suggestions;
	}

	public List<SuggestionsItem> getSuggestions(){
		return suggestions;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"suggestions = '" + suggestions + '\'' + 
			"}";
		}
}