package com.labdb.app;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDate;
import java.util.List;

public class Movie {
    public String Title;
    public int tmdbld;
    public LocalDate release;
    public float IMDRating;
    public int MovieId;
    public int Year;
    public int imdbid;
    public int runtime;
    public List<String> countries;
    public int imdbVotes;
    public String url;
    public int revenue;
    public String Plot;
    public String poster;
    public int budget;
    public List<String> languages;

    // Constructor completo
    public Movie(String title, int tmdbId, LocalDate release, float imdbRating, int movieId, int year, 
                 int imdbId, int runtime, List<String> countries, int imdbVotes, String url, 
                 int revenue, String plot, String poster, int budget, List<String> languages) {
        this.Title = title;
        this.tmdbld = tmdbId;
        this.release = release;
        this.IMDRating = imdbRating;
        this.MovieId = movieId;
        this.Year = year;
        this.imdbid = imdbId;
        this.runtime = runtime;
        this.countries = countries;
        this.imdbVotes = imdbVotes;
        this.url = url;
        this.revenue = revenue;
        this.Plot = plot;
        this.poster = poster;
        this.budget = budget;
        this.languages = languages;
    }
}

