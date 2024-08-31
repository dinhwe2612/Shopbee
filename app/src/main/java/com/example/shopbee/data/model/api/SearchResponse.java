package com.example.shopbee.data.model.api;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Search {
        String search;
        Long timestamp;
        public Search(String search, Long timestamp) {
            this.search = search;
            this.timestamp = timestamp;
        }
        public String getSearch() {
            return search;
        }
        public void setSearch(String search) {
            this.search = search;
        }
        public Long getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
    public void addSearch(Search search) {
        searches.add(search);
    }
    private List<Search> searches;
    public SearchResponse() {
        searches = new ArrayList<>();
    }
    public SearchResponse(List<Search> searches, String email) {
        this.searches = searches;
        this.email = email;
    }
    public List<String> getHistorySearch() {
        List<String> historySearch = new ArrayList<>();
        for (Search search : searches) {
            historySearch.add(search.getSearch());
        }
        return historySearch;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

}
