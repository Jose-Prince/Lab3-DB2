package com.labdb.app;

import java.time.LocalDateTime;

public class Person {
    public String Name;
    public int TMDBId;
    public LocalDateTime Born;
    public LocalDateTime Died;
    public String BornIn;
    public String Url;
    public String IMDBId;
    public String Bio;
    public String Poster;

    public Person(String name, int tmdbId, LocalDateTime born, LocalDateTime died, String bornIn, String url, String imdbId, String bio, String poster) {
        Name = name;
        TMDBId = tmdbId;
        Born = born;
        Died = died;
        BornIn = bornIn;
        Url = url;
        IMDBId = imdbId;
        Bio = bio;
        Poster = poster;
    }
}